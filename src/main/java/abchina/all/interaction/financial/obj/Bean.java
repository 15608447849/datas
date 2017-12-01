package abchina.all.interaction.financial.obj;

/**
 * Created by user on 2017/10/11.
 */

public class Bean {
    /**
     * id : 1001
     * name : 龙行无忧理财产品1
     * profit : 3.8%
     * need_money : 5万
     * investment_days : 251天
     * area : 全国
     * time : 2017.8.24-2017.8.31
     * type : 当前在售
     * qr_code : http://www.jd.com
     */

    private int id;
    private String name;
    private String profit;
    private String need_money;
    private String investment_days;
    private String area;
    private String time;
    private String type;
    private String qr_code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getNeed_money() {
        return need_money;
    }

    public void setNeed_money(String need_money) {
        this.need_money = need_money;
    }

    public String getInvestment_days() {
        return investment_days;
    }

    public void setInvestment_days(String investment_days) {
        this.investment_days = investment_days;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", profit='" + profit + '\'' +
                ", need_money='" + need_money + '\'' +
                ", investment_days='" + investment_days + '\'' +
                ", area='" + area + '\'' +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", qr_code='" + qr_code + '\'' +
                '}';
    }
}
