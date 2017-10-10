package ccb.buyAllshop.obj;

import java.util.List;

/**
 * Created by user on 2017/9/12.
 */

public class SubClass {

    /**
     * id : 13 二级分类id 楼层数+序列号（程序生成） 所有二级分类需去掉第一个热点推荐
     * name : 影音摄像 二级分类名称
     * banner : []  //此字段数据为空
     */
    private String id;
    private String name;
    private List<?> banner;

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

    public List<?> getBanner() {
        return banner;
    }

    public void setBanner(List<?> banner) {
        this.banner = banner;
    }
}
