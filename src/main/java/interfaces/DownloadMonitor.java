package interfaces;

import com.winone.ftc.mentity.mbean.entity.State;
import com.winone.ftc.mentity.mbean.entity.Task;
import com.winone.ftc.mtools.FileUtil;
import com.winone.ftc.mtools.TaskUtils;
import lunch.Say;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by user on 2017/8/7.
 */
public class DownloadMonitor extends Task.onResultAdapter {
    private ArrayList<MUrl> list = new ArrayList<>(); //总下载数
    private ArrayList<Task> cIndexList = new ArrayList<>();// 已完成数
    private MonitorAction action;
    private ParamManager paramManager;
    public DownloadMonitor(MonitorAction action,ParamManager paramManager) {
        this.action = action;
        this.paramManager = paramManager;
    }


    public int remaining(){
        return list.size() - cIndexList.size();
    }
    public Iterator<MUrl> iterator(){
        return list.iterator();
    }
    public synchronized void add(MUrl url){
        Iterator<MUrl> iterator = list.iterator();
        MUrl curl;
        while (iterator.hasNext()){
            curl = iterator.next();
            if (curl.isSave(url)) return;
        }
        list.add(url);
    }
    public void reset(){
        cIndexList.clear();
        DownloadSwing.get().show();
    }
    public void clear(){
        cIndexList.clear();
        list.clear();
        DownloadSwing.get().hide();
    }
    @Override
    public void onSuccess(State state) {
        remove(state);
    }

    @Override
    public void onFail(State state) {
        if (state.getResult()!=null && state.getResult().equals("200")){
            FileUtil.deleteFile(TaskUtils.getConfigFile(state.getTask()));
        }
        remove(state);
    }

    private synchronized void remove(State state) {
        cIndexList.add(state.getTask());
        DownloadSwing.get().setCurrentInfo(cIndexList.size(),list.size(),paramManager.getKeyTitle(action.getKey()));
        if (remaining()==0){
            if (action!=null){
                //传递已下载的文件的地址
                ArrayList<String> fileLocalPath = new ArrayList<>();
                for (Task task :cIndexList){
                    fileLocalPath.add(TaskUtils.getLocalFile(task));
                }
                action.downOver(fileLocalPath);
            }
            clear();
        }
    }




}
