package interaction;

import java.util.List;

/**
 * Created by user on 2017/7/13.
 */
public class GoodsRecodes {
    List<GoodsMenu> menu;
    List<Goods> goods;

    public GoodsRecodes(List<GoodsMenu> menu, List<Goods> goods) {
        this.menu = menu;
        this.goods = goods;
    }

    public List<GoodsMenu> getMenu() {
        return menu;
    }

    public void setMenu(List<GoodsMenu> menu) {
        this.menu = menu;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }
}
