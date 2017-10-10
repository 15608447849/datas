package abchina.rate;

import abchina.Abchina;
import icbc.foreign_exchange.obj.ForeignExchangeBean;
import interfaces.ActionCall;
import lunch.Say;


/**
 * Created by user on 2017/10/10.
 * 农业银行利率 (从工商银行获取-与建设银行格式一样,复用)
 *
 var JSON1 = {
 rate:[
          {
             id:1001,
             country:'USD',
             purchase:'666.66',
             out:'55555'
             }
      ]
 }
 *
 */
public class Rate extends icbc.foreign_exchange.ForeignExchange {
    private final ccb.foreignExchange.obj.JsBean js = new ccb.foreignExchange.obj.JsBean();
    public Rate(ActionCall action) {
            super(Abchina.getInstance(),action);
    }

    @Override
    protected void workImps() throws Exception {
        super.workImps();
        js.clear();
        for (ForeignExchangeBean it:beanList){
            js.put(new ccb.foreignExchange.obj.Bean(it));
        }
        //写入js
        transInteractionGoods(js,"rate","JSON1");
    }
}
