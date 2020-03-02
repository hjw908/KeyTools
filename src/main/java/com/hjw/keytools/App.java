package com.hjw.keytools;


import com.hjw.keytools.ui.frame.MainFrame;

import javax.swing.*;
import java.awt.*;

public class App {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);

                try {
                    UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                try {
                    MainFrame mainFrame = new MainFrame();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
