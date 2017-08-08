package insurance;

import com.google.gson.Gson;
import insurance.obj.InsuranceBean;
import interaction.Goods;
import interaction.InsuranceGoods;
import interfaces.ActionCall;
import interfaces.BaseThread;
import lunch.Param;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by user on 2017/6/27.
 * 保险
 */
public class Insurance extends BaseThread {
    private ArrayList<InsuranceBean> insuranceArrayList = new ArrayList<>();
    private ArrayList<InsuranceGoods> goodsList = new ArrayList<>();
    private final String URL_HOME = "http://mall.icbc.com.cn";
    private final String URL =URL_HOME+"/searchproducts/pv.jhtml?searchType=DISPLAYCATEGORY&displayCatId=000000000010118";
    public Insurance(ActionCall action){
        super(Param.HOME,Param.PATH);
        this.setActionCall(action);
        start();
    }


    @Override
    protected void workImps() throws Exception {
        insuranceArrayList.clear();
        Document doc = Jsoup.connect(URL).timeout(TIMEOUT).get();
        Elements divs = doc.select("div[class=pro]");
        InsuranceBean insurance;
        String string;
        for (int i=0;i<divs.size();i++){
            insurance = new InsuranceBean();
            insurance.setQrcode_char(URL_HOME+divs.get(i).select("a").get(1).attr("href"));
            insurance.setName(divs.get(i).select("a").get(1).text());
            string = divs.get(i).select("div[class=p-price]").text();
            insurance.setPrice(string.substring(string.indexOf("￥")+1));
            insurance.setShow_img(getGoodsImageUrl(divs.get(i).select("img").first().attr("src")));
            insuranceArrayList.add(insurance);
            goodsList.add(new InsuranceGoods(i,insurance));
        }
        //根据类型创建Map - > json
        Gson gson = new Gson();
        String result = gson.toJson(insuranceArrayList);
        writeJSON(result);
    }

    @Override
    protected void work2Imps() throws Exception {
        if (goodsList.size()>0){
            for (Goods bean : goodsList){
                bean.setImg_cover(bean.getImg_cover().substring(1));
            }
            transInteractionGoods(goodsList, InsuranceGoods.class,"JSON_DATA",false);
        }
    }
}
