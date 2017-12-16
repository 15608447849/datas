package interfaces;

import com.google.gson.Gson;
import com.m.tcps.c.FtcSocketClient;
import com.m.tcps.p.FtcTcpActionsAdapter;
import com.m.tcps.p.Session;
import lunch.Say;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * Created by user on 2017/8/8.
 */
public class NotifyServer extends FtcTcpActionsAdapter implements Runnable{


    private static class Holder{
        private static NotifyServer notifyServer = new NotifyServer();
    }
    public static NotifyServer get(){
        return Holder.notifyServer;
    }
    private Thread t;
    private BlockingQueue<String> queue;

    private NotifyServer(){
        queue = new LinkedTransferQueue<>();
        t = new Thread(this);
        t.start();
    }

    private FtcSocketClient client;
    private InetSocketAddress serverAddress;
    public void initServer(HashMap<String, String> map) {

        if (map == null || map.size()==0) return;
        String flag = map.get("notify");
        if (flag==null || flag.equals("false")) return;
        try {
            String host = map.get("host");
            int port = Integer.parseInt(map.get("port"));
            serverAddress = new InetSocketAddress(host,port);
            Say.I("启动通讯 - "+ serverAddress);
        } catch (Exception e) {
        }
    }

    private void initServer(){
        if (client==null && serverAddress!=null){
            client = new FtcSocketClient(serverAddress,this);
            connect();
        }

    }

    private void connect() {
        new Thread(()->{
            try {
                client.connectServer();
            } catch (IOException e) {
            }
        }).start();
    }

    @Override
    public void connectFail(Session session) {
        Say.I("连接失败 - "+ serverAddress);
        connect();
    }
    @Override
    public void error(Session session, Throwable throwable, Exception e) {
        super.error(session,throwable,e);
    }

    //通知服务器一个消息
    public void notify(CatchBean catchBean){
        if (queue!=null && catchBean!=null) {
            putValue(new Gson().toJson(catchBean));
        }

    }

    private synchronized void putValue(String var) {
            try {
                queue.put(var);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void run() {
        String message;
        while (t!=null){
            try {
                message = queue.take();
                if (client!=null && client.isAlive()){
                    client.getSession().getOperation().writeString(message,"UTF-8");
//                    Say.I("通知服务器数据库存储信息:"+serverAddress+"\n\t" +message);
                }else{
                    putValue(message);
                    initServer();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
