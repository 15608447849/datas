package ccb.interaction.obj.loan;

/**
 * Created by user on 2017/9/18.
 */

public class LoanBean {
    /**
     * id : 1
     * type : 个人住房贷款
     * desc : 是指建设银行或建设银行接受委托向在中国大陆境内城镇购买、建造、大修各类型房屋的自然人发放的贷款。
     * qr_code : http://ehome.ccb.com/Channel/34202182?funcCode=LOAN_APPLY_HOUSE
     */

    private int id;
    private String type;
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
}
