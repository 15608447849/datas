package abchina.preciousMetal.obj;

import icbc.precious_metal.obj.Metal;

import java.util.ArrayList;
import java.util.List;

import static interfaces.BaseThreadAdapter.dateFormat;

/**
 * Created by user on 2017/10/10.
 */
public class PreciousMetal_JS_2 {
    private ArrayList<Metal_2> preciousMetal_2 = new ArrayList<>();
    public ArrayList<Metal_2> getPreciousMetal_2() {
        return preciousMetal_2;
    }

    public void trans(List<Table1Bean> dataList) {
        Metal_2 metal;
        for (Table1Bean data : dataList)
        {
            metal = new Metal_2();
            metal.setId(preciousMetal_2.size());
            metal.setType(data.getProdName());
            metal.setStatus(data.getUpLowDirection());
            metal.setSell(data.getCustomerSell());
            metal.setBuy(data.getCustomerBuy());
            metal.setLow(data.getLowSell());
            metal.setHigh(data.getHighBuy());
            metal.setTime(dateFormat(data.getUpdateTime(),null,null));
            preciousMetal_2.add(metal);
        }
    }
}
