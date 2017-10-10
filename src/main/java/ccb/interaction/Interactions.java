package ccb.interaction;

import ccb.Ccb;
import ccb.interaction.action.*;
import ccb.interaction.obj.JsBean;
import interfaces.ActionCall;
import interfaces.BaseThreadAdapter;

import java.util.HashMap;

/**
 * Created by user on 2017/9/15.
 */
public class Interactions extends BaseThreadAdapter {
    private final JsBean jsBean = new JsBean();
    private HashMap<String,String> header;

    public HashMap<String, String> getHeader() {
        return header;
    }

    public JsBean getJsBean() {
        return jsBean;
    }

    public Interactions(ActionCall action) {
        super(Ccb.getInstance(), action);
    }

    @Override
    protected void initParams() {
        super.initParams();
        if (header==null){
            header = new HashMap<>();
            header.put("Upgrade-Insecure-Requests","1");
            header.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
        }

    }

    @Override
    protected void workImps() throws Exception {
        jsBean.clear();
        //信用卡
        new CreditCard(this);
        //保险
        new Insurance(this);
        //贵金属
        new PreciousMetal(this);
        //理财
        new Finanece(this);
        //贷款
        new Loan(this);
        //优选基金
      new Fund1(this);
        //涨幅阶段  特色基金 人气基金
        new Fund2(this);
        //新发基金
        new Fund3(this);
        //非货币基金
        new Fund4(this);
        //写入js
        transInteractionGoods(jsBean,"data","JSON");
    }



















}
