package ccb.accountGold.obj;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * @XmlAccessorType(XmlAccessType.FIELD)指定映射本类的所有字段
 * @XmlRootElement 用在class类的注解，常与@XmlRootElement，@XmlAccessorType一起使用.也可以单独使用,
 * 如果单独使用,需要在get方法上加@XmlElement等注解.
 * @XmlType,在使用@XmlType的propOrder 属性时，必须列出JavaBean对象中的所有XmlElement，否则会报错。
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "referencepricesettlement")
public class ReferencePriceSettlement{
    // 其实@XmlType已经默认会读取下面的name和age.@XmlElement在@XmlType存在的情况下,只会起到一个标识作用.
    @XmlAttribute(name = "name")
    String name;
    @XmlElement(name = "PM_Txn_Vrty_Cd")
    String pm_txn_vrty_cd;
    @XmlElement(name = "CcyCd")
    String ccycd;
    @XmlElement(name = "Cst_Buy_Prc")
    String cst_buy_prc;
    @XmlElement(name = "MdlRate")
    String mdlrate;
    @XmlElement(name = "Cst_Sell_Prc")
    String cst_sell_prc;
    @XmlElement(name = "Tms")
    String tms;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPm_txn_vrty_cd() {
        return pm_txn_vrty_cd;
    }

    public void setPm_txn_vrty_cd(String pm_txn_vrty_cd) {
        this.pm_txn_vrty_cd = pm_txn_vrty_cd;
    }

    public String getCcycd() {
        return ccycd;
    }

    public void setCcycd(String ccycd) {
        this.ccycd = ccycd;
    }

    public String getCst_buy_prc() {
        return cst_buy_prc;
    }

    public void setCst_buy_prc(String cst_buy_prc) {
        this.cst_buy_prc = cst_buy_prc;
    }

    public String getMdlrate() {
        return mdlrate;
    }

    public void setMdlrate(String mdlrate) {
        this.mdlrate = mdlrate;
    }

    public String getCst_sell_prc() {
        return cst_sell_prc;
    }

    public void setCst_sell_prc(String cst_sell_prc) {
        this.cst_sell_prc = cst_sell_prc;
    }

    public String getTms() {
        return tms;
    }

    public void setTms(String tms) {
        this.tms = tms;
    }
}
