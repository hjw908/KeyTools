package com.hjw.keytools.ui.frame;

import com.hjw.keytools.ui.UiConstants;
import com.hjw.keytools.ui.listener.JTextFieldHintListener;
import com.hjw.keytools.ui.listener.MyDocumentListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class PersonInfoPanel {
    MainFrame mainFrame;
    private static PersonInfoPanel personInfoPanel;
    public JPanel personInfoContentPanel;

    JTextField policeStationField;
    JTextField startjTextField;
    JTextField endjTextField;

    private PersonInfoPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        init();
    }

    public static PersonInfoPanel getInstance(MainFrame mainFrame) {
        if (personInfoPanel == null) {
            personInfoPanel = new PersonInfoPanel(mainFrame);
            return personInfoPanel;
        }
        return personInfoPanel;
    }

    public JPanel getPersonInfoContentPanel() {
        return personInfoContentPanel;
    }


    public void init() {
        personInfoContentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        personInfoContentPanel.setBorder(BorderFactory.createEmptyBorder(1, 4, 1, 4));

        JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
        jTabbedPane.setTabLayoutPolicy(JTabbedPane.HORIZONTAL);
        jTabbedPane.addTab("身份证正面认证", createIdCardFrontJPaenl());
        jTabbedPane.addTab("身份证反面认证", createIdCardReverseJPanel());
        jTabbedPane.addTab("驾驶证认证", createDrivingCardJPanel());
        jTabbedPane.addTab("行驶证认证", createDriverCardJPanel());

        JScrollPane tabScrolPane = new JScrollPane(jTabbedPane);

        personInfoContentPanel.add(tabScrolPane);
    }


    /**
     * 身份证正面操作面板
     *
     * @return
     */
    public Component createIdCardFrontJPaenl() {
        JPanel idCardPanel = new JPanel(new BorderLayout(5, 6));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLayeredPane jLayeredPane = new JLayeredPane();//层级面板，用于重叠面板

        ImageIcon imageIcon = new ImageIcon(PersonInfoPanel.class.getResource("/idcard_z2.png"));
        JPanel picJpanel = new JPanel(new BorderLayout());
        picJpanel.setBounds(150, 10, 600, 400);//层级面板一定要设置坐标位置和长度。不然不会展示
        picJpanel.setOpaque(true);
//        picJpanel.setBackground(Color.cyan);

        JLabel imgjLabel = new JLabel(imageIcon);
        picJpanel.add(imgjLabel, BorderLayout.CENTER);

        JLabel policeStation_tra_label = new JLabel();
        JPanel police_transparentJPanel = createPanel( 400, 260, 180, 23);
        police_transparentJPanel.add(policeStation_tra_label);

        JLabel date_start_tra_label = new JLabel();
        JPanel date_start_transparentJ_Panel = createPanel( 400, 290, 90, 23);
        date_start_transparentJ_Panel.add(date_start_tra_label);

        JLabel date_end_tra_label = new JLabel();
        JPanel date_end_transparentJ_Panel = createPanel( 510, 290, 90, 23);
        date_end_transparentJ_Panel.add(date_end_tra_label);

        jLayeredPane.add(picJpanel, Integer.valueOf(100));
        jLayeredPane.add(police_transparentJPanel, Integer.valueOf(200));
        jLayeredPane.add(date_start_transparentJ_Panel, Integer.valueOf(200));
        jLayeredPane.add(date_end_transparentJ_Panel, Integer.valueOf(200));
//        jLayeredPane.add(transparentJpanel,Integer.valueOf(1));

        JLabel dateStartLabel = new JLabel("身份证有效日期开始: ");
        startjTextField = new JTextField(15);
        setJpanelFont(dateStartLabel);
        startjTextField.addFocusListener(new JTextFieldHintListener(startjTextField, UiConstants.HINT_STARTDATE_TEXT));
        startjTextField.getDocument().addDocumentListener(new MyDocumentListener(date_start_tra_label, startjTextField.getText()));

        JLabel dateEndLabel = new JLabel("身份证有效日期截止: ");
        endjTextField = new JTextField(15);
        setJpanelFont(dateEndLabel);
        endjTextField.addFocusListener(new JTextFieldHintListener(endjTextField, UiConstants.HINT_ENDDATE_TEXT));
        endjTextField.getDocument().addDocumentListener(new MyDocumentListener(date_end_tra_label, endjTextField.getText()));

        JLabel policeStationLabel = new JLabel("签发机构名称: ");
        policeStationField = new JTextField(15);
        policeStationField.getDocument().addDocumentListener(new MyDocumentListener(policeStation_tra_label
                , policeStationField.getText()));//监听文本域，动态的改变jlabel的内容

        setJpanelFont(policeStationLabel);

        topPanel.add(policeStationLabel);
        topPanel.add(policeStationField);
        topPanel.add(dateStartLabel);
        topPanel.add(startjTextField);
        topPanel.add(dateEndLabel);
        topPanel.add(endjTextField);

        JButton generateBut = new JButton("一键生成");
        generateFunction(generateBut);
        JButton clearBut = new JButton("重置");
        clearFunction(clearBut);
        bottomPanel.add(generateBut);
        bottomPanel.add(clearBut);

        idCardPanel.add(topPanel, BorderLayout.NORTH);
        idCardPanel.add(jLayeredPane, BorderLayout.CENTER);
        idCardPanel.add(bottomPanel, BorderLayout.SOUTH);
        return idCardPanel;
    }

    public JPanel createPanel( int x, int y, int width, int height) {
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jPanel.setBounds(x, y, width, height);
        jPanel.setOpaque(false);
//        jPanel.setBackground(color);
        return jPanel;
    }

    /**
     * 身份证反面操作面板
     *
     * @return
     */
    public Component createIdCardReverseJPanel() {
        JPanel idCardPanel = new JPanel(new BorderLayout(5, 15));
        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        JPanel topPanel_1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel topPanel_2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel topPanel_3 = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel nameLabel = new JLabel("姓名: ");
        setJpanelFont(nameLabel);
        JTextField nameField = new JTextField(5);
        JLabel sexLabel = new JLabel("性别: ");
        setJpanelFont(sexLabel);
        JTextField sexField = new JTextField(5);
        JLabel nationLabel = new JLabel("民族: ");
        setJpanelFont(nationLabel);
        JTextField nationField = new JTextField(5);
        JLabel birthJLabel = new JLabel("出生年月:");
        setJpanelFont(birthJLabel);
        JTextField birthField = new JTextField(5);
        topPanel_1.add(nameLabel);
        topPanel_1.add(nameField);
        topPanel_1.add(sexLabel);
        topPanel_1.add(sexField);
        topPanel_1.add(nationLabel);
        topPanel_1.add(nationField);
        topPanel_1.add(birthJLabel);
        topPanel_1.add(birthField);

        JLabel addressLabel = new JLabel("公民所在住址: ");
        setJpanelFont(addressLabel);
        JTextField addressField = new JTextField(30);
        topPanel_2.add(addressLabel);
        topPanel_2.add(addressField);

        JLabel idLabel = new JLabel("公民身份证号码: ");
        setJpanelFont(idLabel);
        JTextField idField = new JTextField(30);
        topPanel_3.add(idLabel);
        topPanel_3.add(idField);

        topPanel.add(topPanel_1);
        topPanel.add(topPanel_2);
        topPanel.add(topPanel_3);

        JLabel jLabel = new JLabel(new ImageIcon(PersonInfoPanel.class.getResource("/idcard_f3.png")));

        JButton generateBut = new JButton("一键生成");
        JButton clearBut = new JButton("重置");
        bottomPanel.add(generateBut);
        bottomPanel.add(clearBut);
        bottomPanel.add(generateBut);
        bottomPanel.add(clearBut);

        idCardPanel.add(topPanel, BorderLayout.NORTH);
        idCardPanel.add(jLabel, BorderLayout.CENTER);
        idCardPanel.add(bottomPanel, BorderLayout.SOUTH);
        return idCardPanel;
    }


    /**
     * 驾驶证的操作面板
     *
     * @return
     */
    public Component createDriverCardJPanel() {
        JPanel driverCardPanel = new JPanel(new BorderLayout(5, 15));

        JLabel jLabel = new JLabel("CCCC");
        driverCardPanel.add(jLabel);
        return driverCardPanel;
    }

    /**
     * 行驶证的操作面板
     *
     * @return
     */
    public Component createDrivingCardJPanel() {
        JPanel drivingCardPanel = new JPanel(new BorderLayout(5, 10));
        JLabel nameLabel = new JLabel("姓名: ");
        setJpanelFont(nameLabel);
        JTextField nameField = new JTextField(10);

        JLabel idLabel = new JLabel("证件号: ");
        setJpanelFont(idLabel);
        JTextField idField = new JTextField(30);

        JLabel vaildPeriodLabel = new JLabel("有效期限: ");
        setJpanelFont(vaildPeriodLabel);
        JTextField startDateField = new JTextField(15);
        JTextField endDateField = new JTextField(15);
        JLabel zhiLabel = new JLabel("至");
        setJpanelFont(zhiLabel);

        JPanel topPanel_1 = new JPanel();
        topPanel_1.add(nameLabel);
        topPanel_1.add(nameField);
        topPanel_1.add(idLabel);
        topPanel_1.add(idField);

        JPanel topPanel_2 = new JPanel();
        topPanel_2.add(vaildPeriodLabel);
        topPanel_2.add(startDateField);
        topPanel_2.add(zhiLabel);
        topPanel_2.add(endDateField);

        JPanel topPanel = new JPanel(new GridLayout(2, 1, 2, 2));
        topPanel.add(topPanel_1);
        topPanel.add(topPanel_2);

        ImageIcon imageIcon = changeImageMultiple(new ImageIcon(PersonInfoPanel.class.getResource("/driver_pic.png")), 1);
        JLabel imgLabel = new JLabel(imageIcon);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton generateBut = new JButton("一键生成");
        JButton clearBut = new JButton("重置");
        bottomPanel.add(generateBut);
        bottomPanel.add(clearBut);
        bottomPanel.add(generateBut);
        bottomPanel.add(clearBut);

        drivingCardPanel.add(topPanel, BorderLayout.NORTH);
        drivingCardPanel.add(imgLabel, BorderLayout.CENTER);
        drivingCardPanel.add(bottomPanel, BorderLayout.SOUTH);
        return drivingCardPanel;
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

    public void setJpanelFont(JLabel jLabel) {
        jLabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
        jLabel.setForeground(new Color(76, 118, 226));

    }

    public void clearFunction(JButton but) {
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                policeStationField.setText("");
                startjTextField.setText(UiConstants.HINT_STARTDATE_TEXT);
                startjTextField.setForeground(Color.gray);
                endjTextField.setText(UiConstants.HINT_ENDDATE_TEXT);
                endjTextField.setForeground(Color.gray);
            }
        });
    }

    public void generateFunction(JButton but){
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Random random = new Random();
                int random_num = random.nextInt(8);
                String[] policeArray =UiConstants.POLICE_STATION;
                policeStationField.setText(policeArray[random_num]);

                SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
                Date startDate = randomDate();
                String startDateStr = format.format(startDate);
                String endDateStr = dateDelay(format.format(startDate),5,7);
                startjTextField.setText(startDateStr);
                endjTextField.setText(endDateStr);
                startjTextField.setForeground(Color.BLACK);
                endjTextField.setForeground(Color.BLACK);


            }
        });
    }

    public String dateDelay(String randomDate, int dalayYearNum,int dalayDateNum){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String dateDelay = "";
        try {
            Date date = simpleDateFormat.parse(randomDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.YEAR,dalayYearNum);
            calendar.add(Calendar.MONTH,dalayDateNum);
            dateDelay = simpleDateFormat.format(calendar.getTime());
            return dateDelay;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return randomDate;
    }

    public static Date randomDate(){
        String startDate = "2015.01.01";
        String endDate = "2020.01.31";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date randomDate = null;
        try {
            Date start = simpleDateFormat.parse(startDate);
            Date end = simpleDateFormat.parse(endDate);

            long start_long = start.getTime();
            long end_long = end.getTime();

            if(start_long > end_long){
                return null;
            }

            long random_long = random(start_long,end_long);
            randomDate = new Date(random_long);
            return randomDate;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return randomDate;
    }

    public static long random(long start_long, long end_long){
//        System.out.println(Math.random());
        long dateLong = (long) (start_long +  Math.random()* (end_long-start_long)); //如果不加上start_long，是1970
        return dateLong;
    }

}
