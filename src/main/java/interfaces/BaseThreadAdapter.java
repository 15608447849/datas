package interfaces;

import ccb.interaction.obj.precious.PmJson;
import com.google.gson.Gson;
import lunch.Say;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.StringReader;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.bind.*;
/**
 * Created by user on 2017/9/12.
 */
public abstract class BaseThreadAdapter extends BaseThread{
    //存储 document对象
    private final LinkedHashMap<String,Document> cache = new LinkedHashMap<String,Document>(20,0.75f,true){
        public boolean removeEldestEntry(Map.Entry<String, Document> eldest){
            return size()> 20;
        }
    };

    protected BaseThreadAdapter(ParamManager paramManager, ActionCall action) {
        super(paramManager);
        this.setActionCall(action);
        initParams();
        start();
    }
    //用于初始化一些参数
    protected void initParams(){}

    //判断map是否存在缓存
    public Document getDocumentByUrl(String url) throws Exception{
        return getDocumentByUrl(url,null);
    }
    //判断map是否存在缓存
    public Document getDocumentByUrl(String url,HashMap<String,String> map) throws Exception{
        if (cache.containsKey(url)) return cache.get(url);
        return connectUrlAndStorage(url,"get",map,null);
    }
    //根据 URL  获取 docments 对象, 并存储在 map中.
    private Document connectUrlAndStorage(String url,String type,HashMap<String,String> header,HashMap<String,String> params) throws Exception{
        Document doc = null;
        Connection con = Jsoup.connect(url);
        setConnectHeader(con,header);
        if (params!=null && !params.isEmpty()){
            con =   con.data(params);
        }
        con = con.timeout(TIMEOUT);
        if (type.equalsIgnoreCase("post")){
            doc = con.post();
        }else{
            doc = con.get();
        }
        if (doc!=null){
            cache.put(url,doc);
        }
        return doc;
    }

    public void clearDocumentCache(){
        cache.clear();
    }

    protected  String charHandles(String str){
        str = str.replace("/","~");
        str = str.replace("【","[");
        str = str.replace("】","]");
        str = str.replace("（","(");
        str = str.replace("）",")");
        str = str.replace("：","#");
        str = str.replace("！","!");
        str = str.replace("*","x");
        str = str.replace("\"","^");

        return str.trim();
    }

    private void setConnectHeader(Connection connection,Map<String,String> header){
        if (header!=null && !header.isEmpty()){
            Iterator<Map.Entry<String,String>> iterator = header.entrySet().iterator();
            Map.Entry<String,String> entry;
            while (iterator.hasNext()){
                entry = iterator.next();
                connection = connection.header(entry.getKey(),entry.getValue());
            }

        }
    }

    public String getTextByUrl(String url, HashMap<String, String> header) throws Exception{
        try{
            Connection connection = Jsoup.connect(url);
            connection = connection.timeout(TIMEOUT);
            setConnectHeader(connection,header);
            return connection.execute().body().trim();
        }catch (SocketTimeoutException e){
            return getTextByUrl(url);
        }
    }
    public Connection.Response getHttpResponse(String url, Map<String, String> header,boolean isIgnoreContentType) throws Exception{
            Connection connection = Jsoup.connect(url);
            connection = connection.timeout(TIMEOUT);
            setConnectHeader(connection,header);
            connection.ignoreContentType(isIgnoreContentType);
            return connection.execute();
    }

    //判断map是否存在缓存
    public String getTextByUrl(String url) throws Exception{
        return getTextByUrl(url,null);
    }


    /**
     * xml -> object (jaxb)
     */
    @SuppressWarnings("unchecked")
    protected <T> T xmlToObject(String xml,Class<T> classType) throws Exception{
            JAXBContext context = JAXBContext.newInstance(classType);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (T) unmarshaller.unmarshal(new StringReader(xml));
    }



    /**
     * 根据json字符串返回Map对象
     * @param json
     * @return
     */
    public static Map<String,Object> jsonToMap(String json){
        return new Gson().fromJson(json, HashMap.class);
    }

    /**
     * json text -> json object
     */
    public static <T extends Object> T jTextToJson(String jsonText,Class<T>  tClass){
        return  new Gson().fromJson(jsonText,tClass);
    }

    //日期转换 默认: "yyyy-MM-dd HH:mm:ss.ms" -> "MM月dd日 HH:mm分"
    public static String dateFormat(String sourceDate,String sFormat,String dFormat){
            try {
                if (sFormat==null || sFormat.isEmpty()) sFormat = "yyyy-MM-dd HH:mm:ss";
                if (dFormat==null || dFormat.isEmpty()) dFormat = "MM月dd日 HH:mm分";
                SimpleDateFormat sdf=new SimpleDateFormat(sFormat);
                Date time = sdf.parse(sourceDate);
                sdf = new SimpleDateFormat(dFormat);
                return sdf.format(time);
            } catch (ParseException e) {
                return   sourceDate;
            }
    }

    //截取字符串
    public static String carpter(String data,String start,String end){
        if (data.contains(start)){
            int i = data.indexOf("\"");
            data = data.substring(i+1);
        }
        if (data.contains(end)){
            data = data.substring(0,data.indexOf("\""));
        }
        return data;
    }

}
