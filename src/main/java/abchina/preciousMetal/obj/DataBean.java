package abchina.preciousMetal.obj;

import java.util.List;

/**
 * Created by user on 2017/10/10.
 */
public class DataBean {
    private List<TableBean> Table;
    private List<Table1Bean> Table1;
    public List<TableBean> getTable() {
        return Table;
    }
    public void setTable(List<TableBean> Table) {
        this.Table = Table;
    }
    public List<Table1Bean> getTable1() {
        return Table1;
    }
    public void setTable1(List<Table1Bean> Table1) {
        this.Table1 = Table1;
    }
    @Override
    public String toString() {
        return "DataBean{" +
                "Table=" + Table +
                ", Table1=" + Table1 +
                '}';
    }
}
