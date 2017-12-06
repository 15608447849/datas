package interfaces;

import lunch.Say;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by user on 2017/8/22.
 */
public class TimeThreadFactory {
    private static final HashMap<String,BaseThreadByTimerBean> threadMap = new HashMap<>();

    public static void create(TimeBean bean){
        try {

            setTimerParam(0,bean.getDeterminated_time());
            setTimerParam(1,bean.getInterval_time());
            setTimerParam(2,bean.getSpecial_time());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private static void setTimerParam(int type, HashMap<String, String> map) {
        //添加时间任务

        if (map!=null && map.size()>0){
            Iterator<Map.Entry<String,String>> iterator = map.entrySet().iterator();
            Map.Entry<String,String> entry = null;
            BaseThreadByTimerBean baseThreadByTimerBean;
            String[] times ;
            while (iterator.hasNext()){
                entry = iterator.next();

                try {
                    times = entry.getValue().split("#");
                    for (String time :times){
                        baseThreadByTimerBean = threadMap.get("["+type+"@"+time+"]");
                        if (baseThreadByTimerBean == null){
                            baseThreadByTimerBean = new BaseThreadByTimerBean(type,time);
                            threadMap.put("["+type+"@"+time+"]",baseThreadByTimerBean);
                        }
                        baseThreadByTimerBean.addExecuteClazz(entry.getKey());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
