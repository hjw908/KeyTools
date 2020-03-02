package com.hjw.keytools.ui.frame;

import com.hjw.keytools.ui.UiConstants;
import com.hjw.keytools.utils.ConsoleTextArea;
import com.hjw.keytools.utils.FileUtils;
import com.hjw.keytools.utils.HttpClientUtil;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PixelGenerationPanel {
    private static PixelGenerationPanel pixelGenerationPanel;
    JTextField colorShowField;
    MainFrame mainFrame;

    private JPanel pixelContentPanel;
    private JPanel pixelIndexPanel;
    private JPanel imageFormatPanel;
    private JPanel colorPalettePanel;
    private JPanel textPanel;
    private JPanel picPathPanel;
    private JPanel allSubPanel;
    ColorPickerWindow colorPickerWindow;

    JTextField firstPixelField;
    JTextField secondPixelField;

    JTextField showTextField;
    JTextField picPathField;

    String suffix = "png";

    private PixelGenerationPanel(MainFrame mainFrame) throws Exception {
        this.mainFrame = mainFrame;
        init();
    }

    public static PixelGenerationPanel getInstance(MainFrame mainFrame) throws Exception {
        if (pixelGenerationPanel == null) {
            pixelGenerationPanel = new PixelGenerationPanel(mainFrame);
        }
        return pixelGenerationPanel;
    }

    private void init() throws IOException {
        pixelContentPanel = new JPanel(new BorderLayout());
        pixelIndexPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imageFormatPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel colorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        allSubPanel = new JPanel(new GridLayout(7, 1, 10, 10));//Vgap这里可以调节所有组件间距,rows 7条件控制台的垂直位置

        JLabel pixeLabel = new JLabel("像素： ");
        setJpanelFont(pixeLabel);
        firstPixelField = new JTextField(10);
        firstPixelField.setHorizontalAlignment(JTextField.CENTER);//设置文本域内容居中
        secondPixelField = new JTextField(10);
        secondPixelField.setHorizontalAlignment(JTextField.CENTER);
        JLabel x_IconLabel = new JLabel(new ImageIcon(PixelGenerationPanel.class.getResource("/X_icon.png")));

        JButton receivePic = new JButton("一键获取");
        receivePic.setActionCommand("obtainPic");
        obtainPic(receivePic);

        pixelIndexPanel.add(pixeLabel);
        pixelIndexPanel.add(firstPixelField);
        pixelIndexPanel.add(x_IconLabel);
        pixelIndexPanel.add(secondPixelField);
        pixelIndexPanel.add(receivePic);

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

        imageFormatPanel.add(picFormatLabel);
        imageFormatPanel.add(pngButton);
        imageFormatPanel.add(jpgButton);
        imageFormatPanel.add(jpegButton);
        imageFormatPanel.add(gifButton);


        JLabel showTextLabel = new JLabel("展示的文案: ");
        setJpanelFont(showTextLabel);
        showTextField = new JTextField(30);
        showTextField.setHorizontalAlignment(JTextField.CENTER);

        JButton pickColorBut = new JButton("取色板");
        pickColorBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                colorPickerWindow = new ColorPickerWindow(mainFrame, pixelGenerationPanel);
            }
        });


        textPanel.add(showTextLabel);
        textPanel.add(showTextField);


        colorShowField = new JTextField(30);
        colorShowField.setEditable(false);

        JLabel picColorLabel = new JLabel("图片背景色: ");
        setJpanelFont(picColorLabel);
        colorPanel.add(picColorLabel);
        colorPanel.add(colorShowField);
        colorPanel.add(pickColorBut);

        picPathPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel picPathLabel = new JLabel("图片的路径: ");
        setJpanelFont(picPathLabel);
        picPathField = new JTextField(30);
        JButton openBut = new JButton("打开");
        openFile(openBut);
        picPathPanel.add(picPathLabel);
        picPathPanel.add(picPathField);
        picPathPanel.add(openBut);

        JPanel textAreaJpanel =new JPanel(new FlowLayout(FlowLayout.CENTER));
//        JTextArea jTextArea = new JTextArea(10,75);
        ConsoleTextArea jTextArea = new ConsoleTextArea();
        jTextArea.setLineWrap(true);
        //设置组合边界
        Border titleBorder = BorderFactory.createTitledBorder("Console");
        Border lowerBorder = BorderFactory.createLoweredBevelBorder();

        jTextArea.setText(UiConstants.TERMINAL_HINT + getSystemTime() + "\r\n");
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setBorder(BorderFactory.createCompoundBorder(titleBorder,lowerBorder));
        textAreaJpanel.add(jScrollPane);



        allSubPanel.add(imageFormatPanel);
        allSubPanel.add(textPanel);
        allSubPanel.add(colorPanel);
        allSubPanel.add(picPathPanel);
        allSubPanel.add(pixelIndexPanel);

        JPanel operatorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        operatorPanel.add(allSubPanel);

        pixelContentPanel.add(operatorPanel,BorderLayout.NORTH);
        pixelContentPanel.add(textAreaJpanel,BorderLayout.CENTER);

    }

    public String getSystemTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String s = dateFormat.format(new Date());
        return s;
    }

    public void openFile(JButton but){
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FileUtils.openFileDialog(mainFrame,picPathField);
            }
        });
    }

    public void radioButtonSelect(final JRadioButton jRadioButton) {
        jRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                suffix = jRadioButton.getText().toLowerCase();
            }
        });

    }

    public void obtainPic(JButton but) {
        if (but.getActionCommand().equals("obtainPic")) {
            but.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    String url = "";
                    if (!firstPixelField.getText().equals("") && !secondPixelField.getText().equals("")&& !picPathField.getText().equals("")) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(firstPixelField.getText());
                        sb.append("x");
                        sb.append(secondPixelField.getText());

                        String fileName = sb.toString() + "." + suffix;
                        System.out.println(fileName);
                        System.out.println(colorShowField.getBackground().toString());
                        String rgbHexTemp = ColorPickerWindow.toHex(colorShowField.getBackground().getRed(), colorShowField.getBackground().getGreen(), colorShowField.getBackground().getBlue());
                        String rgbHex = rgbHexTemp.replace("#", "");
                        System.out.println(rgbHex);
                        String picPath = picPathField.getText();


                        if (showTextField.getText().equals("")) {
                            url = "https://via.placeholder.com/" + fileName + "/" + rgbHex + "/";

                        } else {
                            url = "https://via.placeholder.com/" + fileName + "/" + rgbHex + "?text=" + showTextField.getText();
//                            System.out.println(url);
                        }

                        try {
                            HttpClientUtil.getImages(url, fileName,picPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("生成图片失败");
                        }
                    }else {
                        JOptionPane.showMessageDialog(mainFrame, UiConstants.JTEXTFILED_NOT_NULL,UiConstants.MESSAGE_TITLE,JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });
        }
    }


    public JPanel getPixelContentPanel() {
        return pixelContentPanel;
    }


    public void setJpanelFont(JLabel jLabel) {
        jLabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
        jLabel.setForeground(new Color(76, 118, 226));

    }


    public static void main(String[] args) {
//        System.out.println(PixelGenerationPanel.class.getResource(""));
//        System.out.println(PixelGenerationPanel.class.getResource("/demo.png"));
//        while (true) {
//            Point mousePoint = MouseInfo.getPointerInfo().getLocation();
//            Robot robot = null;
//            try {
//                robot = new Robot();
//                System.out.println(robot.getPixelColor(mousePoint.x, mousePoint.y));
//                Thread.sleep(1000);
//            } catch (AWTException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }




    }

}
