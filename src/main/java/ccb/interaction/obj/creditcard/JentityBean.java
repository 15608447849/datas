package ccb.interaction.obj.creditcard;

/**
 * Created by user on 2017/9/19.
 */
public class JentityBean {

    /**
     * dcrindex : 20100523_1274580436
     * title : 龙卡变形金刚主题信用卡
     * subtitle : 特色功能|@|增值服务|@|营销活动设计|@|年费标准和优惠政策
     * cardimg : /cn/creditcard/cards/upload//20170616125635280753.jpg
     * category : 激情时尚
     * ishot : 1
     * isnew : 1
     * formid : standard_form
     * isapply : 1
     */

    private String dcrindex;
    private String title;
    private String subtitle;
    private String cardimg;
    private String category;
    private String ishot;
    private String isnew;
    private String formid;
    private String isapply;

    public String getDcrindex() {
        return dcrindex;
    }

    public void setDcrindex(String dcrindex) {
        this.dcrindex = dcrindex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getCardimg() {
        return cardimg;
    }

    public void setCardimg(String cardimg) {
        this.cardimg = cardimg;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIshot() {
        return ishot;
    }

    public void setIshot(String ishot) {
        this.ishot = ishot;
    }

    public String getIsnew() {
        return isnew;
    }

    public void setIsnew(String isnew) {
        this.isnew = isnew;
    }

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid;
    }

    public String getIsapply() {
        return isapply;
    }

    public void setIsapply(String isapply) {
        this.isapply = isapply;
    }

    @Override
    public String toString() {
        return "JentityBean{" +
                "dcrindex='" + dcrindex + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", cardimg='" + cardimg + '\'' +
                ", category='" + category + '\'' +
                ", ishot='" + ishot + '\'' +
                ", isnew='" + isnew + '\'' +
                ", formid='" + formid + '\'' +
                ", isapply='" + isapply + '\'' +
                '}';
    }
}
