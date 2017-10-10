package ccb.accountGold;

import ccb.Ccb;
import ccb.accountGold.obj.Bean;
import ccb.accountGold.obj.JsBean;
import ccb.accountGold.obj.ReferencePriceSettlement;
import ccb.accountGold.obj.ReferencePriceSettlements;
import interfaces.ActionCall;
import interfaces.BaseThreadAdapter;
import interfaces.Tuple;
import lunch.Say;

import java.beans.Transient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 2017/9/15.
 */
public class AccountGold extends BaseThreadAdapter {
    private JsBean jsBean = new JsBean();
    public AccountGold(ActionCall action) {
        super(Ccb.getInstance(), action);
    }

    @Override
    protected void workImps() throws Exception {
        String xml = getTextByUrl("http://gold.ccb.com/cn/home/news/zhgjs_new.xml");
        ReferencePriceSettlements xmlBean =  xmlToObject(xml, ReferencePriceSettlements.class);
        for (ReferencePriceSettlement it : xmlBean.getList()){
            jsBean.add(translate(it));
        }
        //写入js
        transInteractionGoods(jsBean,"AccountPreciousmeta","JSON2");
    }


    private Bean translate(ReferencePriceSettlement xmlBean){

        Tuple<String,Integer> tuple = transType(xmlBean.getPm_txn_vrty_cd());
        int index = tuple.getParam2();
        String SEC_COD = tuple.getParam1();
        String CURR1 = transUnit(xmlBean.getCcycd());
        String BUY_PRI = xmlBean.getCst_buy_prc();// 银行买入价 == 客户卖出价
        String SEL_PRI = xmlBean.getCst_sell_prc();// 银行卖出价 == 客户买入价
        String PRICE_TIME = transDate(xmlBean.getTms());// 报价时间
        return new Bean(index,SEC_COD,CURR1,BUY_PRI,SEL_PRI,PRICE_TIME);
    }


    private Tuple<String, Integer> transType(String SEC_COD){
        if     (SEC_COD.equals("02")){return new Tuple("AU9995",3);}
        else if(SEC_COD.equals("01")){return new Tuple("AU9999",2);}
        else if(SEC_COD.equals("21")){return new Tuple("人民币银",4);}
        else if(SEC_COD.equals("22")){return new Tuple("人民币铂",7);}
        else if(SEC_COD.equals("15")){return new Tuple("美元金(钞)",0);}
        else if(SEC_COD.equals("16")){return new Tuple("美元金(汇)",1);}
        else if(SEC_COD.equals("17")){return new Tuple("美元银(钞)",5);}
        else if(SEC_COD.equals("18")){return new Tuple("美元银(汇)",6);}
        else if(SEC_COD.equals("19")){return new Tuple("美元铂(钞)",8);}
        else if(SEC_COD.equals("20")){return new Tuple("美元铂(汇)",9);}
        else                           {return new Tuple("未知",-1);}
    }
    private String transUnit(String CURR1){ return  CURR1.equals("156")?"元/克":CURR1.equals("840")?"美元/盎司":"未知";}
    private String transDate(String PRICE_TIME){
        return  dateFormat(PRICE_TIME,"yyyy-MM-dd HH:mm:ss.ms","MM月dd日 HH:mm分");
    }

}
