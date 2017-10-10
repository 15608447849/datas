package icbc.interaction;

import icbc.fund.obj.HotFund;

/**
 * Created by user on 2017/7/3.
 */
public class FundHotGoods extends Goods {
    public FundHotGoods(int id,HotFund h){
        setId(id+"");
        setClazz("3");
        setLink(h.getQrcode_char());
        setCode(h.getCode());
        setName(h.getName());
        setKind(h.getKind());
        setType(h.getType());
        setWorth_yesterday(h.getDay_income_rate());
        setWorth_seven_day(h.getWeek_income_rate());
        setRise_day(h.getDay_rise_rate());
        setRise_trimester(h.getMonth_rise_rate());
        setStar(h.getStar_grade()+"");
        setStatus(h.getStatus());
    }
}
