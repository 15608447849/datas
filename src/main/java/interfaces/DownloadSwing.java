package interfaces;

import lunch.Param;
import lunch.Say;

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
    JFrame frame;
    JProgressBar progressbar;
    JLabel label;
    public DownloadSwing() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame = new JFrame("颖网科技数据抓取组件");
        frame.setBounds(0, 0, 300,60);
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

        label=new JLabel("未知");
        contentPanel.add(new JPanel().add(label), BorderLayout.CENTER);
        contentPanel.add(progressbar, BorderLayout.SOUTH);

    }

    private static class Holder{
        private static final DownloadSwing instands = new DownloadSwing();
    }
    public static DownloadSwing get(){
        return Holder.instands;
    }

    public synchronized void setCurrentInfo(int progress,int max,String title){
        if (!isShow){
            show();
        }
        label.setText(title+" 资源下载中,请稍等 [ "+progress+"/"+max+" ]");
        progressbar.setValue(  (int)(((float)progress / (float)max) * 100));
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
//        frame.setVisible(true);
    }

    public boolean isShow = false;

    public void show(){
        isShow = true;
        frame.setVisible(isShow);
    }
    public void hide(){
        isShow = false;
        frame.setVisible(isShow);
    }



}

