package com.hjw.keytools.ui.listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class JTextFieldHintListener implements FocusListener {
    String hintContent;
    JTextField jTextField;

    public JTextFieldHintListener(JTextField jTextField,String content) {
        this.jTextField = jTextField;
        this.hintContent = content;
        this.jTextField.setText(content);
        this.jTextField.setForeground(Color.GRAY);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(jTextField.getText().equals(hintContent)){
            jTextField.setText("");
            jTextField.setForeground(Color.black);
        }

    }

    @Override
    public void focusLost(FocusEvent e) {
        if(jTextField.getText().equals("")){
            jTextField.setText(hintContent);
            jTextField.setForeground(Color.gray);
        }
    }
}
