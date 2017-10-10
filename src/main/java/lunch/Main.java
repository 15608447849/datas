package lunch;

import icbc.Icbc;
import icbc.credit_card.CreditCard;
import interfaces.ActionCall;
import interfaces.CatchBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 2017/7/7.
 */
public class Main {
    // 主目录 主文件夹 执行时间或类型(-1 -2 -3 正数) 并发线程数  本地端口号(默认65000)
    public static void main(String[] args){
            new LunchThread();
//            while(true);
    }
}
