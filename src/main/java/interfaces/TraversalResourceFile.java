package interfaces;

import com.winone.ftc.mtools.MD5Util;
import lunch.Say;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;

/**
 * Created by user on 2017/9/5.
 */
public class TraversalResourceFile extends SimpleFileVisitor<Path>{
    private final TRAction action;
    private final Object attr;
    public TraversalResourceFile(String root,Object attr,TRAction action) {
        if (root==null || attr==null || action==null) throw new IllegalArgumentException("param is null.["+root+","+attr+","+action+"]");
        this.action = action;
        this.attr = attr;
        try {
            Files.walkFileTree(Paths.get(root), this);
        } catch (IOException e) {
            action.onError(e);
        }
    }

    // 在访问子目录前触发该方法
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return action.onReceive(dir,attrs,attr);
    }

    @Override
    public FileVisitResult visitFileFailed(Path path, IOException e) throws IOException {
        return FileVisitResult.TERMINATE;
    }
    @Override
    public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) {
        return action.onReceive(filePath,attrs,attr);
    }
}
