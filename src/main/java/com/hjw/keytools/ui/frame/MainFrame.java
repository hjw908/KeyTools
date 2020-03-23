package com.hjw.keytools.ui.frame;

import com.hjw.keytools.ui.UiConstants;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainFrame extends JFrame {
    JList<String> jList;
    JPanel contentJpanel;
    JSplitPane jSplitPane;
    JPanel contentLayoutPanel;

    JPanel timeContentPanel;
    TimeStampConvertPanel timeStampConvertPanel;
    JPanel testPanel_blue;
    PixelGenerationPanel pixelGenerationPanel;
    JPanel pixelContentPanel;
    QRCodePanel qrCodePanel;
    JPanel qrCodeContentPanel;
    JSONFormatJPanel jsonFormatJPanel;
    JPanel jsonformatContentPanel;
    PersonInfoPanel personInfoPanel;
    JPanel personInfoContentPanel;
    EncryptionJpanel encryptionJpanel;
    JPanel encryptionContentPanel;

    public MainFrame() throws Exception {
        super();
        initFrame();
    }

    private void initFrame() throws Exception {
        contentLayoutPanel = getContentLayoutPanel();
        createMenu();
        add(contentLayoutPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("keyTools");
        setIconImage(new ImageIcon(MainFrame.class.getResource("/keytool_icon.png")).getImage());
        setSize(new Dimension(1100, 602));//675
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JPanel getContentLayoutPanel() throws Exception {
        jList = new JList<String>(UiConstants.FUN_NAME);
        jList.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList.setSelectedIndex(0);

        //监听左侧list的切换页面动作
        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                switch (jList.getSelectedIndex()) {
                    case UiConstants.FUN_0: {
                        contentJpanel.removeAll();
                        contentJpanel.add(pixelContentPanel);
                        repaint();
                        revalidate();
                        break;
                    }
                    case UiConstants.FUN_1: {
                        contentJpanel.removeAll();
                        contentJpanel.add(timeContentPanel);
                        repaint();
                        revalidate();
                        break;

                    }
                    case UiConstants.FUN_2: {
                        contentJpanel.removeAll();
                        contentJpanel.add(qrCodeContentPanel);
                        repaint();
                        revalidate();
                        break;
                    }
                    case UiConstants.FUN_3: {
                        contentJpanel.removeAll();
                        contentJpanel.add(jsonformatContentPanel);
                        repaint();
                        revalidate();
                        break;
                    }
                    case UiConstants.FUN_4: {
                        contentJpanel.removeAll();
                        contentJpanel.add(personInfoContentPanel);
                        repaint();
                        revalidate();
                        break;
                    }

                    case UiConstants.FUN_5: {
                        contentJpanel.removeAll();
                        contentJpanel.add(encryptionContentPanel);
                        repaint();
                        revalidate();
                        break;
                    }

                    case UiConstants.FUN_6: {
                        break;
                    }


                }
            }
        });

        JScrollPane jScrollPane = new JScrollPane(jList);
        jScrollPane.setBorder(new TitleBorder("function section"));
        jScrollPane.setMinimumSize(new Dimension(120, 80));

        contentJpanel = new JPanel();

        //测试面板
        testPanel_blue = new JPanel();
        testPanel_blue.setBackground(Color.BLUE);
//        testPanel_yellow = new JPanel();
//        testPanel_yellow.setBackground(Color.yellow);

//        pixelGenerationPanel = PixelGenerationPanel.

        pixelGenerationPanel = PixelGenerationPanel.getInstance(MainFrame.this);
        pixelContentPanel = pixelGenerationPanel.getPixelContentPanel();

        timeStampConvertPanel = TimeStampConvertPanel.getInstance(MainFrame.this);
        timeContentPanel = timeStampConvertPanel.getTimeContentPanel();

        qrCodePanel = QRCodePanel.getInstance(MainFrame.this);
        qrCodeContentPanel = qrCodePanel.getQRCodeContentPanel();

        jsonFormatJPanel = JSONFormatJPanel.getInstance(this);
        jsonformatContentPanel = jsonFormatJPanel.getJsonFormatcontenJPanel();

        personInfoPanel = PersonInfoPanel.getInstance(this);
        personInfoContentPanel = personInfoPanel.getPersonInfoContentPanel();

        encryptionJpanel = EncryptionJpanel.getInstance(this);
        encryptionContentPanel = encryptionJpanel.getEncryptionPanel();


        //首次运行程序的时候，contentPanel默认展示第一个list的内容
        if (jList.getSelectedIndex() == 0) {
            contentJpanel.add(pixelContentPanel);
        }

        jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, jScrollPane, contentJpanel);
        //是否打开快速折叠
        jSplitPane.setOneTouchExpandable(true);
        //设置快速折叠按钮大小
        jSplitPane.setDividerSize(5);

        contentLayoutPanel = new JPanel(new BorderLayout());
        contentLayoutPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        contentLayoutPanel.add(jSplitPane, BorderLayout.CENTER);

        return contentLayoutPanel;
    }


    public void createMenu() {
        //创建一个菜单栏
        JMenuBar jMenuBar = new JMenuBar();
        //创建一级菜单
        JMenu fileMenu = new JMenu("file");
        JMenu editMenu = new JMenu("edit");
        JMenu viewMenu = new JMenu("view");
        JMenu aboutMenu = new JMenu("about");

        jMenuBar.add(fileMenu);
        jMenuBar.add(editMenu);
        jMenuBar.add(viewMenu);
        jMenuBar.add(aboutMenu);

        JMenuItem newMenuItem = new JMenuItem("new");
        JMenuItem openMenuItem = new JMenuItem("open");
        JMenuItem saveMenuItem = new JMenuItem("save");
        JMenuItem exitMenuItem = new JMenuItem("exit");

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        //分割线
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        setJMenuBar(jMenuBar);
    }
}
