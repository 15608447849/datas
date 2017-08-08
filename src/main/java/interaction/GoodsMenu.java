package interaction;

/**
 * Created by user on 2017/7/13.
 */
public class GoodsMenu extends Goods {
    public GoodsMenu(String id,String name) {
        setId(id);
        setName(name);
    }
    public boolean isSave(Goods goods){
        return getId().equals(goods.getClazz());
    }
}
