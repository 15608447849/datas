package ccb.accountGold.obj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/9/15.
 */
public class JsBean {
    private Bean[] preciousMetal_2 = new Bean[10];

    public Bean[] getPreciousMetal_2() {
        return preciousMetal_2;
    }

    public void add(Bean bean){
        if (bean.getId()<0 || bean.getId()>preciousMetal_2.length) return;
        preciousMetal_2[bean.getId()] = bean;
    }
}
