package interfaces;

import com.winone.ftc.mtools.FileUtil;

/**
 * Created by user on 2017/8/23.
 */
public class StoreTextBean {
    private String text ;
    private String filePath;
    private String fileName;
    public StoreTextBean(String text, String filePath,String fileName) {
        this.text = text;
        this.filePath = filePath;
        this.fileName = fileName;
    }
    protected void store(){
        FileUtil.writeStringToFile(text, filePath, fileName, false);
    }
}
