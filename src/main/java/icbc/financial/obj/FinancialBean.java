package icbc.financial.obj;

/**
 * Created by user on 2017/6/26.
 */
public class FinancialBean {
    private String name ;
    private String code ;
    private String is_fixed_limit="0" ;
    private String limit_price ;
    private String date_limit="0" ;
    private String year_income_rate ;
    private String grade ;
    private String qrcode_char="https://mybank.icbc.com.cn/servlet/ICBCPERBANKLocationServiceServlet?serviceId=PBL20111002" ;
    private String push_date ;
    private String  desc_file_url ;
    private String unit_income_rate;
    private String unit_income_date;
    private String title;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIs_fixed_limit() {
        return is_fixed_limit;
    }

    public void setIs_fixed_limit(String is_fixed_limit) {
        this.is_fixed_limit = is_fixed_limit;
    }

    public String getLimit_price() {
        return limit_price;
    }

    public void setLimit_price(String limit_price) {
        this.limit_price = limit_price;
    }

    public String getDate_limit() {
        return date_limit;
    }

    public void setDate_limit(String date_limit) {
        this.date_limit = date_limit;
    }

    public String getYear_income_rate() {
        return year_income_rate;
    }

    public void setYear_income_rate(String year_income_rate) {
        this.year_income_rate = year_income_rate;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getQrcode_char() {
        return qrcode_char;
    }

    public void setQrcode_char(String qrcode_char) {
        this.qrcode_char = qrcode_char;
    }

    public String getPush_date() {
        return push_date;
    }

    public void setPush_date(String push_date) {
        this.push_date = push_date;
    }

    public String getDesc_file_url() {
        return desc_file_url;
    }

    public void setDesc_file_url(String desc_file_url) {
        this.desc_file_url = desc_file_url;
    }

    public String getUnit_income_rate() {
        return unit_income_rate;
    }

    public void setUnit_income_rate(String unit_income_rate) {
        this.unit_income_rate = unit_income_rate;
    }

    public String getUnit_income_date() {
        return unit_income_date;
    }

    public void setUnit_income_date(String unit_income_date) {
        this.unit_income_date = unit_income_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
