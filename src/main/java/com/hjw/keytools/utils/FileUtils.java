package com.hjw.keytools.utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FileUtils {

    public static void openFileDialog(Component parent,JTextField picPathField){
        //创建文件选取器
        JFileChooser jFileChooser = new JFileChooser();
        //设置当前文件夹路径
        jFileChooser.setCurrentDirectory(new File("d."));
        //设置文件选择模式，有只选文件夹，文件或者两者都可以选
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //设置是否允许多选
        jFileChooser.setMultiSelectionEnabled(false);

        int result = jFileChooser.showOpenDialog(parent);
        //如果点击确认
        if(result == JFileChooser.APPROVE_OPTION){
            File objFile = jFileChooser.getSelectedFile();
            picPathField.setText(objFile.getAbsolutePath());
            System.out.println(objFile.getAbsolutePath());
        }



    }

    public static void main(String[] args) {

    }
}
