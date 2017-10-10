package lunch;

import com.winone.ftc.mtools.FileUtil;
import com.winone.ftc.mtools.StringUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2017/8/7.
 */
public class Param {

    public static String HOME = ".";
    public static String PATH ="/o2o";
    public static void setHome(String home) throws IOException {
        File file = new File(home);
        if (!file.exists()){
            if (!file.mkdir()){
                throw new IOException("创建文件夹:"+ home+",失败.");
            }
        }
        HOME = file.getAbsolutePath();
        HOME.replaceAll("\\\\","/");
        if (HOME.endsWith("/")){
            HOME = HOME.substring(0,HOME.length()-1);
        }
        HOME = home;
    }
    public static void setPATH(String path){
        PATH = path;
        if (!PATH.startsWith("/")) PATH = FileUtil.SEPARATOR+PATH;
    }
    public static String replaceBlank(String str) {
        String dest = null;
        if (str!=null) {
            Pattern p = Pattern.compile("\\s+|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }




}