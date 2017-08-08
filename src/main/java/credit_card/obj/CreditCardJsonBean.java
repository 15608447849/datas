package credit_card.obj;

/**
 * Created by lzp on 2017/6/23.
 *
 {"id":1,
 "card_name":"香格里拉白金卡(VISA+银联)",
 //卡面版本 0表示横版  1表示竖版
 "card_style":0,
 //品牌 多个品牌的用 , 隔开
 "brand":"visa,union_pay",
 //卡面 可能包含多个卡面
 "card_cover":["D:/card/visaxgll1.jpg"],
 //币种
 "currency":"多币种",
 //卡片简述
 "card_desc":"1.120消费积分自动兑换1金环会积分；2.香格里拉旗下酒店享最优房价9折，用餐享85折，指定酒店健身中心年卡享最高8折",
 "qrcode_char":"xxxxxxxxxurl"

 }
 */
public class CreditCardJsonBean {
    private String card_name;
    //卡面版本 0表示横版  1表示竖版
    private String card_style = "0";
    //品牌 多个品牌的用 , 隔开
    private String brand;
    //品牌源
    private String brand_st;
    //卡面 可能包含多个卡面
    private String card_cover;
    //币种
    private String currency;
    //卡片简述
    private String card_desc;
    private String qrcode_char = "https://mybank.icbc.com.cn/servlet/ICBCPERBANKLocationServiceServlet?serviceId=PBL200722";
    //发行地区
    private String card_area;


    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    public String getCard_style() {
        return card_style;
    }

    public void setCard_style(String card_style) {
        this.card_style = card_style;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCard_cover() {
        return card_cover;
    }

    public void setCard_cover(String card_cover) {
        this.card_cover = card_cover;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCard_desc() {
        return card_desc;
    }

    public void setCard_desc(String card_desc) {
        this.card_desc = card_desc;
    }

    public String getQrcode_char() {
        return qrcode_char;
    }

    public void setQrcode_char(String qrcode_char) {
        this.qrcode_char = qrcode_char;
    }

    public String getCard_area() {
        return card_area;
    }

    public void setCard_area(String card_area) {
        this.card_area = card_area;
    }

    public String getBrand_st() {
        return brand_st;
    }

    public void setBrand_st(String brand_st) {
        this.brand_st = brand_st;
    }
}
