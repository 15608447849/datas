package interfaces;

import java.util.HashMap;

/**
 * Created by user on 2017/8/21.
 */
public class TimeBean {
    private HashMap<String,String> notify_server;
    private HashMap<String,String> determinated_time;
    private HashMap<String,String> interval_time;
    private HashMap<String,String> special_time;

    public HashMap<String, String> getNotify_server() {
        return notify_server;
    }

    public void setNotify_server(HashMap<String, String> notify_server) {
        this.notify_server = notify_server;
    }

    public HashMap<String, String> getDeterminated_time() {
        return determinated_time;
    }

    public void setDeterminated_time(HashMap<String, String> determinated_time) {
        this.determinated_time = determinated_time;
    }

    public HashMap<String, String> getInterval_time() {
        return interval_time;
    }

    public HashMap<String, String> getSpecial_time() {
        return special_time;
    }

    public void setSpecial_time(HashMap<String, String> special_time) {
        this.special_time = special_time;
    }

    public void setInterval_time(HashMap<String, String> interval_time) {
        this.interval_time = interval_time;
    }

    @Override
    public String toString() {
        return "\n"+notify_server+"\n"+determinated_time+"\n"+interval_time+"\n"+special_time+"\n";
    }

    public boolean check() {
        return  determinated_time!=null || interval_time!=null || special_time!=null;
    }
}
