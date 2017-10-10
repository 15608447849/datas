package icbc.interaction;

import icbc.fund.obj.HighProfit;

/**
 * Created by user on 2017/7/3.
 */
public class FundProfitGoods extends Goods {

    public FundProfitGoods(int id,HighProfit h){
        setId(id+"");
        setClazz("4");
        setLink(h.getQrcode_char());
        setCode(h.getCode());
        setName(h.getName());
        setWorth_unit(h.getUnit_income_rate());
        setWorth_total(h.getAll_income_rate());
        setWorth_time(h.getUnit_income_date());
        setRise_day(h.getDay_rise_rate());
        setRise_this_year(h.getRise_this_year());
        setRise_month(h.getRise_month());
        setRise_three_month(h.getRise_three_month());
        setRise_half_year(h.getRise_half_year());
        setRise_year(h.getRise_year());
        setStar(h.getStar_grade()+"");
    }
}
