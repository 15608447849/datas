package ccb.interaction.action;
import ccb.interaction.Interactions;
import ccb.interaction.obj.insurance.InsuranceBean;
import lunch.Say;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;


/**
 * Created by user on 2017/9/15.
 */
public class Insurance extends InteractionsSubImp{
    private final String URL_FOM = "http://insurance.ccb.com/tran/WCCMainB1L1?CCB_IBSVersion=V5&SERVLET_NAME=WCCMainB1L1&PAGE=%d&TXCODE=NBX201";
    public Insurance(Interactions interaction) throws Exception{
        super(interaction);
    }

    @Override
    void execute() throws Exception{
        int num = getNumber();
        for (int i = 1 ; i <= num ; i++){
            parsePage(i);
        }
    }


    //获取总页数
    private int getNumber() throws Exception{
        int num = 1;
        String url = String.format(URL_FOM,num);
        Document doc = interaction.getDocumentByUrl(url,interaction.getHeader());
        Elements div_class_vcc_pagelist = doc.select("div[class=vcc-pagelist]");
        Element script = div_class_vcc_pagelist.select("script").first();
        String html = script.html();
        String[] vals = html.trim().split(",");
        String val = vals[0].substring(vals[0].indexOf("(")+1);
        val = val.substring(val.indexOf("'")+1,val.lastIndexOf("'"));
        num = Integer.parseInt(val);
        return num;
    }

    //解析页面
    private void parsePage(int i) throws Exception{

        String url = String.format(URL_FOM,i);
        Document doc = interaction.getDocumentByUrl(url,interaction.getHeader());
        Elements elements = doc.select("div[class=searchResult_Lbox]");
        for (Element it : elements){
            createBean(it);
        }
    }
    //生成具体数据对象
    private void createBean(Element it) {
        int id = interaction.getJsBean().getInsurance().size()+1;
        Element element = it.select("div[class=searchResult_title]").first();
        Element tmp = it.select("a").first();
        String name = tmp.html();
        String link = tmp.attr("onclick");
        link = link.substring(link.indexOf("'")+1,link.lastIndexOf("'"));
        link = "http://insurance.ccb.com"+link;
        tmp = element.select("script").first();
        String[] strArr = tmp.html().trim().split(";");
        String apply = strArr[0].substring(strArr[0].indexOf("'")+1,strArr[0].lastIndexOf("'")).trim();//可能没有
        Elements elements = it.select("div[class=searchResult_inf]").select("span[class=f_or]");
        String company = elements.get(0).html();
        String product_class = elements.get(1).html();
        String type = elements.get(2).html();
        String attention_degree =  elements.get(3).html();
        tmp = it.select("div[class=searchResult_p]").select("script").first();
        strArr = tmp.html().trim().split("'");
        String describe = strArr[1];//可能没有
        interaction.getJsBean().getInsurance().add(new InsuranceBean(id,name,link,apply,company,product_class,type,attention_degree,describe));
    }


}
