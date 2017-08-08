package interaction;

import credit_card.obj.CreditCardJsonBean;

/**
 * Created by user on 2017/7/3.
 */
public class CreditCardGoods extends Goods {
    public CreditCardGoods(int id,CreditCardJsonBean bean) {
        setId(id+"");
        setClazz("1");
        setName(bean.getCard_name());
        setArea(bean.getCard_area());
        setBrand(bean.getBrand_st());
        setMoney_type(bean.getCurrency());
        setDesc(bean.getCard_desc());
        setImg_cover(bean.getCard_cover());
        toGoodsRecode();
    }
}
