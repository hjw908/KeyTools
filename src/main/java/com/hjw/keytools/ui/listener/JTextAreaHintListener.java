package com.hjw.keytools.ui.listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class JTextAreaHintListener implements FocusListener {
    private JTextArea jTextArea;
    private String hintText;

    public JTextAreaHintListener(JTextArea jTextArea,String hintText) {
        this.jTextArea = jTextArea;
        this.hintText = hintText;
        jTextArea.setText(hintText);
        jTextArea.setForeground(Color.GRAY);
    }

    @Override
    public void focusGained(FocusEvent e) {
        String temptext = jTextArea.getText();
        if(temptext.equals(hintText)){
            jTextArea.setText("");
            jTextArea.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        String tempText = jTextArea.getText();
        if(tempText.equals("")){
            jTextArea.setText(hintText);
            jTextArea.setForeground(Color.GRAY);
        }
    }
}
