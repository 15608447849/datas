package icbc.person_loan.obj;

import java.util.ArrayList;

/**
 * Created by user on 2017/6/24.
 */
public class PersonLoanBean {
    private LoanRate loan_rate;
    private ArrayList<LoanContent> loan_array;

    public LoanRate getLoan_rate() {
        return loan_rate;
    }

    public void setLoan_rate(LoanRate loan_rate) {
        this.loan_rate = loan_rate;
    }

    public ArrayList<LoanContent> getLoan_array() {
        return loan_array;
    }

    public void setLoan_array(ArrayList<LoanContent> loan_array) {
        this.loan_array = loan_array;
    }
}
