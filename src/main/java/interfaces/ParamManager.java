package interfaces;

/**
 * Created by user on 2017/8/21.
 */
public interface ParamManager {
    void setHomeFile(String path);
    void setDirs(String path);
    String getHomeFile();
    String getPathFile();
    String getKeyTitle(String key);
    void setSaveDay(int day);
    int getSaveDay();
}
