package abchina.all.interaction.loan;

import abchina.all.AllData;
import abchina.all.imps.AllDataIns;
import abchina.all.interaction.loan.obj.LoanBean;
import com.winone.ftc.mtools.StringUtil;
import lunch.Say;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

public class Loan extends AllDataIns {
    private static final HashMap<Integer, String> TYPE_CLASS = new HashMap<Integer, String>();
    private static final String URL = "http://www.abchina.com/cn/PersonalServices/Loans";
    private int currentIndex;

    public Loan(AllData mData) throws Exception {
        super(mData);
    }

    @Override
    protected void init() {
        initTypeClassMap();
        currentIndex = 1;
    }

    private void initTypeClassMap() {
        TYPE_CLASS.put(1, "安居 好时贷系列");
        TYPE_CLASS.put(2, "消费 好时贷系列");
        TYPE_CLASS.put(3, "创业 好时贷系列");
        TYPE_CLASS.put(4, "特色贷款业务"   );
    }

    private String convURL(String url) {
        String result = "";

        if (!StringUtil.isEntry(url) && !url.isEmpty()) {
           result = URL + url.substring(1);  //去除标记为当前页面的小数点. 并转化为绝对路径。
        }

        return result;
    }

    @Override
    public void execute() throws Exception {
        Element root = mData.getDocumentByUrl(URL).getElementById("daikuan");
        LoanBean loanBean;
        if (root != null) {
            Elements contents = root.getElementsByClass("fina_tab_content");

            for (Element element : contents) {
                Elements innerContents = element.getElementsByClass("mob_div_border");

                for (Element content : innerContents) {
                    loanBean = new LoanBean();

                    loanBean.setId(mData.getJsObject().getLoan().size());
                    loanBean.setName(content.select("a").first().text());
                    loanBean.setType(TYPE_CLASS.get(currentIndex));
                    loanBean.set_class(currentIndex);
                    loanBean.setDesc(content.select("dt[class=mob_fontS_normal pc_com_dt]").first().text());
                    loanBean.setQr_code(convURL(content.select("a").first().attr("href")));

                    mData.getJsObject().getLoan().add(loanBean);
                }

                currentIndex++;
            }
        }
    }
}
