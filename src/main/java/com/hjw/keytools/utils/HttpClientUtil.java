package com.hjw.keytools.utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

    /**
     * 使用HttpClient发送get的请求
     * httpGetMethond
     *
     * @param url
     * @return 返回请求回来的String内容
     * @throws IOException
     */
    public static String httpGetMethond(String url) {
        String respondContent = null;
        CloseableHttpResponse response = null;
        InputStream inputStream = null;
        HttpEntity httpEntity = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            setGetHead(httpGet, getHeaderHashMap());
            RequestConfig config = RequestConfig.custom()
                    //设置连接超时时间(单位毫秒)
                    .setConnectTimeout(5000)
                    //设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(5000)
                    //socket读写超时时间(单位毫秒)
                    .setSocketTimeout(5000)
                    //设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true).build();
            httpGet.setConfig(config);

            response = httpClient.execute(httpGet);

            int statusCode = response.getStatusLine().getStatusCode();
            Header[] allHeader = response.getAllHeaders();
            System.out.println("statusCode: " + statusCode);
            for (int i = 0; i < allHeader.length; i++) {
                System.out.println("allHeader: " + allHeader[i]);
            }
            httpEntity = response.getEntity();
            inputStream = new BufferedInputStream(httpEntity.getContent());
//            inputStream = response.getEntity().getContent();
            byte[] buffer = new byte[2024];
            int currentByteReadCount = 0;
            long totalContactsCount = -1;
            int readContactsCount = 0;
            StringBuilder strbuf = new StringBuilder();
            while ((currentByteReadCount = inputStream.read(buffer)) != -1) {
                String readData = new String(buffer, 0, currentByteReadCount, "UTF-8");
                strbuf.append(readData);
//                if (readData.indexOf("}~{") >= 0) {
//                    readContactsCount++;
//                }
            }
            respondContent = strbuf.toString();
            System.out.println(statusCode);
            System.out.println(respondContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            //EntityUtils.consume()其功能就是关闭HttpEntity是的流，如果手动关闭了InputStream也可以不调用这个方法
            closeResource(httpClient, response, inputStream, httpEntity);
        }
        return respondContent;
    }

    public static void closeResource(CloseableHttpClient httpClient, CloseableHttpResponse response, InputStream inputStream, HttpEntity httpEntity) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (httpEntity != null) {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (response != null) {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public static String httpPostMethod(String url, Map<String, String> headerMap, Map<String, String> paramsMap) {
        String respondContent = null;
        InputStream inputStream = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost();
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(50000)
                .setConnectionRequestTimeout(50000)
                .setSocketTimeout(50000)
                .setRedirectsEnabled(true).build();

        httpPost.setConfig(config);
        setPostHead(httpPost, headerMap);
        setPostParams(httpPost, paramsMap);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
//            inputStream = response.getEntity().getContent();
            inputStream = httpEntity.getContent();
            int i = 0;
            byte[] buffer = new byte[1024];
            StringBuffer sbf = new StringBuffer();
            while ((i = inputStream.read(buffer)) != -1) {
                sbf.append(new String(buffer, 0, i, "UTF-8"));
            }
            respondContent = sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            closeResource(httpClient, response, inputStream,httpEntity);
        }
        return respondContent;
    }

    public static HashMap<String, String> getHeaderHashMap() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        hashMap.put("Accept-Encoding", "gzip, deflate, br");
        hashMap.put("Accept-Language", "zh-CN,zh;q=0.9");
        hashMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.100 Safari/537.36");
        hashMap.put("Cookie", "__cfduid=dadc17b7109006f70d6b6af53606ad9dd1582182429; _ga=GA1.2.581510899.1582182461; _gid=GA1.2.1278217550.1582269105");
        hashMap.put("Connection", "keep-alive");

        return hashMap;
    }

    /**
     * 设置get请求的头信息
     *
     * @param httpGet
     * @param map
     */
    public static void setGetHead(HttpGet httpGet, Map<String, String> map) {
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpGet.addHeader(key, value);
            }
        }
    }

    /**
     * 设置post请求的头信息
     *
     * @param httpPost
     * @param headerMap
     */
    public static void setPostHead(HttpPost httpPost, Map<String, String> headerMap) {
        if (headerMap != null && headerMap.size() > 0) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpPost.setHeader(key, value);
            }
        }
    }

    public static void setPostParams(HttpPost httpPost, Map<String, String> paramsMap) {
        if (paramsMap != null && paramsMap.size() > 0) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                nvps.add(new BasicNameValuePair(key, value));
            }
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String preurl = "https://restapi.amap.com/v3/geocode/geo?";
        //您的key&address=北京市朝阳区阜通东大街6号&city=北京
        String param = "key=" + "0f06a966f194c8f185cbcec77228286f" + "&address=深圳市福田区侨香路国华大厦4G&city=深圳";
        HttpClientUtil.httpGetMethond(preurl + param);
    }
}
