package interfaces;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by user on 2017/9/5.
 */
public interface TRAction {
    /**
     return FileVisitResult.TERMINATE;
     return FileVisitResult.CONTINUE;
     */
    FileVisitResult onReceive(Path filePath, BasicFileAttributes attrs,Object attr);
    void onError(Exception e);
}
