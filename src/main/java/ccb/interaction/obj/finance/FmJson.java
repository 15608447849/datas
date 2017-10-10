package ccb.interaction.obj.finance;


import java.util.List;

/**
 * Created by user on 2017/9/18.
 */
public class FmJson{
    private int totalCount;
    private int pageNo;
    private int pageSize;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    private List<JentityFinanceBean> ProdList;
    public List<JentityFinanceBean> getProdList() {
        return ProdList;
    }
    public void setProdList(List<JentityFinanceBean> prodList) {
        ProdList = prodList;
    }
}
