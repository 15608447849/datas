package ccb.seckillsOrGroups.obj;

/**
 * Created by user on 2017/9/12.
 *
 "goods_id":"0001876443", //取链接中间部分/products/fs_0001876443_201709054224.jhtml
 "title":"华为 Mate 9 4GB+64GB版 移动联通电信4G手机 双卡双待", //商品名称
 "price_old":"30.00", //商城价  注意不要带￥符号
 "price":"15.00",//抢购价
 "image_cover":"o2o/GroupBuy/res/0000009061/1499236053532_4.jpg",  //商品的第一张图片 需取图片xxx_4.XXX 路径为存储的相对路径
 "discount":"", //没有 不抓 空白
 "amount":"100", //剩余数量
 "time_start":"1500775200000", //页面中只能去到时间格式 2017-09-11 10:00:00 需转换为时间戳
 "time_end":"1500796800000", ////页面中只能去到时间格式 2017-09-11 10:00:00 需转换为时间戳
 "buy_link":"http://buy.ccb.com/products/fs_0001876443_201709054224.jhtml" //商品抢购链接
 *
 */


public class Bean {
    private String goods_id;
    private String title;
    private String price_old;
    private String price;
    private String image_cover;
    private String discount;
    private String amount;
    private String time_start;
    private String time_end;
    private String buy_link;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice_old() {
        return price_old;
    }

    public void setPrice_old(String price_old) {
        this.price_old = price_old;
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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getBuy_link() {
        return buy_link;
    }

    public void setBuy_link(String buy_link) {
        this.buy_link = buy_link;
    }










}
