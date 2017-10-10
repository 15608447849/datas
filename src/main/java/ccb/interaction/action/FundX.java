package ccb.interaction.action;

import ccb.interaction.Interactions;
import ccb.interaction.obj.fundX.JEntity;
import com.google.gson.Gson;

/**
 * Created by user on 2017/9/19.
 */
public abstract class FundX extends InteractionsSubImp {
    /**
     * TXCODE=NF4J07  (fund 1 2 3 )
     * TXCODE=NF4J01  (fund 4 )
     *
     * 1 - FIRSTTAG=%1
     * 2 - FIRSTTAG=% (2 4 5 )
     * 3 - FIRSTTAG=%3
     * 4 - FIRSTTAG=无 -> &ISCURRFUND=0
     *PAGE=%d&PERPAGE=%d 控制当前页数 和 每页显示数 用于翻页
     */

    protected final String LINK = "http://fund.ccb.com/cn/fund/product/product_detail.html?%s&%s&%s=0";
    protected final String PRE = "http://fund.ccb.com/tran/WCCMainPlatV5?CCB_IBSVersion=V5&isAjaxRequest=true&SERVLET_NAME=WCCMainPlatV5";
    protected final String TYPE_PARAM = "&TXCODE=%s";
    protected final String PARAM1 = "&FIRSTTAG=%d";
    protected final String PARAM2 = "&ISCURRFUND=%d";
    protected final String END = "&PAGE=%d&PERPAGE=%d";

    public FundX(Interactions interaction) throws Exception {
        super(interaction);
    }

    protected String getType(int number){
        if (number==2) return "涨幅阶段";
        if (number==4) return "特色基金";
        if (number==5) return "人气基金";
        return "";
    }



    /**
     *
     * @param address 格式化地址
     * @param type 类型
     * @param curPage 当前页数
     * @param pageSize 每页数量
     */
    protected void execute(String address, int type, int curPage, int pageSize) throws Exception {
        String url = String.format(address,type,curPage,pageSize);
        String text =  interaction.getTextByUrl(url,interaction.getHeader());
        JEntity entity = new Gson().fromJson(text,JEntity.class);
        translation(type,entity);
        //计算 -是否进入下一页
        if (entity.getCurPage() < entity.getTotalPage()){
            curPage++;
            execute(address,type,curPage,pageSize);
        }
    }

    protected abstract void translation(int type,JEntity entity);






}
