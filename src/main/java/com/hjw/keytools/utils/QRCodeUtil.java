package com.hjw.keytools.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.hjw.keytools.ui.UiConstants;
import com.hjw.keytools.ui.frame.MainFrame;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QRCodeUtil {
    public static byte[] saveQrcodeImage(MainFrame mainFrame, String text, int width , int height, String filePath, String format){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        byte[] byteData = null;
        try {
            BitMatrix bitMatrix =  qrCodeWriter.encode(text, BarcodeFormat.QR_CODE,width,height);
            String absolutePathStr = filePath+ File.separator +"MyQRcode" +"."+format.toLowerCase();
            Path path = FileSystems.getDefault().getPath(absolutePathStr);
            MatrixToImageWriter.writeToPath(bitMatrix,format,path);
            JOptionPane.showMessageDialog(mainFrame,"二维码保存成功", UiConstants.MESSAGE_TITLE,JOptionPane.INFORMATION_MESSAGE);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix,format,byteArrayOutputStream);//二维码数据都放到byteArrayOutputStream里了
            byteData = byteArrayOutputStream.toByteArray();
            return byteData;

        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteData;
    }

    public static byte[] generateImage(String text, int width, int height){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        byte[] byteData = null;
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(text,BarcodeFormat.QR_CODE,width,height);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix,"PNG",byteArrayOutputStream);//二维码数据都放到byteArrayOutputStream里了
            byteData = byteArrayOutputStream.toByteArray();
            return byteData;
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        return byteData;
    }

    public static void main(String[] args) {
//        QRCodeUtil.saveQrcodeImage("hjw",100,100,"D:\\aaa\\hjw.png");
    }
}
