package icbc;

import com.winone.ftc.mtools.FileUtil;
import icbc.interaction.GoodsDataAllRecode;
import interfaces.ParamManager;
import interfaces.ParamManagerAdapter;
import interfaces.Tuple;

import javax.xml.ws.Holder;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 2017/8/21.
 */
public class Icbc extends ParamManagerAdapter {
    private final List<String> goodsList = new ArrayList();
    private Icbc(){
        super("icbc");
        map.put("icbc.offline_xperience_achine.NormalGood","融E购正常商品");
        map.put("icbc.offline_xperience_achine.GroupBuy","融E购秒杀团购");
        map.put("icbc.fund.Fund","工商银行基金");
        map.put("icbc.credit_card.CreditCard","工商银行信用卡");
        map.put("icbc.financial.Financial","工商银行金融产品");
        map.put("icbc.insurance.Insurance","工商银行保险");
        map.put("icbc.person_loan.PersonLoan","工商银行个人理财");
        map.put("icbc.precious_metal.PreciousMetal","工商银行贵金属");
        map.put("icbc.foreign_exchange.ForeignExchange","工商银行利率");

        goodsList.add("icbc.credit_card.CreditCard");
        goodsList.add("icbc.financial.Financial");
        goodsList.add("icbc.fund.Fund");
        goodsList.add("icbc.precious_metal.PreciousMetal");
        goodsList.add("icbc.person_loan.PersonLoan");
        goodsList.add("icbc.insurance.Insurance");
        GoodsDataAllRecode.get().init(this);
    }

    private static class Holder{
        private static final Icbc instance = new Icbc();
    }
    public static Icbc getInstance(){
        return Holder.instance;
    }
    public List<String> getList(){
        return goodsList;
    }

    public final String[] IMAGE_KEYS = new String[]{"Referer"};
    public final String[] IMAGE_VALUES =  new String[]{"http://mall.icbc.com.cn/"};
    private final Tuple<String[],String[]> IMAGR_PARAMS = new Tuple<>(IMAGE_KEYS,IMAGE_VALUES);
    public Tuple<String[],String[]> getImageParams(){
        return IMAGR_PARAMS;
    }

    @Override
    public String toString() {
        return "中国工商银行";
    }
}
