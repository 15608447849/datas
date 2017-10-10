package ccb.buyAllshop;

import ccb.Ccb;
import ccb.buyAllshop.obj.FatClass;
import ccb.buyAllshop.obj.JsBean;
import ccb.buyAllshop.obj.Shop;
import ccb.buyAllshop.obj.SubClass;
import com.winone.ftc.mtools.FileUtil;
import interfaces.ActionCall;
import interfaces.BaseThreadAdapter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2017/9/12.
 */
public class BuyAllShop extends BaseThreadAdapter {
    private final String HOME_URL = "http://buy.ccb.com";
    private final String HOME_URL_2 = "http://shop.ccb.com/common/queryProductIntroduce.jhtml?productId=%s";
    private final JsBean jsBean = new JsBean();
    public BuyAllShop(ActionCall action) {
        super(Ccb.getInstance(),action);
    }
    @Override
    protected void workImps() throws Exception {
        jsBean.clear();
        Document document = getDocumentByUrl(HOME_URL);
        Elements div_class_floor = document.select("div[class=floor]");
        for (int i = 0; i< div_class_floor.size(); i++){
            handleDiv(div_class_floor.get(i).select("ul").first());
        }
        clearDocumentCache();
        //生成js
        transInteractionGoods(jsBean,"data","JSON_DATA");
    }

    private void handleDiv(Element ul) {
        Elements li_class_row3_c_l = ul.select("li[class=row3_c_l]");
        //获取菜单(一级)
        FatClass fat = handleMenus(li_class_row3_c_l);
        Elements li_class_row_center_li = ul.select("li[class=row_center_li]");
        //获取二级菜单和内容
        List<Shop> shopList = handleContent(fat,li_class_row_center_li);
        jsBean.putMenu(fat);
        jsBean.putGoodsByList(shopList);
    }



    //一级菜单生成
    private FatClass handleMenus(Elements li_class_row3_c_l) {
        Element img_class_scrollLoading = li_class_row3_c_l.select("img[class=scrollLoading]").first();
        String val = img_class_scrollLoading.attr("alt");
        String arr[] = val.split("\\s+");
        return  new FatClass(arr[0].replace("F",""),arr[1].replace("/",""));
    }
    //获取二级菜单 和 单商品内容
    private List<Shop> handleContent(FatClass fat, Elements li_class_row_center_li) {
        Element div_class_row_center_mod = li_class_row_center_li.select("div[class=row_center_mod]").first();
        Element div_mod_tit = div_class_row_center_mod.select("div[class=mod_tit]").first();
        Element div_mod_tid = div_class_row_center_mod.select("div[class=mod_tid]").first();
        return handleContentImp(fat,div_mod_tit,div_mod_tid);
    }



    private List<Shop> handleContentImp(FatClass fat, Element div_mod_tit, Element div_mod_tid) {
       //商品列表
        Elements shopList = div_mod_tid.select("div[class=row_select none]");
        Elements titleList = div_mod_tit.select("div");
        SubClass subClass ;
        List<SubClass> list = new ArrayList<>();
        List<Shop> list2 = new ArrayList<>();
        for (int i=0;  i<shopList.size();i++){
            subClass = new SubClass();
            subClass.setId(fat.getId()+(i+1));
            subClass.setName(titleList.get(i+2).text());
            shopParse(fat,subClass,shopList.get(i),list2);
            list.add(subClass);
        }
        fat.setSeclass(list);//设置二级标题

        return list2;
    }

    private void shopParse(FatClass fatClass,SubClass subClass, Element div_row_select, List<Shop> list2) {
        Elements div_prod_block = div_row_select.select("div[class=mod_recomm_prod]").first().select("div[class=prod_block]");

        final String imagePre = fatClass.getId()+ FileUtil.SEPARATOR+subClass.getId()+FileUtil.SEPARATOR;
        Shop shop;
        String val;
        String url;
        Element temp;
        String imagePath;
        for (Element it:div_prod_block){
            shop = new Shop();
            shop.setClass_id(subClass.getId()); //对应的二级分类id

            temp = it.select("div[class=row3_piclayshow]").first().select("a").first();
            //设置商品title
            val = temp.attr("title");
            val = charHandles(val);
            shop.setTitle(val);
            val = HOME_URL+temp.attr("href"); //进入详情的链接
            url = val;
            shop.setGoods_id(val.substring(val.indexOf("_")+1,val.lastIndexOf(".")));//设置商品ID
            shop.setBuy_link(val.replaceAll("pd","m"));//二维码链接
            imagePath = imagePre+shop.getGoods_id();
            //设置封面图片
            temp = it.select("img").first();
            val = temp.attr("data-url").replace("_2","_4");
            val = getGoodsImageUrl(val,imagePath);
            shop.setImage_cover(val.substring(1));
            infoPageParse(shop,url,imagePath,0);
            transContents(shop,imagePath);
            list2.add(shop);
        }
    }




    //进入页面解析
    private void infoPageParse(Shop shop, String url, String imagePath,int tag) {
        try {
            Document doc  = getDocumentByUrl(url);
            Elements elements = doc.select("script");
            if (elements.size()==1)  {
                String nUrl = elements.first().html().replace("window.location.href=","");
                nUrl = nUrl.substring(1,nUrl.length()-2);
                infoPageParse(shop,nUrl,imagePath,tag);
            }else{
                //获取类型 1 = 个人商城 2 = 分期优选
                int type = doc.select("div[class=fl]").first().select("a").size();

                //获取价格
                elements =doc.select("input[id=prodPriceByone]");
                shop.setPrice(elements.attr("value"));


                String val = "no message";
                if (type==1){
                    //获取描述
                    elements = doc.select("form[id=form1]");
                    //查找font
                    elements = elements.select("font");
                    val =elements.first().html();
                }else if (type==2){
                    //获取描述
                    elements = doc.select("div[class=protitle]");
                    //查找span
                    val = elements.select("span").last().html();
                }
                shop.setDesc(val);
                //获取图片
                elements = doc.select("div[id=idList]").select("a");
                List<String> images = new ArrayList<>();
                for (Element it:elements){
                    val = it.attr("href");
                    val = getGoodsImageUrl(val,imagePath);
                    images.add(val.substring(1));
                }
                shop.setImage_list(images);
                elements = doc.select("div[class=info]");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("<div text-align:center>");
                //获取内容
                if (type==1){
                    stringBuilder.append(elements.first().html());
                }else if (type==2){
                    val = getTextByUrl(String.format(HOME_URL_2,shop.getGoods_id()));// {html:......}
                    stringBuilder.append(jsonToMap(val).get("html"));
                }
                stringBuilder.append("</div>");
                shop.setContent(stringBuilder.toString());
            }
        } catch (Exception e) {
            if (tag<3){
                tag++;
                infoPageParse(shop,url,imagePath,tag);
            }
        }
    }


    private void transContents(Shop shop,String path){
        String sStr = shop.getContent();

        String regEx = "<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(sStr);
        final String flagStr = "##img##";
        String tmp = sStr.replaceAll(regEx, flagStr);

        String val;
        while (matcher.find()) {
            val = matcher.group(0);
            if (val.contains("data-url")){
                val = val.substring(val.indexOf("data-url"));
            }
            val = val.substring(val.indexOf("\"")+1);
            val = val.substring(0,val.indexOf("\""));
            val = getGoodsImageUrl(val,path);
            tmp = tmp.replaceFirst(flagStr, "<img src=\'"+val.substring(1)+"\'>");
        }
        shop.setContent(tmp);
    }


}
