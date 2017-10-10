package ccb.interaction.action;

import ccb.interaction.Interactions;

import ccb.interaction.obj.fundX.FundXBean;
import ccb.interaction.obj.fundX.JEntity;
import ccb.interaction.obj.fundX.JEntityBean;


/**
 * Created by user on 2017/9/19.
 */
public class Fund4 extends FundX {

    private final String URL = "http://fund.ccb.com/tran/WCCMainPlatV5?CCB_IBSVersion=V5&isAjaxRequest=true&SERVLET_NAME=WCCMainPlatV5&TXCODE=NF4J01&PERPAGE=%d&ISCURRFUND=0&PAGE=%d";
    private final String LINK = "http://fund.ccb.com/cn/fund/product/product_detail.html?%s&%s&%s=0";


    public Fund4(Interactions interaction) throws Exception {
        super(interaction);
    }


    @Override
    void execute() throws Exception {
        String url = PRE + TYPE_PARAM;
        url = String.format(url,"NF4J01");
        url = url +  PARAM2 + END;
        execute(url,0,1,200);
    }


    /**
     *       id:1002, //自增ID
     code:'001740', //代码  FUNDCODE
     type:'混合型', //类型 FSTLEVELNAME
     name:'广大中国制造', //基金名称  FUNDNAME
     net_worth:'1.3690', //最新净值  NETVALUE
     Accumulated_net:'1.3690', //累计净值  TOTALVALUE
     one_week:'-0.80%', //近1日 NETVALUECHG
     one_month:'-0.80%', //近1月 W4NAVCHGRATE
     three_month:'30.55%',//近3月 W12NAVCHGRATE
     qr_code:'http://fund.ccb.com/cn/fund/product/product_detail.html?162411&098&ISCURRFUND=0' //二维码规则为    http://fund.ccb.com/cn/fund/product/product_detail.html?{FUNDCODE}&{FUNDMKTCODE}&{ISCURRFUND}=0
     */



    @Override
    protected void translation(int type, JEntity entity) {
        FundXBean bean;
        for (JEntityBean it : entity.getINFO()){
            bean= new FundXBean();
            bean.setId(interaction.getJsBean().getFund4().size()+1);
            bean.setCode(it.getFUNDCODE());
            bean.setType(it.getFSTLEVELNAME());
            bean.setName(it.getFUNDNAME());
            bean.setNet_worth(it.getNETVALUE());
            bean.setAccumulated_net(it.getTOTALVALUE());
            bean.setOne_week(it.getNETVALUECHG());
            bean.setOne_month(it.getW4NAVCHGRATE());
            bean.setThree_month(it.getW12NAVCHGRATE());
            bean.setQr_code(String.format(LINK,it.getFUNDCODE(),it.getFUNDMKTCODE(),it.getISCURRFUND()));
            interaction.getJsBean().getFund4().add(bean);
        }
    }
}
