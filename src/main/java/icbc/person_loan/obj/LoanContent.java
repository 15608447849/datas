package icbc.person_loan.obj;

import java.util.ArrayList;

/**
 * Created by user on 2017/6/24.
 */
public class LoanContent {
    private String id;
    private String name;
    private BusinessSummary businessSummary;
    private ArrayList<String> requirement;
    private ArrayList<FAQBean> FAQ;
    private String qrcode_char = "";
    private ArrayList<String> material_requested;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BusinessSummary getBusinessSummary() {
        return businessSummary;
    }

    public void setBusinessSummary(BusinessSummary businessSummary) {
        this.businessSummary = businessSummary;
    }

    public ArrayList<String> getRequirement() {
        return requirement;
    }

    public void setRequirement(ArrayList<String> requirement) {
        this.requirement = requirement;
    }

    public ArrayList<FAQBean> getFAQ() {
        return FAQ;
    }

    public void setFAQ(ArrayList<FAQBean> FAQ) {
        this.FAQ = FAQ;
    }

    public String getQrcode_char() {
        return qrcode_char;
    }

    public void setQrcode_char(String qrcode_char) {
        this.qrcode_char = qrcode_char;
    }

    public ArrayList<String> getMaterial_requested() {
        return material_requested;
    }

    public void setMaterial_requested(ArrayList<String> material_requested) {
        this.material_requested = material_requested;
    }


}
