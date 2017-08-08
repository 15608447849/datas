package interaction;

import com.google.gson.Gson;
import com.winone.ftc.mtools.FileUtil;
import interfaces.CatchBean;
import interfaces.GoodsCatchBean;
import lunch.Param;


import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by user on 2017/7/13.
 */
public class GoodsDataAllRecode {
    private Map<GoodsMenu,ArrayList<Goods>> allListMap = new LinkedHashMap<>();
    private String key = "InteractionGoods";
    private String path = key+"/json";
    private String fileName_json = key+".json";
    private String fileName_js = key+".js";
    public String getKey(){
        return key;
    }
    public String getPath(){return path;}
    public String getJs(){return fileName_js;}
    public String getJson(){return fileName_json;}
    private GoodsDataAllRecode() {
        allListMap.put(new GoodsMenu("1","信用卡"),new ArrayList<>());
        allListMap.put(new GoodsMenu("2","理财"),new ArrayList<>());
        allListMap.put(new GoodsMenu("3","基金-热销推荐"),new ArrayList<>());
        allListMap.put(new GoodsMenu("4","基金-收益排行"),new ArrayList<>());
        allListMap.put(new GoodsMenu("5","基金-新发产品"),new ArrayList<>());
        allListMap.put(new GoodsMenu("6","贵金属"),new ArrayList<>());
        allListMap.put(new GoodsMenu("7","保险"),new ArrayList<>());
        allListMap.put(new GoodsMenu("8","个人贷款"),new ArrayList<>());
    }
    private static class Holder{
        private static GoodsDataAllRecode recode = new GoodsDataAllRecode();
    }
    public static GoodsDataAllRecode get(){
        return Holder.recode;
    }

    private int index = 0;
    private final ReentrantLock lock  = new ReentrantLock();
    public void put(Goods data){
        try{
            lock.lock();
            final Goods goods = data;
            goods.setId((++index)+"");
            Iterator<Map.Entry<GoodsMenu,ArrayList<Goods>>> iterator = allListMap.entrySet().iterator();
            Map.Entry<GoodsMenu,ArrayList<Goods>> entry ;
            while (iterator.hasNext()){
                entry = iterator.next();
                if (entry.getKey().isSave(goods)){
                    entry.getValue().add(goods);
                }



            }
        }finally {
            lock.unlock();
        }
    }
    private void clearMap(){
        index = 0;
        Iterator<Map.Entry<GoodsMenu,ArrayList<Goods>>> iterator = allListMap.entrySet().iterator();
        Map.Entry<GoodsMenu,ArrayList<Goods>> entry ;
        while (iterator.hasNext()){
            entry = iterator.next();
            entry.getValue().clear();
        }
    }
    public synchronized void recode(){

        List<GoodsMenu> goodsMenusList = new ArrayList<>();
        List<Goods> goodsList = new ArrayList<>();

        Iterator<Map.Entry<GoodsMenu,ArrayList<Goods>>> iterator = allListMap.entrySet().iterator();
        Map.Entry<GoodsMenu,ArrayList<Goods>> entry ;
        while (iterator.hasNext()){
            entry = iterator.next();
            goodsMenusList.add(entry.getKey());
            for (Goods goods :entry.getValue()){
                goodsList.add(goods);
            }
        }
        if (goodsList.size()==0) return;
        String content = new Gson().toJson(new GoodsRecodes(goodsMenusList,goodsList));
        content = content.replace("clazz","class");
        boolean flag = FileUtil.writeStringToFile(content, Param.HOME+Param.PATH+ FileUtil.SEPARATOR+path,fileName_json,false);
        if (flag){
//            Say.I("记录互动茶几JSON: "+ Param.HOME+Param.PATH+FileUtil.SEPARATOR+path);
        }
        content = "var JSON_DATA = "+content;
        flag = FileUtil.writeStringToFile(content, Param.HOME+Param.PATH+ FileUtil.SEPARATOR+path,fileName_js,false);
        if (flag){
//            Say.I("记录互动茶几JS: "+ Param.HOME+Param.PATH+FileUtil.SEPARATOR+path);
        }
        clearMap();
    }
    public GoodsCatchBean buildCatch(String rootPath, List<CatchBean> list){

        GoodsCatchBean goodsCatchBean = new GoodsCatchBean(list);
            goodsCatchBean.setJsFileLink(rootPath+ FileUtil.SEPARATOR+getPath()+ FileUtil.SEPARATOR+getJs());
            goodsCatchBean.setJsonFileLink(rootPath+ FileUtil.SEPARATOR+getPath()+ FileUtil.SEPARATOR+getJson());
            return goodsCatchBean;
    }
}
