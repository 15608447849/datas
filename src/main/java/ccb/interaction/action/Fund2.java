package ccb.interaction.action;

import ccb.interaction.Interactions;

import ccb.interaction.obj.fundX.FundXBean;
import ccb.interaction.obj.fundX.JEntity;
import ccb.interaction.obj.fundX.JEntityBean;
import lunch.Say;


/**
 * Created by user on 2017/9/19.
 */
public class Fund2 extends FundX {
    public Fund2(Interactions interaction) throws Exception {
        super(interaction);
    }

    @Override
    void execute() throws Exception {
        String url = PRE + TYPE_PARAM;
        url = String.format(url,"NF4J07");
        url = url  + PARAM1 + END;
        execute(url,2,1,200);//涨幅阶段
        execute(url,4,1,200);//特色基金
        execute(url,5,1,200);//人气基金
    }
    /**
     * {
     id:1002,
     code:'001740',
     class:'涨幅阶段',
     type:'混合型',
     name:'广大中国制造',
     net_worth:'1.3690',
     Accumulated_net:'1.3690',
     one_week:'-0.80%',
     one_month:'-0.80%',
     three_month:'30.55%',
     qr_code:'http://fund.ccb.com/cn/fund/product/product_detail.html?162411&098&ISCURRFUND=0'
     }
     */
    @Override
    protected void translation(int type, JEntity entity) {
        FundXBean bean;
        for (JEntityBean it : entity.getINFO()){
            bean= new FundXBean();
            bean.setId(interaction.getJsBean().getFund2().size()+1);
            bean.setClassX(getType(type));
            bean.setCode(it.getFUNDCODE());
            bean.setType(it.getFSTLEVELNAME());
            bean.setName(it.getFUNDNAME());
            bean.setNet_worth(it.getNETVALUE());
            bean.setAccumulated_net(it.getTOTALVALUE());
            bean.setOne_week(it.getNETVALUECHG());
            bean.setOne_month(it.getW4NAVCHGRATE());
            bean.setThree_month(it.getW12NAVCHGRATE());
            bean.setQr_code(String.format(LINK,it.getFUNDCODE(),it.getFUNDMKTCODE(),it.getISCURRFUND()));
            interaction.getJsBean().getFund2().add(bean);
        }
    }


}
