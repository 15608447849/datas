package ccb.foreignExchange.obj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/9/15.
 */
public class JsBean {
    private List<Bean> rate = new ArrayList<>();

    public List<Bean> getRate() {
        return rate;
    }
    public void put(Bean bean){
        rate.add(bean);
    }
    public void clear(){
        rate.clear();
    }
}
