package icbc.person_loan.obj;

/**
 * Created by user on 2017/6/24.
 */
public class LoanRate {
    private String six_month_rate;
    private String year_rate;
    private String three_year_rate;
    private String five_year_rate;
    private String over_five_year_rate;

    public String getSix_month_rate() {
        return six_month_rate;
    }

    public void setSix_month_rate(String six_month_rate) {
        this.six_month_rate = six_month_rate;
    }

    public String getYear_rate() {
        return year_rate;
    }

    public void setYear_rate(String year_rate) {
        this.year_rate = year_rate;
    }

    public String getThree_year_rate() {
        return three_year_rate;
    }

    public void setThree_year_rate(String three_year_rate) {
        this.three_year_rate = three_year_rate;
    }

    public String getFive_year_rate() {
        return five_year_rate;
    }

    public void setFive_year_rate(String five_year_rate) {
        this.five_year_rate = five_year_rate;
    }

    public String getOver_five_year_rate() {
        return over_five_year_rate;
    }

    public void setOver_five_year_rate(String over_five_year_rate) {
        this.over_five_year_rate = over_five_year_rate;
    }
}
