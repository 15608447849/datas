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
    public StoreTextBean(String text, String fullPath) {
        this.text = text;
        fullPath = FileUtil.replaceFileSeparatorAndCheck(fullPath,null,null);
        this.filePath = fullPath.substring(0,fullPath.lastIndexOf(FileUtil.SEPARATOR));
        this.fileName = fullPath.substring(fullPath.lastIndexOf(FileUtil.SEPARATOR)+1);
    }
    public void store(){
        FileUtil.writeStringToFile(text, filePath, fileName, false);
    }

    public void delete() {
        FileUtil.deleteFile(filePath+FileUtil.SEPARATOR+fileName);
    }
}
