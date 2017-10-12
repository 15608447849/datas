package abchina.all.interaction.loan.obj;


import com.google.gson.annotations.SerializedName;

public class LoanBean {
    private int id;
    private String type;

    @SerializedName("class")
    private int _class;

    private String name;
    private String desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public int get_class() {
        return _class;
    }

    public void set_class(int _class) {
        this._class = _class;
    }

    @Override
    public String toString() {
        return "LoanBean{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", _class=" + _class +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", qr_code='" + qr_code + '\'' +
                '}';
    }
}
