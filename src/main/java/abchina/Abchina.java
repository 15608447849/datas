package abchina;
import interfaces.ParamManagerAdapter;
import lunch.Say;

/**
 * Created by user on 2017/10/10.
 */
public class Abchina extends ParamManagerAdapter {
    private Abchina() {
        super("abchina");
        map.put("abchina.rate.Rate","农业银行外汇");
        map.put("abchina.preciousMetal.PreciousMetal","农业银行贵金属");
        map.put("abchina.all.AllData","农业银行互动营销");



    }
    private static class Holder{
        private static final Abchina instance = new Abchina();
    }
    public static Abchina getInstance(){
        return Abchina.Holder.instance;
    }


    @Override
    public String toString() {
        return "中国农业银行";
    }
}
