package person_loan;

import com.google.gson.Gson;
import interaction.Goods;
import interaction.PersonalLoanGoods;
import interfaces.ActionCall;
import interfaces.BaseThread;
import interfaces.MUrl;
import lunch.Param;
import lunch.Say;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import person_loan.obj.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by user on 2017/6/24.
 * 个人理财
 */
public class PersonLoan extends BaseThread {
    private PersonLoanBean personLoanBean = new PersonLoanBean();
    private ArrayList<LoanContent> loanContentArrayList = new ArrayList<>();
    private final String URL_RATE = "http://www.icbc.com.cn/ICBCDynamicSite2/other/rmbcredit.aspx";
    private final String URL_A = "http://www.icbc.com.cn/ICBC/%E4%B8%AA%E4%BA%BA%E9%87%91%E8%9E%8D/%E8%B4%B7%E6%AC%BE/%E6%B6%88%E8%B4%B9%E8%B4%B7%E6%AC%BE/%E4%B8%AA%E4%BA%BA%E4%BF%A1%E7%94%A8%E6%B6%88%E8%B4%B9%E8%B4%B7%E6%AC%BE.htm"; // 个人信用消费贷款
    private final String URL_B = "http://www.icbc.com.cn/ICBC/%E4%B8%AA%E4%BA%BA%E9%87%91%E8%9E%8D/%E8%B4%B7%E6%AC%BE/%E6%B6%88%E8%B4%B9%E8%B4%B7%E6%AC%BE/%E4%B8%AA%E4%BA%BA%E5%AE%B6%E5%B1%85%E6%B6%88%E8%B4%B9%E8%B4%B7%E6%AC%BE.htm"; // 个人家居消费贷款
    private final String URL_C = "http://www.icbc.com.cn/ICBC/%E4%B8%AA%E4%BA%BA%E9%87%91%E8%9E%8D/%E8%B4%B7%E6%AC%BE/%E7%89%B9%E8%89%B2%E8%B4%B7%E6%AC%BE/%E4%B8%AA%E4%BA%BA%E8%B4%A8%E6%8A%BC%E8%B4%B7%E6%AC%BE.htm"; //  个人质押贷款
    private final String URL_D = "http://www.icbc.com.cn/ICBC/%E4%B8%AA%E4%BA%BA%E9%87%91%E8%9E%8D/%E8%B4%B7%E6%AC%BE/%E4%BD%8F%E6%88%BF%E8%B4%B7%E6%AC%BE/%E4%B8%80%E6%89%8B%E4%B8%AA%E4%BA%BA%E4%BD%8F%E6%88%BF%E8%B4%B7%E6%AC%BE.htm"; // 一手个人住房贷款

    private void addContent(LoanContent content){
            loanContentArrayList.add(content);
    }
    private void clearContent(){
            loanContentArrayList.clear();
    }
    public PersonLoan(ActionCall action) {
        super(Param.HOME,Param.PATH);
        this.setActionCall(action);
        start();
    }

    @Override
    protected void workImps() throws Exception {
        clearContent();
        rate();
        personalCreditConsumptionLoans();
        personalHomeConsumptionLoan();
        personalPledgedLoan();
        personalHousingLoan();
        if (loanContentArrayList.size()!=4) throw new Exception("数据不完整");
        personLoanBean.setLoan_array(loanContentArrayList);
        writeJSON( new Gson().toJson(personLoanBean));
    }

    @Override
    protected void work2Imps() throws Exception {
        ArrayList<PersonalLoanGoods> goodsList = new ArrayList<>();
        String hUrl = "http://www.icbc.com.cn";
        Document document = Jsoup.connect(hUrl+"/HtmlPatch/ICBC/sy/gerendaikuan.htm").timeout(TIMEOUT).get();
        Elements elements = document.select("div[class=conItem]");
        Iterator<Element> iterator = elements.iterator();
        PersonalLoanGoods goods ;
        int i = 1;
        Element element;
        String val;
        String []vals;
        while (iterator.hasNext()){
            goods = new PersonalLoanGoods();
            element = iterator.next();
            goods.setId((i++)+"");
            val = hUrl+element.select("img").first().attr("src");
            goods.setImg_cover(addResUrl(new MUrl(val,RES_HOME_PATH)));
            vals = element.select("p").first().text().split("\\s");
            goods.setName(vals[0]);
            goods.setDesc(vals[1]);
            goods.toGoodsRecode();
            goodsList.add(goods);
        }
        for (Goods bean : goodsList){
            bean.setImg_cover(bean.getImg_cover().substring(1));
        }
        transInteractionGoods(goodsList, PersonalLoanGoods.class,"JSON_DATA",false);
    }

    private void rate() throws Exception{
                //利率
                Document doc = Jsoup.connect(URL_RATE).timeout(TIMEOUT).get();
                Elements table = doc.select("table[rules=all]");
                Elements tbody = table.select("tbody");
                Elements trArr = tbody.select("tr");
                LoanRate rate = new LoanRate();
                rate.setSix_month_rate(trArr.get(2).select("td").get(1).html());
                rate.setYear_rate(trArr.get(3).select("td").get(1).html());
                rate.setThree_year_rate(trArr.get(5).select("td").get(1).html());
                rate.setFive_year_rate(trArr.get(6).select("td").get(1).html());
                rate.setOver_five_year_rate(trArr.get(7).select("td").get(1).html());
                personLoanBean.setLoan_rate(rate);
    }

    /**
     * 融E借
     * @return
     */
    private void personalCreditConsumptionLoans() throws Exception{
                LoanContent loanContent = new LoanContent();
                Document doc = Jsoup.connect(URL_A).timeout(TIMEOUT).get();
                loanContent.setId("1");
                loanContent.setName( doc.select("span[id=TitleHtmlPlaceholderDefinition]").select("p").get(0).html());
                loanContent.setQrcode_char(URL_A);
                BusinessSummary businessSummary = new BusinessSummary();
                Elements pArr = doc.select("span[id=Tag1ContentHtmlPlaceholderDefinition]").select("p");
                businessSummary.setTitle(pArr.get(0).html().substring(2));
                ArrayList<String> items = new ArrayList<>();
                String[] arr =  pArr.get(1).html().split("<br>\\s+");
                for(int i = 0;i<arr.length;i++){
                    items.add(
                            (i+1)+"."+arr[i].substring(2)
                    );
                }
                businessSummary.setItems(items);
                loanContent.setBusinessSummary(businessSummary);

                pArr = doc.select("span[id=Tag2ContentHtmlPlaceholderDefinition]").select("p");
                arr = pArr.get(0).html().split("<br>\\s+");
                ArrayList<String> requirement = new ArrayList<>();

                for(int i = 0;i<arr.length;i++){
                    requirement.add(
                            arr[i].substring(2)
                    );
                }
                loanContent.setRequirement(requirement);
                Elements span = doc.select("span[id=Tag4ContentHtmlPlaceholderDefinition]");
                arr = span.html().split("<br>\\s");

                ArrayList<FAQBean> faqBeanArrayList = new ArrayList<>();
                FAQBean bean;
                int index = 1;
                for(int i = 0;i<arr.length;i+=2){
                    bean = new FAQBean();
                    bean.setIndex(index+"");
                    bean.setTitle(arr[i].replace("<strong>","").substring(2));
                    bean.setAnswor(arr[i+1].replace("</strong>","").substring(2));
                    faqBeanArrayList.add(bean);
                    index++;
                }
                loanContent.setFAQ(faqBeanArrayList);
                addContent(loanContent);
    }


    /**
     * 个人家具消费
     */
    private void personalHomeConsumptionLoan() throws Exception{

                LoanContent loanContent = new LoanContent();
                Document doc = Jsoup.connect(URL_B).timeout(TIMEOUT).get();
                loanContent.setQrcode_char(URL_B);
                loanContent.setId("2");
                loanContent.setName(doc.select("span[id=TitleHtmlPlaceholderDefinition]").get(0).text());

                BusinessSummary businessSummary = new BusinessSummary();
                Elements pArr = doc.select("span[id=Tag1ContentHtmlPlaceholderDefinition]").select("p");
                businessSummary.setTitle(pArr.get(0).text().substring(2));
                String arr[] = pArr.get(1).html().replaceAll("<strong>","").replaceAll("</strong>","").split("<br>\\s+");

                ArrayList<String> items = new ArrayList<>();
                for (int i=1;i<arr.length;i++){
                    items.add(arr[i].substring(2));
                }
                businessSummary.setItems(items);
                loanContent.setBusinessSummary(businessSummary);

                String url = "http://www.icbc.com.cn/icbc/%e4%b8%aa%e4%ba%ba%e9%87%91%e8%9e%8d/%e4%b8%9a%e5%8a%a1%e4%bb%8b%e7%bb%8d%e5%bc%b9%e5%87%ba/%e8%b4%b7%e6%ac%be/%e4%b8%aa%e4%ba%ba%e5%ae%b6%e5%b1%85%e6%b6%88%e8%b4%b9%e8%b4%b7%e6%ac%be%e7%94%b3%e8%af%b7%e6%9d%a1%e4%bb%b6.htm";
                doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
                arr = doc.select("span[id=FreeDefinePlaceholderControl1]").html().split("<br>\\s+");
                ArrayList<String> requirement = new ArrayList<>();
                for (int i=0;i<arr.length;i++){
                    requirement.add(arr[i].substring(2));
                }
                loanContent.setRequirement(requirement);

                url = "http://www.icbc.com.cn/icbc/%e4%b8%aa%e4%ba%ba%e9%87%91%e8%9e%8d/%e4%b8%9a%e5%8a%a1%e4%bb%8b%e7%bb%8d%e5%bc%b9%e5%87%ba/%e8%b4%b7%e6%ac%be/%e4%b8%aa%e4%ba%ba%e5%ae%b6%e5%b1%85%e6%b6%88%e8%b4%b9%e8%b4%b7%e6%ac%be%e7%94%b3%e8%af%b7%e8%b5%84%e6%96%99.htm";
                doc =  Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
                arr = doc.select("span[id=FreeDefinePlaceholderControl1]").html().split("<br>\\s+");
                ArrayList<String> material_requested = new ArrayList<>();
                for (int i=0;i<arr.length;i++){
                    material_requested.add(arr[i].substring(2));
                }
                loanContent.setMaterial_requested(material_requested);
                addContent(loanContent);
    }
    /**
     * 个人质押贷款
     */
    private void personalPledgedLoan() throws Exception{
                LoanContent loanContent = new LoanContent();
                Document doc = Jsoup.connect(URL_C).timeout(TIMEOUT).get();
                loanContent.setQrcode_char(URL_C);
                loanContent.setId("3");

                loanContent.setName(doc.select("span[id=TitleHtmlPlaceholderDefinition]").get(0).text());
                BusinessSummary businessSummary = new BusinessSummary();
                Elements pArr = doc.select("span[id=Tag1ContentHtmlPlaceholderDefinition]").select("p");
                businessSummary.setTitle(pArr.get(0).text().substring(2));
                String arr[] = pArr.get(1).html().replaceAll("<strong>","").replaceAll("</strong>","").split("<br>\\s+");

                ArrayList<String> items = new ArrayList<>();
                for (int i=1;i<arr.length;i++){
                    items.add(arr[i].substring(2));
                }
                businessSummary.setItems(items);
                loanContent.setBusinessSummary(businessSummary);

                String url = "http://www.icbc.com.cn/icbc/%e4%b8%aa%e4%ba%ba%e9%87%91%e8%9e%8d/%e4%b8%9a%e5%8a%a1%e4%bb%8b%e7%bb%8d%e5%bc%b9%e5%87%ba/%e8%b4%b7%e6%ac%be/%e4%b8%aa%e4%ba%ba%e8%b4%a8%e6%8a%bc%e8%b4%b7%e6%ac%be%e7%94%b3%e8%af%b7%e6%9d%a1%e4%bb%b6.htm";
                doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
                arr = doc.select("span[id=FreeDefinePlaceholderControl1]").html().split("<br>\\s+");
                ArrayList<String> requirement = new ArrayList<>();
                for (int i=0;i<arr.length;i++){
                    requirement.add(arr[i].substring(2));
                }
                loanContent.setRequirement(requirement);
                addContent(loanContent);
    }

    /**
     * 一手个人住房贷款
     */
    private void personalHousingLoan()throws Exception {
                LoanContent loanContent = new LoanContent();
                Document doc = Jsoup.connect(URL_D).timeout(TIMEOUT).get();
                loanContent.setQrcode_char(URL_D);
                loanContent.setId("4");
                loanContent.setName(doc.select("span[id=TitleHtmlPlaceholderDefinition]").get(0).text());

                BusinessSummary businessSummary = new BusinessSummary();
                Elements pArr = doc.select("span[id=Tag1ContentHtmlPlaceholderDefinition]").select("p");
                businessSummary.setTitle(pArr.get(0).text().substring(2)+pArr.get(1).text().substring(2));
                String arr[] = pArr.get(2).html().replaceAll("<strong>","").replaceAll("</strong>","").split("<br>\\s+");

              ArrayList<String> items = new ArrayList<>();
                for (int i=1;i<arr.length;i++){
                    items.add(arr[i].substring(2));
                }
                businessSummary.setItems(items);
                loanContent.setBusinessSummary(businessSummary);

                ArrayList<FAQBean> faqBeanArrayList = new ArrayList<>();
                Elements span = doc.select("span[id=Tag4ContentHtmlPlaceholderDefinition]");

                arr = span.select("p").get(0).html().split("<br>\\s");
                FAQBean bean  = new FAQBean();
                    bean.setIndex("1");
                    bean.setTitle(arr[0].replace("<strong>","").substring(2));
                    bean.setAnswor(arr[1].replace("</strong>","").substring(2));
                faqBeanArrayList.add(bean);

                arr = span.select("p").get(1).html().split("<br>\\s");
                bean  = new FAQBean();
                bean.setIndex("2");
                bean.setTitle(arr[0].replace("<strong>","").substring(2));
                bean.setAnswor(arr[1].replace("</strong>","").substring(2)+span.select("p").get(2).text().substring(2));
                faqBeanArrayList.add(bean);

                loanContent.setFAQ(faqBeanArrayList);

                String url = "http://www.icbc.com.cn/icbc/%e4%b8%aa%e4%ba%ba%e9%87%91%e8%9e%8d/%e4%b8%9a%e5%8a%a1%e4%bb%8b%e7%bb%8d%e5%bc%b9%e5%87%ba/%e8%b4%b7%e6%ac%be/%e4%b8%80%e6%89%8b%e4%b8%aa%e4%ba%ba%e4%bd%8f%e6%88%bf%e8%b4%b7%e6%ac%be%e7%94%b3%e8%af%b7%e6%9d%a1%e4%bb%b61.htm";
                doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
                arr = doc.select("span[id=FreeDefinePlaceholderControl1]").html().split("<br>\\s+");
                ArrayList<String> requirement = new ArrayList<>();
                for (int i=0;i<arr.length;i++){
                    requirement.add(arr[i].substring(2));
                }
                loanContent.setRequirement(requirement);

                url = "http://www.icbc.com.cn/icbc/%e4%b8%aa%e4%ba%ba%e9%87%91%e8%9e%8d/%e4%b8%9a%e5%8a%a1%e4%bb%8b%e7%bb%8d%e5%bc%b9%e5%87%ba/%e8%b4%b7%e6%ac%be/%e4%b8%80%e6%89%8b%e4%b8%aa%e4%ba%ba%e4%bd%8f%e6%88%bf%e8%b4%b7%e6%ac%be%e6%89%80%e9%9c%80%e6%9d%90%e6%96%99.htm";
                doc =  Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
                arr = doc.select("span[id=FreeDefinePlaceholderControl1]").html().split("<br>\\s+");
                ArrayList<String> material_requested = new ArrayList<>();
                for (int i=0;i<arr.length;i++){
                    material_requested.add(arr[i].substring(2));
                }
                loanContent.setMaterial_requested(material_requested);
                addContent(loanContent);
    }
}
