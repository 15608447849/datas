package abchina.all.interaction.financial;

import abchina.all.AllData;
import abchina.all.imps.AllDataIns;
import abchina.all.interaction.financial.obj.Bean;
import abchina.all.interaction.financial.obj.Bean2;
import abchina.all.interaction.financial.obj.SourceData;
import lunch.Say;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by user on 2017/10/11.
 */
public class Financial extends AllDataIns {
    public Financial(AllData mData) throws Exception {
        super(mData);
    }
    private int NUMBER;
    private String link;
    private String mUrl ;
    private HashMap<String,String> map;
    @Override
    protected void init() {
        NUMBER = 1500;
        link = "http://ewealth.abchina.com/fs/%s.htm";
        mUrl = "http://ewealth.abchina.com/app/data/api/DataService/BoeProductV2?i=%d&s=%d";
        map = new HashMap<>();
        map.put("当前在售","&w=%25E5%258F%25AF%25E5%2594%25AE%257C%257C%257C%257C%257C%257C%257C1%257C%257C0%257C%257C0");
        map.put("即将发售","&w=%25E6%259C%25AA%25E5%2594%25AE%257C%257C%257C%257C%257C%257C%257C1%257C%257C0%257C%257C0");
        map.put("销售完成","&w=%25E5%25B7%25B2%25E5%2594%25AE%257C%257C%257C%257C%257C%257C%257C1%257C%257C0%257C%257C0");
    }

    @Override
    public void execute() throws Exception {
        Iterator<Map.Entry<String,String>> iterator = map.entrySet().iterator();
        Map.Entry<String,String> entry;
        while (iterator.hasNext()){
            entry = iterator.next();
            typeToData(entry.getKey(),entry.getValue());
        }
    }





    private void typeToData(String type,String endParams) throws Exception{

        //查询条数
        SourceData sData = translate(endParams,1,1);
        int size = sData.getData().getTable1().get(0).getTotal();

        int cIndex = 1;
        Bean bean;
        for (int i= 0; i < size;i += NUMBER){
            sData = translate(endParams,cIndex,NUMBER);

            for (SourceData.DataBean.TableBean data : sData.getData().getTable()){
                bean = new Bean();
                bean.setId(mData.getJsObject().getFinancial().size());
                bean.setName(data.getProdName());
                bean.setProfit(data.getProdProfit());
                bean.setNeed_money(data.getPurStarAmo()+"元");
                bean.setInvestment_days(data.getProdLimit());
                bean.setArea(data.getProdArea());
                bean.setTime(data.getProdSaleDate());
                bean.setType(type);
                bean.setQr_code(String.format(link,data.getProductNo()));
                mData.getJsObject().getFinancial().add(bean);
            }
            cIndex++;
        }

    }




    private SourceData translate(String last,int page, int number) throws Exception {
        return mData.urlTranslateJsonObject(String.format(mUrl,page,number)+last,SourceData.class);
    }

}
