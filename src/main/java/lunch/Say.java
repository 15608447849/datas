package lunch;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 2017/6/24.
 */
public class Say {
    private static final SimpleDateFormat format = new SimpleDateFormat("[ yyyy-MM-dd HH:mm:ss]\t");//:ms
    private static boolean print = true;
    public static void I(Object object){
        if (print){
            System.out.println(format.format(new Date())+"    "+object);
        }
    }
}
