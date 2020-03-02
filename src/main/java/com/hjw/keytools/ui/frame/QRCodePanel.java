package com.hjw.keytools.ui.frame;

import javax.swing.*;
import java.awt.*;

public class QRCodePanel {
    private static QRCodePanel qrCodePanel;
    private MainFrame mainFrame;
    JPanel qrCodeContentPanel;

    private QRCodePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        init();
    }

    public static QRCodePanel getInstance(MainFrame mainFrame){
        if(qrCodePanel == null){
            qrCodePanel = new QRCodePanel(mainFrame);
        }
        return qrCodePanel;
    }

    public JPanel getQRCodeContentPanel(){
        return qrCodeContentPanel;
    }

    public void init(){
        qrCodeContentPanel = new JPanel(new GridLayout(1,3));
        JPanel inputContentJpanel = new JPanel(new FlowLayout());
        JPanel qrPicContentPanel = new JPanel();
//        qrPicContentPanel.setSize(new Dimension(400,400));
        qrPicContentPanel.setBackground(Color.yellow);

        JLabel hintLabel = new JLabel("请输入转换二维码的文本内容");
        setJpanelFont(hintLabel);
        JTextArea inputJTextArea = new JTextArea(20,25);

        JPanel butJPanel = new JPanel();
        JButton convertBut = new JButton("转换");

        inputContentJpanel.add(hintLabel);
        inputContentJpanel.add(inputJTextArea);

        butJPanel.add(convertBut);

        qrCodeContentPanel.add(inputContentJpanel);
        qrCodeContentPanel.add(butJPanel);
        qrCodeContentPanel.add(qrPicContentPanel);

    }

    public void setJpanelFont(JLabel jLabel) {
        jLabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
        jLabel.setForeground(new Color(76, 118, 226));

    }
}
