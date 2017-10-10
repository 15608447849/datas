package ccb.interaction.action;

import ccb.interaction.Interactions;
import ccb.interaction.obj.loan.LoanBean;
import lunch.Say;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by user on 2017/9/18.
 */
public class Loan extends InteractionsSubImp {
    private final String PRE = "http://ehome.ccb.com";
    private final String HUrl = PRE+"/Channel/22804995";
    public Loan(Interactions interaction) throws Exception {
        super(interaction);
    }

    @Override
    void execute() throws Exception {
        Document document = interaction.getDocumentByUrl(HUrl);

        Elements elements = document.select("div[id=rm15]");
        Element its;
        Elements eits;
        String val;
        LoanBean loanBean;
        for (Element it : elements){
            loanBean= new LoanBean();
            loanBean.setId(interaction.getJsBean().getLoan().size()+1);
            its = it.select("div[id=title]").first();

            loanBean.setType(its.text());
//            its = it.select("div[id=data]").first();
                //缩略图 PRE+its.select("img").attr("src")

            eits =  it.select("div[id=data]").select("tr");
            it = eits.get(0);
            loanBean.setDesc(it.text());
            it = eits.get(1);
            val = it.select("input").first().attr("onclick");
            val = val.substring(val.indexOf("'"),val.lastIndexOf("'"));
            loanBean.setQr_code(val);
            interaction.getJsBean().getLoan().add(loanBean);
        }

    }
}
