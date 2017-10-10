package icbc.interaction;

import icbc.insurance.obj.InsuranceBean;

/**
 * Created by user on 2017/7/3.
 */
public class InsuranceGoods extends Goods {
    public InsuranceGoods(int id,InsuranceBean bean) {
        setClazz("7");
        setId(id+"");
        setImg_cover(bean.getShow_img());
        setName(bean.getName());
        setPrice(bean.getPrice());
        setLink(bean.getQrcode_char());
        toGoodsRecode();
    }
}
