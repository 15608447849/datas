package interfaces;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2017/8/7.
 */
public class DownloadSwing  implements ActionListener, ChangeListener {
    public static final Map<String,String> titleMap = new HashMap<>();
    static {
        titleMap.put("offline_xperience_achine.NormalGood","融E购正常商品资源下载中");
        titleMap.put("offline_xperience_achine.GroupBuy","融E购秒杀团购资源下载中");
        titleMap.put("credit_card.CreditCard","工商银行信用卡资源下载中");
        titleMap.put("financial.Financial","工商银行金融产品资源下载中");
        titleMap.put("fund.Fund","工商银行基金产品资源下载中");
        titleMap.put("precious_metal.PreciousMetal","工商银行贵金属产品资源下载中");
        titleMap.put("person_loan.PersonLoan","工商银行个人理财资源下载中");
        titleMap.put("insurance.Insurance","工商银行保险产品资源下载中");
    }
    private static int index = 0;

    JFrame frame;
    JProgressBar progressbar;
    int progress;
    public DownloadSwing(String title,int progress) {
        index++;
        if (index == titleMap.size()) index = 0;
        this.progress = progress;
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String value = titleMap.get(title);
        frame = new JFrame(value==null?title:value);

        frame.setBounds(100, 100+(index*52), 300, 50);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        Container contentPanel = frame.getContentPane();

        progressbar = new JProgressBar();

        progressbar.setOrientation(JProgressBar.HORIZONTAL);

        progressbar.setMinimum(0);

        progressbar.setMaximum(100);

        progressbar.setValue(0);

        progressbar.setStringPainted(true);

        progressbar.addChangeListener(this);

        progressbar.setPreferredSize(new Dimension(300, 20));

        progressbar.setBorderPainted(true);

        progressbar.setBackground(Color.pink);

        contentPanel.add(progressbar, BorderLayout.SOUTH);

        frame.setVisible(true);
    }



    public void setCurrent(int progress){
        progressbar.setValue(  (int)(((float)progress / (float)this.progress) * 100));
        if (this.progress == progress){
            close();
        }
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {

    }

    public void close() {
        frame.dispose();
    }
}

