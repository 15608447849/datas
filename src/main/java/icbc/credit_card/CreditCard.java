package icbc.credit_card;

import icbc.Icbc;
import icbc.credit_card.obj.CreditCardJsonBean;
import icbc.interaction.CreditCardGoods;
import icbc.interaction.Goods;
import interfaces.ActionCall;
import interfaces.BaseThread;
import interfaces.MUrl;
import lunch.Say;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by lzp on 2017/6/21.
 * 信用卡
 * 1.首先通过https://mybank.icbc.com.cn/servlet/com.icbc.inbs.servlet.ICBCINBSEstablishSessionServlet?netType=104&Language=zh_CN&firstPage=211&isFromDoor=0&flag=0&name=0&credType=0获取sessionId
 * 2.https://mybank.icbc.com.cn/servlet/ICBCINBSReqServlet?dse_sessionId=(拿到的sessionId)&dse_operationName=per_ApplyCreditCardNLOp&doFlag=201&toOldList=1&beginPos=1&qryBeginPos=1&SelAreaCode=0200 拿到第一页数据
 * 在第一页中找到initParams(1, 1, 48, 48, 20)内容
 */
public class CreditCard extends BaseThread {
    private String URL_GET_SESSION_ID ="https://mybank.icbc.com.cn/servlet/com.icbc.inbs.servlet.ICBCINBSEstablishSessionServlet?netType=104&Language=zh_CN&firstPage=211&isFromDoor=0&flag=0&name=0&credType=0";
    private String INDEX_PAGE = "https://mybank.icbc.com.cn/servlet/ICBCINBSReqServlet?dse_sessionId=%s&dse_operationName=per_ApplyCreditCardNLOp&doFlag=201&toOldList=1&beginPos=%s&qryBeginPos=1&SelAreaCode=0200";
    protected final HashMap<String,String> brandMap = new HashMap<>();
    private final LinkedList<CreditCardJsonBean> beanList = new LinkedList<>();
    private ArrayList<CreditCardGoods> goodsList;

    public CreditCard(ActionCall action) {
        super(Icbc.getInstance());
        this.setActionCall(action);
        brandMap.put("威士","visa");
        brandMap.put("万事达卡","master");
        brandMap.put("美国运通","american_express");
        brandMap.put("中国银联","union_pay");
        brandMap.put("JCB","jcb");
        brandMap.put("大来","diners_club");
        start();
    }



    private void clearBeans() {
            beanList.clear();
    }

    protected void addBean(CreditCardJsonBean bean){
           beanList.add(bean);
    }

    public String getSessionId() throws IOException {
        Document doc = Jsoup.connect(URL_GET_SESSION_ID).timeout(TIMEOUT).get();
        Elements script = doc.select("script");
        String src = script.html();
        src = src.substring(src.indexOf("=")+1);
        src = src.substring(src.indexOf("=")+1);
        String sessionId = src.substring(0,src.indexOf("&"));
        return sessionId;
    }
    private String getTotalCount(String indexUrl) throws IOException {
        Document doc = Jsoup.connect(indexUrl).timeout(TIMEOUT).get();
        Elements script  = doc.select("script[language=javascript1.1]");
        String str = script.get(1).toString();
        str = str.substring(str.indexOf("initParams")+11);
        str = str.substring(0,str.lastIndexOf(")"));
        String arr[] = str.split(",");
        return arr[2].trim();
    }

    private void getData(String sessionId,String totalCount) throws Exception {
        int number = Integer.parseInt(totalCount);
        for (int i = 1;i<number;i+=20){
            executeData(String.format(INDEX_PAGE,sessionId,i));
        }
    }

    private void executeData(String url) throws Exception {
        Document doc = Jsoup.connect(url).timeout(TIMEOUT).get();
        Elements table = doc.select("table[class=lst]");
        Elements tbody = table.select("tbody");
        Elements tr = tbody.select("tr");
        for (int i = 1;i<tr.size();i++){
            createData(tr.get(i).select("td"));
        }
    }
    //创建数据
    private void createData(Elements elements) {
        CreditCardJsonBean bean = new CreditCardJsonBean();
        bean.setCard_name(elements.get(1).select("a").html());
        bean.setBrand_st(elements.get(3).html().replace("<br>银联",""));
        bean.setBrand(brandMap.get(bean.getBrand_st()));
        bean.setCurrency(elements.get(4).text());
        bean.setCard_area(elements.get(2).text());
        bean.setCard_cover(addResUrl(new MUrl( elements.get(5).select("img").get(0).attr("src"),RES_HOME_PATH)));
        bean.setCard_desc(elements.get(6).html());
        addBean(bean);
    }

    private void checkList() throws IOException {
        HashMap<String,ArrayList> map = new HashMap<>();
        Iterator<String> iterator = brandMap.keySet().iterator();
               while (iterator.hasNext()){
                   map.put(brandMap.get(iterator.next()),new ArrayList());
               }
        goodsList = new ArrayList<>();
        CreditCardJsonBean bean ;
        for (int i = 0;i<beanList.size();i++){
            bean = beanList.get(i);
            map.get(bean.getBrand()).add(bean);
            goodsList.add(new CreditCardGoods(i,bean));
        }
        //根据类型创建Map - > json
        writeFile(objTansJson(map));
    }


    @Override
    protected void workImps() throws Exception {
        clearBeans();
        String sessionId = getSessionId();
        String indexUrl = String.format(INDEX_PAGE,sessionId,"1");
        String totalCount = getTotalCount(indexUrl);
        getData(sessionId,totalCount);
        checkList();
    }

    @Override
    protected void work2Imps() throws Exception {
        if (goodsList!=null && goodsList.size()>0){
            for (Goods bean : goodsList){
                bean.setImg_cover(bean.getImg_cover().substring(1));
            }
            transInteractionGoods(goodsList, CreditCardGoods.class,"JSON_DATA",false);
        }
    }
}
