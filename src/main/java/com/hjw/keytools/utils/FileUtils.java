package com.hjw.keytools.utils;

import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;

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

    public static void saveFileDialog(Component parent,JTextArea jTextArea){
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("e."));
        jFileChooser.setSelectedFile(new File("my.json"));
        int result = jFileChooser.showSaveDialog(parent);
        if(result == JFileChooser.APPROVE_OPTION){
            File file = jFileChooser.getSelectedFile();
            String text = jTextArea.getText();
            System.out.println(file.getAbsolutePath());

            try {
                OutputStream out = new FileOutputStream(file);
                out.write(text.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeTempFile(String content){
        URL url = FileUtils.class.getResource("/tempFile.txt");
        File file = new File(url.getFile());
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes("UTF-8"));
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(FileUtils.class.getResource("/tempFile.txt"));;
    }
}
