package abchina.all.interaction.funds;

import abchina.all.AllData;
import abchina.all.imps.AllDataIns;
import abchina.all.interaction.funds.obj.Bean;
import abchina.all.interaction.funds.obj.SourceData;
import lunch.Say;

/**
 * Created by user on 2017/10/12.
 */
public class FundAll extends AllDataIns{

    public FundAll(AllData mData) throws Exception {
        super(mData);
    }

    private String pre;
    private String last;
    private int NUMBER;
    @Override
    protected void init() {
        //http://ewealth.abchina.com/app/data/api/DataService/FundFilterV2_New?i=1&s=1&w=0%257C-1%257C-1%257C1%257C0%257C1%257C1%257C0%257C-1%257C9_DESC&o=2
        NUMBER = 500;
        pre = "http://ewealth.abchina.com/app/data/api/DataService/FundFilterV2_New?i=%d&s=%d";
        last = "&w=0%257C-1%257C-1%257C1%257C0%257C1%257C1%257C0%257C-1%257C9_DESC&o=2";

    }

    @Override
    public void execute() throws Exception {
        SourceData sData = tranlslate(1,1);
        int size = Integer.parseInt(sData.getData().getTable1().get(0).getRowCount());
        int cPage = 1;
        Bean bean;
        for (int i = 0 ; i < size;i+=NUMBER){
            sData = tranlslate(cPage,NUMBER);

            for (SourceData.DataBean.TableBean data : sData.getData().getTable()){
                bean = new Bean();
                bean.setId(mData.getJsObject().getFund4().size());
                bean.setCode(data.getFundCode());
                bean.setName(data.getShortName());
                bean.setNet_worth(stringToFloatFormat(data.getNetValue()));
                bean.setAccumulated_net(stringToFloatFormat(data.getAccumulatedNetValue()));
                bean.setOne_day( stringToFloatFormat(data.getDayGrowthRate(),1,"%"));
                bean.setOne_month( stringToFloatFormat(data.getLastMonth(), 100,"%"));
                bean.setSeason_month( stringToFloatFormat(data.getLastQuarter(),100,"%"));
                bean.setQr_code(String.format("http://ewealth.abchina.com/fund/%s.htm",data.getFundCode()));
                mData.getJsObject().getFund4().add(bean);
            }
            cPage++;
        }




    }


    private SourceData tranlslate(int page,int number) throws Exception{
        return mData.urlTranslateJsonObject(String.format(pre,page,number)+last,SourceData.class);
    }



 protected static String stringToFloatFormat(String numberStr,float number,String mark){
     return String.format("%.2f",(Float.parseFloat(numberStr) * number)) + mark;
 }
    protected static String stringToFloatFormat(String numberStr){
        return stringToFloatFormat(numberStr,1,"");
    }

}
