package abchina.all.interaction.financial.obj;

import java.util.List;

/**
 * Created by user on 2017/10/11.
 */

public class SourceData {
    /**
     * Data : {"Table":[{"ProductNo":"SYWJ160001","ProdName":"中国农业银行私人银行稳健策略委托资产管理半年开放式理财产品1号","ProdClass":"开放","ProdLimit":"1826天","ProdProfit":"-","ProdYildType":"非保本浮动收益","PrdYildTypeOrder":"2","ProdArea":"全国发行","szComDat":"2016.08.22","ProdSaleDate":"16.08.22-16.08.25","IsCanBuy":"0","PurStarAmo":1000000,"RowNumber":1}],"Table1":[{"total":7835}]}
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
             * ProductNo : SYWJ160001
             * ProdName : 中国农业银行私人银行稳健策略委托资产管理半年开放式理财产品1号
             * ProdClass : 开放
             * ProdLimit : 1826天
             * ProdProfit : -
             * ProdYildType : 非保本浮动收益
             * PrdYildTypeOrder : 2
             * ProdArea : 全国发行
             * szComDat : 2016.08.22
             * ProdSaleDate : 16.08.22-16.08.25
             * IsCanBuy : 0
             * PurStarAmo : 1000000
             * RowNumber : 1
             */

            private String ProductNo;
            private String ProdName;
            private String ProdClass;
            private String ProdLimit;
            private String ProdProfit;
            private String ProdYildType;
            private String PrdYildTypeOrder;
            private String ProdArea;
            private String szComDat;
            private String ProdSaleDate;
            private String IsCanBuy;
            private int PurStarAmo;
            private int RowNumber;

            public String getProductNo() {
                return ProductNo;
            }

            public void setProductNo(String ProductNo) {
                this.ProductNo = ProductNo;
            }

            public String getProdName() {
                return ProdName;
            }

            public void setProdName(String ProdName) {
                this.ProdName = ProdName;
            }

            public String getProdClass() {
                return ProdClass;
            }

            public void setProdClass(String ProdClass) {
                this.ProdClass = ProdClass;
            }

            public String getProdLimit() {
                return ProdLimit;
            }

            public void setProdLimit(String ProdLimit) {
                this.ProdLimit = ProdLimit;
            }

            public String getProdProfit() {
                return ProdProfit;
            }

            public void setProdProfit(String ProdProfit) {
                this.ProdProfit = ProdProfit;
            }

            public String getProdYildType() {
                return ProdYildType;
            }

            public void setProdYildType(String ProdYildType) {
                this.ProdYildType = ProdYildType;
            }

            public String getPrdYildTypeOrder() {
                return PrdYildTypeOrder;
            }

            public void setPrdYildTypeOrder(String PrdYildTypeOrder) {
                this.PrdYildTypeOrder = PrdYildTypeOrder;
            }

            public String getProdArea() {
                return ProdArea;
            }

            public void setProdArea(String ProdArea) {
                this.ProdArea = ProdArea;
            }

            public String getSzComDat() {
                return szComDat;
            }

            public void setSzComDat(String szComDat) {
                this.szComDat = szComDat;
            }

            public String getProdSaleDate() {
                return ProdSaleDate;
            }

            public void setProdSaleDate(String ProdSaleDate) {
                this.ProdSaleDate = ProdSaleDate;
            }

            public String getIsCanBuy() {
                return IsCanBuy;
            }

            public void setIsCanBuy(String IsCanBuy) {
                this.IsCanBuy = IsCanBuy;
            }

            public int getPurStarAmo() {
                return PurStarAmo;
            }

            public void setPurStarAmo(int PurStarAmo) {
                this.PurStarAmo = PurStarAmo;
            }

            public int getRowNumber() {
                return RowNumber;
            }

            public void setRowNumber(int RowNumber) {
                this.RowNumber = RowNumber;
            }
        }

        public static class Table1Bean {
            /**
             * total : 7835
             */

            private int total;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }
        }
    }
}
