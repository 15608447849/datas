package interaction;

import precious_metal.obj.MetalSource;

/**
 * Created by user on 2017/7/3.
 */
public class MetalGoods extends Goods {
    public MetalGoods(){}
    public MetalGoods(int id,MetalSource m){
        setId(id+"");
        setClazz("6");
        setName(m.getMetalname());
        setIs_rise(m.getUpordown());
        setPrice_buy(m.getBuyprice());
        setPrice_sell(m.getSellprice());
        setPrice_center(m.getMiddleprice());
        setRise_day_val(m.getOpenprice_dv());
        setRise_day_range(m.getOpenprice_dr());
        setRise_year_range(m.getOpenprice_yr());
        toGoodsRecode();
    }
}
