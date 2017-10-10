package interfaces;

import com.google.gson.Gson;
import lunch.Say;
import m.tcps.c.FtcSocketClient;
import m.tcps.p.CommunicationAction;
import m.tcps.p.Op;
import m.tcps.p.Session;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by user on 2017/8/8.
 */
public class NotifyServer implements CommunicationAction {
    private final ArrayList<String> alreadyKeyList = new ArrayList();
    private FtcSocketClient client;
    //单一线程池
    private ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    public void initServer(InetSocketAddress serverAddress){
        if (client==null){
            client = new FtcSocketClient(serverAddress,this);
            client.connectServer();
        }
    }

    private NotifyServer(){
    }
    private static class Holder{
        private static NotifyServer notifyServer = new NotifyServer();
    }
    public static NotifyServer get(){
        return Holder.notifyServer;
    }
    @Override
    public void connectSucceed(Session session) {
        Say.I("已连接服务器 - "+ session.getSocket());
    }

    @Override
    public void receiveString(Session session, Op op, String s) {
    }

    @Override
    public void connectClosed(Session session) {
//        Say.I("关闭连接 - "+ session.getSocket());
    }

    @Override
    public void error(Session session, Throwable throwable, Exception e) {
        if(throwable!=null){
            throwable.printStackTrace();
        }
        if (e!=null){
            e.printStackTrace();
        }
    }

    //通知服务器
    public synchronized void notify(CatchBean catchBean){
        if (client!=null && client.isAlive()){
            if (alreadyKeyList.contains(catchBean.getName())) return;
            alreadyKeyList.add(catchBean.getName());
            singleThreadExecutor.execute(() ->
                    client.getSession().writeString(new Gson().toJson(catchBean)));
        }
    }
}
