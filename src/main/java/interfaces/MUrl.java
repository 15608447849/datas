package interfaces;

import java.util.HashMap;

/**
 * Created by user on 2017/8/7.
 */
public class MUrl {
    private String url ;
    private String path = "/";
    private String fileName;
    private HashMap<String,String> map;
    public MUrl(String url, String path, String fileName) {
        this.url = url;
        if (path!=null) this.path = path;
        if (fileName!=null) this.fileName = fileName;else this.fileName = url.substring(url.lastIndexOf("/")+1);
    }
    public MUrl(String url, String path) {
        this(url,path,null);
    }


    public MUrl(String url, String path, String fileName, String[] key, String[] value) {
        this(url,path,fileName);
        try {
            this.map = new HashMap<>();
            for (int i=0;i<key.length;i++){
                map.put(key[i],value[i]);
            }
        } catch (Exception e) {
            map = null;
        }
    }
    public MUrl(String url, String path,String[] key, String[] value) {
        this(url,path,null,key,value);
    }

    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }
    public HashMap<String, String> getMap() {
        return map;
    }
    public boolean isSave(MUrl other){
        return this.url.equals(other.getUrl()) && this.path.equals(other.getPath()) && this.fileName.equals(other.getFileName());
    }
    public void setPathHome(String pathHome) {
        this.path = pathHome+this.path;
    }

    @Override
    public String toString() {
        return "MUrl{" +
                "url='" + url + '\'' +
                ", path='" + path + '\'' +
                ", fileName='" + fileName + '\'' +
                ", map=" + map +
                '}';
    }
}

