package icbc.offline_xperience_achine.obj.menus;

import java.util.List;

/**
 * Created by user on 2017/7/5.
 */
public class Menu {
    private String id;
    private String name;
    private List<Seclass> seclass;

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

    public List<Seclass> getSeclass() {
        return seclass;
    }

    public void setSeclass(List<Seclass> seclass) {
        this.seclass = seclass;
    }
}
