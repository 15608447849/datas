package abchina.all.interaction.Pre;

import abchina.all.AllData;
import abchina.all.imps.AllDataIns;
import abchina.all.interaction.Pre.obj.Bean;
import com.winone.ftc.mtools.FileUtil;
import lunch.Say;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by user on 2017/10/10.
 */
public class PreciousMetal_1 extends AllDataIns {



    public PreciousMetal_1(AllData mData) throws Exception {
        super(mData);
    }

    private String[] urlList;
    @Override
    protected void init() {

        urlList = new String[]{
                "http://ewealth.abchina.com/Gold/EntityGold/jqhs/"
                ,"http://ewealth.abchina.com/Gold/EntityGold/zfzh/"
                ,"http://ewealth.abchina.com/Gold/EntityGold/sctz/"
                ,"http://ewealth.abchina.com/Gold/EntityGold/jxzp/"};
    }

    @Override
    public void execute() throws Exception {

        Document document;
        Elements divs;
        Bean bean;
        String type;
        String imgUrl;
        for (String url : urlList){
            document = mData.getDocumentByUrl(url);
            divs = document.select("div[class=goldImgListCon]");
            type = document.select("span[class=iconNewsList]").first().text();

            for (Element element : divs){
                bean = new Bean();
                bean.setId(mData.getJsObject().getPreciousMetal_1().size());
                bean.setType(type);
                bean.setName(element.select("a").last().attr("title"));
                bean.setQr_code(element.select("a").last().attr("href").replaceFirst(".",url));
                imgUrl = element.select("a").first().select("img").first().attr("src").replaceFirst(".",url);
                imgUrl = mData.getGoodsImageUrl(imgUrl,getClass().getSimpleName()+ FileUtil.SEPARATOR+type);
                bean.setType_img(imgUrl);
                mData.getJsObject().getPreciousMetal_1().add(bean);
            }
        }

    }
}
