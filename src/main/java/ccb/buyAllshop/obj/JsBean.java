package ccb.buyAllshop.obj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/9/13.
 */
public class JsBean {
    private List<FatClass> menu = new ArrayList<>();
    private List<Shop> goods = new ArrayList<>();

    public List<FatClass> getMenu() {
        return menu;
    }


    public List<Shop> getGoods() {
        return goods;
    }


    public void clear(){
        menu.clear();
        goods.clear();
    }

    public void putMenu(FatClass fatClass){
        menu.add(fatClass);
    }
    public void putGoods(Shop shop){
        goods.add(shop);
    }

    public void putGoodsByList(List<Shop> shopList) {
        for (Shop shop : shopList){
            putGoods(shop);
        }
    }
}
