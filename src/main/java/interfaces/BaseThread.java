package interfaces;

import com.google.gson.GsonBuilder;
import com.winone.ftc.mcore.imps.ManagerImp;
import com.winone.ftc.mentity.mbean.entity.Task;
import com.winone.ftc.mentity.mbean.entity.TaskFactory;
import com.winone.ftc.mtools.FileUtil;
import lunch.Say;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by user on 2017/8/7.
 */
public abstract class BaseThread extends Thread implements MonitorAction {
    //超时时间
    public int TIMEOUT = 10 * 1000;
    protected String homeDir;
    //标识
    protected String KEY;
    //资源主目录路径
    protected String RES_HOME_PATH;
    //json主目录路径
    protected String JSON_HOME_PATH;
    //json文件名
    protected String JSON_FILE_NAME;
    //资源文件下载链接文件
    protected String RES_URL_DOWNLOAD_FILE;
    //重新尝试次数
    private int reMackCount = 0;
    //时间
    private long sTime = System.currentTimeMillis();

    private final ReentrantLock lock = new ReentrantLock();
    protected final ParamManager paramManager;
    private final DownloadMonitor monitor;
    private final ArrayList<StoreTextBean> recodeTextList = new ArrayList<>();
    private void addTextBean(StoreTextBean textBean){
        recodeTextList.add(textBean);
    }
    private void clearTextBean(){
        recodeTextList.clear();
    }
    private void recodeTextBean(){
        Iterator<StoreTextBean> iterator = recodeTextList.iterator();
        while (iterator.hasNext()){
            iterator.next().store();//存文本数据
            iterator.remove();
        }
    }
    private void deleteRecodeTextBean(){
        Iterator<StoreTextBean> iterator = recodeTextList.iterator();
        while (iterator.hasNext()){
            iterator.next().delete();//存文本数据
        }
    }
    //动作回调
    public final List<ActionCall> actionCallList  = new ArrayList<>();
    //正在运行
    private volatile boolean isRunning;
    private volatile boolean isStop = false;

    //添加回调接口
    public void setActionCall(ActionCall actionCall) {
        try {
            lock.lock();
            if (actionCall==null){
                return;
            }
            actionCallList.add(actionCall);
        } finally {
            lock.unlock();
        }
    }
    public BaseThread(ParamManager paramManager) {
        this.setName("DataPick-"+getId());
        this.setDaemon(true);
        this.monitor = new DownloadMonitor(this,paramManager);
        this.paramManager = paramManager;
        this.homeDir = this.paramManager.getHomeFile();
        String path = this.paramManager.getPathFile();
        KEY = getClass().getSimpleName();
        RES_HOME_PATH = String.format( path + "/%s/res", KEY);
        JSON_HOME_PATH = String.format(path + "/%s/json", KEY);
        JSON_FILE_NAME = String.format("%s.json", KEY);
        RES_URL_DOWNLOAD_FILE = String.format(path + "/%s/down/resource.address", KEY);
        KEY=getClass().getName();
    }
    //休眠
    protected void waitTime(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }
    private void waitTimeByTryAng(int time){
//        Say.I(KEY+ " 抓取数据超时, "+time+" 毫秒后尝试重试");
        waitTime(time);
    }
    /**
     * 超时
     */
    protected boolean waitTime() {
        if (reMackCount > 15) {
//            Say.I(KEY+ " 抓取数据超时, 尝试重试次数已超过最大值,即将结束数据抓取.");
            stopSelf();
            return false;
        } else if (reMackCount > 12) {
            waitTimeByTryAng(1000 * getRandomNumber(30,60));
        } else if (reMackCount < 6) {
            waitTimeByTryAng(1000 * getRandomNumber(10,15));
        } else {
            waitTimeByTryAng(1000 * getRandomNumber(15,30));
        }
        reMackCount++;
        return true;
    }

    private int getRandomNumber(int min ,int max){
            Random random = new Random();
            return random.nextInt(max)%(max-min+1) + min;
    }

    @Override
    public void run() {
        while (!isStop){
            if (isRunning){
                work();
            }
            if (!isStop){
                synchronized (lock){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                    }
                }
            }

        }
    }
    private String addUrl(MUrl url) {
        monitor.add(url);
        return url.getPath() + (url.getFileName().startsWith("/") ? url.getFileName() : "/" + url.getFileName());
    }
    /**
     * 添加下载任务
     */
    public String addResUrl(MUrl url) {
        return addUrl(url);
    }
    /**
     * 通知开始下载任务
     */
    private void notifyQueryDownload() {
        monitor.reset();
        Iterator<MUrl> iterator = monitor.iterator();
        MUrl url;
        while (iterator.hasNext()) {
            url = iterator.next();
            url.setPathHome(homeDir);
            createHttpTask(url, monitor);
        }
    }

    /**
     * 进入下载队列
     * @param url
     * @param onResult
     */
    private void createHttpTask(MUrl url, DownloadMonitor onResult) {
        Task task = TaskFactory.httpTaskDown(url.getUrl(),
                "GET",
                url.getPath(),
                url.getFileName(),
                url.getMap(),
                false).
                setOnResult(onResult);
        if (url.getMap() != null) task.setDirectDown(true);
        ManagerImp.get().load(task);
    }

    private void error(Exception e) {
        try {
            lock.lock();
            if (actionCallList != null) {
                Iterator<ActionCall> iterator = actionCallList.iterator();
                while (iterator.hasNext()){
                    iterator.next().error(e);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    protected abstract void workImps() throws Exception;

    protected void work2Imps() throws Exception {}

    protected void work3Imps() throws Exception {
        if (monitor.remaining()> 0) {
            //通知下载
            notifyQueryDownload();
        } else {
            completeOver(false);
        }
    }


    /**
     * 完成
     */
    private void completeOver(boolean flag) {
        CatchBean catchBean = new CatchBean();
        catchBean.setName(KEY);
        //删除旧JSON文件
        deleteRecodeTextBean();
        if (flag) {
            catchBean.setResZip(compressExe(0)); //设置压缩资源文件目录路径返回下载ZIP包地址
            catchBean.setResFileLink(RES_URL_DOWNLOAD_FILE);//所有的单文件下载地址
        }
        recodeTextBean();
        File fDir = new File(homeDir+JSON_HOME_PATH);
        if (fDir.exists()){
            File[] files = fDir.listFiles();
            for (File f: files){
                if (f.getName().endsWith("js")){
                    catchBean.setJsFileLink(JSON_HOME_PATH+FileUtil.SEPARATOR+f.getName());
                }else if (f.getName().endsWith("json")){
                    catchBean.setJsonFileLink(JSON_HOME_PATH+FileUtil.SEPARATOR+f.getName());
                }
            }
        }
        release();
        callByAndRemove(catchBean);
    }
    //移除回调
    private void callByAndRemove(CatchBean catchBean) {
        try {
            lock.lock();
            if (actionCallList != null && actionCallList.size()>0) {
                Iterator<ActionCall> iterator = actionCallList.iterator();
                while (iterator.hasNext()){
                    iterator.next().onComplete(catchBean);
                    iterator.remove();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 工作
     */
    protected void work() {
        try {
            init();
            workImps();
            work2Imps();
            work3Imps();
        } catch (Exception e) {
            if (e instanceof SocketTimeoutException){
               Say.I("超时 -  task: [" + paramManager.getKeyTitle(KEY) +"]");
            }else{
                error(e);
            }
            if (waitTime()){
                work();
            }
        }

    }

    /**
     * 初始化
     */
    protected void init() {
        //记录启动时间  (当启动时间距离当前时间>2小时(60 * 60 * 2 * 1000),会被回收 )
        Say.I("task: [" + paramManager.getKeyTitle(KEY) +"]  ,thread: ["+this.getName() +"]  ,thread number: ["+ Thread.activeCount()+"]");
        sTime = System.currentTimeMillis();
        monitor.clear();
        clearTextBean();
    }
    /**
     * 释放
     */
    private void release(){
        monitor.clear();
        clearTextBean();
        isRunning = false;
    }
    //写入文件
    public void writeFile(String content){
        writeFile(content, JSON_FILE_NAME);
    }
    //写入文件
    public void writeFile(String content, String file){
        writeFile(content, JSON_HOME_PATH, file);
    }
    private void writeFile(String content, String path, String name) {
        path = homeDir+path;
        addTextBean(new StoreTextBean(content,path,name));
    }
    //转换js
    public String translateByJs(String jsName, String result, boolean isBrace) {
        result = result.replaceAll("clazz", "class");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("var ").append(jsName).append(" = ");
        if (isBrace) {
            stringBuffer.append(result.startsWith("{") ? "" : "{");
        }
        stringBuffer.append(result);
        if (isBrace) {
            stringBuffer.append(result.endsWith("}") ? "" : "}");
        }
        return stringBuffer.toString();
    }
    //对象转json
    public static String objTansJson(Object object){
        return new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create().toJson(object);
    }

    public void transInteractionGoods(Object object, Class clazz, String jsName) throws Exception {
        writeFile(translateByJs(jsName, objTansJson(object), true), clazz.getSimpleName() + ".js");
    }

    public void transInteractionGoods(Object object, Class clazz, String jsName, boolean isBrace) throws Exception {
        writeFile(translateByJs(jsName, objTansJson(object), isBrace), clazz.getSimpleName() + ".js");
    }

    public void transInteractionGoods(Object object, String jsFileName, String jsName) throws Exception {
        writeFile(translateByJs(jsName,objTansJson(object), true), jsFileName + ".js");
    }

    public void transInteractionGoods(Object object, String jsFileName, String jsName, boolean isBrace) throws Exception {
        writeFile(translateByJs(jsName, objTansJson(object), isBrace), jsFileName + ".js");
    }
    public String getGoodsImageUrl(String url) {
        return getGoodsImageUrl(url, null);
    }
    public String getGoodsImageUrl(String url, String path) {
        return getGoodsImageUrl(url,path,null,null);
    }
    public String getGoodsImageUrl(String url, String path,String[] keys,String[] values) {
        path = RES_HOME_PATH + (path == null ? "" : FileUtil.SEPARATOR + path);
        return addResUrl(
                new MUrl(url, path, keys,values)
        );
    }

    /**
     * 压缩文件
     */
    private String compressExe(int tryCount) {
        String path = homeDir + RES_HOME_PATH;
        File resDirs = new File(path);
        if (!resDirs.exists()) {
            return null;
        }

        try {
            path = path + FileUtil.SEPARATOR + "res.zip";
            File zipFile = new File(path);
            if (zipFile.exists()) {
                if (!zipFile.delete()) {
                    if (tryCount < 10) {
                        tryCount++;
                        System.gc();
                        Say.I(KEY + " 资源目录 旧zip包删除失败,15秒后尝试删除.");
                        try {
                            Thread.sleep(15 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        compressExe(tryCount);
                    } else {
                        Say.I(KEY + " 资源目录 旧zip包删除失败,请手动删除.无法生成资源zip包.");
                        return RES_HOME_PATH + FileUtil.SEPARATOR + "res.zip";
                    }
                }
            }

            Project prj = new Project();
            Zip zip = new Zip();
            zip.setProject(prj);
            zip.setDestFile(zipFile);
            FileSet fileSet = new FileSet();
            fileSet.setProject(prj);
            fileSet.setDir(resDirs);
            zip.addFileset(fileSet);
            zip.execute();
            return RES_HOME_PATH + FileUtil.SEPARATOR + "res.zip";
        } catch (BuildException e) {
            e.printStackTrace();
        }
        return null;
    }




    private void writeRes2File(String content, String filePath) {
        filePath = homeDir+filePath;
        FileUtil.writeStringToFile(content, filePath.substring(0, filePath.lastIndexOf("/") + 1), filePath.substring(filePath.lastIndexOf("/") + 1), false);
    }


    @Override
    public void downOver(Object data) {
        ArrayList<String> list = (ArrayList<String>) data;
        ArrayList<String> resPath = new ArrayList<>();
        //过滤 - 删除文件中的 主目录 字符串
        for (String str : list){
            resPath.add(str.replace(homeDir,""));
        }
        //写入存储资源路径的Json文件
        writeRes2File(objTansJson(resPath),RES_URL_DOWNLOAD_FILE);
        //文件过滤 -删除不在指定时间内且不在需要保存的列表中的文件
        deleteRes(list);
        //下载结果回调
        completeOver(true);
    }


    //return yyyy-MM-dd
    protected String getNowDayStr(){
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH) + 1)+"-"+now.get(Calendar.DAY_OF_MONTH);
    }
    protected boolean isLatestDayByNowPStr(String timeStr, int day){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = format.parse(timeStr);
            Date date2 = format.parse(getNowDayStr());
            return isLatestDay(date1,date2,day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    //判断时间是否在当前时间指定天数内
    private boolean isLatestDay(Date compareTime,Date nowTime,int day){
        if (day<=0) return false;
        Calendar calendar = Calendar.getInstance();  //得到日历
        calendar.setTime(nowTime);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -day);  //设置为指定天数前
        Date beforeDays = calendar.getTime();   //得到指定天数前的时间
        if(beforeDays.getTime() < compareTime.getTime()){
            return true;
        }else{
            return false;
        }
    }

    private void deleteRes(ArrayList<String> list) {
        new TraversalResourceFile(homeDir+RES_HOME_PATH, list, new TRAction.Adpter() {
            @Override
            public FileVisitResult onReceive(Path filePath, BasicFileAttributes attrs, Object attr) {
                try {
                    if (filePath.toFile().isDirectory()){
                        if (filePath.toFile().listFiles().length==0) filePath.toFile().delete(); //删除空文件夹
                    }else{
                        Iterator<String> iterator = ((ArrayList<String>) attr).iterator(); //记录的所有文件
                        String sPath = filePath.toFile().getCanonicalPath();//当前遍历到的文件
                        while (iterator.hasNext()){
                            if (new File(iterator.next()).getCanonicalPath().equals(filePath.toFile().getCanonicalPath())){
                                //找到一个在文件列表中存在的 - 需要保存的文件
                                iterator.remove();
                                return FileVisitResult.CONTINUE;
                            }
                        }
                        //需要删除的文件
                        if (!sPath.contains(".conf")){ //如果不是 .conf文件 则删除
                            //接着判断当前保存天数
                            if (!isLatestDay(new Date(attrs.lastModifiedTime().toMillis()), new Date(), paramManager.getSaveDay())){
                                FileUtil.deleteFile(sPath);
                            }
//                          Say.I("删除: "+ sPath + " "+(FileUtil.deleteFile(sPath)?"成功":"失败"));
                        }
                    }

                } catch (IOException e) {
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    @Override
    public String getKey() {
        return KEY;
    }

    /**
     * 正在运行中
     * @return
     */
    public boolean isRunning() {
        return isRunning;
    }
    public void reStart(){
        if (isRunning) return;
        isRunning = true;

        synchronized (lock){
            try {
                lock.notifyAll();
            } catch (Exception e) {
            }
        }

    }
    protected void stopSelf(){
        isStop = true;
        //
        synchronized (lock){
            try {
                lock.notifyAll();
            } catch (Exception e) {
            }
        }
    }

    public boolean isStop() {
        return isStop;
    }

    public long getsTime() {
        return sTime;
    }
}
