package icbc.offline_xperience_achine;

import icbc.Icbc;
import interfaces.ActionCall;
import interfaces.BaseThread;

import lunch.Param;
import icbc.offline_xperience_achine.obj.GroupsSeckillsBean;
import icbc.offline_xperience_achine.obj.groups.Groups;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2017/7/5.
 * 团购秒杀
 */
public class GroupBuy extends BaseThread {
    private String HOME_URL = "http://mall.icbc.com.cn";
    private String url = HOME_URL +"/promotion/promotion.jhtml";
    public GroupBuy(ActionCall action) {
        super(Icbc.getInstance());
        this.setActionCall(action);
        start();
    }
    @Override
    protected void workImps() throws Exception {
        Document document = Jsoup.connect(url).timeout(TIMEOUT).get();
        Elements els = document.select("#content .qiang_itemlist");
        GroupsSeckillsBean groupsSeckillsBean = new GroupsSeckillsBean();
            seckills(groupsSeckillsBean,els.get(0));
            groups(groupsSeckillsBean,els.get(1));
        filter(groupsSeckillsBean);
        transInteractionGoods(groupsSeckillsBean,"data","json_3");
    }

    private void filter(GroupsSeckillsBean groupsSeckillsBean) {
        for (Groups goods : groupsSeckillsBean.getSeckills()){
            goods.setBuy_link(goods.getBuy_link().replace(
                    "http://mall.icbc.com.cn/shopSeckill/seckillProductDetail.jhtml?",
                    "http://m.mall.icbc.com.cn/mobile/mobileSeckill/mobileSeckillDetail.jhtml?"));
        }
        for (Groups goods : groupsSeckillsBean.getGroups()){
            goods.setBuy_link(goods.getBuy_link().replace(
                    "http://mall.icbc.com.cn/shopGroup/promotionProdDetail.jhtml?",
                    "http://m.mall.icbc.com.cn/mobile/groupbuy/promotionProdDetail.jhtml?"));
        }
    }

    //团购
    private void groups(GroupsSeckillsBean groupsSeckillsBean,Element element) {
        groupsSeckillsBean.setGroups(new ArrayList<>());
        Elements els = element.select(".q_itemlist ul li");
        String url;
        Groups groups;
        for (Element ele : els) {
            url = HOME_URL + ele.select(".item_grts a").first().attr("href");
            groups = new Groups();
            setGroupItems(groups,url);
            groupsSeckillsBean.getGroups().add(groups);
        }
    }



    //秒杀
    private void seckills(GroupsSeckillsBean groupsSeckillsBean,Element element) {
        groupsSeckillsBean.setSeckills(new ArrayList<>());
        Elements els = element.select(".q_itemlist ul li");
        String url;
        Groups groups;
        for (Element ele : els) {
            url = HOME_URL + ele.select(".item_grts a").first().attr("href");
            groups = new Groups();
            setSeckillsItems(groups,url);
            groupsSeckillsBean.getSeckills().add(groups);
        }
    }

    public void setSeckillsItems(Groups groups, String url) {
        try {
            if (groups==null) return;
            Document doc =  Jsoup.connect(url).timeout(TIMEOUT).get();
            groups.setGoods_id(doc.getElementById("seckillActyId").attr("value"));
            groups.setDiscount(doc.select(".ftl_new").text().replace("╒█", ""));
            groups.setAmount(doc.select(".bg_red span").text());
            groups.setTitle(doc.select("#content .q_breadcrumb").first().text());
            groups.setTime_start(doc.getElementById("promotion_startTime").attr("value"));
            groups.setTime_end(doc.getElementById("promotion_endTime").attr("value"));
            groups.setPrice(doc.select("span.spancolor_gre").first().text().replace("гд", "").replace("￥", ""));
            groups.setPrice_old(doc.select("del").text().replace("гд", ""));

            Elements els = doc.select("#scroll li a img");
            groups.setImage_list(new ArrayList<>());
            int flag = 0;
            String imageUrl = null;
            for (Element element : els) {
                    flag++;
                    imageUrl = element.attr("src").replace("_2.", "_4.");
                    imageUrl = getGoodsImageUrl(imageUrl,groups.getGoods_id());
                    if (flag == 1) groups.setImage_cover(imageUrl);
                    groups.getImage_list().add(imageUrl);
                }
            groups.setBuy_link(url);
        } catch (Exception e) {
//            e.printStackTrace();
            waitTime(500);
            setSeckillsItems(groups,url);
        }
    }



    private void setGroupItems(Groups groups, String url) {
        try {
            Document doc =  Jsoup.connect(url).timeout(TIMEOUT).get();
            groups.setGoods_id(doc.getElementById("promotionProdId").attr("value"));
            groups.setDiscount(doc.select(".ftl_new").text().replace("折", ""));
            groups.setAmount(doc.select(".bg_red em").text());
            groups.setAmount_sell(doc.select(".bg_red span").text());
            groups.setTitle(doc.select("#content .q_breadcrumb").attr("title"));
            groups.setTime_start(doc.getElementById("promotion_startTime").attr("value"));
            groups.setTime_end(doc.getElementById("promotion_endTime").attr("value"));
            groups.setPrice(doc.getElementById("prodPrice").text()
                    .replace("￥", ""));

            if (doc.getElementById("originPrice") != null && doc.getElementById("originPrice").text() != null){
                groups.setPrice_old(doc.getElementById("originPrice").text()
                        .replace("￥", ""));
            }

            Elements els = doc.select("script");
            groups.setImage_list(new ArrayList<>());

            for (Element element : els) {
                if (element.toString().indexOf("var skinHtml = '<ul id=") > -1) {
                    String script = element.toString();
                    String regEx = "<img src=\"([\\S]*)\"";
                    Pattern pattern = Pattern.compile(regEx);
                    Matcher matcher = pattern.matcher(script);
                    int flag = 0;
                    String imageUrl;
                    while (matcher.find()) {
                        flag++;
                        imageUrl = matcher.group().replaceAll("\"", "").replaceAll("<img src=", "");
                        imageUrl = imageUrl.replace("_2.", "_4.");
                        imageUrl = getGoodsImageUrl(imageUrl,groups.getGoods_id());
                        if (flag == 1) groups.setImage_cover(imageUrl);
                        groups.getImage_list().add(imageUrl);
                    }
                    break;
                }
            }
            groups.setBuy_link(url);
        } catch (Exception e) {
//            e.printStackTrace();
            waitTime(1000);
            setSeckillsItems(groups,url);
        }
    }
    @Override
    public String getGoodsImageUrl(String url, String path) {
        return super.getGoodsImageUrl(url, path,Icbc.getInstance().getImageParams().getParam1(),Icbc.getInstance().getImageParams().getParam2()).substring(1);
    }
}
