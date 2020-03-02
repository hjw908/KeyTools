package com.hjw.keytools.ui.frame;

import com.hjw.keytools.ui.UiConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStampConvertPanel {
    private static TimeStampConvertPanel timeStampConvertPanel;
    MainFrame mainFrame;
    JPanel cleanFirstPanel;
    JPanel cleanSecondPanel;
    JPanel timeContentPanel;

    JLabel currentTimeLabel;
    JLabel timeLabel;
    JLabel timeStampLabel;
    private Timer time;
    JLabel currentTimeStampLabel;
    Clipboard clipboard;

    public static String tempCopyTimeContent;
    public static String tempCopyTimeStampContent;

    JtextFieldCustom timestampField;
    JtextFieldCustom timeField;


    private TimeStampConvertPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        init();
    }

    public static TimeStampConvertPanel getInstance(MainFrame mainFrame){
        if(timeStampConvertPanel == null){
            timeStampConvertPanel = new TimeStampConvertPanel(mainFrame);
        }
        return timeStampConvertPanel;
    }

    public JPanel getTimeContentPanel(){
        return timeContentPanel;
    }

    public void init(){
        cleanFirstPanel = new JPanel(new BorderLayout());
        cleanSecondPanel = new JPanel(new BorderLayout());
        timeContentPanel = new JPanel(new GridLayout(2,1,15,15));
        JPanel cunrrentTimePanel = new JPanel(new GridLayout(2,1,35,35));
        JPanel subCunrrentTimePanel_1 = new JPanel();
        JPanel subCunrrentTimePanel_2 = new JPanel();
        JPanel timeConvertPanel = new JPanel(new GridLayout(3,2,35,35));
        JPanel subTimeConvertPanel_1 = new JPanel(new GridLayout(1,2,15,45));
        JPanel subTimeConvertPanel_2 = new JPanel();
        JPanel subTimeConvertPanel_3 = new JPanel(new GridLayout(1,2,15,45));

        currentTimeLabel = getCurrentTimeLabel();
        currentTimeStampLabel = getCurrentTimeStampLabel();
        JButton copyBut_1 = new JButton("复制");
        copyBut_1.setActionCommand("first_copy");
        JButton copyBut_2 = new JButton("复制");
        copyBut_2.setActionCommand("second_copy");
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        copySuccessDialog(copyBut_1,mainFrame);
        copySuccessDialog(copyBut_2,mainFrame);

        JLabel timestampTextLabel = new JLabel("Unix时间戳（Unix timestamp）");
        timestampTextLabel.setForeground(new Color(106, 100,226));
        timestampTextLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));


        JLabel timeTextLabel = new JLabel("当前系统时间（System time）");
        timeTextLabel.setForeground(new Color(106, 100,226));
        timeTextLabel.setFont((new Font("微软雅黑",Font.BOLD,20)));

        timestampField = new JtextFieldCustom();
        timestampField.setFont(new Font("微软雅黑",Font.PLAIN,10));

        timeField = new JtextFieldCustom();
        timeField.setFont(new Font("微软雅黑",Font.PLAIN,10));

        JButton upConvertBut = new JButton("↑");
        upConvertBut.setActionCommand("timeToStamp");
        JButton downConvertBut = new JButton("↓");
        downConvertBut.setActionCommand("StampToTime");
        String[] timeUnit = new String[]{"秒","毫秒"};
        final JComboBox<String> jComboBox = new JComboBox<String>(timeUnit);
        jComboBox.setSelectedIndex(0);
        convertSuccess(upConvertBut,jComboBox,mainFrame);
        convertSuccess(downConvertBut,jComboBox,mainFrame);

        JButton cleanTimestampFieldBut = new JButton("重置");
        JButton cleanTimesFieldBut = new JButton("重置");
        cleanTimestampFieldBut.setActionCommand("cleanTimestampFieldBut");
        cleanTimesFieldBut.setActionCommand("cleanTimesFieldBut");
        cleanSuccess(cleanTimestampFieldBut);
        cleanSuccess(cleanTimesFieldBut);

        subCunrrentTimePanel_1.add(currentTimeLabel);
        subCunrrentTimePanel_1.add(copyBut_1);
        subCunrrentTimePanel_2.add(currentTimeStampLabel);
        subCunrrentTimePanel_2.add(copyBut_2);
        cunrrentTimePanel.add(subCunrrentTimePanel_1);
        cunrrentTimePanel.add(subCunrrentTimePanel_2);

        cleanFirstPanel.add(cleanTimestampFieldBut,BorderLayout.WEST);
        cleanSecondPanel.add(cleanTimesFieldBut,BorderLayout.WEST);

        subTimeConvertPanel_1.add(timestampTextLabel);
        subTimeConvertPanel_1.add(timestampField);
        subTimeConvertPanel_1.add(cleanFirstPanel);

        subTimeConvertPanel_2.add(upConvertBut);
        subTimeConvertPanel_2.add(downConvertBut);
        subTimeConvertPanel_2.add(jComboBox);

        subTimeConvertPanel_3.add(timeTextLabel);
        subTimeConvertPanel_3.add(timeField);
        subTimeConvertPanel_3.add(cleanSecondPanel);


        timeConvertPanel.add(subTimeConvertPanel_1);
        timeConvertPanel.add(subTimeConvertPanel_2);
        timeConvertPanel.add(subTimeConvertPanel_3);

        timeContentPanel.add(cunrrentTimePanel);
        timeContentPanel.add(timeConvertPanel);
    }

    /**
     * 重置监听方法
     */
    public void cleanSuccess(JButton but){
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getActionCommand().equals("cleanTimestampFieldBut")){
                    timestampField.setText("");
                }else if(actionEvent.getActionCommand().equals("cleanTimesFieldBut")){
                    timeField.setText("");
                }
            }
        });

    }

    /**
     * GUI展示获取动态的时间
     * @return
     */
    public JLabel getCurrentTimeLabel(){
        if(timeLabel == null){
            timeLabel = new JLabel("");
            timeLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
            timeLabel.setForeground(new Color(154, 182, 248));
            time = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timeLabel.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                }
            });
            time.start();
        }
        return timeLabel;
    }

    /**
     * GUI展示获取动态的时间戳
     * @return
     */
    public JLabel getCurrentTimeStampLabel(){
        if(timeStampLabel == null){
            timeStampLabel = new JLabel("");
            timeStampLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
            timeStampLabel.setForeground(new Color(154, 182, 248));
            time = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timeStampLabel.setText(new Date().getTime() + "");
                }
            });
            time.start();
        }
        return timeStampLabel;
    }

    /**
     * 复制成功后的对话框提示方法
     * @param but 传入的不同复制按钮组件
     */
    public void copySuccessDialog(JButton but, final Component mainFrame){
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(e.getActionCommand() == "first_copy"){
                    tempCopyTimeContent = currentTimeLabel.getText();
                    StringSelection stringSelection = new StringSelection(tempCopyTimeContent);
                    clipboard.setContents(stringSelection,null);
                    JOptionPane.showMessageDialog(mainFrame, UiConstants.COPY_SUCCESS_MESSAGE,UiConstants.MESSAGE_TITLE,JOptionPane.INFORMATION_MESSAGE);
                }else if(e.getActionCommand() == "second_copy"){
                    tempCopyTimeStampContent = currentTimeStampLabel.getText();
                    StringSelection stringSelection = new StringSelection(tempCopyTimeStampContent);
                    clipboard.setContents(stringSelection,null);
                    JOptionPane.showMessageDialog(mainFrame, UiConstants.COPY_SUCCESS_MESSAGE,UiConstants.MESSAGE_TITLE,JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    public void convertSuccess(final JButton convertBut, final JComboBox jComboBox,final Component mainFrame){
        convertBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(convertBut.getActionCommand().equals("timeToStamp")){
                    String timeFieldContent = timeField.getText();
                    if(!timeFieldContent.equals("")){
                        String stamp = timeToStamp(timeFieldContent,jComboBox.getSelectedIndex());
                        timestampField.setText(stamp);
                    }else{
                        JOptionPane.showMessageDialog(mainFrame,UiConstants.TIME_FIELD_NOT_NULL_MESSAGE,UiConstants.MESSAGE_TITLE,JOptionPane.INFORMATION_MESSAGE);
                    }
                }else if (convertBut.getActionCommand().equals("StampToTime")){
                    String timeStampFieldContent = timestampField.getText();
                    if(!timeStampFieldContent.equals("")){
                        String time = stampToTime(timeStampFieldContent,jComboBox.getSelectedIndex());
                        timeField.setText(time);
                    }else {
                        JOptionPane.showMessageDialog(mainFrame,UiConstants.TIMESTAMP_FIELD_NOT_NULL_MESSAGE,UiConstants.MESSAGE_TITLE,JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
    }

    /**
     * timeToStamp
     * 时间转换为时间戳
     * @param time
     * @return
     */
    public static String timeToStamp(String time,int timeUnit){
        String timeStamp = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if(!time.equals("")&&isValidTimeFormat(time)){
            if(timeUnit == UiConstants.MILLISECOND){
                try {
                    long timeStampLong = dateFormat.parse(time).getTime();
                    timeStamp = Long.toString(timeStampLong);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else if (timeUnit == UiConstants.TIME_SECOND){
                try {
                    long timeStampLong = dateFormat.parse(time).getTime();
                    timeStamp = Long.toString(timeStampLong/1000);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return timeStamp;
    }

    /**
     * stampToTime
     * 时间戳转换为时间
     * @param stamp 时间戳
     * @param timeUnit 秒 还是 毫秒
     * @return
     */
    public static String stampToTime(String stamp,int timeUnit){
        long stampLog = Long.valueOf(stamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = "";
        if(!stamp.equals("")){
            if(timeUnit == UiConstants.TIME_SECOND){
                time = dateFormat.format(new Date(stampLog*1000));
            }else if(timeUnit == UiConstants.MILLISECOND){
                time = dateFormat.format(new Date(stampLog));
            }
        }
        return time;
    }

    /**
     * isValidTimeFormat
     * 判断日期时间格式是否符合指定的规范
     * @param time
     * @return
     */
    public static boolean isValidTimeFormat(String time){
        boolean validDateFlag = true;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            dateFormat.parse(time);
        } catch (ParseException e) {
//            e.printStackTrace();
            validDateFlag = false;
        }
        return validDateFlag;
    }
}
