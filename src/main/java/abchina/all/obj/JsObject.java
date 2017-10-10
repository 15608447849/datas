package abchina.all.obj;

import java.util.ArrayList;

/**
 * Created by user on 2017/10/10.
 */
public class JsObject {

    private ArrayList<Object> insurance = new ArrayList<>();
    private ArrayList<Object> insurance1 = new ArrayList<>();
    private ArrayList<Object> preciousMetal_1 = new ArrayList<>();
    private ArrayList<Object> financial = new ArrayList<>();
    private ArrayList<Object> financial_1 = new ArrayList<>();
    private ArrayList<Object> fund1 = new ArrayList<>();
    private ArrayList<Object> fund2 = new ArrayList<>();
    private ArrayList<Object> fund3 = new ArrayList<>();
    private ArrayList<Object> fund4 = new ArrayList<>();
    private ArrayList<Object> creditcard = new ArrayList<>();
    private ArrayList<Object> loan = new ArrayList<>();

    public ArrayList<Object> getInsurance() {
        return insurance;
    }

    public ArrayList<Object> getInsurance1() {
        return insurance1;
    }

    public ArrayList<Object> getPreciousMetal_1() {
        return preciousMetal_1;
    }

    public ArrayList<Object> getFinancial() {
        return financial;
    }

    public ArrayList<Object> getFinancial_1() {
        return financial_1;
    }

    public ArrayList<Object> getFund1() {
        return fund1;
    }

    public ArrayList<Object> getFund2() {
        return fund2;
    }

    public ArrayList<Object> getFund3() {
        return fund3;
    }

    public ArrayList<Object> getFund4() {
        return fund4;
    }

    public ArrayList<Object> getCreditcard() {
        return creditcard;
    }

    public ArrayList<Object> getLoan() {
        return loan;
    }

    public void clearAll(){
        insurance.clear();
        insurance1.clear();
        preciousMetal_1.clear();
        financial.clear();
        financial_1.clear();
        fund1.clear();
        fund2.clear();
        fund3.clear();
        fund4.clear();
        creditcard.clear();
        loan.clear();
    }
}
