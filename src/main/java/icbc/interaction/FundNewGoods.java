package icbc.interaction;

import icbc.fund.obj.NewFund;

/**
 * Created by user on 2017/7/3.
 */
public class FundNewGoods extends Goods {

    public FundNewGoods(int id,NewFund h){
        setId(id+"");
        setClazz("5");
        setLink(h.getQrcode_char());
        setCode(h.getCode());
        setName(h.getName());
        setMoney_type(h.getCurrency());
        setKind(h.getKind());
        setType(h.getType());
        setTime(h.getIssue_date());
        setPrice(h.getIssue_price());
    }
}
