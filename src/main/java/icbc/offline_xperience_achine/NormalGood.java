package icbc.offline_xperience_achine;

import icbc.Icbc;
import interfaces.ActionCall;
import interfaces.BaseThread;
import lunch.Param;
import icbc.offline_xperience_achine.obj.NorGoodsBean;
import icbc.offline_xperience_achine.obj.goodss.Goods;
import icbc.offline_xperience_achine.obj.menus.Banner;
import icbc.offline_xperience_achine.obj.menus.Menu;
import icbc.offline_xperience_achine.obj.menus.Seclass;
import lunch.Say;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2017/7/5.
 * 正常商品
 */
public class NormalGood extends BaseThread{
    private final String ICBCURL = "http://mall.icbc.com.cn";

    public NormalGood(ActionCall action) {
        super(Icbc.getInstance());
        this.setActionCall(action);
        start();
    }
    @Override
    protected void workImps() throws Exception {
        Document document = Jsoup.connect(ICBCURL).timeout(TIMEOUT).get();
        Elements els = document.select("#content .floors_main");
        NorGoodsBean obj = new NorGoodsBean();
        Menu menu;
        Seclass seclass;
        Banner banner;
        Goods goods;
        Elements elsTem;
        Elements elsTem2;
        String val;
            for (Element el : els) {
                menu = new Menu();
                menu.setId(el.select(".floors_tit").text().replace("F", "").substring(0, 1));
                menu.setName(el.select(".floors_tit span").text());
                menu.setSeclass(new ArrayList<>());

                elsTem = el.select(".tabName li a");
                for (Element ele : elsTem) {
                    if (!ele.text().equals("工银投资交易")) {
                        seclass = new Seclass();
                        val = ele.attr("onclick").replace("floorSwitchCate('", "").replace("')", "");
                        seclass.setId(val);
                        seclass.setName(ele.text());
                        seclass.setBanner(new ArrayList<>());

                        val = "floors_" + val;
                        elsTem2 = el.select("#" + val + " .floor_slide ul li a");
                        for (Element ele2 : elsTem2) {
                            banner = new Banner();
                            val = ele2.select("img").attr("_src").trim();
                            banner.setImg(getGoodsImageUrl(val));
                            banner.setLink(ele2.attr("href"));//
                            seclass.getBanner().add(banner);
                        }
                        menu.getSeclass().add(seclass);
                    }
                }
                obj.getMenu().add(menu);

                elsTem = el.select(".floors_info div.floors_r .floors_tab");
                String cid;
                for (Element ele : elsTem) {
                    val = ele.attr("id").substring(7);
                    cid = val;
                    if (cid.equals("0000001157")) continue;
                        elsTem2 = ele.select("ul li[data=6prod]");
                        if (null != elsTem2 && elsTem2.size() > 0) {
                            for (Element elem : elsTem2) {
                                goods = new Goods();
                                goods.setClass_id(cid);
                                val = ICBCURL + elem.select("a").first().attr("href");
                                goods.setBuy_link(val);
                                setGoodsContent(goods,val);
                                if (goods.getPrice().equals("")) {
                                    goods.setPrice(elem.select("div.price").text().replace("￥", ""));
                                }
                                obj.getGoods().add(goods);
                            }

                        } else {
                            elsTem2 = ele.select("ul li[data=6adv]");
                            if (null != elsTem2 && elsTem2.size() > 0) {
                                for (Element elem : elsTem2) {
                                    goods = new Goods();
                                    goods.setClass_id(cid);
                                    goods.setBuy_link(elem.select("a").first().attr("href"));
                                    goods.setTitle(elem.select("a .one").text());
                                    val = getGoodsImageUrl(elem.select("a img").first().attr("_src").trim());
                                    goods.setImage_cover(val);
                                    goods.setImage_list(new ArrayList<>());
                                    goods.getImage_list().add(val);
                                    obj.getGoods().add(goods);
                                }
                            }
                        }
                }

            }

            //过滤link-过滤content - iframe
            filter(obj.getGoods());


            //转换js
            transInteractionGoods(obj,"data","json_1");
    }




    private void filter(List<Goods> goodsList) {
        for (Goods goods : goodsList){
            goods.setBuy_link(goods.getBuy_link().replace("http://mall.icbc.com.cn/products/pd","http://m.mall.icbc.com.cn/mobile/mobileProduct/product"));
            if (goods.getContent().contains("iframe")){
                goods.setContent(goods.getContent().replace("/utils/httpclient.jhtml?",ICBCURL+"/utils/httpclient.jhtml?"));
            }
        }
    }

    private void setGoodsContent(Goods goods, String val) {
        try {
            if (goods==null) return;
            Document doc = Jsoup.connect(val).timeout(TIMEOUT).get();
            try {
                if(doc.select("#content .prop li").first().attr("id").equals("promotionMes"))
                    goods.setGoods_id(doc.select("#content .prop li").get(1).text().substring(5));
                else
                    goods.setGoods_id(doc.select("#content .prop li").first().text().substring(5));
            } catch (NullPointerException e) {
                return;
            }

            goods.setTitle(doc.select("#currProductName_js").attr("value"));
            goods.setDesc(doc.select("p.p_red").first().text());
            //Say.I(doc.select("#storeName_js").attr("value"));

            if(null != doc.getElementById("prodPrice"))
                goods.setPrice(doc.getElementById("prodPrice").text().replace("гд", ""));
            else if(null != doc.select(".pro_detail_c span.price") &&  doc.select(".pro_detail_c span.price").size()>1)
                goods.setPrice(doc.select(".pro_detail_c span.price").get(1).text());

            goods.setContent(translateContent(goods.getGoods_id(),doc.select("#p_detaillist div.tabcon").first().toString()));

            Elements els = doc.select("script");
            goods.setImage_list( new ArrayList<>());
            String value;
            for(Element element :els){
                if(element.toString().indexOf("var skinHtml = '<ul id=")>-1){
                    String script = element.toString();
                    String regEx = "<img src=\"([\\S]*)\"";
                    Pattern pattern = Pattern.compile(regEx);
                    Matcher matcher = pattern.matcher(script);
                    int flag = 0;
                    while (matcher.find()){
                        flag++;
                        value = matcher.group().replaceAll("\"", "").replaceAll("<img src=", "").replace("_2.", "_4.");
                        value = getGoodsImageUrl(value,goods.getGoods_id());
                        if(flag==1) goods.setImage_cover(value);
                        goods.getImage_list().add(value);
                    }
                }
            }

        } catch (Exception e) {
//            e.printStackTrace();
            waitTime(500);
            setGoodsContent(goods,val);
        }
    }



    public String translateContent(String goodsid,String goodsDetails) {
        String regEx = "<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(goodsDetails);
        String flagStr = "##img##";
        String tmpGoodsDetails = goodsDetails.replaceAll(regEx, flagStr);

        String imgtab;
        String[] urls ;
        String[] urlss ;
        String url ;
        String path ;
        String realimgtab;
        while (matcher.find()) {
            imgtab = matcher.group(0);
            urls = imgtab.split("src=\"");
            urlss = urls[1].split("\"");
            url = urlss[0];
            if (url.startsWith("http")) {
                path = getGoodsImageUrl(url,goodsid);
                realimgtab = urls[0] + "src=\"" + path + "\"" + urlss[1];
                realimgtab = realimgtab.replace("\\", "\\\\");
                tmpGoodsDetails = tmpGoodsDetails.replaceFirst(flagStr, realimgtab);
            }
        }
        return tmpGoodsDetails;
    }

    @Override
    public String getGoodsImageUrl(String url, String path) {
        return super.getGoodsImageUrl(url, path,Icbc.getInstance().getImageParams().getParam1(),Icbc.getInstance().getImageParams().getParam2()).substring(1);
    }




}

