package ccb.foreignExchange.obj;

import icbc.foreign_exchange.obj.ForeignExchangeBean;

/**
 * Created by user on 2017/9/15.
 */
public class Bean {

    public Bean(ForeignExchangeBean bean) {
        this.id  = bean.getId();
        this.country = bean.getCurrency_code();
        this.purchase = bean.getExchOffer();
        this.out = bean.getExchBid();
    }

    /**
     * id : 1
     * country : USD
     * purchase : 666.66
     * out : 55555
     */



    private String id;
    private String country;
    private String purchase;
    private String out;

    public String getId() {
        return id;
    }


    public String getCountry() {
        return country;
    }


    public String getPurchase() {
        return purchase;
    }


    public String getOut() {
        return out;
    }
}
