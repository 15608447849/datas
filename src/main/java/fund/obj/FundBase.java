package fund.obj;

public class FundBase {
	private String name;
	private String code;
	private String qrcode_char="";

	public FundBase() {
	}

	public FundBase(Object... objects) {
		if (objects.length < 4)
			return;
		name = (String) objects[1];
		code = (String) objects[2];
		qrcode_char = (String) objects[3];
	}


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

	public String getQrcode_char() {
		return qrcode_char;
	}

	public void setQrcode_char(String qrcode_char) {
		this.qrcode_char = qrcode_char;
	}

	
}
