package com.hjw.keytools.utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.*;

public class HttpClientUtil {
    public static CloseableHttpClient httpClient;

    public static CloseableHttpResponse response;



    public static InputStream httpclientGet(String url) throws IOException {
        InputStream inputStream = null;
        //创建默认的客户端
        httpClient = HttpClients.createDefault();

        //创建get请求实例
        HttpGet httpGet = new HttpGet(url);
        //设置头信息
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.100 Safari/537.36");
        httpGet.setHeader("Cookie", "__cfduid=dadc17b7109006f70d6b6af53606ad9dd1582182429; _ga=GA1.2.581510899.1582182461; _gid=GA1.2.1278217550.1582269105");
        httpGet.setHeader("Connection", "keep-alive");

        try {
            //执行get后，响应体
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            Header[] allHeader = response.getAllHeaders();
            System.out.println("statusCode: " + statusCode);
            for (int i = 0; i < allHeader.length; i++) {
                System.out.println("allHeader: " + allHeader[i]);
            }
            //获取消息实体
            HttpEntity httpEntity = response.getEntity();

            if (httpEntity != null) {
//            System.out.println(EntityUtils.toString(httpEntity));
//                EntityUtils.toByteArray(httpEntity);
                inputStream = httpEntity.getContent();
//                byte[] btImg = readStream(inputStream);
                return inputStream;

//            InputStreamReader streamReader = new InputStreamReader(inputStream);
//            int line = 0;
//            line = streamReader.read();
//            while(line != -1){
//                System.out.println((char)line);
//
//                    line = streamReader.read();
//                }
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return inputStream;
    }


    /**
     * 从网上抓取图片内容
     * 对一张图片作抓取
     * 如果有多张图片可以for
     */
    public static void getImages(String url,String fileName,String picPath) throws Exception {

        InputStream inputStream = httpclientGet(url);
        byte[] btImg = readStream(inputStream);

        if (null != btImg && btImg.length > 0) {
            System.out.println("读取到：" + btImg.length + " 字节");
            writeToDisk(btImg, fileName,picPath);
        } else {
            System.err.println("没有从该连接获得内容");
        }
        closeResource();
    }



    /**
     * 将图片写入到磁盘
     *
     * @param img      图片数据流
     * @param fileName 文件保存时的名称
     */
    public static void writeToDisk(byte[] img, String fileName,String picPath) {
        try {
            File file = new File(picPath + File.separator + fileName);
            FileOutputStream fops = new FileOutputStream(file);
            fops.write(img);
            fops.flush();
            fops.close();
            System.out.println("图片已经写入到" + picPath + "下");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 从输入流中获取数据写到内存里
     *
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);

        }
        inStream.close();
        return outStream.toByteArray();
    }

    public static void reptilePic(String url) {
        InputStream inputStream = null;
        HttpEntity entity = null;

        //创建默认的客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建get请求实例
        HttpGet httpGet = new HttpGet(url);
        //设置头信息
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.100 Safari/537.36");
        httpGet.setHeader("Cookie", "__cfduid=dadc17b7109006f70d6b6af53606ad9dd1582182429; _ga=GA1.2.581510899.1582182461; _gid=GA1.2.1278217550.1582269105");
        httpGet.setHeader("Connection", "keep-alive");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            entity = response.getEntity();


            // 获取返回实体
            String content = null;

            content = EntityUtils.toString(entity, "utf-8");
//            System.out.println(content);

            // 解析网页 得到文档对象
            Document doc = Jsoup.parse(content);
            Elements elements = doc.select("img");
            Elements ee = doc.getElementsByAttribute("src");
            for (Element e : ee) {
                String obj = e.attr("src");

                System.out.println("haha: "+obj);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    public static void closeResource() throws IOException {
        if(httpClient!=null){
            httpClient.close();
        }
        if(response != null){
            response.close();
        }

    }

    public static void main(String[] args) throws Exception {
//        HttpClientUtil.httpclientGet("https://via.placeholder.com/780x200");
//        getImages("https://via.placeholder.com/780x200");
//        reptilePic("https://blog.csdn.net/sinat_32238399/article/details/81389899");
    }
}
