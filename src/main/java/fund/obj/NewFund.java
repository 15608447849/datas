package fund.obj;

public class NewFund extends FundBase {
	private String currency;
	private String issue_date;
	private String issue_price;
	private String kind;
	private String type;
	public NewFund() {
	}

	public NewFund(Object... objects) {
		if (objects.length < 9)
			return;
		setName((String) objects[1]);
		setCode((String) objects[2]);
		setQrcode_char((String) objects[3]);
		currency = (String) objects[4];
		issue_date = (String) objects[5];
		issue_price = (String) objects[6];
		kind=(String)objects[7];
		type=(String)objects[8];
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getIssue_date() {
		return issue_date;
	}

	public void setIssue_date(String issue_date) {
		this.issue_date = issue_date;
	}

	public String getIssue_price() {
		return issue_price;
	}

	public void setIssue_price(String issue_price) {
		this.issue_price = issue_price;
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


}
