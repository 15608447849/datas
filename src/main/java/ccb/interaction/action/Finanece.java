package ccb.interaction.action;

import ccb.interaction.Interactions;
import ccb.interaction.obj.finance.FinanceBean;
import ccb.interaction.obj.finance.FmJson;
import ccb.interaction.obj.finance.JentityFinanceBean;
import com.google.gson.Gson;
import lunch.Say;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;

/**
 * Created by user on 2017/9/18.
 */
public class Finanece extends InteractionsSubImp {
    private final String url_homa= "http://finance.ccb.com/cn/finance/product.html";
    private final String  url_pre = "http://finance.ccb.com/cc_webtran/queryFinanceProdList.gsp?queryForm.brand=%s";
    private final String url_end = "&queryForm.provinceId=110&pageNo=1&pageSize=%d";
    private final String link = "http://finance.ccb.com/cn/finance/ProductDetails.html?PRODUCT_ID=%s";


    private SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Finanece(Interactions interaction) throws Exception {
        super(interaction);
    }

    @Override
    void execute() throws Exception {
       parse("01","利得盈");
       parse("02","建行财富");
       parse("03","乾元");
       parse("04","汇得盈");
       parse("05","其它");
    }

    private void parse(String type,String name) throws Exception  {

        String url = String.format(url_pre,type);
        url += url_end;
        String val = String.format(url,1);
        val = interaction.getTextByUrl(val);
        Gson gson = new Gson();
        FmJson fmJson = gson.fromJson(val,FmJson.class);
        val = String.format(url, fmJson.getTotalCount());
        val = interaction.getTextByUrl(val);
        fmJson = gson.fromJson(val,FmJson.class);
        FinanceBean financeBean;
        for (JentityFinanceBean it : fmJson.getProdList()){
            financeBean = new FinanceBean();
            financeBean.setId(interaction.getJsBean().getFinancial().size()+1);
            financeBean.setName(it.getName());
            financeBean.setProfit(it.getYieldRate()+"%");
            financeBean.setNeed_money(it.getPurFloorAmt()+"万");
            financeBean.setInvestment_days(it.getInvestPeriod()+"天");
            financeBean.setType(name);
            financeBean.setQr_code(String.format(link,it.getCode()));
            interaction.getJsBean().getFinancial().add(financeBean);
        }
    }


}
