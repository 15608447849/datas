package ccb.interaction.obj.precious;

/**
 * Created by user on 2017/9/18.
 */

public class PreciousMetalBean {
    /**
     * id : 1
     * type : 建行金 投资
     * type_img : img/12.jpg
     * name : 投资金条（梯形金1）
     * product_img : img/12.jpg
     * qr_code : http://gold.ccb.com/cn/gold/20130821_1377074520.html
     */

    private int id;
    private String type;
    private String type_img;
    private String name;
    private String product_img;
    private String qr_code;

    public PreciousMetalBean(int id, String type, String type_img, String name, String product_img, String qr_code) {
        this.id = id;
        this.type = type;
        this.type_img = type_img;
        this.name = name;
        this.product_img = product_img;
        this.qr_code = qr_code;
    }

    public int getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public String getType_img() {
        return type_img;
    }
    public String getName() {
        return name;
    }
    public String getProduct_img() {
        return product_img;
    }
    public String getQr_code() {
        return qr_code;
    }


}
