package icbc.interaction;

import icbc.financial.obj.FinancialBean;

/**
 * Created by user on 2017/7/3.
 */
public class FinancialGoods extends Goods {

    public FinancialGoods(int id,FinancialBean bean) {
        this.setId(id+"");
        this.setClazz("2");
        this.setName(bean.getName());
        this.setLink(bean.getQrcode_char());
        this.setYield(bean.getYear_income_rate());
        this.setRisk_level(bean.getGrade().replace("pr",""));
        this.setInfo(bean.getTitle());
        this.setStart_price(bean.getLimit_price());
        this.setTime_limit(bean.getDate_limit()+"天");
        this.setLink("https://0x9.me/Spfol");
        toGoodsRecode();
    }

}
