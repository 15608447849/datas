package icbc.financial;

import icbc.Icbc;
import icbc.financial.obj.FinancialBean;
import icbc.interaction.FinancialGoods;
import interfaces.ActionCall;
import interfaces.BaseThread;
import interfaces.MUrl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by user on 2017/6/26.
 * 基金
 */
public class Financial extends BaseThread  {
    private String URL = "https://mybank.icbc.com.cn/servlet/ICBCBaseReqServletNoSession?dse_operationName=per_FinanceCurProListP3NSOp&pageFlag=0&Area_code=1900&requestChannel=302&nowPageNum_turn=%d";
    private static final String PDF_URL = "https://image.mybank.icbc.com.cn/picture/Perfinancingproduct/%s.pdf";
    private int total = -1;
    private ArrayList<FinancialBean> beanList =  new ArrayList<>();
    private ArrayList<FinancialGoods> goodsList;
    private final String push_data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());


    public Financial(ActionCall action) {
        super(Icbc.getInstance());
        this.setActionCall(action);
        this.start();
    }

    /*

    this.sId = index*10-10;

     */
    private void clearBean(){
            beanList.clear();
    }
    private void addBean(FinancialBean bean){
            beanList.add(bean);
    }






    @Override
    protected void workImps() throws Exception {
        clearBean();
//        Say.I(this+"    "+URL );
        String url = String.format(URL,1);
        Document doc = Jsoup.connect(url).timeout(TIMEOUT).get();
        parse(doc);
        int titleNunber = theHome(doc);
        for (int i=2;i<=titleNunber;i++){
            url = String.format(URL,i);
            doc = Jsoup.connect(url).timeout(TIMEOUT).get();
            parse(doc);
        }

            if (beanList.size() != total){
                throw new IllegalStateException("data pick is error ,total = "+total+" ,current size = "+ beanList.size());
            }
//            Say.I("转换json中...");
            HashMap<String,ArrayList<FinancialBean>> maps = new HashMap<>();
            for (int i=1;i<=5;i++){
                maps.put("pr"+i,new ArrayList<>());
            }
            goodsList = new ArrayList<>();
            FinancialBean bean ;
            for (int i =0;i<beanList.size();i++){
                bean = beanList.get(i);
                maps.get(bean.getGrade()).add(bean);
                goodsList.add(new FinancialGoods(i,bean));
            }
            //根据类型创建Map - > json
            writeFile(objTansJson(maps));
    }

    @Override
    protected void work2Imps()throws Exception {
           transInteractionGoods(goodsList, FinancialGoods.class,"JSON_DATA",false);
    }

    /**
     * 第一页
     * 获取总条数,创建下一个线程
     */
    private int theHome(Document doc) throws Exception{
        String str = doc.html();
        str = str.substring(str.indexOf("#shownum")+16);
        str = str.substring(0, str.indexOf(")"));

        int sum = Integer.parseInt(str);
        total = sum;
        sum = (sum % 10)> 0 ? sum /10 +1 : sum /10;
       return sum;
    }



    private void parse(Document doc) throws Exception{

        Elements divs = doc.select("div[class=ebdp-pc4promote-circularcontainer]");
        FinancialBean financialBean;
        String val;
        Elements elements;
        MUrl mUrl;
        for (int i = 0;i < divs.size() ;i++){
            financialBean = new FinancialBean();
            financialBean.setName(divs.get(i).select("a").first().text());
            //启购金额
            val="div[id=doublelabel2_"+i+"-content]"; //doublelabel2_0-content
            financialBean.setLimit_price(divs.select(val).text());
            val = divs.get(i).select("a").first().attr("href");
            val = val.substring(val.indexOf("'")+1);
            val = val.substring(0,val.indexOf("'"));
            financialBean.setCode(val);
            //下载pdf
            mUrl = new MUrl(String.format(PDF_URL,val),RES_HOME_PATH);
            financialBean.setDesc_file_url(addResUrl(mUrl));
            val = "div[id=doublelabel1_"+i+"]";
            elements = divs.select(val);
            val = "div[class=ebdp-pc4promote-doublelabel-head]";
            val = elements.select(val).text();
            //单位净值和年化受利率
            if (val.equals("单位净值")){
                val = "div[id=doublelabel1_"+i+"-content]";
                financialBean.setUnit_income_rate(elements.select(val).text());
                val = "div[class=ebdp-pc4promote-doublelabel-foot]";
                financialBean.setUnit_income_date(elements.select(val).text());
            }
            if (val.equals("预期年化收益率/业绩基准")){
                val = "div[id=doublelabel1_"+i+"-content]";
                financialBean.setYear_income_rate(elements.select(val).text());
            }

            val = "div[id=doublelabel3_"+i+"-content]";
            val = divs.select(val).text();
            if (val.equals("无固定期限")){
                financialBean.setIs_fixed_limit("0");
                financialBean.setDate_limit("0");
            }else if (val.contains("最短")){
                financialBean.setIs_fixed_limit("1");
                financialBean.setDate_limit(val.substring(4,val.length()-1));
            }else{
                financialBean.setIs_fixed_limit("0");
                financialBean.setDate_limit(val.substring(0,val.length()-1));
            }
            val="div[id=tt"+i+"-content]";
            financialBean.setGrade("pr"+divs.select(val).text());
            //信息
            val="span[class=ebdp-pc4promote-circularcontainer-text1]";
            financialBean.setTitle(divs.select(val).get(i).text());
            financialBean.setPush_date(push_data);
            addBean(financialBean);
        }

    }


}
