package ccb.interaction.obj.insurance;

/**
 * Created by user on 2017/9/15.
 */

public class InsuranceBean {
    /**
     * id : 1
     * name : 太平幸福一生终身年金保险（分红型）
     * apply : 出生28天~60周岁
     * company : 太平人寿
     * product_class : 年金险
     * attention_degree : 8
     * type : 期缴
     * describe : 即交即领返还快犹豫期后，即可领取10%年交保险费的特别保险金；返还快速、获享更多惊喜及收益。年年得利终身享年年领取基本保额和累积红利保额之和...
     * qr_code : http://insurance.ccb.com/cn/insurance/product/201512282036442553.html?Cvr_ID=3486&Ins_Co_ID=010033&Ins_Co_ShrtNm=太平人寿&Intfc_Tpl_TpCd=L1&IS_TB=1&Cvr_PD_Cgy_ID=06000000&Prmt_Minr_Rcgn_ind=&Intfc_Tpl_TpCd=L1&IS_YY=0
     */

    private int id; //自增ID
    private String name=""; //产品名称
    private String apply="";  //适用人群
    private String company=""; //隶属公司
    private String product_class=""; //产品类别
    private String attention_degree="";  //关注度
    private String type=""; //缴费方式
    private String describe="无"; //描述
    private String qr_code="";//二维码链接的取值需从名称链接的a标签中的onclick="seeDetail()中取值并带上前缀http://insurance.ccb.com

    public InsuranceBean(int id, String name, String qr_code, String apply, String company, String product_class, String type, String attention_degree,String describe) {
        this.id = id;
        this.name = name;
        this.apply = apply;
        this.company = company;
        this.product_class = product_class;
        this.type = type;
        this.describe = describe;
        this.attention_degree = attention_degree;
        this.qr_code = qr_code;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getApply() {
        return apply;
    }

    public String getCompany() {
        return company;
    }

    public String getProduct_class() {
        return product_class;
    }

    public String getAttention_degree() {
        return attention_degree;
    }

    public void setAttention_degree(String attention_degree) {
        this.attention_degree = attention_degree;
    }

    public String getType() {
        return type;
    }

    public String getDescribe() {
        return describe;
    }

    public String getQr_code() {
        return qr_code;
    }

}
