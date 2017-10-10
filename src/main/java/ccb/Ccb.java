package ccb;

import icbc.Icbc;
import interfaces.ParamManager;
import interfaces.ParamManagerAdapter;
import lunch.Say;

import java.util.HashMap;

/**
 * Created by user on 2017/9/11.
 */
public class Ccb extends ParamManagerAdapter{
    private Ccb() {
        super("ccb");
        map.put("ccb.seckillsOrGroups.SeckillsOrGroups","善融商城秒杀团购");
        map.put("ccb.buyAllshop.BuyAllShop","善融商城全部商品");
        map.put("ccb.foreignExchange.ForeignExchange","建设银行外汇");
        map.put("ccb.accountGold.AccountGold","建设银行个人贵金属");
        map.put("ccb.interaction.Interactions","建设银行互动产品");
    }
    private static class Holder{
        private static final Ccb instance = new Ccb();
    }
    public static Ccb getInstance(){
        return Ccb.Holder.instance;
    }


    @Override
    public String toString() {
        return "中国建设银行";
    }
}
