package icbc.fund.obj;

public class HotFund extends FundBase {
	private String day_income_rate;
	private String week_income_rate;
	private String day_rise_rate;
	private String month_rise_rate;
	private int star_grade;
	private String kind;
	private String type;
	private String status;
	public HotFund() {

	}

	public HotFund(Object... objects) {
		if (objects.length < 12)
			return;
		setName((String) objects[1]);
		setCode((String) objects[2]);
		setQrcode_char((String) objects[3]);
		day_income_rate = (String) objects[4];
		week_income_rate = (String) objects[5];
		day_rise_rate = (String) objects[6];
		month_rise_rate = (String) objects[7];
		star_grade = (int) objects[8];
		kind=(String) objects[9];
		type=(String) objects[10];
		status=(String) objects[11];
	}

	public String getDay_income_rate() {
		return day_income_rate;
	}

	public void setDay_income_rate(String day_income_rate) {
		this.day_income_rate = day_income_rate;
	}

	public String getWeek_income_rate() {
		return week_income_rate;
	}

	public void setWeek_income_rate(String week_income_rate) {
		this.week_income_rate = week_income_rate;
	}

	public String getDay_rise_rate() {
		return day_rise_rate;
	}

	public void setDay_rise_rate(String day_rise_rate) {
		this.day_rise_rate = day_rise_rate;
	}

	public String getMonth_rise_rate() {
		return month_rise_rate;
	}

	public void setMonth_rise_rate(String month_rise_rate) {
		this.month_rise_rate = month_rise_rate;
	}

	public int getStar_grade() {
		return star_grade;
	}

	public void setStar_grade(int star_grade) {
		this.star_grade = star_grade;
	}
	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



}
