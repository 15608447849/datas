package ccb.interaction.action;

import ccb.interaction.Interactions;

import ccb.interaction.obj.fundX.FundXBean;
import ccb.interaction.obj.fundX.JEntity;
import ccb.interaction.obj.fundX.JEntityBean;

/**
 * Created by user on 2017/9/19.
 */
public class Fund3 extends FundX {

    public Fund3(Interactions interaction) throws Exception {
        super(interaction);
    }

    @Override
    void execute() throws Exception {
        String url = PRE + TYPE_PARAM;
        url = String.format(url,"NF4J07");
        url = url  + PARAM1 + END;
        execute(url,3,1,200);
    }

    /**
     *
     id:1001, //自增ID
     code:'001740', //代码 CCBFUNDCODE
     type:'混合型', //类型   Glx_Fnd_Lvl1_CL_Nm
     name:'广大中国制造', //基金名称 Fnd_ShrtNm
     company:'建信人寿基金管理有限责任公司', //基金公司  Scr_Admn_Nm
     start_day:'2017-08-23', //认购开始日 SrtDt
     end_day:'2017-08-23', //认购结束日 EdDt
     high_rate:'1.2%', //最高认购费率 Eps_FeeRt_Val
     qr_code:'http://fund.ccb.com/cn/fund/product/product_detail.html?162411&098&ISCURRFUND=0' //二维码规则为    http://fund.ccb.com/cn/fund/product/product_detail.html?{CCBFUNDCODE}&{FUNDMKTCODE}&{ISCURRFUND}=0
     */
    @Override
    protected void translation(int type, JEntity entity) {
        FundXBean bean;
        for (JEntityBean it : entity.getINFO()){
            bean = new FundXBean();
            bean.setId(interaction.getJsBean().getFund3().size()+1);
            bean.setCode(it.getCCBFUNDCODE());
            bean.setType(it.getGlx_Fnd_Lvl1_CL_Nm());
            bean.setName(it.getFnd_ShrtNm());
            bean.setCompany(it.getScr_Admn_Nm());
            bean.setStart_day(it.getSrtDt());
            bean.setEnd_day(it.getEdDt());
            bean.setHigh_rate(it.getEps_FeeRt_Val());
            bean.setQr_code(String.format(LINK,it.getCCBFUNDCODE(),it.getFUNDMKTCODE(),it.getISCURRFUND()));
            interaction.getJsBean().getFund3().add(bean);
        }
    }





}
