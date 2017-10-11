package abchina.all.interaction.card;

import abchina.all.AllData;
import abchina.all.imps.AllDataIns;
import abchina.all.interaction.card.obj.CardBean;
import abchina.all.interaction.card.obj.SourceData;
import com.winone.ftc.mtools.FileUtil;
import com.winone.ftc.mtools.StringUtil;
import lunch.Say;
import org.jsoup.nodes.Document;

import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2017/10/10.
 */
public class Creditcard extends AllDataIns {
    private static final String PAGE_SIZE_BASE  = "{{PS}}";
    private static final String PAGE_INDEX_BASE = "{{PI}}";
    private static final String URL_BASE = "http://www.abchina.com/services/creditcard/Webapi/api/ApiWebCardInfo/GetCardInfoList?PageSize="
           + PAGE_SIZE_BASE  + "&PageIndex="
           + PAGE_INDEX_BASE + "&CardName=&CardType=&CardsRights=&Cointype=&AskFor=false&CardSource=&ApplyArea=36";

    private static final String URL_HEAD = "http://www.abchina.com";

    private static final HashMap<Integer, String> TYPE_NAME = new HashMap<Integer, String>();

    private static final int PAGE_SIZE_NUM = 220;

    private int currentIndex;


    public Creditcard(AllData mData) throws Exception {
        super(mData);
    }

    @Override
    protected void init() {
        initTypeMap();
        currentIndex = 0;   //初始化当前页面所在索引。
    }

    /**
     * 初始化類型map
     */

    private void initTypeMap() {
        TYPE_NAME.put(1 , "标准类型");
        TYPE_NAME.put(11, "车主类型");
        TYPE_NAME.put(4 , "商旅类型");
        TYPE_NAME.put(5 , "女性类型");
        TYPE_NAME.put(2 , "白金类型");
        TYPE_NAME.put(6 , "年轻类型");
        TYPE_NAME.put(3 , "公务类型");
        TYPE_NAME.put(7 , "其他类型");
    }

    private SourceData getSourceData(String url) throws Exception {
        return mData.jTextToJson(mData.getHttpResponse(url, null, true).body(), SourceData.class);
    }

    private String convUrl(int pageSize, int pageIndex) {
        return URL_BASE.replace(PAGE_INDEX_BASE, String.valueOf(pageIndex)).replace(PAGE_SIZE_BASE, String.valueOf(pageSize));
    }

    private String getDescription(String url) {
        String result = "";

        if (url != null && !url.trim().isEmpty()) {
            try {
                result = mData.getDocumentByUrl(url).select("div[class=sc_widthPer64_info ybw_2]").first().text();
            } catch (Exception e) {}
        }
        return result.trim();
    }

    @Override
    public void execute() throws Exception {
        SourceData sourceData = getSourceData(convUrl(1, 1));
        CardBean   cardBean   = null;
        if (sourceData != null) {
            int totalNum  = sourceData.getTotal();
            String webURL = null;
            for (int handler = 0; handler < totalNum; handler += PAGE_SIZE_NUM) {
                sourceData = getSourceData(convUrl(PAGE_SIZE_NUM, ++currentIndex));
                for (SourceData.ResultsBean res : sourceData.getResults()) {
                    cardBean = new CardBean();
                    cardBean.setId(mData.getJsObject().getCreditcard().size());
                    cardBean.setName(res.getCardName());
                    cardBean.setType(TYPE_NAME.get(res.getCardType()));

                    webURL = res.getWebDetaiNewUrl().startsWith(URL_HEAD) ? res.getWebDetaiNewUrl() : URL_HEAD + res.getWebDetaiNewUrl();
                    cardBean.setQr_code(webURL);

                    cardBean.setDesc1(res.getSlogan());
                    cardBean.setDesc2(getDescription(webURL));
                    cardBean.setCard_img(mData.getGoodsImageUrl(
                            res.getImgPubUrl() + res.getSrcFile(),
                            this.getClass().getSimpleName() + FileUtil.SEPARATOR + cardBean.getName()));
                    Say.I(cardBean);
                    mData.getJsObject().getCreditcard().add(cardBean);
                }
            }

        }

    }
}
