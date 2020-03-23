package com.hjw.keytools.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class EncryptionUtil {


    /**
     * AES可以使用128、192、和256位密钥，并且用128位分组加密和解密数据，相对来说安全很多
     * <p>
     * * 加密
     * * 1.构造密钥生成器
     * * 2.根据ecnodeRules规则初始化密钥生成器
     * * 3.产生密钥
     * * 4.创建和初始化密码器
     * * 5.内容加密
     * * 6.返回字符串
     *
     * @return
     */
    public static String AESEnCode(String encodeRules, String content) {
        String AES_encode = "";
        try {
            //构造密钥生成器，指定为AES算法
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            //生成一个128位的随机源,根据传入的字节数组

            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(encodeRules.getBytes("UTF-8"));
            keyGen.init(128, secureRandom);


//            keyGen.init(128);
            //产生原始对称密钥
            SecretKey original_key = keyGen.generateKey();
            //获得原始对称密钥的字节数组
            byte[] raw = original_key.getEncoded();
            //根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            //根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");


            //初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] content_byte = content.getBytes("UTF-8");
            //根据密码器的初始化方式--加密：将数据加密
            byte[] AES_byte = cipher.doFinal(content_byte);
            //将加密后的数据转换为字符串
            //这里用Base64Encoder中会找不到包
            //解决办法：
            //在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了
            AES_encode = new String(Base64.encodeBase64(AES_byte));
            return AES_encode;

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return AES_encode;
    }


    public static String AESDnCode(String encodeRules, String content) {
        String AES_decode = "";
        try {
            //以下7步同加密过程的7步
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//            keyGen.init(128,new SecureRandom(encodeRules.getBytes()));

            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");//签名算法
            secureRandom.setSeed(encodeRules.getBytes("UTF-8"));
            keyGen.init(128, secureRandom);

//            keyGen.init(128);
            SecretKey origin_key = keyGen.generateKey();
            byte[] raw = origin_key.getEncoded();
            SecretKey key = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");


            cipher.init(Cipher.DECRYPT_MODE, key);
            //将加密并编码后的内容解码成字节数组
//            byte[] content_byte = Base64.decodeBase64(content.getBytes("UTF-8"));
            byte[] content_byte = Base64.decodeBase64(content);
            //解密
            byte[] decode_byte = cipher.doFinal(content_byte);
            AES_decode = new String(decode_byte, "UTF-8");
            return AES_decode;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return AES_decode;
    }

    public static void main(String[] args) {
        String s = EncryptionUtil.AESEnCode("111", "hjw");
        System.out.println("加密： " + s);
        String ss = EncryptionUtil.AESDnCode("111", s);
        System.out.println("解密： " + ss);
    }
}
