package interfaces;

import java.util.List;

/**
 * Created by user on 2017/7/13.
 */
public class GoodsCatchBean extends CatchBean{
    private List<CatchBean> catchBeansList;
    public GoodsCatchBean(List<CatchBean> catchBeansList) {
        this.catchBeansList = catchBeansList;
    }

    public List<CatchBean> getCatchBeansList() {
        return catchBeansList;
    }
}
