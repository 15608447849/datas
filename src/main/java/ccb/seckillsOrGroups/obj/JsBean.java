package ccb.seckillsOrGroups.obj;

import java.util.ArrayList;

/**
 * Created by user on 2017/9/12.
 */
public class JsBean {
    private ArrayList<Bean> seckills = new ArrayList<>();
    private ArrayList<Bean> groups = new ArrayList<>();

    public void clear(){
        seckills.clear();
        groups.clear();
    }

    public void putkills(Bean bean){
        if (bean==null) return;
        seckills.add(bean);
    }
    public void putGroups(Bean bean){
        if (bean==null) return;
        groups.add(bean);
    }

    public ArrayList<Bean> getSeckills() {
        return seckills;
    }

    public ArrayList<Bean> getGroups() {
        return groups;
    }
}
