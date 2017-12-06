package lunch;

import abchina.Abchina;
import ccb.Ccb;
import com.google.gson.Gson;
import com.winone.ftc.mcore.imps.ManagerImp;
import com.winone.ftc.mentity.mbean.entity.ManagerParams;
import com.winone.ftc.mtools.FileUtil;
import com.winone.ftc.mtools.Log;
import icbc.Icbc;
import icbc.interaction.GoodsDataAllRecode;
import interfaces.*;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * Created by user on 2017/8/7.
 */
public class LunchThread extends Thread implements ActionCall{
    private int intervalTime = 0;
    private List<String> lunchList;
    private int nextStart = 0;
    private final String jsonFile ="./pick.json";

    public LunchThread() {
        ConfigBean configBean = init();//初始化定时器等
        initDownParam(configBean.getDownloadMaxThread());
        initLaunceList(configBean.getFirstBack(),configBean.getHome(),configBean.getDirectory(),configBean.getSaveDay());//判断类型,设置主目录 路径
        intervalTime = configBean.getExecuteType();
        start();
    }

    private void initLaunceList(String firstBack,String home,String dirs,int saveDay) {
        ParamManagerAdapter adpter = getBackType(firstBack);
        if (adpter==null){
            Say.I("不匹配的参数类型: firstBack ,不识别的银行类型.请修改后重新尝试.");
            System.exit(0);
        }else{
            adpter.setHomeFile(home);
            adpter.setDirs(dirs);
            adpter.setSaveDay(saveDay);
            lunchList = adpter.getStartItemList();
            //Say.I("当前抓取的银行: "+adpter+"\n抓取数据存储主目录: "+ home +"\n可抓取类别:\n"+lunchList);
        }


    }

    private ParamManagerAdapter getBackType(String firstBack) {
        if (firstBack.equals("icbc")){
            return Icbc.getInstance();
        }else
        if (firstBack.equals("ccb")){
            return Ccb.getInstance();
        }else
        if (firstBack.equals("abchina")){
            return Abchina.getInstance();
        }
        return null;
    }


    //初始化
    private void initDownParam(int threadNumber) {
        if (threadNumber<=1) threadNumber = 1;
        ManagerParams params = new ManagerParams();
        params.setPrintf(false);
        params.setCheckNetwork(false);
        params.setRecode(false);
        params.setRuntimeThreadMax(threadNumber);
        ManagerImp.get().initial(params);//全局管理器
    }

    /**
     * 初始化配置文件
     * @return
     */
    private ConfigBean init() {
        ConfigBean configBean = null;
        try {
            File file = new File(jsonFile);
            if (file.exists() && file.isFile()){
                String content = FileUtil.getFileText(file.getCanonicalPath(),false);
                Gson gson = new Gson();
                configBean = gson.fromJson(content,ConfigBean.class);
                if (configBean.getExecuteType()!=0){
                    TimeBean bean = gson.fromJson(content, TimeBean.class);
                    if (bean.check()){
                        TimeThreadFactory.create(bean);
                    }
                    NotifyServer.get().initServer(bean.getNotify_server());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configBean!=null? configBean: new ConfigBean();
    }
    @Override
    public void run() {
        Say.I("启动数据爬虫");
            if (intervalTime == 0){
                Say.I("数据抓取成功将结束进程.");
                lunch();
            }else if (intervalTime == 1){
                Say.I("不自动抓取,不结束进程.");
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }else if (intervalTime == 2){
                Say.I("执行首次数据抓取并保持存活.");
                lunch();
            }else{
                exit(1);
            }
    }

    //启动
    private void lunch() {
        if (lunchList==null || lunchList.size()==0) {
           Say.I("无抓取子项,跳过自动抓取.");
           exit(intervalTime);
           return;
        }
        nextStart = 0 ;
        specialStart();
        for (String clazz : lunchList){
            BaseThreadManager.getInstants().launchThread(clazz,this);
        }
    }
   private void exit(int val){
        if (val==0){
            Say.I("退出.");
            System.exit(0);
        }
    }
    @Override
    public void error(Exception e) {
        e.printStackTrace();
    }
    @Override
    public void onComplete(CatchBean catchBean) {
        specialPut(catchBean);
        //通知服务器
        NotifyServer.get().notify(catchBean);
        if (lunchList.contains(catchBean.getName())) {
            nextStart++;
        }
        if (nextStart == lunchList.size()){
            exit(intervalTime);
            specialNotify();
        }
    }
    private void specialStart(){
        GoodsDataAllRecode.get().clear(); //工商银行,六个商品的json组合
    }
    private void specialPut(CatchBean catchBean){
        GoodsDataAllRecode.get().recode(catchBean.getName()); //工商银行,六个商品的json组合(互动茶几)
    }
    private void specialNotify(){
        NotifyServer.get().notify(GoodsDataAllRecode.get().buildCatch());
    }

}
