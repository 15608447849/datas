package insurance.obj;

/**
 * Created by user on 2017/6/27.
 */
public class InsuranceBean {
    private String name;
    private String price;
    private String show_img;
    private String qrcode_char="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShow_img() {
        return show_img;
    }

    public void setShow_img(String show_img) {
        this.show_img = show_img;
    }

    public String getQrcode_char() {
        return qrcode_char;
    }

    public void setQrcode_char(String qrcode_char) {
        this.qrcode_char = qrcode_char;
    }
}
