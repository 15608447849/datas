package icbc.fund.obj;

public class HighProfit extends FundBase {
	private String unit_income_rate;
	private String unit_income_date;
	private String all_income_rate;
	private String day_rise_rate;
	private String rise_this_year;
	private String rise_month;
	private String rise_three_month;
	private String rise_half_year;
	private String rise_year;
	private int star_grade;

	public HighProfit() {
	}

	public HighProfit(Object... objects) {
		if (objects.length < 14)
			return;
		setName((String) objects[1]);
		setCode((String) objects[2]);
		setQrcode_char((String) objects[3]);
		unit_income_rate = (String) objects[4];
		unit_income_date = (String) objects[5];
		all_income_rate = (String) objects[6];
		day_rise_rate = (String) objects[7];
		rise_this_year=(String) objects[8];
		rise_month=(String) objects[9];
		rise_three_month=(String) objects[10];
		rise_half_year=(String) objects[11];
		rise_year=(String) objects[12];
		star_grade=(int) objects[13];
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

	public String getAll_income_rate() {
		return all_income_rate;
	}

	public void setAll_income_rate(String all_income_rate) {
		this.all_income_rate = all_income_rate;
	}

	public String getDay_rise_rate() {
		return day_rise_rate;
	}

	public void setDay_rise_rate(String day_rise_rate) {
		this.day_rise_rate = day_rise_rate;
	}

	public String getRise_this_year() {
		return rise_this_year;
	}

	public void setRise_this_year(String rise_this_year) {
		this.rise_this_year = rise_this_year;
	}

	public String getRise_month() {
		return rise_month;
	}

	public void setRise_month(String rise_month) {
		this.rise_month = rise_month;
	}

	public String getRise_three_month() {
		return rise_three_month;
	}

	public void setRise_three_month(String rise_three_month) {
		this.rise_three_month = rise_three_month;
	}

	public String getRise_half_year() {
		return rise_half_year;
	}

	public void setRise_half_year(String rise_half_year) {
		this.rise_half_year = rise_half_year;
	}

	public String getRise_year() {
		return rise_year;
	}

	public void setRise_year(String rise_year) {
		this.rise_year = rise_year;
	}

	public int getStar_grade() {
		return star_grade;
	}

	public void setStar_grade(int star_grade) {
		this.star_grade = star_grade;
	}

	@Override
	public String toString() {
		super.toString();
		return "HighProfit [unit_income_rate=" + unit_income_rate
				+ ", unit_income_date=" + unit_income_date
				+ ", all_income_rate=" + all_income_rate + ", day_rise_rate="
				+ day_rise_rate + "]";
	}

}
