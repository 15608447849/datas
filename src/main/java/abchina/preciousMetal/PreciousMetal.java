package abchina.preciousMetal;

import abchina.AbcBTA;
import abchina.preciousMetal.obj.PreciousMetal_JS_2;
import abchina.preciousMetal.obj.PreciousMetal_JS_3;
import abchina.preciousMetal.obj.SourceData;
import interfaces.ActionCall;
import lunch.Say;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;

/**
 * Created by user on 2017/10/10.
 */
public class PreciousMetal extends AbcBTA {
    private final String HURL = "http://ewealth.abchina.com/app/data/api/DataService/GoldInfoV2";
    public PreciousMetal(ActionCall action) {
        super(action);
    }
    @Override
    protected void workImps() throws Exception {
        Connection.Response cr  = getHttpResponse(HURL,null,true);
        SourceData data =jTextToJson(cr.body(),SourceData.class);
        PreciousMetal_JS_2 js_2 = new PreciousMetal_JS_2();
        js_2.trans(data.getData().getTable1());
        //写入js
        transInteractionGoods(js_2,"preciousMetal_2","JSON2");
        PreciousMetal_JS_3 js_3 = new PreciousMetal_JS_3();
        js_3.trans(data.getData().getTable());
        //写入js
        transInteractionGoods(js_3,"preciousMetal_3","JSON3");
    }
}
