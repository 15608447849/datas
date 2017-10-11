package abchina.all;

import abchina.AbcBTA;
import abchina.all.interaction.Pre.PreciousMetal_1;
import abchina.all.interaction.insur.Insurance;
import abchina.all.interaction.insur.obj.SourceData;
import abchina.all.obj.JsObject;
import interfaces.ActionCall;

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
//        new Insurance(this);
//        new PreciousMetal_1(this);

        transInteractionGoods(jsObject,"data","JSON");

    }

    @Override
    public String getGoodsImageUrl(String url, String path) {
        return super.getGoodsImageUrl(url, path, k, v);
    }

    public <T> T urlTranslateJsonObject(String urlFormat,int page, int number,Class<T> clazz) throws Exception {
        return jTextToJson( getHttpResponse(String.format(urlFormat,page,number),null,true).body(),clazz);
    }

}
