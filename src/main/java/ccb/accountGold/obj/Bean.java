package ccb.accountGold.obj;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2017/9/15.
 */

public class Bean {

    /**
     * id : 1 //自增ID
     * product : 美元金(钞) //产品类型
     * buy_price : 1312.36 //客户买入价
     * sale_price : 1338.36 //客户卖出价
     * unit : 美元/盎司 //单位
     * time : 09月04日 16:44分 //报价时间
     */


    private int id = -1;
    private String product;
    private String buy_price;
    private String sale_price;
    private String unit;
    private String time;

    public Bean(int id,String sec_cod, String curr1, String buy_pri, String sel_pri, String price_time) {
            this.id = id;
            this.product = sec_cod;
            this.unit = curr1;
            this.buy_price = buy_pri;
            this.sale_price = sel_pri;
            this.time = price_time;
    }

    public int getId() {
        return id;
    }


    public String getProduct() {
        return product;
    }


    public String getBuy_price() {
        return buy_price;
    }


    public String getSale_price() {
        return sale_price;
    }


    public String getUnit() {
        return unit;
    }


    public String getTime() {
        return time;
    }

}
