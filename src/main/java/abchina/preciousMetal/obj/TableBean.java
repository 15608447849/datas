package abchina.preciousMetal.obj;

/**
 * Created by user on 2017/10/10.
 */
public class TableBean {
    /**
     * ProdCode : Ag(T+D)
     * CurrentPrice : 3860.00
     * UpLowDirection : 0
     * UpLowRate : 0.03%
     * SuccessAmount : 1511142
     * OpenPrice : 3860.00
     * YesterdayPrice : 3857.00
     * HighestPrice : 3866.00
     * LowestPrice : 3850.00
     * Kind : 001DZYH
     * UpdateTime : 2017-10-10 10:47:32
     * ShowOrder : 1
     * BuyUrl : http://ewealth.abchina.com/Gold/client/201501/t20150109_661266.htm
     */

    private String ProdCode;
    private String CurrentPrice;
    private String UpLowDirection;
    private String UpLowRate;
    private String SuccessAmount;
    private String OpenPrice;
    private String YesterdayPrice;
    private String HighestPrice;
    private String LowestPrice;
    private String Kind;
    private String UpdateTime;
    private String ShowOrder;
    private String BuyUrl;

    public String getProdCode() {
        return ProdCode;
    }

    public void setProdCode(String ProdCode) {
        this.ProdCode = ProdCode;
    }

    public String getCurrentPrice() {
        return CurrentPrice;
    }

    public void setCurrentPrice(String CurrentPrice) {
        this.CurrentPrice = CurrentPrice;
    }

    public String getUpLowDirection() {
        return UpLowDirection;
    }

    public void setUpLowDirection(String UpLowDirection) {
        this.UpLowDirection = UpLowDirection;
    }

    public String getUpLowRate() {
        return UpLowRate;
    }

    public void setUpLowRate(String UpLowRate) {
        this.UpLowRate = UpLowRate;
    }

    public String getSuccessAmount() {
        return SuccessAmount;
    }

    public void setSuccessAmount(String SuccessAmount) {
        this.SuccessAmount = SuccessAmount;
    }

    public String getOpenPrice() {
        return OpenPrice;
    }

    public void setOpenPrice(String OpenPrice) {
        this.OpenPrice = OpenPrice;
    }

    public String getYesterdayPrice() {
        return YesterdayPrice;
    }

    public void setYesterdayPrice(String YesterdayPrice) {
        this.YesterdayPrice = YesterdayPrice;
    }

    public String getHighestPrice() {
        return HighestPrice;
    }

    public void setHighestPrice(String HighestPrice) {
        this.HighestPrice = HighestPrice;
    }

    public String getLowestPrice() {
        return LowestPrice;
    }

    public void setLowestPrice(String LowestPrice) {
        this.LowestPrice = LowestPrice;
    }

    public String getKind() {
        return Kind;
    }

    public void setKind(String Kind) {
        this.Kind = Kind;
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

    public String getBuyUrl() {
        return BuyUrl;
    }

    public void setBuyUrl(String BuyUrl) {
        this.BuyUrl = BuyUrl;
    }

    @Override
    public String toString() {
        return "TableBean{" +
                "ProdCode='" + ProdCode + '\'' +
                ", CurrentPrice='" + CurrentPrice + '\'' +
                ", UpLowDirection='" + UpLowDirection + '\'' +
                ", UpLowRate='" + UpLowRate + '\'' +
                ", SuccessAmount='" + SuccessAmount + '\'' +
                ", OpenPrice='" + OpenPrice + '\'' +
                ", YesterdayPrice='" + YesterdayPrice + '\'' +
                ", HighestPrice='" + HighestPrice + '\'' +
                ", LowestPrice='" + LowestPrice + '\'' +
                ", Kind='" + Kind + '\'' +
                ", UpdateTime='" + UpdateTime + '\'' +
                ", ShowOrder='" + ShowOrder + '\'' +
                ", BuyUrl='" + BuyUrl + '\'' +
                '}';
    }
}
