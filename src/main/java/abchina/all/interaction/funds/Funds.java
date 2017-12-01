package abchina.all.interaction.funds;

import abchina.all.AllData;
import abchina.all.imps.AllDataIns;
import abchina.all.interaction.funds.obj.Bean;
import lunch.Say;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by user on 2017/10/12.
 */
public class Funds extends AllDataIns {

    public Funds(AllData mData) throws Exception {
        super(mData);
    }

    private String pre_link;
    private String url_prefer;
    private String url_ybphb;
    private String url_new;
    @Override
    protected void init() {
        pre_link = "http://ewealth.abchina.com/fund";
        url_prefer =  pre_link+"/AbPrefer/";
        url_new =pre_link+"/FundNew/";
        url_ybphb = pre_link+ "/yjphb/";
    }

    @Override
    public void execute() throws Exception {

        //优选
        select(url_prefer,0,"tbody",true);
        //新发
        select(url_new,1,"tbody",true);
        //股票型 周涨幅 - 10
        //混合型 周涨幅 - 11
        //债券性 周涨幅 - 12
        //保本型 周涨幅 - 13
        select(url_ybphb,10,"tbody[id=gpx_jzf]",false);
        select(url_ybphb,11,"tbody[id=hhx_jzf]",false);
        select(url_ybphb,12,"tbody[id=zqx_jzf]",false);
        select(url_ybphb,13,"tbody[id=bbx_jzf]",false);
    }




    private void select(String url,int type,String tag,boolean deleteFirst) throws Exception {
        Elements trList = null;
        try {
            trList = mData.getDocumentByUrl(url).select(tag).select("tr");
        } catch (org.jsoup.HttpStatusException e) {
            if (e.getStatusCode() == 500){
                select(url,type,tag,deleteFirst);
                return;
            }
        }
        if (deleteFirst){
            trList.remove(0);
            trList.remove(0);
        }

        for (int i = 0; i < trList.size(); i+=2){
            if (type == 0 ){
                prefer(trList.get(i));
            }else if (type==1){
                fnew(trList.get(i));
            }else
            if (type == 10){
                yb("股票型",trList.get(i));
            }else if (type == 11){
                yb("混合型",trList.get(i));
            }else if (type == 12){
                yb("债券性",trList.get(i));
            }
            else if (type == 13){
                yb("保本型",trList.get(i));
            }
        }
    }



    //优选
    private void prefer(Element it) throws Exception{
        Elements tdList = it.select("td");
        Bean bean = new Bean();
        bean.setId(mData.getJsObject().getFund1().size());
        bean.setRanking(tdList.get(0).text());
        bean.setCode(tdList.get(1).text());
        bean.setQr_code(tdList.get(1).select("a").first().attr("href").replace("..",pre_link));
        bean.setName(tdList.get(2).text().replace("...",""));
        bean.setNet_worth(tdList.get(3).text());
        bean.setAccumulated_net(tdList.get(4).text());
        bean.setOne_day(FundAll.stringToFloatFormat(getScriptByTarget(tdList.get(5)),1,"%"));
        bean.setOne_month(FundAll.stringToFloatFormat(getScriptByTarget(tdList.get(6)),100,"%"));
        bean.setSeason_month(FundAll.stringToFloatFormat(getScriptByTarget(tdList.get(7)),100,"%"));
        mData.getJsObject().getFund1().add(bean);
    }

    //新发
    private void fnew(Element it)  throws Exception {
        Elements tdList = it.select("td");
        Bean bean = new Bean();
        bean.setId(mData.getJsObject().getFund3().size());
        bean.setCode(tdList.get(0).text());
        bean.setQr_code(tdList.get(0).select("a").first().attr("href").replace("..",pre_link));
        bean.setName(tdList.get(1).text());
        bean.setCompany(tdList.get(2).text());
        bean.setManager(tdList.get(3).text());
        mData.getJsObject().getFund3().add(bean);
    }
    //收益排行
    private void yb(String type, Element it) {
        Elements tdList = it.select("td");
        Bean bean = new Bean();
        bean.setId(mData.getJsObject().getFund2().size());
        bean.setType(type);
        bean.setRanking(tdList.get(0).text());
        bean.setCode(tdList.get(1).text());
        bean.setQr_code(tdList.get(1).select("a").first().attr("href").replace("..",pre_link));
        bean.setName(tdList.get(2).text());
        bean.setNet_worth(tdList.get(3).text());
        bean.setAccumulated_net(tdList.get(4).text());
        bean.setOne_week(FundAll.stringToFloatFormat(getScriptByTarget(tdList.get(5)),100,"%"));
        mData.getJsObject().getFund2().add(bean);
    }









    private String getScriptByTarget(Element element){
       return  mData.carpter( element.select("script").first().html(),"\"","\"");
    }


}
