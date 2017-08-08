package offline_xperience_achine.obj.menus;

import java.util.List;

/**
 * Created by user on 2017/7/5.
 */
public class Seclass {
    private String id;
    private String name;
    private List<Banner> banner;

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

    public List<Banner> getBanner() {
        return banner;
    }

    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }
}
