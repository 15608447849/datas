package icbc.foreign_exchange;

import icbc.Icbc;
import icbc.foreign_exchange.obj.ForeignExchangeBean;
import interfaces.ActionCall;
import interfaces.BaseThread;
import interfaces.ParamManager;
import lunch.Say;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by user on 2017/8/11.
 */
public class ForeignExchange extends BaseThread {

    private final String URL_ADDRESS = "http://www.icbc.com.cn/ICBCDynamicSite/Optimize/Quotation/QuotationListIframe.aspx";

    protected final ArrayList<ForeignExchangeBean> beanList = new ArrayList<>();
    private void initList(){
        beanList.add(new ForeignExchangeBean("1","USD"));
        beanList.add(new ForeignExchangeBean("2","CAD"));
        beanList.add(new ForeignExchangeBean("3","BRL"));
        beanList.add(new ForeignExchangeBean("4","GBP"));
        beanList.add(new ForeignExchangeBean("5","ZAR"));
        beanList.add(new ForeignExchangeBean("6","EUR"));
        beanList.add(new ForeignExchangeBean("7","RUB"));
        beanList.add(new ForeignExchangeBean("8","HKD"));
        beanList.add(new ForeignExchangeBean("9","JPY"));
        beanList.add(new ForeignExchangeBean("10","AUD"));
    }


    //构造
    public ForeignExchange(ActionCall action) {
        this(Icbc.getInstance(),action);
    }
    //构造 - 子类使用
    public ForeignExchange(ParamManager paramManager,ActionCall action) {
        super(paramManager);
        this.setActionCall(action);
        initList();
        start();
    }

    //工作
    @Override
    protected void workImps() throws Exception {
        //抓取全部
        Document doc = Jsoup.connect(URL_ADDRESS).timeout(TIMEOUT).get();
        Elements els = doc.select("table.tableDataTable tr");

        ForeignExchangeBean bean ;
        String val;
       for (int i = 1;i<els.size();i++){
           Element el = els.get(i);
           Elements tds = el.select("td");
           bean = new ForeignExchangeBean();
           bean.setId(i+"");
           val = tds.get(0).text();
           bean.setCurrency_name(val);
           bean.setCurrency_code(val.substring(val.indexOf("(")+1,val.lastIndexOf(")")));
           bean.setPer(tds.get(1).text());
           bean.setExchOffer(tds.get(2).text());
           bean.setExchBid(tds.get(3).text());
           bean.setCashBid(tds.get(4).text());
           bean.setTime(tds.get(5).text());
           //过滤
          for (ForeignExchangeBean b : beanList){
              if (bean.getCurrency_code().equals(b.getCurrency_code())){
                  b.tData(bean);
              }
          }
       }
        //根据类型创建Map - > json
        writeFile(objTansJson(beanList),"foreign_exchange.json");
    }

}
