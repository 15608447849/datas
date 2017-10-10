package abchina.preciousMetal.obj;

import java.util.ArrayList;
import java.util.List;

import static interfaces.BaseThreadAdapter.dateFormat;

/**
 * Created by user on 2017/10/10.
 */
public class PreciousMetal_JS_3 {
    private ArrayList<Metal_3> preciousMetal_3 = new ArrayList<>();

    public ArrayList<Metal_3> getPreciousMetal_3() {
        return preciousMetal_3;
    }

    public void trans(List<TableBean> dataList) {

        Metal_3 metal;
        for (TableBean data : dataList)
        {
            metal = new Metal_3();
            metal.setId(preciousMetal_3.size());
            metal.setType(data.getProdCode());
            metal.setNow(data.getCurrentPrice());
            metal.setStatus(data.getUpLowDirection());
            metal.setRose(data.getUpLowRate());
            metal.setVolume(data.getShowOrder());
            metal.setOpening_price(data.getOpenPrice());
            metal.setOld_price(data.getYesterdayPrice());
            metal.setLow(data.getLowestPrice());
            metal.setHigh(data.getHighestPrice());
            metal.setTime(dateFormat(data.getUpdateTime(),null,null));
            preciousMetal_3.add(metal);
        }
    }
}
