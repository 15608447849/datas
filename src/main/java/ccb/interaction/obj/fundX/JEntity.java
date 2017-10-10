package ccb.interaction.obj.fundX;




import java.util.List;

/**
 * Created by user on 2017/9/19.
 */
public class JEntity {
    private int PerPage;
    private int TotalPage;
    private String SUCCESS;
    private String FIRSTTAG;
    private String SECONDTAG;
    private int CurPage;
    private int TotalRec;
    private String ORDERTYPE;
    private String ORDERCLOUMN;
    private List<JEntityBean> INFO;

    public int getPerPage() {
        return PerPage;
    }

    public void setPerPage(int perPage) {
        PerPage = perPage;
    }

    public int getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(int totalPage) {
        TotalPage = totalPage;
    }

    public String getSUCCESS() {
        return SUCCESS;
    }

    public void setSUCCESS(String SUCCESS) {
        this.SUCCESS = SUCCESS;
    }

    public String getFIRSTTAG() {
        return FIRSTTAG;
    }

    public void setFIRSTTAG(String FIRSTTAG) {
        this.FIRSTTAG = FIRSTTAG;
    }

    public String getSECONDTAG() {
        return SECONDTAG;
    }

    public void setSECONDTAG(String SECONDTAG) {
        this.SECONDTAG = SECONDTAG;
    }

    public int getCurPage() {
        return CurPage;
    }

    public void setCurPage(int curPage) {
        CurPage = curPage;
    }

    public int getTotalRec() {
        return TotalRec;
    }

    public void setTotalRec(int totalRec) {
        TotalRec = totalRec;
    }

    public String getORDERTYPE() {
        return ORDERTYPE;
    }

    public void setORDERTYPE(String ORDERTYPE) {
        this.ORDERTYPE = ORDERTYPE;
    }

    public String getORDERCLOUMN() {
        return ORDERCLOUMN;
    }

    public void setORDERCLOUMN(String ORDERCLOUMN) {
        this.ORDERCLOUMN = ORDERCLOUMN;
    }

    public List<JEntityBean> getINFO() {
        return INFO;
    }

    public void setINFO(List<JEntityBean> INFO) {
        this.INFO = INFO;
    }
}
