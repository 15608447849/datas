package ccb.interaction.obj.creditcard;

/**
 * Created by user on 2017/9/19.
 */
public class CreditCardBean {
    /**
     * id : 1
     * type : 热点产品
     * name : 龙卡 JOY 信用卡1
     * card_img : img/14.jpg
     * desc1 : 特色功能与增值服务、年费标准及优惠政策、超值活动
     * desc2 : 龙卡JOY信用卡（以下简称“龙卡JOY卡”）是中国建设银行面向35岁以下年轻客户发行的专属信用卡产品，可满足年轻客户多样化的用卡需求。
     * qr_code : http://creditcard.ccb.com/cn/creditcard/cards/20170526_1495776259.html
     */

    private int id;
    private String type;
    private String name;
    private String card_img;
    private String desc1;
    private String desc2;
    private String qr_code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCard_img() {
        return card_img;
    }

    public void setCard_img(String card_img) {
        this.card_img = card_img;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }
}
