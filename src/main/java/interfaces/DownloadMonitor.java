package interfaces;

import com.winone.ftc.mentity.mbean.State;
import com.winone.ftc.mentity.mbean.Task;
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
    private DownloadSwing downloadSwing;

    public DownloadMonitor(MonitorAction action) {
        this.action = action;
    }


    public int remaining(){
        return list.size() - cIndexList.size();
    }
    public Iterator<MUrl> iterator(){
        return list.iterator();
    }
    public void add(MUrl url){
        Iterator<MUrl> iterator = list.iterator();
        while (iterator.hasNext()){
            if (iterator.next().isSave(url)) break;
        }
        list.add(url);
    }
    public void reset(){
        cIndexList.clear();
        if (downloadSwing!=null){
            downloadSwing.close();
        }
        downloadSwing = new DownloadSwing(action.getKey(),list.size());
    }
    public void clear(){
        cIndexList.clear();
        list.clear();
        if (downloadSwing!=null){
            downloadSwing = null;
        }
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
        addComplete(state.getTask());
        if (downloadSwing!=null){
            downloadSwing.setCurrent(cIndexList.size());
        }
        if (remaining()==0){
            if (action!=null){
                ArrayList<String> fileLocalPath = new ArrayList<>();
                for (Task task :cIndexList){
                    fileLocalPath.add(TaskUtils.getLocalFile(task));
                }
                action.downOver(fileLocalPath);
            }
            clear();
        }
    }

    private void addComplete(Task task) {
        Iterator<Task> iterator = cIndexList.iterator();
        while (iterator.hasNext()){
            if (iterator.next().equals(task)) return;
        }
        cIndexList.add(task);
    }


}
