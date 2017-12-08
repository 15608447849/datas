package icbc.offline_xperience_achine.obj;

import icbc.offline_xperience_achine.obj.goodss.Goods;
import icbc.offline_xperience_achine.obj.menus.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/7/5.
 */
public class NorGoodsBean {
    private List<Menu> menu;
    private List<Goods> goods;

    public NorGoodsBean() {
        this.menu = new ArrayList<>();
        this.goods = new ArrayList<>();
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "NorGoodsBean{" +
                "menu=" + menu +
                ", goods=" + goods +
                '}';
    }
}
