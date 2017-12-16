package interfaces;

import com.winone.ftc.mtools.ClazzUtil;
import com.winone.ftc.mtools.Log;
import lunch.Say;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by user on 2017/8/19.
 * 抓取线程管理器
 *
 */
public class BaseThreadManager extends Thread{
    public final HashMap<String,BaseThread> runningMap = new HashMap<>();
    private final ReentrantLock public_lock = new ReentrantLock();
    private final long IN_TIME = 30 * 60 * 1000L; //30分钟
    private BaseThreadManager (){start();}
    private static class Holder {
        private static BaseThreadManager manager = new BaseThreadManager();
    }
    public static BaseThreadManager getInstants(){
        return Holder.manager;
    }

    /**
     * 启动一个抓取任务的线程
     * @param clazz
     * @param call
     */
    public void launchThread(String clazz, ActionCall call){
        try {
            public_lock.lock();
            Iterator<Map.Entry<String,BaseThread>> iterator = runningMap.entrySet().iterator();
            Map.Entry<String,BaseThread> entry;
            BaseThread thread;
            while (iterator.hasNext()){
                entry = iterator.next();
                if (entry.getKey().equals(clazz)){ //已经存在
                    thread = entry.getValue();
                    //添加回调接口
//
                    //是否活跃状态?是否正在运行中
                    if (thread.isStop()){
                        iterator.remove();
                    }else{
                        thread.setActionCall(call);
                        if (!thread.isRunning()){
                            thread.reStart();
                            return;
                        }
                    }
                }
            }

            try {
                thread = (BaseThread) ClazzUtil.newInstance(clazz,new Class[]{ActionCall.class},new Object[]{call});
                runningMap.put(clazz,thread);
                Say.I("执行>>\t"+ thread.getKeyString());
                thread.reStart();
                Thread.sleep(100);
            } catch (Exception e) {
                Say.I(clazz +" 启动失败: "+ e.toString());
            }
        } finally {
            public_lock.unlock();
        }
    }










    /**
     * 移除一个线程
     */
    @Override
    public void run() {
        while (true){
            try {
                sleep( 30 * 60 * 1000L); //半小时检测一次 //
            } catch (InterruptedException e) {
            }
            checkThreadState();
        }

    }

    private void checkThreadState() {
        try{
            public_lock.lock();
            Iterator<Map.Entry<String,BaseThread>> iterator = runningMap.entrySet().iterator();
            Map.Entry<String,BaseThread> entry;
            while (iterator.hasNext()){
                entry = iterator.next();
                if (entry.getValue().isStop()){
                    iterator.remove();
                }else if ( !entry.getValue().isRunning() &&
                        (System.currentTimeMillis() - entry.getValue().getsTime() )> IN_TIME ){
                    entry.getValue().stopSelf();
                    iterator.remove();
                }
            }
        }finally {
            public_lock.unlock();
        }
    }
}
