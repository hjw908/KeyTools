package com.hjw.keytools.ui.listener;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;

/**
 * 自定义内容监听器
 *
 * @author hjw
 * @ClassName MyDocumentListener
 * @descrition 自定义内容监听器用于监听内容的变化
 * @date 2020.2.28
 */
public class MyDocumentListener implements DocumentListener {
    //    JTextArea jTextArea;
//    JTextField jTextField;
    JLabel label; //对哪个组件进行动态的变更内容
    String content;

    public MyDocumentListener(JLabel label, String content) {
        this.label = label;
        this.content = content;
    }

    @Override
    public void insertUpdate(DocumentEvent documentEvent) {
        Document doc = documentEvent.getDocument();
        try {
            this.content = doc.getText(0, doc.getLength());
//            this.jTextArea.setText(content);
            this.label.setText(content);
            this.label.setFont(new Font("微软雅黑",Font.PLAIN,15));
//            System.out.println("insert:" + content);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent documentEvent) {
        Document doc = documentEvent.getDocument();
//        jTextArea.setText("");
        label.setText("");
        try {
            content = doc.getText(0, doc.getLength());
//            System.out.println("remove:" + content);
//            jTextArea.setText(content);
            label.setText(content);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changedUpdate(DocumentEvent documentEvent) {
        Document doc = documentEvent.getDocument();
        try {
            this.content = doc.getText(0, doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
