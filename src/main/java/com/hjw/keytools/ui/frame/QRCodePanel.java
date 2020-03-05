package com.hjw.keytools.ui.frame;

import com.hjw.keytools.ui.UiConstants;
import com.hjw.keytools.utils.FileUtils;
import com.hjw.keytools.utils.QRCodeUtil;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QRCodePanel {
    private static QRCodePanel qrCodePanel;
    private MainFrame mainFrame;
    JPanel qrCodeContentPanel;
    JTextField picPathField;
    String suffix = "png";
    JTextArea inputJTextArea;
    JTextArea outputJTextArea;
    JPanel j2;

    private QRCodePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        init();
    }

    public static QRCodePanel getInstance(MainFrame mainFrame) {
        if (qrCodePanel == null) {
            qrCodePanel = new QRCodePanel(mainFrame);
        }
        return qrCodePanel;
    }

    public JPanel getQRCodeContentPanel() {
        return qrCodeContentPanel;
    }

    public void init() {
        qrCodeContentPanel = new JPanel(new BorderLayout());
        JPanel firstJpanel = new JPanel(new FlowLayout(20, 40, 10));
        JPanel inputContentJpanel = new JPanel();
        JPanel qrPicContentPanel = new JPanel();

        JPanel formatJpanel = new JPanel();
        JLabel picFormatLabel = new JLabel("图片格式： ");
        setJpanelFont(picFormatLabel);
        JRadioButton pngButton = new JRadioButton("PNG");
        JRadioButton jpgButton = new JRadioButton("JPG");
        JRadioButton jpegButton = new JRadioButton("JPEG");
        JRadioButton gifButton = new JRadioButton("GIF");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(pngButton);
        buttonGroup.add(jpgButton);
        buttonGroup.add(jpegButton);
        buttonGroup.add(gifButton);
        pngButton.setSelected(true);
        radioButtonSelect(pngButton);
        radioButtonSelect(jpgButton);
        radioButtonSelect(jpegButton);
        radioButtonSelect(gifButton);

        formatJpanel.add(picFormatLabel);
        formatJpanel.add(pngButton);
        formatJpanel.add(jpgButton);
        formatJpanel.add(jpegButton);
        formatJpanel.add(gifButton);

        JPanel picPathPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel picPathLabel = new JLabel("图片的路径: ");
        setJpanelFont(picPathLabel);
        picPathField = new JTextField(30);
        JButton openBut = new JButton("打开");
        openFile(openBut);
        picPathPanel.add(picPathLabel);
        picPathPanel.add(picPathField);
        picPathPanel.add(openBut);

        JPanel saveJPanel = new JPanel(new BorderLayout(5, 5));
        saveJPanel.add(formatJpanel, BorderLayout.NORTH);
        saveJPanel.add(picPathPanel, BorderLayout.CENTER);


        JLabel hintTextLabel = new JLabel("请输入转换二维码的文本内容:");
        setJpanelFont(hintTextLabel);
        inputJTextArea = new JTextArea(20, 35);
        inputJTextArea.setBorder(BorderFactory.createTitledBorder("文案"));

        JLabel hintQrLabel = new JLabel("此处生成二维码:");
        setJpanelFont(hintQrLabel);
//
        outputJTextArea = new JTextArea(20, 35);
        outputJTextArea.setEnabled(false);
        outputJTextArea.setBorder(BorderFactory.createTitledBorder("二维码"));


        JPanel butJPanel = new JPanel();
        JButton convertBut = new JButton("转换");
        convertBut.setActionCommand("convert");
        convertQrCode(convertBut);


        JPanel j = new JPanel(new BorderLayout(5, 20));
        j.add(hintTextLabel, BorderLayout.NORTH);
        j.add(inputJTextArea, BorderLayout.CENTER);
        inputContentJpanel.add(j);
//        inputContentJpanel.add(inputJTextArea);

        butJPanel.add(convertBut);

        j2 = new JPanel(new BorderLayout(5, 20));
        j2.add(hintQrLabel, BorderLayout.NORTH);
        j2.add(outputJTextArea, BorderLayout.CENTER);
//        j2.add(qrPicLabel,BorderLayout.CENTER);
        qrPicContentPanel.add(j2);

        firstJpanel.add(inputContentJpanel);
        firstJpanel.add(butJPanel);
        firstJpanel.add(qrPicContentPanel);

        JPanel secondJpanel = new JPanel();
        secondJpanel.add(saveJPanel);


        qrCodeContentPanel.add(firstJpanel, BorderLayout.CENTER);
        qrCodeContentPanel.add(secondJpanel, BorderLayout.SOUTH);


    }

    public void convertQrCode(JButton but) {
        if (but.getActionCommand().equals("convert")) {

            but.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String text = inputJTextArea.getText();
                    if (!text.equals("") && picPathField.getText().equals("")) {

                        byte[] qrByte = QRCodeUtil.generateImage(text, 250, 250);
                        showQrCodeOnJpanel(qrByte);
                    }else if(!text.equals("") && !picPathField.getText().equals("")){
                        String filePath = picPathField.getText();

                        byte[] qrByte = QRCodeUtil.saveQrcodeImage(mainFrame,text,250,250,filePath,suffix);
                        showQrCodeOnJpanel(qrByte);
                    }else if(text.equals("")){
                        JOptionPane.showMessageDialog(mainFrame,"文本内容不能为空", UiConstants.MESSAGE_TITLE,JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });

        }
    }

    public void showQrCodeOnJpanel(byte[] qrByte){
        outputJTextArea.setOpaque(false);
        ImageIcon imageIcon = new ImageIcon(qrByte);
        JLabel qrPicLabel = new JLabel(imageIcon);
        qrPicLabel.setBorder(BorderFactory.createTitledBorder("二维码"));
        j2.remove(1);
        j2.add(qrPicLabel);
        j2.repaint();
        j2.revalidate();
        System.out.println("dddd");
    }


    /**
     * 变更图片缩放比例
     *
     * @param imageIcon
     * @param i
     * @return
     */
    public ImageIcon changeImageMultiple(ImageIcon imageIcon, double i) {
        int width = (int) (imageIcon.getIconWidth() * i);
        int height = (int) (imageIcon.getIconHeight() * i);
        Image img = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(img);
        return icon;
    }

    public void radioButtonSelect(final JRadioButton jRadioButton) {
        jRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                suffix = jRadioButton.getText().toLowerCase();
            }
        });

    }

    public void openFile(JButton but) {
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FileUtils.openFileDialog(mainFrame, picPathField);
            }
        });
    }

    public void setJpanelFont(JLabel jLabel) {
        jLabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
        jLabel.setForeground(new Color(76, 118, 226));

    }
}
