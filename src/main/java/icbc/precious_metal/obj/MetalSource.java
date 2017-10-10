package icbc.precious_metal.obj;
/**
 * 
 * @author cxg
 *黄金数据
 *所有字段名均与目标网站爬取的json中的键名完全相同
 */
public class MetalSource {
	/**品种*/
	private String metalname;
	/**单位*/
	private String unit;
	/**涨跌趋势    1.涨 2.跌*/
	private String upordown;
	/**银行买入价*/
	private String buyprice;
	/**银行卖出价*/
	private String sellprice;
	/**中间价*/
	private String middleprice;
	/**当日涨跌值*/
	private String openprice_dv;
	/**当日涨跌幅*/
	private String openprice_dr;
	/**当年涨跌幅*/
	private String openprice_yr;
	public String getMetalname() {
		return metalname;
	}
	public void setMetalname(String metalname) {
		this.metalname = metalname;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getUpordown() {
		return upordown;
	}
	public void setUpordown(String upordown) {
		this.upordown = upordown;
	}
	public String getBuyprice() {
		return buyprice;
	}
	public void setBuyprice(String buyprice) {
		this.buyprice = buyprice;
	}
	public String getSellprice() {
		return sellprice;
	}
	public void setSellprice(String sellprice) {
		this.sellprice = sellprice;
	}
	public String getMiddleprice() {
		return middleprice;
	}
	public void setMiddleprice(String middleprice) {
		this.middleprice = middleprice;
	}
	public String getOpenprice_dv() {
		return openprice_dv;
	}
	public void setOpenprice_dv(String openprice_dv) {
		this.openprice_dv = openprice_dv;
	}
	public String getOpenprice_dr() {
		return openprice_dr;
	}
	public void setOpenprice_dr(String openprice_dr) {
		this.openprice_dr = openprice_dr;
	}
	public String getOpenprice_yr() {
		return openprice_yr;
	}
	public void setOpenprice_yr(String openprice_yr) {
		this.openprice_yr = openprice_yr;
	}
	
}
