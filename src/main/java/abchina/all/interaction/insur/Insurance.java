package abchina.all.interaction.insur;

import abchina.all.AllData;
import abchina.all.imps.AllDataIns;
import abchina.all.interaction.insur.obj.Bean;
import abchina.all.interaction.insur.obj.Bean2;
import abchina.all.interaction.insur.obj.SourceData;
import lunch.Say;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by user on 2017/10/11.
 */
public class Insurance extends AllDataIns {

    private final int NUMBER = 200;
    private String  mUrl;
    private String link ;
    public Insurance(AllData mData) throws Exception {
        super(mData);
    }

    @Override
    protected void init() {
        mUrl = "http://ewealth.abchina.com/app/data/api/DataService/InsuranceFilterV2?i=%d&s=%d";
        link ="http://ewealth.abchina.com/Insurance/%s.htm";
    }

    @Override
    public void execute() throws Exception {

        SourceData sData = translate(0,1);
        int size = Integer.parseInt(sData.getData().getTable1().get(0).getRowsCount());

        int pageSize = 1;
        if (size>NUMBER){
            pageSize=size/NUMBER; //获取总页数
            if (size % NUMBER != 0 ){
                pageSize +=1 ;
            }
        }
        Bean bean;
        //分页遍历
        for (int i = 0;i < pageSize;i++){
            sData = translate(i,NUMBER);
            for (SourceData.DataBean.TableBean data : sData.getData().getTable()){
                bean = new Bean();
                bean.setId(mData.getJsObject().getInsurance().size());
                bean.setName(data.getInsuranceName());
                bean.setCompany(data.getCompanyName());
                bean.setType(data.getITypeName());
                bean.setTerm(data.getInsuranceDate());
                bean.setQr_code(String.format(link,data.getProdCode()));
                mData.getJsObject().getInsurance().add(bean);
            }
        }
        //获取热销榜单
        Document document = mData.getDocumentByUrl("http://ewealth.abchina.com/insurance/exbd/");
        Elements trList = document.select("tbody").select("tr");
        trList.remove(0);
        trList.remove(0);

        Bean2 bean2 ;
        Elements tdList;
        for (Element element : trList){
            tdList = element.select("td");
          bean2 = new Bean2();
          bean2.setId(mData.getJsObject().getInsurance1().size());

          bean2.setRanking(tdList.get(0).text());
          bean2.setName(tdList.get(1).text().replace("...",""));
          bean2.setCompany(tdList.get(2).text());
          bean2.setType(tdList.get(3).text());
          bean2.setTerm(tdList.get(4).text());
          bean2.setQr_code(tdList.get(1).select("a").first().attr("href").replace("..","http://ewealth.abchina.com/insurance"));
            mData.getJsObject().getInsurance1().add(bean2);


        }

    }

    private SourceData translate(int page,int number) throws Exception {
        return mData.jTextToJson( mData.getHttpResponse(String.format(mUrl,page,number),null,true).body(),SourceData.class);
    }


}
