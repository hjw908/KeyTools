package com.hjw.keytools.ui.frame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class PersonInfoPanel {
    MainFrame mainFrame;
    private static PersonInfoPanel personInfoPanel;
    public JPanel personInfoContentPanel;

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

//        personInfoContentPanel = new JPanel(new BorderLayout());
        personInfoContentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        personInfoContentPanel.setBorder(BorderFactory.createEmptyBorder(4,4,4,20));
//        personInfoContentPanel.setBounds(10,20,700,600);

//        personInfoContentPanel.setBackground(Color.YELLOW);
//        personInfoContentPanel.setSize(new Dimension(500,600));
//        JPanel j = new JPanel(new BorderLayout());
//        j.setSize(500,500);
//        j.setBackground(Color.CYAN);

        JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
//        jTabbedPane.setBackground(Color.RED);
//        jTabbedPane.setSize(700,500);
//        jTabbedPane.setTabLayoutPolicy(JTabbedPane.LEFT);//设置选项卡标签的布局方式为滚动布局
        jTabbedPane.setTabLayoutPolicy(JTabbedPane.HORIZONTAL);
        jTabbedPane.addTab("身份证正面认证", createIdCardFrontJPaenl());
        jTabbedPane.addTab("身份证反面认证", createIdCardReverseJPaenl());
        jTabbedPane.addTab("行驶证认证", createDriverCardJPanel());
        jTabbedPane.addTab("驾驶证认证", createDrivingCardJPanel());
//        personInfoContentPanel.setSelectedIndex(0);

        // 添加选项卡选中状态改变的监听器
//        jTabbedPane.addChangeListener(new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                System.out.println("当前选中的选项卡: " + jTabbedPane.getSelectedIndex());
//            }
//        });
//        j.add(jTabbedPane,BorderLayout.CENTER);
//        jTabbedPane.setBounds(0,100,500,500);
        JScrollPane tabScrolPane = new JScrollPane(jTabbedPane);
//        tabScrolPane.setSize(600,900);
//        personInfoContentPanel.add(tabScrolPane,BorderLayout.WEST);
        personInfoContentPanel.add(tabScrolPane);
    }


    /**
     * 身份证正面操作面板
     *
     * @return
     */
    public Component createIdCardFrontJPaenl() {
        JPanel idCardPanel = new JPanel(new BorderLayout(5,15));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        ImageIcon imageIcon = new ImageIcon(PersonInfoPanel.class.getResource("/idcard_z.png"));
        JLabel imgjLabel = new JLabel(imageIcon);


        JLabel dateStartLabel = new JLabel("身份证有效日期开始: ");
        JTextField startjTextField = new JTextField(15);
        setJpanelFont(dateStartLabel);

        JLabel dateEndLabel = new JLabel("身份证有效日期截止: ");
        JTextField endjTextField = new JTextField(15);
        setJpanelFont(dateEndLabel);

        JLabel policeStationLabel = new JLabel("签发机构名称: ");
        JTextField policeStationField = new JTextField(15);
        setJpanelFont(policeStationLabel);

        topPanel.add(policeStationLabel);
        topPanel.add(policeStationField);
        topPanel.add(dateStartLabel);
        topPanel.add(startjTextField);
        topPanel.add(dateEndLabel);
        topPanel.add(endjTextField);

        JButton generateBut = new JButton("一键生成");
        JButton clearBut = new JButton("重置");
        bottomPanel.add(generateBut);
        bottomPanel.add(clearBut);

        idCardPanel.add(topPanel,BorderLayout.NORTH);
        idCardPanel.add(imgjLabel,BorderLayout.CENTER);
        idCardPanel.add(bottomPanel,BorderLayout.SOUTH);
        return idCardPanel;
    }

    /**
     * 身份证反面操作面板
     *
     * @return
     */
    public Component createIdCardReverseJPaenl() {
        JPanel idCardPanel = new JPanel(new BorderLayout(5,15));
        JPanel topPanel = new JPanel(new GridLayout(3,1));
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

        JLabel jLabel = new JLabel(new ImageIcon(PersonInfoPanel.class.getResource("/idcard_f.png")));

        JButton generateBut = new JButton("一键生成");
        JButton clearBut = new JButton("重置");
        bottomPanel.add(generateBut);
        bottomPanel.add(clearBut);
        bottomPanel.add(generateBut);
        bottomPanel.add(clearBut);

        idCardPanel.add(topPanel,BorderLayout.NORTH);
        idCardPanel.add(jLabel,BorderLayout.CENTER);
        idCardPanel.add(bottomPanel,BorderLayout.SOUTH);
        return idCardPanel;
    }


    /**
     * 驾驶证的操作面板
     *
     * @return
     */
    public Component createDriverCardJPanel() {
        JPanel driverCardPanel = new JPanel(new BorderLayout(5,15));

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
        JPanel drivingCardPanel = new JPanel();
        JLabel jLabel = new JLabel("AAAA");
        drivingCardPanel.add(jLabel);
        return drivingCardPanel;
    }



    public void setJpanelFont(JLabel jLabel) {
        jLabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
        jLabel.setForeground(new Color(76, 118, 226));

    }
}
