package abchina.all.interaction.card.obj;

public class CardBean {
    private int id; //自增ID
    private String type;//信用卡分类
    private String name;//名称
    private String card_img; //信用卡图片
    private String desc1;//描述
    private String desc2;//详细介绍
    private String qr_code; //二维码

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

    @Override
    public String toString() {
        return "CardBean{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", card_img='" + card_img + '\'' +
                ", desc1='" + desc1 + '\'' +
                ", desc2='" + desc2 + '\'' +
                ", qr_code='" + qr_code + '\'' +
                '}';
    }
}
