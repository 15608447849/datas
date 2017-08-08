package lunch;

import java.util.ArrayList;

/**
 * Created by user on 2017/7/7.
 */
public class Main {
    public static void main(String[] args){
        try {
            if (args!=null && args.length>=1) Param.setHome(args[0]);
                else Param.setHome("C:/ICBC");
//            long loopTime = 24 * 60 * 60 * 1000;
            long loopTime = -1; //

            if (args!=null && args.length>=2) {
                if (args[1].equals("-1") || args[1].equals("-2") || args[1].equals("-3")){
                    loopTime = Integer.parseInt(args[1]);
                }else{
                    try {
                        loopTime = Integer.parseInt(args[1]);
                        if (loopTime < 0){
                            loopTime = -1;
                        }
                        else{
                             loopTime *= 1000L;
                        }
                    } catch (NumberFormatException e) {
                        Say.I("循环时间输入错误,请重新输入.");
                        return;
                    }
                }
            }
            int threadNumber = 1;
            if (args!=null && args.length>=3){
                try {
                    threadNumber = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    Say.I("设置队列下载最大任务数错误.");
                    return;
                }
            }
            ArrayList<String> list = new ArrayList();
                list.add("offline_xperience_achine.NormalGood");
                list.add("offline_xperience_achine.GroupBuy");
                list.add("fund.Fund");
                list.add("credit_card.CreditCard");
                list.add("financial.Financial");
                list.add("precious_metal.PreciousMetal");
                list.add("person_loan.PersonLoan");
                list.add("insurance.Insurance");

            new LunchThread(list, loopTime,threadNumber); //24小时自动抓取一次

        } catch (Exception e) {
        }
    }
}
