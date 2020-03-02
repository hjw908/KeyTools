package com.hjw.keytools.ui.frame;


import com.hjw.keytools.ui.UiConstants;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.OptionPaneUI;
import java.awt.*;
import java.awt.datatransfer.Clipboard;

import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class ColorPickerWindow extends JFrame {
    //    private static ColorPickerWindow colorPickerWindow;
//    PixelGenerationPanel pixelGenerationPanel;
    MainFrame mainFrame;
    PixelGenerationPanel pixelGenerationPanel;

    private JPanel mainJpanel;
    int[] rgbArray;

    JTextField greenField;
    JTextField redField;
    JTextField blueField;
    JTextField hexField;

    JTextField showField;

    Clipboard clipboard;

    public ColorPickerWindow(MainFrame mainFrame, PixelGenerationPanel pixelGenerationPanel) throws HeadlessException {
        this.mainFrame = mainFrame;
        this.pixelGenerationPanel = pixelGenerationPanel;
        init();
    }

//    public static ColorPickerWindow getInstance(){
//        if(colorPickerWindow == null){
//            colorPickerWindow = new ColorPickerWindow();
//            return colorPickerWindow;
//        }
//        return colorPickerWindow;
//    }

    public void init() {

        initFrame();

//        add(cololrJPanel);

//        getContentPane().setLayout(null);//一种控件布局方式。只有setLayout(null)的时候才能够对，控件进行自由（按照点位置）进行布局
    }


    public JPanel getMainPanel() {
        JPanel mainPanel = new JPanel();
        JPanel showPanel = new JPanel();
        showPanel.setSize(180, 60);
        ImageIcon tempPickColorImageIcon = new ImageIcon(ColorPickerWindow.class.getResource("/pickColor.png"));
        ImageIcon pickColorimageIcon = changeImageMultiple(tempPickColorImageIcon, 0.25);
        JLabel pickColorPic = new JLabel(pickColorimageIcon);
        showField = new JTextField(30);
//        showField.setBackground(Color.BLUE);
        showField.setEditable(false);
        showPanel.add(pickColorPic);
        showPanel.add(showField);

        JPanel pickPanel = new JPanel();
        JPanel cololrJPanel = new JPanel(new GridLayout(1, 1));
//        cololrJPanel.setBackground(Color.BLUE);
        cololrJPanel.setOpaque(true);


        ImageIcon tempImageIcon = new ImageIcon(ColorPickerWindow.class.getResource("/grb.png"));
        final ImageIcon imageIcon = changeImageMultiple(tempImageIcon, 1);


        JLabel rgbPicLabel = new JLabel(imageIcon);
//        rgbPicLabel.setOpaque(true);
        cololrJPanel.add(rgbPicLabel);


        rgbPicLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                BufferedImage bi = null;
                try {
                    bi = ImageIO.read(new File(ColorPickerWindow.class.getResource("/grb.png").toURI()));

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
//                grbArray =  getColorRGB(mouseEvent.getX(),mouseEvent.getY());
                rgbArray = getRGB(bi, mouseEvent.getX(), mouseEvent.getY());
//
                redField.setText(String.valueOf(rgbArray[0]));
                greenField.setText(String.valueOf(rgbArray[1]));
                blueField.setText(String.valueOf(rgbArray[2]));
                showField.setBackground(new Color(rgbArray[0], rgbArray[1], rgbArray[2]));
                hexField.setText(toHex(rgbArray[0], rgbArray[1], rgbArray[2]));
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });


        JPanel grbIndexJpanel = new JPanel();
        JLabel greenIndex = new JLabel("G:");
        greenField = new JTextField(4);

        JLabel redIndex = new JLabel("R:");
        redField = new JTextField(4);

        final JLabel blueIndex = new JLabel("B:");
        blueField = new JTextField(4);

        JLabel hexLabel = new JLabel("hex:");
        hexField = new JTextField(8);
        grbIndexJpanel.add(redIndex);
        grbIndexJpanel.add(redField);
        grbIndexJpanel.add(greenIndex);
        grbIndexJpanel.add(greenField);
        grbIndexJpanel.add(blueIndex);
        grbIndexJpanel.add(blueField);
//        grbIndexJpanel.add(hexLabel);
//        grbIndexJpanel.add(hexField);

        JPanel butJPanel = new JPanel();
        JButton grbCopyBut = new JButton("grb复制");
        grbCopyBut.setActionCommand("grbCopy");
        JButton hexCopyBut = new JButton("16进制复制");
        hexCopyBut.setActionCommand("hexCopy");
        copySuccessDialog(grbCopyBut);
        copySuccessDialog(hexCopyBut);


        butJPanel.add(grbCopyBut);
        butJPanel.add(hexCopyBut);

        JPanel hexPanel = new JPanel();
        hexPanel.add(hexLabel);
        hexPanel.add(hexField);

        JPanel operationJpanel = new JPanel(new FlowLayout());
        JButton chooseBut = new JButton("选择");
        chooseColor(chooseBut);
        JButton cancelBut = new JButton("取消");
        cancelWindow(cancelBut);
        operationJpanel.add(chooseBut);
        operationJpanel.add(cancelBut);


        JPanel grbAndHexPanel = new JPanel(new GridLayout(4, 1));
        grbAndHexPanel.add(grbIndexJpanel);
        grbAndHexPanel.add(hexPanel);
        grbAndHexPanel.add(butJPanel);
        grbAndHexPanel.add(operationJpanel);


        pickPanel.add(cololrJPanel);
        pickPanel.add(grbAndHexPanel);

//        pickPanel.setOpaque(true);//面板不透明

        mainPanel.add(showPanel);
        mainPanel.add(pickPanel);

//        mainPanel.setOpaque(true);//面板不透明
        return mainPanel;


    }

    public void chooseColor(JButton but) {
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (rgbArray.length == 3) {
                    Color color = new Color(rgbArray[0], rgbArray[1], rgbArray[2]);
                    pixelGenerationPanel.colorShowField.setBackground(color);
                    dispose();
                }
            }
        });
    }

    /**
     * 取消当前的取色板的窗口
     *
     * @param but
     */
    public void cancelWindow(JButton but) {
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }


    /**
     * copySuccessDialog
     * 复制成功的弹窗
     *
     * @param but
     */
    public void copySuccessDialog(JButton but) {
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (actionEvent.getActionCommand().equals("grbCopy")) {
                    if (!isFieldEmpty(redField) && !isFieldEmpty(greenField) && !isFieldEmpty(blueField)) {
                        String rgbText = redField.getText() + "," + greenField.getText() + "," + blueField.getText();
                        StringSelection stringSelection = new StringSelection(rgbText);
                        clipboard.setContents(stringSelection, null);
                        JOptionPane.showMessageDialog(ColorPickerWindow.this, UiConstants.COPY_SUCCESS_MESSAGE, UiConstants.MESSAGE_TITLE, JOptionPane.INFORMATION_MESSAGE);
                    }
                } else if (actionEvent.getActionCommand().equals("hexCopy")) {
                    if (!isFieldEmpty(hexField)) {
                        String hexText = hexField.getText();
                        StringSelection stringSelection = new StringSelection(hexText);
                        clipboard.setContents(stringSelection, null);
                        JOptionPane.showMessageDialog(ColorPickerWindow.this, UiConstants.COPY_SUCCESS_MESSAGE, UiConstants.MESSAGE_TITLE, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });


    }

    public void initFrame() {
        this.setTitle("取色板");
        this.setResizable(false);
        this.setVisible(true);

        //获取屏幕大小
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screnHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        int winWidth = 600;
        int winHeight = 400;

        int winX = (screenWidth - winWidth) / 2;
        int winY = (screnHeight - winHeight) / 2;
        this.setBounds(winX, winY, winWidth, winHeight);

        mainJpanel = getMainPanel();

        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        //设置不透明
//        mainJpanel.setOpaque(true);

        add(mainJpanel);
//        getColorGrb();
    }

    /**
     * 判断是否文本域为空
     *
     * @param textField
     * @return
     */
    public boolean isFieldEmpty(JTextField textField) {
        boolean flag = false;
        if (textField.getText().equals("")) {
            flag = true;
        }
        return flag;
    }

    /**
     * 将RGB转换为16进制Hex
     *
     * @param r red颜色分量
     * @param g green颜色分量
     * @param b blue颜色分量
     * @return
     */
    public static String toHex(int r, int g, int b) {
        String hexStr = "#" + toHexValue(r) + toHexValue(g) + toHexValue(b);
        return hexStr;
    }

    public static String toHexValue(int num) {
        StringBuilder sb = new StringBuilder(Integer.toHexString(num & 0xff));//如果不进行&0xff，那么当一个byte会转换成int时，由于int是32位，而byte只有8位这时会进行补位,和0xff相与后，高24比特就会被清0了，结果就对了
        while (sb.length() < 2) {
            sb.append("0");
        }
        return sb.toString().toUpperCase();
    }


    /**
     * 获取颜色的三色指标
     * getColorGrb
     *
     * @return
     */
    public int[] getColorRGB(int x, int y) {
        int[] arrRGB = new int[]{0, 0, 0};
        //可以获取鼠标的XY坐标
//            Point mouserPoint = MouseInfo.getPointerInfo().getLocation();
        try {
            Robot robot = new Robot();
            System.out.println(robot.getPixelColor(x, y));
            Color color = robot.getPixelColor(x, y);
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();
            arrRGB = new int[]{red, green, blue};

            System.out.println(color.getRGB());
//            Color color = robot.getPixelColor(mouserPoint.x,mouserPoint.y);
            return arrRGB;
        } catch (AWTException e) {
            e.printStackTrace();
        }

        return arrRGB;
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

    /**
     * 取得图像上指定位置像素的 rgb 颜色分量。
     * image.getRGB(x, y)返回的是int 32 位 ，在Java中每个RGB像素所占的位数为8.
     * 分别获得原数据的AB、CD、EF不同位置的数据
     * 分别对应RGB三色中的红色R为AB，绿色G为CD，蓝色B为EF
     *
     * @param image 源图像。
     * @param x     图像上指定像素位置的 x 坐标。
     * @param y     图像上指定像素位置的 y 坐标。
     * @return 返回包含 rgb 颜色分量值的数组。元素 index 由小到大分别对应 r，g，b。
     */
    public static int[] getRGB(BufferedImage image, int x, int y) {
        int[] rgb = null;

        if (image != null && x < image.getWidth() && y < image.getHeight()) {
            rgb = new int[3];
            int pixel = image.getRGB(x, y);
            rgb[0] = (pixel & 0xff0000) >> 16;
            rgb[1] = (pixel & 0xff00) >> 8;
            rgb[2] = (pixel & 0xff);
        }

        return rgb;
    }


    public static void main(String[] args) {
//        ColorPickerWindow.getColorGrb();
//        System.out.println(ColorPickerWindow.class.getResource("/"));
        String s = ColorPickerWindow.toHex(300, 300, 300);
        System.out.println(s);

    }

//    @Override
//    public void run() {
//        timer.schedule(new MyTimerTask(), 0, 10);//每0.1秒就执行定时任务
//    }
//
//    public java.util.Timer timer = new Timer();
//
//    class MyTimerTask extends TimerTask {
//        @Override
//        public void run() {
//            ColorPickerWindow.this.repaint();
////            worldTime++;
//        }
//    }
}
