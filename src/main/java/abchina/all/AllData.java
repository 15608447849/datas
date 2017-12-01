package abchina.all;

import abchina.AbcBTA;
import abchina.all.interaction.Pre.PreciousMetal_1;
import abchina.all.interaction.card.Creditcard;
import abchina.all.interaction.financial.Financial;
import abchina.all.interaction.financial.Financial_1;
import abchina.all.interaction.funds.FundAll;
import abchina.all.interaction.funds.Funds;
import abchina.all.interaction.insur.Insurance;
import abchina.all.interaction.insur.obj.SourceData;
import abchina.all.interaction.loan.Loan;
import abchina.all.obj.JsObject;
import interfaces.ActionCall;
import org.jsoup.Connection;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2017/10/10.
 */
public class AllData extends AbcBTA {

    private final JsObject jsObject = new JsObject();


    private final String[] k = new String[]{"Referer"};
    private final String[] v = new String[]{"http://ewealth.abchina.com/images/irezNew.css"};
    public JsObject getJsObject() {
        return jsObject;
    }

    public AllData(ActionCall action) {
        super(action);
    }

    @Override
    protected void workImps() throws Exception {
        jsObject.clearAll();
        new Insurance(this);
        new PreciousMetal_1(this);
        new Financial(this);
        new Financial_1(this);
        new Creditcard(this);
        new Loan(this);
        new FundAll(this);
        new Funds(this);
        transInteractionGoods(jsObject,"data","JSON");
    }

    @Override
    public String getGoodsImageUrl(String url, String path) {
        return super.getGoodsImageUrl(url, path, k, v);
    }

    public <T> T urlTranslateJsonObject(String url, Map<String,String> map, Class<T> clazz) throws Exception {
        Connection.Response response = getHttpResponse(url,map,true);
        if (response.body()!=null) return jTextToJson( response.body(),clazz);
        return null;
    }
    public <T> T urlTranslateJsonObject(String url,Class<T> clazz) throws Exception {
        return urlTranslateJsonObject(url,null,clazz);
    }

}
