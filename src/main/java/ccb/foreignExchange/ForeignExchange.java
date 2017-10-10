package ccb.foreignExchange;

import ccb.Ccb;
import ccb.foreignExchange.obj.Bean;
import ccb.foreignExchange.obj.JsBean;
import icbc.foreign_exchange.obj.ForeignExchangeBean;
import interfaces.ActionCall;

/**
 * Created by user on 2017/9/15.
 * 同工商银行外汇一样,js格式有变化
 */
public class ForeignExchange extends icbc.foreign_exchange.ForeignExchange {
    private JsBean js = new JsBean();
    public ForeignExchange(ActionCall action) {
        super(Ccb.getInstance(),action);
    }

    @Override
    protected void workImps() throws Exception {
        super.workImps();
        js.clear();
        for (ForeignExchangeBean it:beanList){
            js.put(new Bean(it));
        }
        //写入js
        transInteractionGoods(js,"foreign_exchange","JSON1");
    }
}
