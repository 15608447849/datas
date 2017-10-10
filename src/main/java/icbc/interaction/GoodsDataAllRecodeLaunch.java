package icbc.interaction;

import interfaces.ActionCall;
import interfaces.BaseThreadManager;
import interfaces.CatchBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/8/22.
 */
public class GoodsDataAllRecodeLaunch extends Thread implements ActionCall{
    private ActionCall callBy;
    private int sum;
    private final List<CatchBean> list = new ArrayList<>();
    public GoodsDataAllRecodeLaunch(ActionCall callBy) {
        this.callBy = callBy;
        this.start();
    }

    @Override
    public void run() {
        GoodsDataAllRecode.get().clear();
        List<String> list = GoodsDataAllRecode.get().getParamList();
        sum = list.size();
        for (String clazz : list){
            BaseThreadManager.getInstants().launchThread(clazz,this);
        }
    }

    @Override
    public void error(Exception e) {

    }

    @Override
    public void onComplete(CatchBean catchBean) {
        list.add(catchBean);
        GoodsDataAllRecode.get().recode(catchBean.getName());

        if (list.size() == GoodsDataAllRecode.get().getParamList().size()){
            callBy.onComplete(GoodsDataAllRecode.get().buildCatch());
        }

    }
}
