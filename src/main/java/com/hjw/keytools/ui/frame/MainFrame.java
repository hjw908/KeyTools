package com.hjw.keytools.ui.frame;

import com.hjw.keytools.ui.UiConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainFrame extends JFrame {
        JList<String> jList;
        JPanel contentJpanel;
        JSplitPane jSplitPane;
        JPanel contentLayoutPanel;
        JPanel subFunContentJpanel;
        JLabel timeLabel;
        JLabel timeStampLabel;
        private Timer time;
        JLabel currentTimeLabel;
        JLabel currentTimeStampLabel;
        public static String tempCopyTimeContent;
        Clipboard clipboard;
//        StringSelection stringSelection;

    public MainFrame(){

        contentLayoutPanel = getContentLayoutPanel();
        createMenu();
        add(contentLayoutPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("keyTools");
        setSize(new Dimension(1130,675));
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public JPanel getContentLayoutPanel(){
        jList = new JList<String>(UiConstants.FUN_NAME);
        jList.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList.setSelectedIndex(0);

        JScrollPane jScrollPane = new JScrollPane(jList);
        jScrollPane.setBorder(new TitleBorder("function section"));
        jScrollPane.setMinimumSize(new Dimension(120,80));


        contentJpanel = new JPanel();
        JPanel timeContentPanel = new JPanel(new GridLayout(2,1,15,15));
        JPanel cunrrentTimePanel = new JPanel(new GridLayout(2,1,15,15));
        JPanel subCunrrentTimePanel_1 = new JPanel();
        JPanel subCunrrentTimePanel_2 = new JPanel();
        JPanel timeConvertPanel = new JPanel(new GridLayout(3,2,15,15));
        JPanel subTimeConvertPanel_1 = new JPanel();


        currentTimeLabel = getCurrentTimeLabel();
        currentTimeStampLabel = getCurrentTimeStampLabel();
        JButton copyBut_1 = new JButton("复制");
        copyBut_1.setActionCommand("copy");
        JButton copyBut_2 = new JButton("复制");
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        copyBut_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand() == "copy"){
                    tempCopyTimeContent = currentTimeLabel.getText();
                    StringSelection stringSelection = new StringSelection(tempCopyTimeContent);
                    clipboard.setContents(stringSelection,null);
                }

                JOptionPane.showMessageDialog(MainFrame.this,"复制成功","消息",JOptionPane.INFORMATION_MESSAGE);
            }
        });

//        copySuccessDialog(copyBut_1);
//        copySuccessDialog(copyBut_2);


        JLabel timestampTextLabel = new JLabel("Unix时间戳（Unix timestamp）");
        timestampTextLabel.setForeground(new Color(106, 100,226));
        timestampTextLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        JLabel timeTextLabel = new JLabel("时间");
        final JTextField timestampField = new JTextField(20);
        timestampField.setFont(new Font(null,Font.PLAIN,10));
        JTextField timeField = new JTextField(8);
        timeField.setFont(new Font(null,Font.PLAIN,10));
        JButton upConvertBut = new JButton("qq");
        JButton upConvertBut2 = new JButton("aa");
        JButton upConvertBut3 = new JButton("bb");

        subCunrrentTimePanel_1.add(currentTimeLabel);
        subCunrrentTimePanel_1.add(copyBut_1);
        subCunrrentTimePanel_2.add(currentTimeStampLabel);
        subCunrrentTimePanel_2.add(copyBut_2);
        cunrrentTimePanel.add(subCunrrentTimePanel_1);
        cunrrentTimePanel.add(subCunrrentTimePanel_2);

        subTimeConvertPanel_1.add(timestampTextLabel);
        subTimeConvertPanel_1.add(timestampField);
        timeConvertPanel.add(subTimeConvertPanel_1);

//        timestampField.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Transferable contents = clipboard.getContents(this);
//                DataFlavor flavor= DataFlavor.stringFlavor;
//                if( contents.isDataFlavorSupported(flavor))
//                {
//                    try
//                    {
//                        String str;
//                        str = (String)contents.getTransferData(flavor);
//                        timestampField.setText(str);
//                    }catch(Exception ex)
//                    {
//                        ex.printStackTrace();
//                    }
//                }
//            }
//        });




        timeContentPanel.add(cunrrentTimePanel);
        timeContentPanel.add(timeConvertPanel);



        contentJpanel.add(timeContentPanel);


        jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,jScrollPane,contentJpanel);
        //是否打开快速折叠
        jSplitPane.setOneTouchExpandable(true);
        //设置快速折叠按钮大小
        jSplitPane.setDividerSize(5);

        contentLayoutPanel = new JPanel(new BorderLayout());
        contentLayoutPanel.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
        contentLayoutPanel.add(jSplitPane,BorderLayout.CENTER);

        return contentLayoutPanel;
    }

    public void copySuccessDialog(JButton but){
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(MainFrame.this,"复制成功","消息",JOptionPane.INFORMATION_MESSAGE);
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

    public void createMenu(){
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

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
                try {
                    UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                MainFrame mainFrame = new MainFrame();
            }
        });
    }
}
