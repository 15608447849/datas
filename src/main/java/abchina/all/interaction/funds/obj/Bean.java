package abchina.all.interaction.funds.obj;

/**
 * Created by user on 2017/10/12.
 */
public class Bean {

    /**
     * id : 1005
     * code : 001744
     * name : 广大中国制造5
     * net_worth : 1.3690
     * Accumulated_net : 1.3690
     * one_day : -0.80%
     * one_month : -0.80%
     * season_month : 30.55%
     * qr_code : www.qq.com
     */

    private int id;
    private String code;
    private String ranking;
    private String type;
    private String name;
    private String net_worth;
    private String Accumulated_net;
    private String one_day;
    private String one_month;
    private String season_month;
    private String one_week;
    private String company;
    private String manager;
    private String qr_code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNet_worth() {
        return net_worth;
    }

    public void setNet_worth(String net_worth) {
        this.net_worth = net_worth;
    }

    public String getAccumulated_net() {
        return Accumulated_net;
    }

    public void setAccumulated_net(String Accumulated_net) {
        this.Accumulated_net = Accumulated_net;
    }

    public String getOne_day() {
        return one_day;
    }

    public void setOne_day(String one_day) {
        this.one_day = one_day;
    }

    public String getOne_month() {
        return one_month;
    }

    public void setOne_month(String one_month) {
        this.one_month = one_month;
    }

    public String getSeason_month() {
        return season_month;
    }

    public void setSeason_month(String season_month) {
        this.season_month = season_month;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOne_week() {
        return one_week;
    }

    public void setOne_week(String one_week) {
        this.one_week = one_week;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", ranking='" + ranking + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", net_worth='" + net_worth + '\'' +
                ", Accumulated_net='" + Accumulated_net + '\'' +
                ", one_day='" + one_day + '\'' +
                ", one_month='" + one_month + '\'' +
                ", season_month='" + season_month + '\'' +
                ", one_week='" + one_week + '\'' +
                ", company='" + company + '\'' +
                ", manager='" + manager + '\'' +
                ", qr_code='" + qr_code + '\'' +
                '}';
    }
}
