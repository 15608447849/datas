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
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by user on 2017/8/7.
 */
public abstract class BaseThread extends Thread implements MonitorAction {
    //超时时间
    public int TIMEOUT = 10 * 1000;
    private String homeDir;
    //标识
    public String KEY;
    //资源主目录路径
    public String RES_HOME_PATH;
    //json主目录路径
    public String JSON_HOME_PATH;
    //json文件名
    public String JSON_FILE_NAME;
    //资源文件下载链接文件
    public String RES_URL_DOWNLOAD_FILE;
    //重新尝试次数
    private int reMackCount = 0;
    //时间
    private long sTime = System.currentTimeMillis();
    private final ReentrantLock lock = new ReentrantLock();
    private final ParamManager paramManager;
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
            iterator.next().store();
            iterator.remove();
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
        if (flag) {
            catchBean.setResZip(compressExe(0)); //压缩资源文件目录
            catchBean.setResFileLink(RES_URL_DOWNLOAD_FILE);
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
        //删除资源目录下,非列表中的文件 删除文件
        for (String str : list){
            resPath.add(str.replace(homeDir,""));
        }
        //资源路径
        writeRes2File(objTansJson(resPath),RES_URL_DOWNLOAD_FILE);
        deleteRes(list);
        //下载结果回调
        completeOver(true);
    }

    private void deleteRes(ArrayList<String> list) {
        new TraversalResourceFile(homeDir+RES_HOME_PATH, list, new TRAction() {
            @Override
            public FileVisitResult onReceive(Path filePath, BasicFileAttributes attrs, Object attr) {

                try {
                    if (filePath.toFile().isDirectory()){
                        if (filePath.toFile().listFiles().length==0) filePath.toFile().delete(); //删除空文件夹
                    }else{
                        ArrayList<String> comList = (ArrayList<String>) attr;
                        Iterator<String> iterator = comList.iterator();
                        String sPath = filePath.toFile().getCanonicalPath();
                        File file;
                        while (iterator.hasNext()){
                            file = new File(iterator.next());
                            if (file.getCanonicalPath().equals(filePath.toFile().getCanonicalPath())){
                                iterator.remove();
                                return FileVisitResult.CONTINUE;
                            }
                        }
                        if (!sPath.contains(".conf")){
                            FileUtil.deleteFile(sPath);
//                            Say.I("删除: "+ sPath + " "+(FileUtil.deleteFile(sPath)?"成功":"失败"));
                        }
                    }

                } catch (IOException e) {
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
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
