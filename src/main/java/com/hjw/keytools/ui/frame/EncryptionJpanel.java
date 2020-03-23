package com.hjw.keytools.ui.frame;


import com.hjw.keytools.ui.UiConstants;
import com.hjw.keytools.ui.listener.JTextAreaHintListener;
import com.hjw.keytools.utils.EncryptionUtil;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EncryptionJpanel {
    private static EncryptionJpanel encryptionJpanel;
    MainFrame mainFrame;
    JPanel encryptionContentJpanel;

    JTextArea outputArea;
    JTextArea inputArea;
    JComboBox<String> jComboBox;
    JTextField passwordField;

    private EncryptionJpanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        init();
    }

    public static EncryptionJpanel getInstance(MainFrame mainFrame) {
        if (encryptionJpanel == null) {
            encryptionJpanel = new EncryptionJpanel(mainFrame);
            return encryptionJpanel;
        }
        return encryptionJpanel;
    }

    public JPanel getEncryptionPanel() {
        return encryptionContentJpanel;
    }

    private void init() {
        encryptionContentJpanel = new JPanel();
        JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP);

        jTabbedPane.add("文字加密解密", createTextTabJpanel());
        jTabbedPane.add("MD5加密解密", createMD5TabJpanel());
        jTabbedPane.add("URL加密解密", createURLTabJpanel());

        encryptionContentJpanel.add(jTabbedPane);

    }

    public void setCustomBorder(JTextArea jTextArea, String borderTitle) {
        Border titleBorder = BorderFactory.createTitledBorder(borderTitle);
        Border lowerBorder = BorderFactory.createLoweredBevelBorder();

        jTextArea.setBorder(BorderFactory.createCompoundBorder(titleBorder, lowerBorder));
    }

    public JPanel createTextTabJpanel() {
        JPanel jPanel = new JPanel(new BorderLayout());
        JPanel contentJpanel = new JPanel(new GridLayout(1, 2));
        inputArea = new JTextArea(26, 40);
        setCustomBorder(inputArea, "加密内容");
        inputArea.addFocusListener(new JTextAreaHintListener(inputArea, UiConstants.HINT_CONTENT_ENCRYPTION));

        outputArea = new JTextArea(26, 40);
        setCustomBorder(outputArea, "解密内容");
        outputArea.addFocusListener(new JTextAreaHintListener(outputArea, UiConstants.HINT_CONTENT_DECRYPTION));

        contentJpanel.add(inputArea);
        contentJpanel.add(outputArea);

        JPanel southPanel = new JPanel(new BorderLayout());
        JPanel controlJpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel passwordLabel = new JLabel("密钥");
        setJpanelFont(passwordLabel);
        passwordField = new JTextField(20);
        String[] listarr = {"AES", "DES", "RC4", "Rabbit", "TripleDes"};
        jComboBox = new JComboBox<>(listarr);
        JButton encryptBut = new JButton("加密");
        encryptFuntion(encryptBut);
        JButton decryptBut = new JButton("解密");
        decryptFunction(decryptBut);
        JButton clearBut = new JButton("重置");
        JLabel psContent = new JLabel(UiConstants.PS_CONTENT);
        controlJpanel.add(passwordLabel);
        controlJpanel.add(passwordField);
        controlJpanel.add(jComboBox);
        controlJpanel.add(encryptBut);
        controlJpanel.add(decryptBut);
        controlJpanel.add(clearBut);
        clearFunction(clearBut);
        southPanel.add(controlJpanel, BorderLayout.CENTER);
        southPanel.add(psContent, BorderLayout.SOUTH);


        jPanel.add(contentJpanel, BorderLayout.CENTER);
        jPanel.add(southPanel, BorderLayout.SOUTH);


        return jPanel;


    }

    public void decryptFunction(JButton but) {
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String encodeType = (String) jComboBox.getSelectedItem();
                String outputContent = outputArea.getText();
                String password = passwordField.getText();
                if (!outputContent.equals("") && encodeType.equals("AES")) {
                    if (!outputContent.equals(UiConstants.HINT_CONTENT_DECRYPTION)) {

                        inputArea.setText(EncryptionUtil.AESDnCode(password, outputContent));
                        inputArea.setForeground(Color.BLACK);

                    }
                }
            }
        });
    }

    public void encryptFuntion(JButton but) {
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String encodeType = (String) jComboBox.getSelectedItem();
                String inputContent = inputArea.getText();
                String password = passwordField.getText();
                if (!inputContent.equals("") && encodeType.equals("AES")) {
                    if (!inputContent.equals(UiConstants.HINT_CONTENT_ENCRYPTION)) {
                        outputArea.setText(EncryptionUtil.AESEnCode(password, inputContent));
                        outputArea.setForeground(Color.BLACK);
                    }
                }
            }
        });
    }


    public void clearFunction(JButton but) {
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField.setText("");
                inputArea.setText(UiConstants.HINT_CONTENT_ENCRYPTION);
                inputArea.setForeground(Color.gray);
                outputArea.setText(UiConstants.HINT_CONTENT_DECRYPTION);
                outputArea.setForeground(Color.gray);
            }
        });
    }

    public void setJpanelFont(JLabel jLabel) {
        jLabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
        jLabel.setForeground(new Color(76, 118, 226));

    }

    public JPanel createMD5TabJpanel() {
        JPanel jPanel = new JPanel();
        return jPanel;


    }

    public JPanel createURLTabJpanel() {
        JPanel jPanel = new JPanel();
        return jPanel;


    }

}
