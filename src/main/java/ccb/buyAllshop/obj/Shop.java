package ccb.buyAllshop.obj;

import java.util.List;

/**
 * Created by user on 2017/9/12.
 */

public class Shop {
    /**
     * goods_id : 0001974081     //商品id  取链接的中间部分/products/pd_0001974081.jhtml
     * class_id : 11      //对应的二级分类id
     * title : 三星Galaxy S8 G9500 全网通手机 送赠三星折叠式充电板+自拍杆   //商品名称
     * desc : 24期免息！  //商品名称下的商品描述信息
     * price : 5688.00   //商品商城价格 注意不要带￥符号
     * image_cover :   //商品的封面图片，对应详情的第一张图片
     * image_list : [""]    //商品详情页的图片列表  注意取到后缀为_4的图片 清晰度最高
     * buy_code :  //为空
     * buy_link : http://buy.ccb.com/products/m_0001974081.jhtml //需要展示手机商城的网址，规则为 http://buy.ccb.com/products/pd_0001974081.jhtml 中的pd 改为m
     * content :                          //为商品详情的富文本内容 且需分析文本中的图片并进行抓取，抓取后需替换文本中的图片路径为本地存储路径
     */

    private String goods_id;
    private String class_id;
    private String title;
    private String desc;
    private String price;
    private String image_cover;
    private String buy_code;
    private String buy_link;
    private String content="";
    private List<String> image_list;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage_cover() {
        return image_cover;
    }

    public void setImage_cover(String image_cover) {
        this.image_cover = image_cover;
    }

    public String getBuy_code() {
        return buy_code;
    }

    public void setBuy_code(String buy_code) {
        this.buy_code = buy_code;
    }

    public String getBuy_link() {
        return buy_link;
    }

    public void setBuy_link(String buy_link) {
        this.buy_link = buy_link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImage_list() {
        return image_list;
    }

    public void setImage_list(List<String> image_list) {
        this.image_list = image_list;
    }
}
