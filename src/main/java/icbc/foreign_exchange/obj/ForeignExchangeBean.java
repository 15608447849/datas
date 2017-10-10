package icbc.foreign_exchange.obj;

/**
 * Created by user on 2017/8/11.
 *
 "id":1,
 "currency_code":"USD",
 "currency_name":"美元(USD)",
 "Per":"668.03",
 "ExchOffer":"669.64",
 "ExchBid":"676.73",
 "CashBid":"676.73",
 "time":"2017.07.25 11:23:12"

 */
public class ForeignExchangeBean {
    private String id;
    private String currency_code;
    private String currency_name;
    private String Per;
    private String ExchOffer;
    private String ExchBid;
    private String CashBid;
    private String time;
    public ForeignExchangeBean() {

    }

    public ForeignExchangeBean(String id,String currency_code) {
        this.id = id;
        this.currency_code = currency_code;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }

    public String getPer() {
        return Per;
    }

    public void setPer(String per) {
        Per = per;
    }

    public String getExchOffer() {
        return ExchOffer;
    }

    public void setExchOffer(String exchOffer) {
        ExchOffer = exchOffer;
    }

    public String getCashBid() {
        return CashBid;
    }

    public void setCashBid(String cashBid) {
        CashBid = cashBid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getExchBid() {
        return ExchBid;
    }

    public void setExchBid(String exchBid) {
        ExchBid = exchBid;
    }

    public void tData(ForeignExchangeBean old){
        this.setTime(old.getTime());
        this.setPer(old.getPer());
        this.setCashBid(old.getCashBid());
        this.setCurrency_name(old.getCurrency_name());
        this.setExchBid(old.getExchBid());
        this.setExchOffer(old.getExchOffer());
    }

}
