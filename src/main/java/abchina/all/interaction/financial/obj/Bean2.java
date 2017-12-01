package abchina.all.interaction.financial.obj;

/**
 * Created by user on 2017/10/12.
 */
public class Bean2 {

    /**
     * id : 1001
     * ranking : 1
     * time : 2017.8.24-2017.8.31
     * investment_days : 251天
     * name : 龙行无忧理财至尊版
     * profit : 3.8%
     * area : 全国
     * qr_code : http://www.jd.com
     */

    private int id;
    private String ranking;
    private String time;
    private String investment_days;
    private String name;
    private String profit;
    private String area;
    private String qr_code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInvestment_days() {
        return investment_days;
    }

    public void setInvestment_days(String investment_days) {
        this.investment_days = investment_days;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    @Override
    public String toString() {
        return "Bean2{" +
                "id=" + id +
                ", ranking='" + ranking + '\'' +
                ", time='" + time + '\'' +
                ", investment_days='" + investment_days + '\'' +
                ", name='" + name + '\'' +
                ", profit='" + profit + '\'' +
                ", area='" + area + '\'' +
                ", qr_code='" + qr_code + '\'' +
                '}';
    }
}
