package com.hjw.keytools;




import com.hjw.keytools.ui.frame.MainFrame;
import java.awt.*;

public class App{
    public static MainFrame mainFrame;
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainFrame = new MainFrame();

            }
        });
    }
}
