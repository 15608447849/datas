package ccb.interaction.obj.fundX;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2017/9/19.
 */
public class FundXBean {
    private int id;
    @SerializedName("class")
    private String classX;
    private String company;
    private String start_day;
    private String end_day;
    private String high_rate;
    private String code;
    private String type;
    private String name;
    private String net_worth;
    private String Accumulated_net;
    private String one_week;
    private String one_month;
    private String three_month;
    private String six_month;
    private String qr_code;


    public String getSix_month() {
        return six_month;
    }

    public void setSix_month(String six_month) {
        this.six_month = six_month;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStart_day() {
        return start_day;
    }

    public void setStart_day(String start_day) {
        this.start_day = start_day;
    }

    public String getEnd_day() {
        return end_day;
    }

    public void setEnd_day(String end_day) {
        this.end_day = end_day;
    }

    public String getHigh_rate() {
        return high_rate;
    }

    public void setHigh_rate(String high_rate) {
        this.high_rate = high_rate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public void setAccumulated_net(String accumulated_net) {
        Accumulated_net = accumulated_net;
    }

    public String getOne_week() {
        return one_week;
    }

    public void setOne_week(String one_week) {
        this.one_week = one_week;
    }

    public String getOne_month() {
        return one_month;
    }

    public void setOne_month(String one_month) {
        this.one_month = one_month;
    }

    public String getThree_month() {
        return three_month;
    }

    public void setThree_month(String three_month) {
        this.three_month = three_month;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }
}
