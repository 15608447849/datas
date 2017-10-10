package icbc.interaction;

import com.winone.ftc.mtools.FileUtil;
import icbc.Icbc;
import interfaces.CatchBean;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

import static interfaces.BaseThread.objTansJson;

/**
 * Created by user on 2017/7/13.
 */
public class GoodsDataAllRecode {
    private Map<GoodsMenu,ArrayList<Goods>> allListMap = new LinkedHashMap<>();
    private String key = "InteractionGoods";
    private final ArrayList<String> addressList = new ArrayList<>();
    private Icbc icbc;
    private int index = 0;
    private final ReentrantLock lock  = new ReentrantLock();
    private int currentExistKeyNunber = 0;
    public String getJsonPath(){return key+"/json";}
    public String getAddressPath(){return key+"/down";}
    public String getJs(){return key+".js";}
    public String getJson(){return key+".json";}
    public String getResAddress() {
        return "resource.address";
    }
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



    public void init(Icbc icbc) {
        this.icbc = icbc;
    }

    public List<String> getParamList() {
        return icbc.getList();
    }



    private static class Holder{
        private static GoodsDataAllRecode recode = new GoodsDataAllRecode();
    }
    public static GoodsDataAllRecode get(){
        return Holder.recode;
    }



    public void put(Goods data){
        //
        try{
            lock.lock();
            if (icbc==null) return;
            final Goods goods = data;
            goods.setId((++index)+"");
            Iterator<Map.Entry<GoodsMenu,ArrayList<Goods>>> iterator = allListMap.entrySet().iterator();
            Map.Entry<GoodsMenu,ArrayList<Goods>> entry ;
            while (iterator.hasNext()){
                entry = iterator.next();
                if (entry.getKey().isSave(goods)){
                    entry.getValue().add(goods);
                    if (goods.getImg_cover()!=null){
                        addImageAddress(goods.getImg_cover());
                    }
                }
            }
        }finally {
            lock.unlock();
        }
    }

    private void addImageAddress(String img_cover) {
        if (!img_cover.startsWith("/")){
            img_cover = "/"+img_cover;
        }
        addressList.add(img_cover);
    }

    public void clear(){
        try {
            lock.lock();
            if (icbc==null) return;
            index = 0;
            currentExistKeyNunber = 0;
            addressList.clear();
            Iterator<Map.Entry<GoodsMenu,ArrayList<Goods>>> iterator = allListMap.entrySet().iterator();
            Map.Entry<GoodsMenu,ArrayList<Goods>> entry ;
            while (iterator.hasNext()){
                entry = iterator.next();
                entry.getValue().clear();
            }
        }finally{
                lock.unlock();
        }
    }

    public int recode(String clazzPath){
        try{
            lock.lock();
            int nunber = -1;
            if (icbc!=null) {
                if (icbc.getList().contains(clazzPath)){
                    currentExistKeyNunber++;
                }
                nunber = currentExistKeyNunber;
                if (currentExistKeyNunber == icbc.getList().size()){
                    recode();
                }
            }
            return nunber;
        }finally {
            lock.unlock();
        }
    }
    //记录
    public void recode(){
        try{
            lock.lock();
            if (icbc==null) return;
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
            String content = objTansJson(new GoodsRecodes(goodsMenusList,goodsList));
            content = content.replace("clazz","class");
            FileUtil.writeStringToFile(content, icbc.fullPath()+ FileUtil.SEPARATOR+getJsonPath(),getJson(),false);
            content = "var JSON_DATA = "+content;
            FileUtil.writeStringToFile(content, icbc.fullPath()+ FileUtil.SEPARATOR+getJsonPath(),getJs(),false);
            //纪录地址
            content = objTansJson(addressList);
            FileUtil.writeStringToFile(content, icbc.fullPath()+ FileUtil.SEPARATOR+getAddressPath(),getResAddress(),false);
//            clearMap();
        }finally {
            lock.unlock();
        }

    }
    public CatchBean buildCatch(){
        CatchBean catchBean = new CatchBean();
        catchBean.setName(getClass().getName());
        catchBean.setJsFileLink(icbc.getPathFile()+ FileUtil.SEPARATOR+getJsonPath()+ FileUtil.SEPARATOR+getJs());
        catchBean.setJsonFileLink(icbc.getPathFile()+ FileUtil.SEPARATOR+getJsonPath()+ FileUtil.SEPARATOR+getJson());
        catchBean.setResFileLink(icbc.getPathFile()+ FileUtil.SEPARATOR+getAddressPath()+ FileUtil.SEPARATOR+getResAddress());
        return catchBean;
    }
}
