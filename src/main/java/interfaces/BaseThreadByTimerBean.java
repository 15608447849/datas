package interfaces;

import com.winone.ftc.mtools.ClazzUtil;
import lunch.Say;

import java.util.*;

/**
 * Created by user on 2017/8/22.
 */
public class BaseThreadByTimerBean extends TimerTask implements ActionCall{
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
    private int type = -1; // 0 定时, 1,间隔 2特殊
    private final ArrayList<String> executeList = new ArrayList();
    //时间字符串 23:00:00 或者 60
    private String timeString = "";
    private Timer timer;
    /**
     * 添加执行的类路径
     */
    public BaseThreadByTimerBean addExecuteClazz(String clazz){
        if (!executeList.contains(clazz)){
            executeList.add(clazz);
        }
        return this;
    }

    /**
     * 0 定时, 1,间隔,2特殊
     */
    public BaseThreadByTimerBean(int type, String timeString) throws Exception {
        this.type = type;
        this.timeString = timeString;
        crateTime();
    }

    private void crateTime() throws Exception{
        timer = new Timer();
        if (type ==0 || type==2){
            Date first = string2Date(timeString);
            timer.schedule(this,first,PERIOD_DAY);
        }else if (type==1 ){
            int second = Integer.parseInt(timeString);
            timer.schedule(this,second * 1000,second * 1000);
        }else{
            throw new IllegalArgumentException("type not define.");
        }
    }

    @Override
    public void error(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onComplete(CatchBean catchBean) {
        NotifyServer.get().notify(catchBean);
    }

    @Override
    public void run() {
        for (String clazz : executeList){
            if (type==0 || type == 1){
                BaseThreadManager.getInstants().launchThread(clazz,this);
            }
           if (type == 2){
               ClazzUtil.newInstance(clazz,new Class[]{ActionCall.class},new Object[]{this});
           }
        }
    }
    private Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }
    private Date string2Date(String timeString) {
        String[] strArr = timeString.split(":");

        Calendar calendar = Calendar.getInstance();
        if (strArr.length >= 1){
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(strArr[0]));
        }else{
            calendar.set(Calendar.HOUR_OF_DAY, 0);
        }
        if (strArr.length >= 2){
            calendar.set(Calendar.MINUTE, Integer.parseInt(strArr[1]));
        }else{
            calendar.set(Calendar.MINUTE,0);
        }
        if (strArr.length >= 3){
            calendar.set(Calendar.SECOND, Integer.parseInt(strArr[2]));
        }else{
            calendar.set(Calendar.SECOND, 0);
        }
        Date date = calendar.getTime();
        if (date.before(new Date())) {
            date = addDay(date, 1);
        }
        return date;
    }

    public int getType() {
        return type;
    }
}
