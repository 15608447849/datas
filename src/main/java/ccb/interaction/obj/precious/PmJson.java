package ccb.interaction.obj.precious;


import java.util.List;

/**
 * Created by user on 2017/9/18.
 */
public class PmJson {
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
    private List<JentityResultBean> resultList;
    public List<JentityResultBean> getResultList() {
        return resultList;
    }
    public void setResultList(List<JentityResultBean> resultList) {
        this.resultList = resultList;
    }
}
