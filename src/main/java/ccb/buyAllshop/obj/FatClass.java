package ccb.buyAllshop.obj;

import java.util.List;

/**
 * Created by user on 2017/9/12.
 */

public class FatClass {
    /**
     * id : 1
     * name : 手机/数码
     * seclass : []
     */

    private String id;
    private String name;
    private List<SubClass> seclass;

    public FatClass() {
    }

    public FatClass(String id, String name) {
        this.id = id;
        this.name = name;
    }

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

    public List<?> getSeclass() {
        return seclass;
    }

    public void setSeclass(List<SubClass> seclass) {
        this.seclass = seclass;
    }
}
