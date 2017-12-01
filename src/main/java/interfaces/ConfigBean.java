package interfaces;

import lunch.Say;

import javax.swing.plaf.nimbus.State;
import java.util.HashMap;

/**
 * Created by user on 2017/8/23.
 */
public class ConfigBean {
    private HashMap<String,String> config;
    public void setConfig(HashMap<String, String> config) {
        this.config = config;
    }
    private int getKey(String key,int def){
        if (config==null) return def;
        String val = config.get(key);
        if (val == null) return def;
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
        }
        return def;
    }

    private String getKey(String key,String def){
        if (config==null) return def;
        String val = config.get(key);
        if (val == null) return def;
        return val;
    }

    public String getHome() {
        String val = getKey("home",".");
        if (val!=null){
            val = val.replace("\\","/");
            if (val.endsWith("/")) val = val.substring(0,val.lastIndexOf("/"));
        }
        return val;
    }
    public String getDirectory() {
        String val = getKey("directory",null);
        if (val!=null){
            val = val.replace("\\","/");
            if (val.endsWith("/")) val = val.substring(0,val.lastIndexOf("/"));
        }
        return val;
    }
    /**
     * 0 - 循环抓取
     * -1 数据抓取完毕结束进程
     * -2 不自动抓取,不结束进程
     * -3 执行首次数据抓取
     */
    public int getExecuteType() {
       return getKey("executeType",0);
    }
    public int getDownloadMaxThread() {
        return getKey("downloadMaxThread",1);
    }
    public String getFirstBack() {
        return getKey("firstBack","node"); //icbc 建设银行
    }
}
