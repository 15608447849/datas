package lunch;

import com.google.gson.Gson;
import com.winone.ftc.mcore.imps.ManagerImp;
import com.winone.ftc.mentity.mbean.ManagerParams;
import com.winone.ftc.mtools.ClazzUtil;
import com.winone.ftc.mtools.FileUtil;
import interaction.GoodsDataAllRecode;
import interfaces.*;
import m.tcps.p.CommunicationAction;
import m.tcps.p.Op;
import m.tcps.p.Session;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 2017/8/7.
 */
public class LunchThread extends BaseThread implements ActionCall{
    private final long INTERVAL_TIME;
    private final ArrayList<String> lunchList;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
    private volatile boolean isCatchAllData;
    private int nextStart = 0;
    private final List<String> goodsList = new ArrayList<>();//GOODS LIST
    public LunchThread(ArrayList<String> list,long interval,int threadNumber) {
        super(null,null);
        this.lunchList = list;
        this.INTERVAL_TIME = interval;

        initDownParam(threadNumber);

        Say.I("设置同时下载存储最大数: "+ threadNumber);
        if (interval==-2){
            Say.I("启动数据爬虫,不自动抓取,不结束进程.");
            return;
        }
        start();
    }
    private void initDownParam(int threadNumber) {
        if (threadNumber<=1) threadNumber = 1;
        ManagerParams params = new ManagerParams();
        params.setPrintf(false);
        params.setCheckNetwork(false);
        params.setRecode(false);
        params.setRuntimeThreadMax(threadNumber);
        params.setTcpServerOpen(true);
        params.setComunicationAction(new NotifyServer());
        ManagerImp.get().initial(params);
    }

    @Override
    protected void workImps() throws Exception {
        if (INTERVAL_TIME<0){
            if (INTERVAL_TIME==-1){
                Say.I("不循环抓取,数据抓取完毕结束进程.");
            }else if (INTERVAL_TIME ==-3){
                Say.I("不循环抓取,执行首次抓取数据.");
            }

        }else{
            Say.I("系统在 "+ simpleDateFormat.format(new Date(System.currentTimeMillis()+INTERVAL_TIME)) +"后,自动抓取数据.");
            Thread.sleep(INTERVAL_TIME);
        }
        lunch();
    }
    private void lunch() {
        isCatchAllData = true;
        nextStart = 0 ;
        for (String clazz : lunchList){
            ClazzUtil.newInstance(clazz,new Class[]{ActionCall.class},new Object[]{this});
            waitTime(500);
        }
    }







    @Override
    public void error(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onComplete(CatchBean catchBean) {
        Say.I("抓取成功: "+ catchBean.getName());
        if (lunchList.contains(catchBean.getName())) {
            nextStart++;
        }
        if (nextStart == lunchList.size()){
            isCatchAllData = false;
            GoodsDataAllRecode.get().recode();
            Say.I("全部数据已抓取完毕.");
            if (INTERVAL_TIME<0){
                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    if (INTERVAL_TIME == -1){
                        System.exit(0);
                    }else if (INTERVAL_TIME==-3){
                        return;
                    }
                }
            }else{
                work();
            }
        }

    }
}
