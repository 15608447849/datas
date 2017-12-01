package abchina.all.interaction.insur;

import abchina.all.AllData;
import abchina.all.imps.AllDataIns;
import abchina.all.interaction.insur.obj.Bean;
import abchina.all.interaction.insur.obj.Bean2;
import abchina.all.interaction.insur.obj.SourceData;
import lunch.Say;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

/**
 * Created by user on 2017/10/11.
 */
public class Insurance extends AllDataIns {

    private int NUMBER;
    private String  mUrl;
    private String link ;
    private  HashMap<String,String> map;
    public Insurance(AllData mData) throws Exception {
        super(mData);
    }
    @Override
    protected void init() {
        NUMBER = 500;
        //http://ewealth.abchina.com/app/data/api/DataService/InsuranceFilterV2?i=0&s=15&w=%257C%257C%257C%257C
        mUrl = "http://ewealth.abchina.com/app/data/api/DataService/InsuranceFilterV2?i=%d&s=%d";
        link ="http://ewealth.abchina.com/Insurance/%s.htm";
        map = new HashMap<>();
        map.put("Accept","application/json; charset=utf-8");
        map.put("Accept-Encoding","gzip, deflate");
        map.put("Accept-Language","zh-CN,zh;q=0.8");
        map.put("Cache-Control","max-age=0");
        map.put("Connection","keep-alive");
        map.put("Host","ewealth.abchina.com");
        map.put("Upgrade-Insecure-Requests","1");
        map.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
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
       hot();

    }

    private void hot() throws Exception{
        //获取热销榜单
        Document document =null;
        try {
            document = mData.getDocumentByUrl("http://ewealth.abchina.com/insurance/exbd/");
        }  catch (org.jsoup.HttpStatusException e) {
            if (e.getStatusCode() == 500){
                hot();
                return;
            }
        }
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
        return mData.urlTranslateJsonObject(String.format(mUrl,page,number)+"&w=%257C%257C%257C%257C",map,SourceData.class);
    }


}
