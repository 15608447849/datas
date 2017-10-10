package ccb.interaction.action;

import ccb.interaction.Interactions;
import ccb.interaction.obj.precious.JentityResultBean;
import ccb.interaction.obj.precious.PmJson;
import ccb.interaction.obj.precious.PreciousMetalBean;
import com.google.gson.Gson;
import com.winone.ftc.mtools.FileUtil;
import lunch.Say;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Created by user on 2017/9/18.
 */
public class PreciousMetal extends InteractionsSubImp {
    private final String preUrl = "http://gold.ccb.com";
    private final String mainUrl = preUrl+"/cn/gold/swjzs.html";
    private final String link = "http://gold.ccb.com/cn/gold/%s.html";
    public PreciousMetal(Interactions interaction) throws Exception {
        super(interaction);
    }

    @Override
    void execute() throws Exception {

        Document document  = interaction.getDocumentByUrl(mainUrl);
        Elements div_maininfo_td_list = document.select("div[id=maininfo]").select("td");
        for (Element it : div_maininfo_td_list){
            parse(it.select("a"));
        }
    }

    private void parse(Elements aTagList) {
        String val = aTagList.last().attr("href").trim();

        String name = aTagList.last().html();
        String gifUrl = preUrl + aTagList.first().select("img").first().attr("src");
        gifUrl = interaction.getGoodsImageUrl(gifUrl,name);
        String[] attr = new String[]{name,gifUrl};

        if (val.contains("gjs_product.html?cid=")){
            val = val.replace("gjs_product.html?cid=","");

            getContent(val,1,15,1,attr);
        }
        else if (val.contains("second_category.html?cid=")){
            val = val.replace("second_category.html?cid=","");
            getContent(val,1,15,2,attr);
        }
    }

    private void getContent(String val,int page,int pageSize,int type,String[] attr) {
        if (type == 0) return;
        if (page==0) page=1;
        if (pageSize==0) pageSize=15;
        String url = "" ;
        if (type == 1){
            url = String.format("http://gold.ccb.com/webtran/gold_product_list.gsp?cid=%s&pageSize=%d&pageNo=%d",val,pageSize,page);
        }
        if (type==2){
            url = String.format("http://gold.ccb.com/webtran/gold_category_list.gsp?fcid=%s&pageSize=%d&pageNo=%d",val,pageSize,page);

        }
        //开始获取json数据
        try {
            String jsonText = interaction.getTextByUrl(url);
            PmJson pmJson = new Gson().fromJson(jsonText,PmJson.class);
            if (pmJson.getTotalCount()>pmJson.getPageSize()){
                getContent(val,1,pmJson.getTotalCount(),type,attr);
            }else{
                if (type==1){
                    transData(pmJson,attr);
                }
                if (type==2){
                    filterData(pmJson,attr);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void filterData(PmJson pmJson,String[] attr) {
        String cid;
        String name;
        String img;
        String[] nattr;
        String path;
        for (JentityResultBean resultBean : pmJson.getResultList()){
                cid = resultBean.getcId();
                name = resultBean.getcName();
                path = name;
                if (attr.length>=1) path = attr[0] + FileUtil.SEPARATOR + name;
                img = interaction.getGoodsImageUrl(preUrl+resultBean.getPic(),path);
                nattr = new String[]{name,img,path};
                getContent(cid,1,15,1,nattr);
        }
    }

    //转换数据
    private void transData(PmJson pmJson,String[] attr) {

        String type =   attr[0];
        String type_img =   attr[1];
        String path = type;
        if (attr.length>=3){
            path = attr[2];
        }
        String name;
        int id;
        String product_img;
        String qr_code;
        for (JentityResultBean resultBean : pmJson.getResultList()){
            id = interaction.getJsBean().getPreciousMetal_1().size()+1;
            name = resultBean.getTitle();
            product_img = interaction.getGoodsImageUrl(preUrl+resultBean.getPic(),path + FileUtil.SEPARATOR+name);
            qr_code = String.format(link,resultBean.getpId());
            interaction.getJsBean().getPreciousMetal_1().add(new PreciousMetalBean(id,type,type_img,name,product_img,qr_code));
        }

    }


}
