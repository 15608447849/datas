package abchina.preciousMetal.obj;

/**
 * Created by user on 2017/10/10.
 */
public class Table1Bean {
    /**
     * ProdCode : 9101
     * ProdName : 人民币账户黄金
     * UpLowDirection : 0
     * CustomerSell : 272.61
     * CustomerBuy : 273.11
     * LowSell : 272.32
     * HighBuy : 274.06
     * UpdateTime : 2017-10-10 10:47:30
     * ShowOrder : 1
     * BasePrice : 272.930000
     * CurrentPrice : 272.860000
     * BuyUrl :
     */

    private String ProdCode;
    private String ProdName;
    private String UpLowDirection;
    private String CustomerSell;
    private String CustomerBuy;
    private String LowSell;
    private String HighBuy;
    private String UpdateTime;
    private String ShowOrder;
    private String BasePrice;
    private String CurrentPrice;
    private String BuyUrl;

    public String getProdCode() {
        return ProdCode;
    }

    public void setProdCode(String ProdCode) {
        this.ProdCode = ProdCode;
    }

    public String getProdName() {
        return ProdName;
    }

    public void setProdName(String ProdName) {
        this.ProdName = ProdName;
    }

    public String getUpLowDirection() {
        return UpLowDirection;
    }

    public void setUpLowDirection(String UpLowDirection) {
        this.UpLowDirection = UpLowDirection;
    }

    public String getCustomerSell() {
        return CustomerSell;
    }

    public void setCustomerSell(String CustomerSell) {
        this.CustomerSell = CustomerSell;
    }

    public String getCustomerBuy() {
        return CustomerBuy;
    }

    public void setCustomerBuy(String CustomerBuy) {
        this.CustomerBuy = CustomerBuy;
    }

    public String getLowSell() {
        return LowSell;
    }

    public void setLowSell(String LowSell) {
        this.LowSell = LowSell;
    }

    public String getHighBuy() {
        return HighBuy;
    }

    public void setHighBuy(String HighBuy) {
        this.HighBuy = HighBuy;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }

    public String getShowOrder() {
        return ShowOrder;
    }

    public void setShowOrder(String ShowOrder) {
        this.ShowOrder = ShowOrder;
    }

    public String getBasePrice() {
        return BasePrice;
    }

    public void setBasePrice(String BasePrice) {
        this.BasePrice = BasePrice;
    }

    public String getCurrentPrice() {
        return CurrentPrice;
    }

    public void setCurrentPrice(String CurrentPrice) {
        this.CurrentPrice = CurrentPrice;
    }

    public String getBuyUrl() {
        return BuyUrl;
    }

    public void setBuyUrl(String BuyUrl) {
        this.BuyUrl = BuyUrl;
    }

    @Override
    public String toString() {
        return "Table1Bean{" +
                "ProdCode='" + ProdCode + '\'' +
                ", ProdName='" + ProdName + '\'' +
                ", UpLowDirection='" + UpLowDirection + '\'' +
                ", CustomerSell='" + CustomerSell + '\'' +
                ", CustomerBuy='" + CustomerBuy + '\'' +
                ", LowSell='" + LowSell + '\'' +
                ", HighBuy='" + HighBuy + '\'' +
                ", UpdateTime='" + UpdateTime + '\'' +
                ", ShowOrder='" + ShowOrder + '\'' +
                ", BasePrice='" + BasePrice + '\'' +
                ", CurrentPrice='" + CurrentPrice + '\'' +
                ", BuyUrl='" + BuyUrl + '\'' +
                '}';
    }
}