package precious_metal;

import com.google.gson.*;
import interaction.MetalGoods;
import interfaces.ActionCall;
import interfaces.BaseThread;
import lunch.Param;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import precious_metal.obj.Metal;
import precious_metal.obj.MetalSource;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2017/7/3.
 * 贵金属
 */
public class PreciousMetal extends BaseThread{
    private final String url = "https://mybank.icbc.com.cn/servlet/AsynGetDataServlet?Area_code=0200&proIdsIn=&isFirstTime=1&tranCode=A00462";
    private List<Metal> dataList = null;
    private List<MetalSource> sourceList = null;
    private List<MetalGoods> goodsList = null;
    public PreciousMetal(ActionCall action) {
        super(Param.HOME,Param.PATH);
        this.setActionCall(action);
        start();
    }
    @Override
    protected void workImps() throws Exception {
        parse();
        writeJSON(new Gson().toJson(dataList));

        transInteractionGoods(goodsList, MetalGoods.class,"JSON_DATA",false);
    }



    public void parse() throws Exception{
            Document document = Jsoup.parse(new URL(url).openStream(), "GBK", url);
            String html = document.text();
            if (html == null) throw new NullPointerException("metal get html is null.");
            getMetalSources(html);
            source2Metal();
            source2Goods();
    }

    /**
     * 解析json，获取黄金数据对象集合并返回
     *
     * @param html
     * @return
     */
    private void getMetalSources(String html) {
        sourceList=new LinkedList<>();
        JsonObject returnData = new JsonParser().parse(html).getAsJsonObject();
        JsonElement jel = returnData.get("market");
        JsonArray jsonArray = null;
        if (jel.isJsonArray()) {
            jsonArray = jel.getAsJsonArray();
        }
        if (jsonArray != null) {
            for (JsonElement obj : jsonArray) {
                MetalSource metalSource = new Gson().fromJson(obj,
                        MetalSource.class);
                sourceList.add(metalSource);
            }
        }
    }
    private void source2Goods(){
        goodsList=new LinkedList<>();
        for (int i = 0; i <sourceList.size(); i++){
            goodsList.add(new MetalGoods(i +1,sourceList.get(i)));
        }
    }
    private void source2Metal() {
        dataList=new LinkedList<>();
        long id = 0;
        for (MetalSource metalSource : sourceList) {
            id++;
            Metal metal = new Metal();
            String metalName = metalSource.getMetalname();
            String money = getCurrency(metalName);

            metal.setMoney(money);
            metal.setIs_rise(Integer.parseInt(metalSource.getUpordown()));
            String kind = getKind(metalName);

            metal.setKind(kind);
            metal.setBuy_price(metalSource.getBuyprice());
            metal.setSale_price(metalSource.getSellprice());
            metal.setMid_price(metalSource.getMiddleprice());
            metal.setDay_rise(metalSource.getOpenprice_dv());
            metal.setDay_rise_rate(metalSource.getOpenprice_dr());
            metal.setYear_rise_rate(metalSource.getOpenprice_yr());
            dataList.add(metal);

        }

    }
    private final Map<String, String> currencyMap = new HashMap<String, String>() {
        {
            put("人民币", "cny");
            put("美元", "usd");
            put("英镑", "gbp");
            put("欧元", "eur");
            put("日元", "jpy");
            put("港币", "hkd");
        }
    };
    private final Map<String, String> kindMap = new HashMap<String, String>() {
        {
            put("黄金", "gold");
            put("钯金", "palladium");
            put("铂金", "platinum");
            put("银", "silver");
        }
    };
    private String getCurrency(String metalName) {
        for (String key : currencyMap.keySet()) {
            if (metalName.contains(key))
                return currencyMap.get(key);
        }
        return "";
    }
    private String getKind(String metalName) {
        for (String key : kindMap.keySet()) {
            if (metalName.contains(key))
                return kindMap.get(key);
        }
        return "";
    }
}
