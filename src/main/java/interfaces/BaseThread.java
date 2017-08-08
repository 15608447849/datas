package interfaces;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.winone.ftc.mcore.imps.ManagerImp;
import com.winone.ftc.mentity.mbean.Task;
import com.winone.ftc.mentity.mbean.TaskFactory;
import com.winone.ftc.mtools.FileUtil;
import lunch.Say;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


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

    private DownloadMonitor monitor = new DownloadMonitor(this);
    //动作回调
    public ActionCall actionCall;

    public void setActionCall(ActionCall actionCall) {
        this.actionCall = actionCall;
    }

    protected BaseThread(String homeDirs,String path) {
        this.homeDir = homeDirs;
        KEY = getClass().getSimpleName();
        RES_HOME_PATH = String.format(path + "/%s/res", KEY);
        JSON_HOME_PATH = String.format(path + "/%s/json", KEY);
        JSON_FILE_NAME = String.format("%s.json", KEY);
        RES_URL_DOWNLOAD_FILE = String.format(path + "/%s/down/resource.address", KEY);
        KEY=getClass().getName();
    }

    protected void waitTime(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    /**
     * 超时
     */
    protected void waitTime() {
        if (reMackCount > 10) {
            return;
        } else if (reMackCount > 7) {
            waitTime(1000 * 30);
        } else if (reMackCount < 3) {
            waitTime(1000 * 5);
        } else {
            waitTime(1000 * 10);
        }
        reMackCount++;
    }

    @Override
    public void run() {
        work();
    }

    private String addUrl(MUrl url) {
        if (monitor != null) {
            monitor.add(url);
        }
        return url.getPath() + (url.getFileName().startsWith("/") ? url.getFileName() : "/" + url.getFileName());
    }

    /**
     * 添加下载任务
     */
    public String addResUrl(MUrl url) {
        String path = addUrl(url);
        return path;
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
     *
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
        if (actionCall != null) {
            actionCall.error(e);
        }
    }

    protected abstract void workImps() throws Exception;

    protected void work2Imps() throws Exception {
    }

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
        if (flag) {
            catchBean.setResZip(compressExe(0)); //压缩资源文件目录
            catchBean.setResFileLink(RES_URL_DOWNLOAD_FILE);
        }
        if (actionCall != null) {
            catchBean.setName(KEY);
            File fDir = new File(homeDir+JSON_HOME_PATH);
            if (fDir.exists()){
                File[] files = fDir.listFiles();
                for (File f: files){
                    if (f.getName().endsWith("js")){
                        catchBean.setJsFileLink(homeDir+JSON_HOME_PATH+FileUtil.SEPARATOR+f.getName());
                    }else if (f.getName().endsWith("json")){
                        catchBean.setJsonFileLink(homeDir+JSON_HOME_PATH+FileUtil.SEPARATOR+f.getName());
                    }
                }
            }
            actionCall.onComplete(catchBean);
        }
    }


    protected void work() {
        try {
            init();
            workImps();
            work2Imps();
            work3Imps();
        } catch (Exception e) {
            error(e);
            waitTime();
            work();
        }

    }

    private void init() {
        monitor.clear();
    }

    //写入文件
    public void writeJSON(String json) throws IOException {
        writeJSON(json, JSON_FILE_NAME);
    }

    //写入文件
    public void writeJSON(String json, String file) throws IOException {
        String path = writeJson2File(json, JSON_HOME_PATH, file);
        if (path == null) {
            throw new IOException(getClass().getSimpleName() + "  保存文件失败.");
        } else {
            //            Say.I("     保存JSON文件到 "+ path );
        }
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

    public void transInteractionGoods(Object object, Class clazz, String jsName) throws Exception {
        writeJSON(translateByJs(jsName, new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create().toJson(object), true), clazz.getSimpleName() + ".js");
    }

    public void transInteractionGoods(Object object, Class clazz, String jsName, boolean isBrace) throws Exception {
        writeJSON(translateByJs(jsName, new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create().toJson(object), isBrace), clazz.getSimpleName() + ".js");
    }

    public void transInteractionGoods(Object object, String jsFileName, String jsName) throws Exception {
        writeJSON(translateByJs(jsName, new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create().toJson(object), true), jsFileName + ".js");
    }

    public void transInteractionGoods(Object object, String jsFileName, String jsName, boolean isBrace) throws Exception {
        writeJSON(translateByJs(jsName, new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create().toJson(object), isBrace), jsFileName + ".js");
    }

    public String getGoodsImageUrl(String url) {
        return getGoodsImageUrl(url, null);
    }

    public String getGoodsImageUrl(String url, String path) {
        path = RES_HOME_PATH + (path == null ? "" : FileUtil.SEPARATOR + path);
        return addResUrl(
                new MUrl(url, path, new String[]{"Referer"}, new String[]{"http://mall.icbc.com.cn/"}
                )
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


    private String writeJson2File(String json, String path, String name) {
        path = homeDir+path;
        return FileUtil.writeStringToFile(json, path, name, false) ? path + "/" + name : null;
    }

    private void writeRes2File(String content, String filePath) {
        filePath = homeDir+filePath;
        FileUtil.writeStringToFile(content, filePath.substring(0, filePath.lastIndexOf("/") + 1), filePath.substring(filePath.lastIndexOf("/") + 1), false);
    }


    @Override
    public void downOver(Object data) {
        ArrayList<String> list = (ArrayList<String>) data;
        ArrayList<String> resPath = new ArrayList<>();
        for (String str : list){
            resPath.add(str.replace(homeDir,""));
        }
        //资源路径
        writeRes2File(new Gson().toJson(resPath),RES_URL_DOWNLOAD_FILE);
        //下载结果回调
        completeOver(true);
    }

    @Override
    public String getKey() {
        return KEY;
    }
}
