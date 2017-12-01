package ccb.interaction.action;

import ccb.interaction.Interactions;
import ccb.interaction.obj.creditcard.CreditCardBean;
import ccb.interaction.obj.creditcard.Jentity;
import ccb.interaction.obj.creditcard.JentityBean;
import com.google.gson.Gson;
import com.winone.ftc.mtools.FileUtil;
import com.winone.ftc.mtools.StringUtil;
import lunch.Say;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

/**
 * Created by user on 2017/9/19.
 */
public class CreditCard extends InteractionsSubImp {
    private String Pre;
    private String mUrl;
    private String mFUrl;
    private String link;
    public CreditCard(Interactions interaction) throws Exception {
        super(interaction);

    }
    @Override
    public void init(){
        Pre = "http://creditcard.ccb.com";
        mUrl = "http://creditcard.ccb.com/cn/creditcard/card_list.html";
        mFUrl = "http://creditcard.ccb.com/webtran/get_crd_info.gsp?table_type=1&card_type=%s&card_prov=1001&card_city=1";
        link = "http://creditcard.ccb.com/cn/creditcard/cards/%s.html";
    }
    @Override
    void execute() throws Exception {
        //获取标签 -> 获取json对象 -> 转换成信用卡数据对象
        HashMap<String,Jentity>  content = getJsonData(getTagMap());
        transData(content);
    }

    public Map<String,String> getTagMap() throws Exception {
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        Document document = interaction.getDocumentByUrl("http://creditcard.ccb.com/cn/creditcard/card_list.html");
        Elements li_list = document.select("ul[class=xykxp_card_type_ul]").select("li");
        for (Element it : li_list){
            map.put(it.text(),StringUtil.encodeUrl(StringUtil.encodeUrl(it.text())));
        }
        return map;
    }

    private HashMap<String,Jentity>  getJsonData(Map<String,String> tagMap) throws Exception {
        HashMap<String,Jentity> map = new HashMap<>();
        Iterator<Map.Entry<String,String>> iterator = tagMap.entrySet().iterator();
        Map.Entry<String,String> entity;
        String json;
        Jentity jentity;
        while (iterator.hasNext()){
            entity = iterator.next();

            json = getStringJson(entity.getValue());
            jentity = getJsonEntity(json);

            map.put(entity.getKey(),jentity);
        }

        return map;
    }


    private String getStringJson(String tag) throws Exception {
        String url = String.format(mFUrl,tag);
        String json = interaction.getTextByUrl(url);
        return json;
    }
    private Jentity getJsonEntity(String json) {
        return new Gson().fromJson(json,Jentity.class);
    }

    private void transData(HashMap<String,Jentity> map) {
        Iterator<Map.Entry<String,Jentity>> iterator = map.entrySet().iterator();
        Map.Entry<String,Jentity> entry;
        Jentity jentity;
        while (iterator.hasNext()){
            entry = iterator.next();
            jentity = entry.getValue();
                if (jentity.getObj()==null || jentity.getObj().size()==0) continue;
                for (JentityBean it : jentity.getObj()){
                    CreditCardBean card = new CreditCardBean();
                    card.setId(interaction.getJsBean().getCreditcard().size()+1);
                    card.setType(entry.getKey());
                    card.setName(it.getTitle());
                    card.setCard_img(interaction.getGoodsImageUrl(Pre+it.getCardimg(),entry.getKey()+ FileUtil.SEPARATOR+it.getTitle()));
                    card.setDesc1(it.getSubtitle().replace("|@|","、"));
                    card.setQr_code(String.format(link,it.getDcrindex()));
                    card.setDesc2(pick(card.getQr_code()));
                    interaction.getJsBean().getCreditcard().add(card);
                }
        }

    }

    private String pick(String qr_code) {
        try {
            Document document = interaction.getDocumentByUrl(qr_code);
            Element div = document.select("div[class=details_box_d_Rbox]").first();
            return div.text();
        } catch (Exception e) {
            return pick(qr_code);
        }
    }


}
