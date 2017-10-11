package abchina.all.interaction.insur.obj;

import java.util.List;

/**
 * Created by user on 2017/10/11.
 */

public class SourceData {
    /**
     * Data : {"Table":[{"RowNumber":1,"InsuranceName":"农银随e行交通意外保障计划(网银)","InsuranceType":"20","id":13,"ProdCode":"1118021801","InsuranceDate":"1年","IsAgent":"0","AgentUrl":"optype=1&opcode=102&param1=1118&param2=021801","CompanyName":"农银人寿保险股份有限公司","ITypeName":"保额定保费","CompanyCode":"C101118","CompanyShortName":"农银人寿","IsRecommended":0,"WebSite":"","IsNew":1}],"Table1":[{"RowsCount":"283"}]}
     * ErrorCode : 0
     * ErrorMsg :
     * Url : null
     */

    private DataBean Data;
    private String ErrorCode;
    private String ErrorMsg;
    private Object Url;

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(String ErrorMsg) {
        this.ErrorMsg = ErrorMsg;
    }

    public Object getUrl() {
        return Url;
    }

    public void setUrl(Object Url) {
        this.Url = Url;
    }

    public static class DataBean {
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

        public static class TableBean {
            /**
             * RowNumber : 1
             * InsuranceName : 农银随e行交通意外保障计划(网银)
             * InsuranceType : 20
             * id : 13
             * ProdCode : 1118021801
             * InsuranceDate : 1年
             * IsAgent : 0
             * AgentUrl : optype=1&opcode=102&param1=1118&param2=021801
             * CompanyName : 农银人寿保险股份有限公司
             * ITypeName : 保额定保费
             * CompanyCode : C101118
             * CompanyShortName : 农银人寿
             * IsRecommended : 0
             * WebSite :
             * IsNew : 1
             */

            private int RowNumber;
            private String InsuranceName;
            private String InsuranceType;
            private int id;
            private String ProdCode;
            private String InsuranceDate;
            private String IsAgent;
            private String AgentUrl;
            private String CompanyName;
            private String ITypeName;
            private String CompanyCode;
            private String CompanyShortName;
            private int IsRecommended;
            private String WebSite;
            private int IsNew;

            public int getRowNumber() {
                return RowNumber;
            }

            public void setRowNumber(int RowNumber) {
                this.RowNumber = RowNumber;
            }

            public String getInsuranceName() {
                return InsuranceName;
            }

            public void setInsuranceName(String InsuranceName) {
                this.InsuranceName = InsuranceName;
            }

            public String getInsuranceType() {
                return InsuranceType;
            }

            public void setInsuranceType(String InsuranceType) {
                this.InsuranceType = InsuranceType;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getProdCode() {
                return ProdCode;
            }

            public void setProdCode(String ProdCode) {
                this.ProdCode = ProdCode;
            }

            public String getInsuranceDate() {
                return InsuranceDate;
            }

            public void setInsuranceDate(String InsuranceDate) {
                this.InsuranceDate = InsuranceDate;
            }

            public String getIsAgent() {
                return IsAgent;
            }

            public void setIsAgent(String IsAgent) {
                this.IsAgent = IsAgent;
            }

            public String getAgentUrl() {
                return AgentUrl;
            }

            public void setAgentUrl(String AgentUrl) {
                this.AgentUrl = AgentUrl;
            }

            public String getCompanyName() {
                return CompanyName;
            }

            public void setCompanyName(String CompanyName) {
                this.CompanyName = CompanyName;
            }

            public String getITypeName() {
                return ITypeName;
            }

            public void setITypeName(String ITypeName) {
                this.ITypeName = ITypeName;
            }

            public String getCompanyCode() {
                return CompanyCode;
            }

            public void setCompanyCode(String CompanyCode) {
                this.CompanyCode = CompanyCode;
            }

            public String getCompanyShortName() {
                return CompanyShortName;
            }

            public void setCompanyShortName(String CompanyShortName) {
                this.CompanyShortName = CompanyShortName;
            }

            public int getIsRecommended() {
                return IsRecommended;
            }

            public void setIsRecommended(int IsRecommended) {
                this.IsRecommended = IsRecommended;
            }

            public String getWebSite() {
                return WebSite;
            }

            public void setWebSite(String WebSite) {
                this.WebSite = WebSite;
            }

            public int getIsNew() {
                return IsNew;
            }

            public void setIsNew(int IsNew) {
                this.IsNew = IsNew;
            }
        }

        public static class Table1Bean {
            /**
             * RowsCount : 283
             */

            private String RowsCount;

            public String getRowsCount() {
                return RowsCount;
            }

            public void setRowsCount(String RowsCount) {
                this.RowsCount = RowsCount;
            }
        }
    }
}
