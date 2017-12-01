package abchina.all.interaction.funds.obj;

import java.util.List;

/**
 * Created by user on 2017/10/12.
 */
public class SourceData {
    /**
     * Data : {"Table":[{"Id":"618","FundCode":"150081","PurchaseDiscount":"7.00","AIPDiscount":"5.00","CurrentState":"2","ScaleType":"1","PurchaseUrl":"","LastQuarter":"0.9797","NetValue":"1.8900","AccumulatedNetValue":"1.8900","DayChanged":"0.5300","DayGrowthRate":"0.5300","DeclareDate":"2015-04-13T00:00:00+08:00","ShortName":"信诚双盈Ｂ基金","CanFixedInvestment":"0","LastMonth":"0.9263","LastOneYear":"1.5275","LastTwoYears":"1.4279","CurrentSaleState":"0","CompanyCode":"JJ00000046","CompanyName":"信诚基金管理有限公司","InvestmentType":"1","MillionRate":"0.0000","SinceEstablished":"2.5714","sequence":"2","IsSpecial":"0"}],"Table1":[{"RowCount":"1754"}]}
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
             * Id : 618
             * FundCode : 150081
             * PurchaseDiscount : 7.00
             * AIPDiscount : 5.00
             * CurrentState : 2
             * ScaleType : 1
             * PurchaseUrl :
             * LastQuarter : 0.9797
             * NetValue : 1.8900
             * AccumulatedNetValue : 1.8900
             * DayChanged : 0.5300
             * DayGrowthRate : 0.5300
             * DeclareDate : 2015-04-13T00:00:00+08:00
             * ShortName : 信诚双盈Ｂ基金
             * CanFixedInvestment : 0
             * LastMonth : 0.9263
             * LastOneYear : 1.5275
             * LastTwoYears : 1.4279
             * CurrentSaleState : 0
             * CompanyCode : JJ00000046
             * CompanyName : 信诚基金管理有限公司
             * InvestmentType : 1
             * MillionRate : 0.0000
             * SinceEstablished : 2.5714
             * sequence : 2
             * IsSpecial : 0
             */

            private String Id;
            private String FundCode;
            private String PurchaseDiscount;
            private String AIPDiscount;
            private String CurrentState;
            private String ScaleType;
            private String PurchaseUrl;
            private String LastQuarter;
            private String NetValue;
            private String AccumulatedNetValue;
            private String DayChanged;
            private String DayGrowthRate;
            private String DeclareDate;
            private String ShortName;
            private String CanFixedInvestment;
            private String LastMonth;
            private String LastOneYear;
            private String LastTwoYears;
            private String CurrentSaleState;
            private String CompanyCode;
            private String CompanyName;
            private String InvestmentType;
            private String MillionRate;
            private String SinceEstablished;
            private String sequence;
            private String IsSpecial;

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getFundCode() {
                return FundCode;
            }

            public void setFundCode(String FundCode) {
                this.FundCode = FundCode;
            }

            public String getPurchaseDiscount() {
                return PurchaseDiscount;
            }

            public void setPurchaseDiscount(String PurchaseDiscount) {
                this.PurchaseDiscount = PurchaseDiscount;
            }

            public String getAIPDiscount() {
                return AIPDiscount;
            }

            public void setAIPDiscount(String AIPDiscount) {
                this.AIPDiscount = AIPDiscount;
            }

            public String getCurrentState() {
                return CurrentState;
            }

            public void setCurrentState(String CurrentState) {
                this.CurrentState = CurrentState;
            }

            public String getScaleType() {
                return ScaleType;
            }

            public void setScaleType(String ScaleType) {
                this.ScaleType = ScaleType;
            }

            public String getPurchaseUrl() {
                return PurchaseUrl;
            }

            public void setPurchaseUrl(String PurchaseUrl) {
                this.PurchaseUrl = PurchaseUrl;
            }

            public String getLastQuarter() {
                return LastQuarter;
            }

            public void setLastQuarter(String LastQuarter) {
                this.LastQuarter = LastQuarter;
            }

            public String getNetValue() {
                return NetValue;
            }

            public void setNetValue(String NetValue) {
                this.NetValue = NetValue;
            }

            public String getAccumulatedNetValue() {
                return AccumulatedNetValue;
            }

            public void setAccumulatedNetValue(String AccumulatedNetValue) {
                this.AccumulatedNetValue = AccumulatedNetValue;
            }

            public String getDayChanged() {
                return DayChanged;
            }

            public void setDayChanged(String DayChanged) {
                this.DayChanged = DayChanged;
            }

            public String getDayGrowthRate() {
                return DayGrowthRate;
            }

            public void setDayGrowthRate(String DayGrowthRate) {
                this.DayGrowthRate = DayGrowthRate;
            }

            public String getDeclareDate() {
                return DeclareDate;
            }

            public void setDeclareDate(String DeclareDate) {
                this.DeclareDate = DeclareDate;
            }

            public String getShortName() {
                return ShortName;
            }

            public void setShortName(String ShortName) {
                this.ShortName = ShortName;
            }

            public String getCanFixedInvestment() {
                return CanFixedInvestment;
            }

            public void setCanFixedInvestment(String CanFixedInvestment) {
                this.CanFixedInvestment = CanFixedInvestment;
            }

            public String getLastMonth() {
                return LastMonth;
            }

            public void setLastMonth(String LastMonth) {
                this.LastMonth = LastMonth;
            }

            public String getLastOneYear() {
                return LastOneYear;
            }

            public void setLastOneYear(String LastOneYear) {
                this.LastOneYear = LastOneYear;
            }

            public String getLastTwoYears() {
                return LastTwoYears;
            }

            public void setLastTwoYears(String LastTwoYears) {
                this.LastTwoYears = LastTwoYears;
            }

            public String getCurrentSaleState() {
                return CurrentSaleState;
            }

            public void setCurrentSaleState(String CurrentSaleState) {
                this.CurrentSaleState = CurrentSaleState;
            }

            public String getCompanyCode() {
                return CompanyCode;
            }

            public void setCompanyCode(String CompanyCode) {
                this.CompanyCode = CompanyCode;
            }

            public String getCompanyName() {
                return CompanyName;
            }

            public void setCompanyName(String CompanyName) {
                this.CompanyName = CompanyName;
            }

            public String getInvestmentType() {
                return InvestmentType;
            }

            public void setInvestmentType(String InvestmentType) {
                this.InvestmentType = InvestmentType;
            }

            public String getMillionRate() {
                return MillionRate;
            }

            public void setMillionRate(String MillionRate) {
                this.MillionRate = MillionRate;
            }

            public String getSinceEstablished() {
                return SinceEstablished;
            }

            public void setSinceEstablished(String SinceEstablished) {
                this.SinceEstablished = SinceEstablished;
            }

            public String getSequence() {
                return sequence;
            }

            public void setSequence(String sequence) {
                this.sequence = sequence;
            }

            public String getIsSpecial() {
                return IsSpecial;
            }

            public void setIsSpecial(String IsSpecial) {
                this.IsSpecial = IsSpecial;
            }
        }

        public static class Table1Bean {
            /**
             * RowCount : 1754
             */

            private String RowCount;

            public String getRowCount() {
                return RowCount;
            }

            public void setRowCount(String RowCount) {
                this.RowCount = RowCount;
            }
        }
    }
}
