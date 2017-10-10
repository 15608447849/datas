package icbc.precious_metal.obj;

public class Metal {
	private String money;//币种
	private long is_rise;//涨跌趋势    1.涨 2.跌
	private String kind;//gold(黄金) porpezite(钯金) platinum(铂) silver(银)
	private String buy_price;//买入价
	private String sale_price;//卖出价
	private String mid_price;//中间价
	private String day_rise;//当日增长值
	private String day_rise_rate;//当日增长幅度
	private String year_rise_rate;//当年涨跌幅
	
	
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public long getIs_rise() {
		return is_rise;
	}
	public void setIs_rise(long is_rise) {
		this.is_rise = is_rise;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getBuy_price() {
		return buy_price;
	}
	public void setBuy_price(String buy_price) {
		this.buy_price = buy_price;
	}
	public String getSale_price() {
		return sale_price;
	}
	public void setSale_price(String sale_price) {
		this.sale_price = sale_price;
	}
	public String getMid_price() {
		return mid_price;
	}
	public void setMid_price(String mid_price) {
		this.mid_price = mid_price;
	}
	public String getDay_rise() {
		return day_rise;
	}
	public void setDay_rise(String day_rise) {
		this.day_rise = day_rise;
	}
	public String getDay_rise_rate() {
		return day_rise_rate;
	}
	public void setDay_rise_rate(String day_rise_rate) {
		this.day_rise_rate = day_rise_rate;
	}
	public String getYear_rise_rate() {
		return year_rise_rate;
	}
	public void setYear_rise_rate(String year_rise_rate) {
		this.year_rise_rate = year_rise_rate;
	}

}
