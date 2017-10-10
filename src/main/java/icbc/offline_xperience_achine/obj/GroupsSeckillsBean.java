package icbc.offline_xperience_achine.obj;

import icbc.offline_xperience_achine.obj.groups.Groups;

import java.util.List;

/**
 * Created by user on 2017/7/5.
 */
public class GroupsSeckillsBean {
    private List<Groups> seckills;
    private List<Groups> groups;

    public List<Groups> getSeckills() {
        return seckills;
    }

    public void setSeckills(List<Groups> seckills) {
        this.seckills = seckills;
    }

    public List<Groups> getGroups() {
        return groups;
    }

    public void setGroups(List<Groups> groups) {
        this.groups = groups;
    }
}
