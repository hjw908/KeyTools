package com.hjw.keytools.ui.frame;

import com.hjw.keytools.ui.UiConstants;
import com.hjw.keytools.ui.listener.JTextAreaHintListener;
import com.hjw.keytools.utils.FileUtils;
import com.hjw.keytools.utils.JSONFormatUtil;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;

public class JSONFormatJPanel {
    private static JSONFormatJPanel jsonFormatJPanel;
    JTextArea sourceTextArea;
    JTextArea targetTextArea;
    MainFrame mainFrame;
    JPanel jsonFormatcontentJPanel;
    JTextField seekField;
    boolean ishighlight = false;
    int num;
    JLabel objNumLabel;

    private JSONFormatJPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        init();
    }


    public static JSONFormatJPanel getInstance(MainFrame mainFrame) {
        if (jsonFormatJPanel == null) {
            jsonFormatJPanel = new JSONFormatJPanel(mainFrame);
        }
        return jsonFormatJPanel;
    }

    public JPanel getJsonFormatcontenJPanel() {
        return jsonFormatcontentJPanel;
    }

    private void init() {
        jsonFormatcontentJPanel = new JPanel(new BorderLayout(5, 10));
        JPanel operatorJpanel = new JPanel(new GridLayout(1,3));
        JPanel firstJpanel = new JPanel();
        JPanel secondeJpanel = new JPanel();
        JPanel thirdJpanel = new JPanel();

        JButton convertBut = new JButton("转换");
        converFunction(convertBut);

        JButton clearBut = new JButton("清空");
        clearFunction(clearBut);

        JButton saveBut = new JButton("保存");
        saveFunction(saveBut);

        firstJpanel.add(convertBut);
        firstJpanel.add(clearBut);
        firstJpanel.add(saveBut);

        JButton seekBut = new JButton("查找");
        objNumLabel = new JLabel();
        objNumLabel.setForeground(Color.green);

        seekFunction(seekBut);
        seekField = new JTextField(10);

        secondeJpanel.add(seekBut);
        secondeJpanel.add(seekField);
        secondeJpanel.add(objNumLabel);

        operatorJpanel.add(firstJpanel);
        operatorJpanel.add(secondeJpanel);

        JPanel sourceJpanel = new JPanel();
        sourceTextArea = new JTextArea(27, 45);
        Border sourcetitleBorder = BorderFactory.createTitledBorder("SourceText");
        Border sourcelowerBorder = BorderFactory.createLoweredBevelBorder();
        sourceTextArea.setBorder(BorderFactory.createCompoundBorder(sourcetitleBorder, sourcelowerBorder));
        sourceTextArea.addFocusListener(new JTextAreaHintListener(sourceTextArea, UiConstants.HINT_TEXT));

        JScrollPane sourceJscrollPane = new JScrollPane(sourceTextArea);
        sourceJpanel.add(sourceJscrollPane);

        JPanel targetJpanel = new JPanel();
        targetTextArea = new JTextArea(27, 45);
        Border targettitleBorder = BorderFactory.createTitledBorder("TargetText");
        Border targetlowerBorder = BorderFactory.createLoweredBevelBorder();
        targetJpanel.setBorder(BorderFactory.createCompoundBorder(targettitleBorder, targetlowerBorder));

        JScrollPane targetJscrollPane = new JScrollPane(targetTextArea);
        targetJpanel.add(targetJscrollPane);

        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, sourceJpanel, targetJpanel);
        jSplitPane.setOneTouchExpandable(true);
        jSplitPane.setDividerSize(5);
        thirdJpanel.add(jSplitPane);

        jsonFormatcontentJPanel.add(operatorJpanel, BorderLayout.NORTH);
        jsonFormatcontentJPanel.add(thirdJpanel, BorderLayout.CENTER);
    }

    public void seekFunction(JButton seekBut){
        seekBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Highlighter highlighter = targetTextArea.getHighlighter();
                if(ishighlight){
                    highlighter.removeAllHighlights();
                    ishighlight = false;
                }
                String content = targetTextArea.getText();
                String seekWord = seekField.getText();
                if (!content.equals("") && !seekWord.equals("")) {
                    try {
                        int pos = 0;
                        int objNum = 0;
                        while ((pos = content.indexOf(seekWord, pos)) > 0) {
                            DefaultHighlighter.DefaultHighlightPainter painter =
                                    new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN);
                            highlighter.addHighlight(pos, pos + seekWord.length(), painter);
                            pos = pos + seekWord.length();
                            ishighlight = true;
                            objNum ++;
                        }
//                        num =objNum;
                        objNumLabel.setText(String.valueOf(objNum));
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });
    }

    public void saveFunction(JButton saveBut) {
        saveBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileUtils.saveFileDialog(mainFrame, targetTextArea);
            }
        });
    }

    public void converFunction(JButton convertBut) {
        convertBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tempStr = sourceTextArea.getText();
                if (!tempStr.equals("")) {
                    String jsonString = JSONFormatUtil.jsonFormat(tempStr);
                    targetTextArea.setText(jsonString);
                }
            }
        });
    }

    public void clearFunction(JButton clearBut) {
        clearBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sourceTextArea.setText(UiConstants.HINT_TEXT);
                sourceTextArea.setForeground(Color.GRAY);
                targetTextArea.setText("");
                objNumLabel.setText("");
                seekField.setText("");
            }
        });
    }

}
