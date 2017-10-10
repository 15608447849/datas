package ccb.interaction.obj.creditcard;

import java.util.List;

/**
 * Created by user on 2017/9/19.
 */
public class Jentity {
    /**
     * totalNum : 23
     * obj : []
     */

    private String totalNum;
    private List<JentityBean> obj;

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public List<JentityBean> getObj() {
        return obj;
    }

    public void setObj(List<JentityBean> obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "Jentity{" +
                "totalNum='" + totalNum + '\'' +
                ", obj=" + obj +
                '}';
    }
}
