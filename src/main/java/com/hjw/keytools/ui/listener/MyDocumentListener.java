package com.hjw.keytools.ui.listener;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * 自定义内容监听器
 * @ClassName MyDocumentListener
 * @descrition 自定义内容监听器用于监听内容的变化
 * @author hjw
 * @date 2020.2.28
 *
 */
public class MyDocumentListener implements DocumentListener {
    JTextArea jTextArea;
    String content;

    @Override
    public void insertUpdate(DocumentEvent documentEvent) {
        Document doc = documentEvent.getDocument();
        try {
            this.content = doc.getText(0,doc.getLength());
            this.jTextArea.setText(content);
            System.out.println("insert:" + content);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent documentEvent) {
        Document doc = documentEvent.getDocument();
        jTextArea.setText("");
        try {
            content = doc.getText(0,doc.getLength());
            System.out.println("remove:"+ content);
            jTextArea.setText(content);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changedUpdate(DocumentEvent documentEvent) {
        Document doc = documentEvent.getDocument();
        try {
            this.content = doc.getText(0,doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
