package interaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/7/4.
 */
public class FundGoods {
    private List<Goods> menu;
    private List<Goods> goods;

    public FundGoods(List<Goods> fundHotList, List<Goods> fundNew, List<Goods> fundProfit) {
        this.menu = getMenu();
        this.goods = new ArrayList<>();
        getContent(fundHotList);
        getContent(fundNew);
        getContent(fundProfit);
    }


    public List<Goods> getMenu() {
        List<Goods> list = new ArrayList<>();
        Goods goods = new Goods();
        goods.setId("3");
        goods.setName("基金-热销推荐");
        list.add(goods);

        goods = new Goods();
        goods.setId("4");
        goods.setName("基金-收益排行");
        list.add(goods);

        goods = new Goods();
        goods.setId("5");
        goods.setName("基金-新发产品");
        list.add(goods);
        return list;
    }


    public void getContent(List<Goods> goodsList) {
      for (Goods g :goodsList){
          g.setId((goods.size()+1)+"");
            goods.add(g);
      }
    }

    public void selfLoop() {
        for (Goods goodsBean : goods){
            goodsBean.toGoodsRecode();
        }
    }
}
