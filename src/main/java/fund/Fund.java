package fund;

import com.google.gson.Gson;
import fund.obj.HighProfit;
import fund.obj.HotFund;
import fund.obj.NewFund;
import interaction.FundGoods;
import interaction.FundHotGoods;
import interaction.FundNewGoods;
import interaction.FundProfitGoods;
import interfaces.ActionCall;
import interfaces.BaseThread;
import lunch.Param;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2017/7/3.
 * 理财
 */
public class Fund extends BaseThread{
    private static final String PAGE_PH = "$page$";// 页码占位符
    private static final String NEW_PROD_URL = "https://mybank.icbc.com.cn/icbc/newperbank/perbank3/fund/frame_fund_nologin.jsp?currPageNum="
            + PAGE_PH + "&SheetFlag=0&ChannelCode=302";
    private static final String ALL_PROD_URL = "https://mybank.icbc.com.cn/icbc/newperbank/perbank3/fund/frame_fund_nologin.jsp?currPageNum="
            + PAGE_PH + "&SheetFlag=8&ChannelCode=302";
    private static final String HOT_PROD_URL = "https://mybank.icbc.com.cn/icbc/newperbank/perbank3/fund/frame_fund_hot_nologin.jsp?currPageNum="
            + PAGE_PH;
    private static final String RANK_PROD_URL = "https://mybank.icbc.com.cn/icbc/newperbank/perbank3/fund/frame_fund_income_nologin.jsp?currPageNum="
            + PAGE_PH;

    private static final String LINK="https://mybank.icbc.com.cn/servlet/ICBCPERBANKLocationServiceServlet?serviceId=PBL200302&transData=";

    public Fund() {
        this(null);
    }

    public Fund(ActionCall action) {
        super(Param.HOME,Param.PATH);
        this.setActionCall(action);
        start();
    }

  private Map<String, List> fund = new HashMap<>();

    @Override
    protected void workImps() throws Exception {
        fund.clear();
        fund.put("hot", getHotFund());
        fund.put("highProfits", getHighProfits());
        fund.put("news", getNewFund());
        Map<String, List<HotFund>> allFund = getAllFund();
        fund.put("little", allFund.get("little"));
        fund.put("low", allFund.get("low"));
        fund.put("mid", allFund.get("mid"));
        fund.put("high", allFund.get("high"));
        fund.put("highest", allFund.get("highest"));
        writeJSON(new Gson().toJson(fund));
    }

    @Override
    protected void work2Imps() throws Exception {
        FundGoods fundGoods = new FundGoods(
                work2Goods1(fund.get("hot")),
                work2Goods2(fund.get("highProfits")),
                work2Goods3(fund.get("news"))
        );
        transInteractionGoods(fundGoods, FundGoods.class,"JSON_DATA");
        fundGoods.selfLoop();
    }

    protected List<FundHotGoods> work2Goods1(List<HotFund> hotFunds){
        List<FundHotGoods> fundHotGoods =new LinkedList<>();
        for (int i = 0;i<hotFunds.size();i++) {
            fundHotGoods.add(new FundHotGoods(i,hotFunds.get(i)));
        }
        return fundHotGoods;
    }

    protected List<FundProfitGoods> work2Goods2(List<HighProfit> highProfits){
        List<FundProfitGoods> fundProfitGoods =new LinkedList<>();
        for (int i = 0;i<highProfits.size();i++) {
            fundProfitGoods.add(new FundProfitGoods(i,highProfits.get(i)));
        }
        return fundProfitGoods;
    }

    protected List<FundNewGoods> work2Goods3(List<NewFund> newFunds){
        List<FundNewGoods> founNewGoods=new LinkedList<>();
        for (int i = 0;i<newFunds.size();i++) {
            founNewGoods.add(new FundNewGoods(i,newFunds.get(i)));
        }
        return founNewGoods;
    }

    public List<NewFund> getNewFund() throws Exception{
        List<NewFund> nFunds = new LinkedList<>();
        Document document = Jsoup.connect(NEW_PROD_URL.replace(PAGE_PH, 1 + "")).timeout(TIMEOUT).get();
        // 拿到总页数
        int page = getPage(document);
        for (int j = 1; j <= page; j++) {
            if (j > 1){
                document = Jsoup.connect(NEW_PROD_URL.replace(PAGE_PH, j + "")).timeout(TIMEOUT).get();
            }

            Elements tbody = document.select("tbody[id=datatable-body]");
            Elements trs = tbody.select("tr");
            for (int i = 0; i < trs.size(); i++) {
                Elements tds = trs.get(i).select("td");
                NewFund nFund = new NewFund(
                        (j - 1) * 30 + i,
                        tds.get(1).text(),
                        tds.get(0)
                        .text(),
                        LINK+tds.get(0).text(),
                        tds.get(2).text(),
                        tds.get(5).text(),
                        tds.get(6).text(),
                        tds.get(3).text(),
                        tds.get(4).text());
                nFunds.add(nFund);
            }
        }

        return nFunds;
    }

    public List<HighProfit> getHighProfits() throws Exception{
        List<HighProfit> highProfits = new LinkedList<>();
        Document document = Jsoup.connect(RANK_PROD_URL.replace(PAGE_PH, 1 + "")).timeout(TIMEOUT).get();
        // 拿到总页数
        int page = getPage(document);
        for (int j = 1; j <= page; j++) {
            if (j > 1){
                document = Jsoup.connect(RANK_PROD_URL.replace(PAGE_PH, j + "")).timeout(TIMEOUT).get();
            }
            Elements tbody = document.select("tbody[id=datatable-body]");
            Elements trs = tbody.select("tr");

            for (int i = 0; i < trs.size(); i++) {
                Elements tds = trs.get(i).select("td");
                Elements imgs = tds.select("img");
                HighProfit highProfit = new HighProfit(
                        (
                        j - 1) * 30 + i,
                        tds.get(1).text(),
                        tds.get(0).text(),
                        LINK+tds.get(0).text(),
                        tds.get(2).text(),
                        tds.get(4).html(),
                        tds.get(3).text(),
                        tds.get(5).text(),
                        tds.get(6).text(),
                        tds.get(7).text(),
                        tds.get(8).text(),
                        tds.get(9).text(),
                        tds.get(10).text(),
                        imgs.size()
                );
                highProfits.add(highProfit);
            }
        }
        return highProfits;
    }

    public List<HotFund> getHotFund() throws Exception {
        List<HotFund> hFunds = new LinkedList<HotFund>();
        Document document = Jsoup.connect( HOT_PROD_URL.replace(
                PAGE_PH, 1 + "")).timeout(TIMEOUT).get();

        // 拿到总页数
        int page = getPage(document);
        for (int j = 1; j <= page; j++) {
            if (j > 1){
                document = Jsoup.connect( HOT_PROD_URL.replace(
                        PAGE_PH, j + "")).timeout(TIMEOUT).get();
            }

            Elements tbody = document.select("tbody[id=datatable-body]");
            Elements trs = tbody.select("tr");

            for (int i = 0; i < trs.size(); i++) {
                Elements tds = trs.get(i).select("td");
                Elements imgs = tds.select("img");
                HotFund hFund = new HotFund(
                        (j - 1) * 30 + i,
                        tds.get(1).text(),
                        tds.get(0).text(),
                        LINK+tds.get(0).text(),
                        tds.get(4).text(),
                        tds.get(5).text(),
                        tds.get(6).text(),
                        tds.get(7).text(),
                        imgs.size(),
                        tds.get(2).text(),
                        tds.get(3).text(),
                        tds.get(9).text());
                hFunds.add(hFund);
            }
        }
        return hFunds;
    }

    public Map<String, List<HotFund>> getAllFund() throws Exception{
        Document document = Jsoup.connect(ALL_PROD_URL.replace(
                PAGE_PH, 1 + "")).timeout(TIMEOUT).get();
        // 拿到总页数
        int page = getPage(document);
        Map<String, List<HotFund>> allFundMap = new HashMap<>();
        List<HotFund> little = new LinkedList<HotFund>();// < 0
        List<HotFund> low = new LinkedList<HotFund>();// 0-10%
        List<HotFund> mid = new LinkedList<HotFund>();// 10-20%
        List<HotFund> high = new LinkedList<HotFund>();// 20-30%
        List<HotFund> highest = new LinkedList<HotFund>();// > 30%
        for (int j = 1; j <= page; j++) {
            if (j > 1){

                document = Jsoup.connect(   ALL_PROD_URL.replace(
                        PAGE_PH, j + "")).timeout(TIMEOUT).get();
            }
            Elements tbody = document.select("tbody[id=datatable-body]");
            Elements trs = tbody.select("tr");
            for (int i = 0; i < trs.size(); i++) {
                Elements tds = trs.get(i).select("td");
                HotFund hfund = new HotFund(
                        (j - 1) * 30 + i,
                        tds.get(1).text(),
                        tds.get(0).text(),
                        LINK+tds.get(0).text(),
                        tds.get(2).text(),
                        tds.get(3).text(),
                        tds.get(4).text(),
                        tds.get(5).text(),
                        0,
                        "",
                        "",
                        ""
                );
                double mrate = 0;
                try {
                    mrate = Double.parseDouble(hfund.getMonth_rise_rate());
                } catch (Exception e) {
                    mrate = -1;
                }
                if (mrate < 0) {
                    little.add(hfund);
                } else if (mrate >= 0 && mrate < 10) {
                    low.add(hfund);
                } else if (mrate >= 10 && mrate < 20) {
                    mid.add(hfund);
                } else if (mrate >= 20 && mrate < 30) {
                    high.add(hfund);
                } else if (mrate >= 30) {
                    highest.add(hfund);
                }
            }
        }
        allFundMap.put("little", little);
        allFundMap.put("low", low);
        allFundMap.put("mid", mid);
        allFundMap.put("high", high);
        allFundMap.put("highest", highest);
        return allFundMap;
    }

    private int getPage(Document document) {
        // 拿到总页数
        return Integer
                .parseInt(document
                        .select(".ebdp-pc4promote-pageturn-totalpage b")
                        .first().text());
    }
}
