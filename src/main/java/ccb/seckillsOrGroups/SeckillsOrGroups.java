package ccb.seckillsOrGroups;

import ccb.Ccb;
import ccb.seckillsOrGroups.obj.Bean;
import ccb.seckillsOrGroups.obj.JsBean;
import interfaces.ActionCall;
import interfaces.BaseThreadAdapter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by user on 2017/9/12.
 */
public class SeckillsOrGroups extends BaseThreadAdapter {
    private final String hPre = "http://buy.ccb.com";
    private final String kUrl = hPre+"/products/pbl_1_1_1.jhtml?source_name=1&source_type=srpc_ztg&source_content=xsqg";//秒杀
    //第一页
    private final String gUrl = hPre+"/groupbuy/index.jhtml?source_name=tg&source_type=srpc_ztg&source_content=tg";//团购 //http://buy.ccb.com/groupbuy/index.jhtml?source_name=tg&source_type=srpc_ztg&source_content=tg

    private final String gUrl_subUrl = "http://buy.ccb.com/groupbuy/gbl_0_0_0_0_cat1_0_0_%d.jhtml?buyType=01&selCoupon=0&channel_type=1";

    private JsBean jsBean  = new JsBean();
    //时间格式化工具
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    protected String dateStringToDate(String timeStr){
        Date d;
        try {
            d = sdf.parse(timeStr);
            long l = d.getTime();
            return String.valueOf(l);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStr;
}

    public SeckillsOrGroups(ActionCall action) {
        super(Ccb.getInstance(),action);
    }

    @Override
    protected void workImps() throws Exception {
            jsBean.clear();
            //秒杀
            kills(kUrl);
           //团购
            groups(gUrl);
            clearDocumentCache();
            //写入js
            transInteractionGoods(jsBean,"data","JSON_DATA");
    }



    private void kills(String url)throws Exception {
        Document document = getDocumentByUrl(url);
        Elements dl_list2_tab = document.select("dl[class=list2_tab]").select("a");
        for (int i = 0; i<dl_list2_tab.size();i++){
            killsGen(hPre + dl_list2_tab.get(i).attr("href"));
        }
    }

    private void killsGen(String url) throws Exception{
        Document document = getDocumentByUrl(url);
        killsGenItem(document.select("div[class=list2 clearfix]"));
    }

    private void killsGenItem(Elements div_list2_clear_fix) {
        Elements elements = div_list2_clear_fix.select("dl");
        Element dl;
        Element dt;
        Element span;
        Element a;
        String val;
        Bean bean;
        for (int i = 0; i < elements.size();i++){
            bean = new Bean();
            dl = elements.get(i);
            dt = dl.select("dt").first();
            span = dt.select("span").first();
            a = span.select("a").first();
            val = a.attr("href");
            bean.setGoods_id(val.substring(val.indexOf("_")+1,val.lastIndexOf("_")));
            bean.setBuy_link(hPre+val);
            infoPagePick(bean,1,0);
            jsBean.putkills(bean);
        }
    }

    private void infoPagePick(Bean bean, int type, int tag)  {
        try {
            Document document = getDocumentByUrl(bean.getBuy_link());
            
            if (type==1){
               killsInfo(bean,document);
            }else if (type == 2){
                groupsInfo(bean,document);
            }
        } catch (Exception e) {
            if (tag<3){
                tag++;
                infoPagePick(bean,type,tag);
            }
        }
    }



    private void killsInfo(Bean bean, Document document) throws Exception{
        //title
        Elements b_title = document.select("b[title]");
        bean.setTitle(b_title.text());
        //商城价
        Elements strong_id_price = document.select("strong[id=price]");
        bean.setPrice_old(strong_id_price.text().replace("¥",""));
        //抢购价
        Elements strong_id_flashSaleProdPriceShow = document.select("strong[id=flashSaleProdPriceShow]");
        bean.setPrice(strong_id_flashSaleProdPriceShow.text().replace("¥","").trim());
        //商品第一张图片
        Elements div_id_idList = document.select("div[id=idList]");
        String image = div_id_idList.select("a").attr("href") ;
        image = getGoodsImageUrl(image,bean.getGoods_id());
        bean.setImage_cover(image.substring(1));

        //数量 amount
        Elements input_id_flashSaleProdNum = document.select("input[id=flashSaleProdNum]");
        bean.setAmount(input_id_flashSaleProdNum.attr("value"));
        //开始时间 结束时间
        Elements script = document.select("script");
        for (Element element : script){
            if (element.html().contains("Utils.getSysRemainTime")){
                String val = element.html().replace("Utils.getFlashSaleProdLeftNum","");
                val = val.substring(val.indexOf(")")+2).trim().replace("Utils.getSysRemainTime","");
                val = val.substring(val.indexOf("(")+1,val.indexOf(")"));
                String[] params = val.split(",");
                bean.setTime_start(dateStringToDate(params[0].replace("\"","")));
                bean.setTime_end(dateStringToDate(params[1].replace("\"","")));
                break;
            }
        }
    }


    //团购
    private void groups(String gUrl) throws Exception {
        //获取分页信息
        Document document = getDocumentByUrl(gUrl);
        Elements div_class_page = document.select("div[class=page]");
        int page = div_class_page.select("a").size()-1;

        //获取推荐商品信息
        Elements div_groups = document.select("div[class=groups]");
        groupsParse(div_groups.select("dl").first());
        for (int i = 1 ;i<=page;i++){
            Document doc = getDocumentByUrl(String.format(gUrl_subUrl,i));
            Elements div_class_groups_prolist_clearfix= doc.select("div[class=groups_prolist clearfix]").select("dl");
            for (int j = 0;j <div_class_groups_prolist_clearfix.size();j++){
                groupsParse(div_class_groups_prolist_clearfix.get(j));
            }
        }
    }

    private void groupsParse(Element dl) {
        Bean bean = new Bean();
        Elements li_class_pro_name = dl.select("ul");
        String val = li_class_pro_name.first().select("a").first().attr("href");
        bean.setBuy_link(hPre+val );
        val = val.substring(val.indexOf("/")+1);
        val = val.substring(val.indexOf("/")+1,val.indexOf("."));
        bean.setGoods_id(val);
        infoPagePick(bean,2,0);
        jsBean.putGroups(bean);
    }


    private void groupsInfo(Bean bean, Document document) {
        //标题
        Elements div_class_protitle = document.select("div[class=protitle]");
        bean.setTitle(div_class_protitle.select("strong").first().text());
        //商城价
        Elements del_class_gray = document.select("del[class=gray]");
        bean.setPrice_old(del_class_gray.text().replace("¥","").trim());

        //现在价格
        Elements span_class_price_bg = document.select("span[class=price_bg]");
        bean.setPrice(span_class_price_bg.text().replace("¥","").trim());

        //discount 商品折扣
        Elements span_class_group_dis = document.select("span[class=group_dis]");
        bean.setDiscount(span_class_group_dis.text().replace("¥","").trim().replace("折",""));

        //图片
        Elements img_class_izImage = document.select("img[class=izImage]");
        String image = img_class_izImage.attr("src").replace("_3","_4");
        image = getGoodsImageUrl(image,bean.getGoods_id());
        bean.setImage_cover(image.substring(1));

        Elements script = document.select("script");
        int tag = 0;
        for (Element it : script){
            if (it.html().contains("id='grp_no'")){
                String[] arrStr = it.html().split("\\$");
                String num = arrStr[2];
                num =num.substring(num.indexOf(">")+1);
                num = num.substring(0,num.indexOf("<"));
                bean.setAmount(num);  //已购买人数
                tag++;
            }else if (it.html().contains("groupsUtils.getSysRemainTime")){
                String val = it.html().trim();
                val = val.substring(val.indexOf("(")+1,val.indexOf(")"));
                String[] params = val.split(",");
                bean.setTime_start(dateStringToDate(params[0].replace("\"",""))); //开始时间
                bean.setTime_end(dateStringToDate(params[1].replace("\"",""))); //结束时间
                tag++;
            }
            if (tag>=2) break;
        }

    }


}
