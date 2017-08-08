package lunch;

import com.google.gson.Gson;
import com.winone.ftc.mtools.ClazzUtil;
import com.winone.ftc.mtools.FileUtil;
import interaction.GoodsDataAllRecode;
import interfaces.ActionCall;
import interfaces.CatchBean;
import interfaces.GoodsCatchBean;
import m.tcps.p.CommunicationAction;
import m.tcps.p.Op;
import m.tcps.p.Session;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/8/8.
 */
public class NotifyServer implements CommunicationAction {
    private final ArrayList<String> list = new ArrayList();
    private final List<String> goodsList = new ArrayList<>();
    private void initList() {
        list.add("offline_xperience_achine.NormalGood");
        list.add("offline_xperience_achine.GroupBuy");
        list.add("fund.Fund");
        list.add("credit_card.CreditCard");
        list.add("financial.Financial");
        list.add("precious_metal.PreciousMetal");
        list.add("person_loan.PersonLoan");
        list.add("insurance.Insurance");
    }
    private void initGoodsList(){
        goodsList.add("credit_card.CreditCard");
        goodsList.add("financial.Financial");
        goodsList.add("fund.Fund");
        goodsList.add("precious_metal.PreciousMetal");
        goodsList.add("person_loan.PersonLoan");
        goodsList.add("insurance.Insurance");
    }
    private final String WELCOME_LOG;
    private String getWelcome() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("LAUNCH/CATCH # MESSAGEID % ");
        for (int i = 0;i<list.size();i++){
            stringBuffer.append(" [ ");
            stringBuffer.append(list.get(i));
            stringBuffer.append(" ] ");
        }
        stringBuffer.append(" [ ").append(GoodsDataAllRecode.get().getKey()).append(" ] ");
        return stringBuffer.toString();
    }


    public NotifyServer() {
        initList();
        initGoodsList();
        this.WELCOME_LOG = getWelcome();
    }



    @Override
    public void connectSucceed(Session session) {
            session.writeString(WELCOME_LOG);
    }

    @Override
    public void receiveString(Session session, Op op, String message) {
        //记录任务 - id,任务目的地,任务类型.
        try {
            if( message.contains("%")){
                String[] strArr = message.split("%");
                String[] arr2 = strArr[0].trim().split("#");
                String[] param = strArr[1].trim().split(",");
                String result = "command is not match.";
                String command= arr2[0].trim();
                String messageId = arr2[1].trim();
                if (command.equals("LAUNCH")){
                     launchToTarget(messageId,session,param);
                }else
                if (command.equals("CATCH")){
                     catchToTarget(messageId,session,param);
                }else{
                    session.writeString("DATA%"+result);
                }
            }else{
                session.writeString("error command.");
            }
        } catch (Exception e) {
            session.writeString("error:"+e.getMessage());
        }
    }

    private void catchToTarget(String messageId,Session session, String[] param) {
        if (param.length==1 && param[0].equals(GoodsDataAllRecode.get().getKey())){
            goodsCallCatch(session,param);
        }else{
            otherCatch(messageId,session,param);
        }
    }

    private void otherCatch(String messageId,Session session, String[] param) {
        List<CatchBean> list = new ArrayList<>();
        CatchBean catchBean;
        for (int i = 0; i < param.length;i++){
            catchBean = new CatchBean();
            catchBean.setName(param[i]);
            checkCompel(catchBean);
            list.add(catchBean);
        }
        if (list.size()>0) {
            session.writeString(new Gson().toJson(list));
        }else{
            session.writeString("error");
        }
    }

    private void goodsCallCatch(Session session, String[] param) {

    }

    //启动
    private void launchToTarget(String messageId,Session session, String[] param) {
        if (param.length==1 && param[0].equals(GoodsDataAllRecode.get().getKey())){
            goodsCall(messageId,session,param);
        }else{
            other(messageId,session,param);
        }

    }

    private void other(String messageId,Session session, String[] param) {
        ActionCall action = new ActionCall() {
            @Override
            public void error(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete(CatchBean catchBean) {
                session.writeString(messageId+"#"+new Gson().toJson(catchBean));
            }
        };
        for (int i = 0; i < param.length;i++){
            if (list.contains(param[i])){
                ClazzUtil.newInstance(param[i],new Class[]{ActionCall.class},new Object[]{action});
            }
        }
    }

    private void goodsCall(String messageId,Session session,String[] param) {
        //特殊处理
        final ActionCall goodsAction = new ActionCall() {
            List<CatchBean> mlist = new ArrayList<>();
            @Override
            public void error(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete(CatchBean catchBean) {
                mlist.add(catchBean);
                if (mlist.size() == goodsList.size()){
                    GoodsDataAllRecode.get().recode();

                    GoodsCatchBean goodsCatchBean = GoodsDataAllRecode.get().buildCatch(Param.HOME, mlist);
                    goodsCatchBean.setName(param[0]);
                    String json =  new Gson().toJson(goodsCatchBean);
                    session.writeString(messageId+"#"+json);
                }
            }
        };
        for (int i = 0; i < goodsList.size();i++){
            ClazzUtil.newInstance(goodsList.get(i),new Class[]{ActionCall.class},new Object[]{goodsAction});
        }
    }









    private void checkCompel(CatchBean catchBean) {
        //查看文件夹

        String name = catchBean.getName();
        if (name.contains(".")) name = name.substring(name.lastIndexOf(".")+1);
        String path = Param.HOME+ FileUtil.SEPARATOR+name;
        File dirs = new File(path);
        if (!dirs.exists() || !dirs.isDirectory() || dirs.listFiles().length==0){
            catchBean.setMessage("target directory does not fount.please LAUNCH%"+catchBean.getName());
        }else{
            for (File file : dirs.listFiles()){
                if (file.getName().equals("down")){
                    if (file.listFiles().length==1 && file.listFiles()[0].length() > 0){
                        catchBean.setResFileLink(FileUtil.SEPARATOR+name+FileUtil.SEPARATOR+file.getName()+FileUtil.SEPARATOR+file.listFiles()[0].getName());
                    }
                }
                if (file.getName().equals("json")){
                    if (file.listFiles().length>0 ){
                        for (File f: file.listFiles()){
                            if (f.getName().endsWith("js") && f.length()>0){
                                catchBean.setJsFileLink(FileUtil.SEPARATOR+name+FileUtil.SEPARATOR+file.getName()+FileUtil.SEPARATOR+f.getName());
                            }
                            if (f.getName().endsWith("json") && f.length()>0){
                                catchBean.setJsonFileLink(FileUtil.SEPARATOR+name+FileUtil.SEPARATOR+file.getName()+FileUtil.SEPARATOR+f.getName());
                            }
                        }
                    }
                }
            }
        }
        if (catchBean.getResFileLink()!=null){
            if (catchBean.getResZip()==null) catchBean.setResZip(catchBean.getResFileLink()+"/res.zip");
        }

    }






}
