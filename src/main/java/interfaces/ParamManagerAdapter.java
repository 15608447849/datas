package interfaces;

import icbc.Icbc;
import lunch.Say;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 2017/9/11.
 */
public abstract class ParamManagerAdapter implements ParamManager{
    protected final HashMap<String,String> map = new HashMap<>();
    private String home = ".";
    private String path = "/";

    protected ParamManagerAdapter(String path){
        if (path==null || path.isEmpty()) path = getClass().getSimpleName();
        this.path+=path;
    }

    @Override
    public void setHomeFile(String path) {
        this.home = path;
    }

    @Override
    public void setDirs(String path) {

        if (path==null) return;
        if(!path.startsWith("/")) path = "/"+path;
        if (path.endsWith("/")) path = path.substring(0,path.lastIndexOf("/"));
        this.path = path;
    }

    @Override
    public String getHomeFile() {
        return home;
    }

    @Override
    public String getPathFile() {
        return path;
    }

    @Override
    public String getKeyTitle(String key) {
        return map.get(key)==null?key:map.get(key);
    }
    public HashMap<String,String> getMap(){
        return map;
    }
    public String fullPath(){
        return home+path;
    }
    public List<String> getStartItemList(){
        return Arrays.asList(map.keySet().toArray(new String[map.keySet().size()]));
    }
}
