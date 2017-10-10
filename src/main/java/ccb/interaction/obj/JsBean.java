package ccb.interaction.obj;


import ccb.interaction.obj.creditcard.CreditCardBean;
import ccb.interaction.obj.finance.FinanceBean;

import ccb.interaction.obj.fundX.FundXBean;
import ccb.interaction.obj.insurance.InsuranceBean;
import ccb.interaction.obj.loan.LoanBean;
import ccb.interaction.obj.precious.PreciousMetalBean;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/9/15.
 */
public class JsBean {
        private List<InsuranceBean> insurance = new ArrayList<>();
        public List<InsuranceBean> getInsurance() {
            return insurance;
        }

        private List<PreciousMetalBean> preciousMetal_1 = new ArrayList<>();
        public List<PreciousMetalBean> getPreciousMetal_1() {
            return preciousMetal_1;
        }

    private List<FinanceBean> financial = new ArrayList<>();
    public List<FinanceBean> getFinancial() {
        return financial;
    }

    private List<CreditCardBean> creditcard = new ArrayList<>();
    public List<CreditCardBean> getCreditcard() {
        return creditcard;
    }

    private List<LoanBean> loan = new ArrayList<>();
    public List<LoanBean> getLoan() {
        return loan;
    }

    private List<FundXBean> fund1 = new ArrayList<>();
    public List<FundXBean> getFund1() {
        return fund1;
    }

    private List<FundXBean> fund2 = new ArrayList<>();
    public List<FundXBean> getFund2() {
        return fund2;
    }

    public List<FundXBean> fund3 = new ArrayList<>();
    public List<FundXBean> getFund3() {
        return fund3;
    }

    private List<FundXBean> fund4 = new ArrayList<>();
    public List<FundXBean> getFund4() {
        return fund4;
    }

    public void clear() {
        insurance.clear();
        preciousMetal_1.clear();
        financial.clear();
        creditcard.clear();
        fund1.clear();
        fund2.clear();
        fund3.clear();
        fund4.clear();
        loan.clear();
    }








}
