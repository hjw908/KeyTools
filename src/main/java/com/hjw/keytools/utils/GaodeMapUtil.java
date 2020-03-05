package com.hjw.keytools.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hjw.keytools.ui.UiConstants;

import java.util.ArrayList;
import java.util.List;


public class GaodeMapUtil {
    private static String output = "JSON"; //返回数据格式类型 可选输入内容包括：JSON，XML
    private static String geoUrl = "https://restapi.amap.com/v3/geocode/geo?"; //地理编码 API 服务地址
    private static String regeoUrl = "https://restapi.amap.com/v3/geocode/regeo?"; //逆地理编码API服务地址
    private static String key = UiConstants.GAODE_MAP_KEY;
    private static String walkingUrl = "https://restapi.amap.com/v3/direction/walking?";


    /**
     * 通过结构化地址获取高德经纬度信息
     * byAddressGetLocation
     * @param address
     */
    public static void byAddressGetLocation(String address) {
        String parameters = "address=" + address + "&" + "output=" + output + "&" + "key=" + key;
        String fullUrl = geoUrl + parameters;
        String respondContent = HttpClientUtil.httpGetMethond(fullUrl);
        //从json格式字符串构建一个json对象
        JSONObject respondJson = JSONObject.parseObject(respondContent);
        JSONArray jsonArray = respondJson.getJSONArray("geocodes");
        List<String> list = new ArrayList<String>();
        if(jsonArray.size() > 0 ){
            for(int i=0;i<jsonArray.size();i++){
               JSONObject subJsonobject =  jsonArray.getJSONObject(i);
               String formatted_address =  subJsonobject.getString("formatted_address");
               String location = subJsonobject.getString("location");
               System.out.println(formatted_address+" "+location);
            }
        }
    }

    /**
     * 通过经纬度信息获取地址
     * byLnocatioGetAddress
     * @param location
     */
    public static void byLnocatioGetAddress(double[] location){
        String locationStr = getLocationFormat(location);
        String parameters = "location=" + locationStr + "&" + "output=" + output
                + "&" + "key=" + key + "&" +"radius=" + "1000" +"&extensions=all";
        String fullUrl = regeoUrl + parameters;
        System.out.println(fullUrl);
        String respondContent = HttpClientUtil.httpGetMethond(fullUrl);
        JSONObject jsonObject = JSONObject.parseObject(respondContent);
        String formatted_address = jsonObject.getJSONObject("regeocode").getString("formatted_address");
        System.out.println(formatted_address);
    }

    public static void directionPlanning(double[] originlocation,double[] destinationlocation ){
        String originlocationStr = getLocationFormat(originlocation);
        String destinationlocationStr = getLocationFormat(destinationlocation);
        String parameters = "origin=" + originlocationStr+"&"+ "destination="+destinationlocationStr+"&"+"key="+key +"&output=" + output ;

        String fullUrl = walkingUrl + parameters;
        HttpClientUtil.httpGetMethond(fullUrl);
    }

    public static String getLocationFormat(double[] location){
        String locationStr =  location[0] + "," +location[1];
        return locationStr;
    }

    public static void main(String[] args) {
//        GaodeMapUtil.byAddressGetGPS("深圳市福田区侨香路国华大厦"); //114.011284,22.547225
//        GaodeMapUtil.byAddressGetGPS("深圳市南山区龙海家园"); //113.904440,22.530267
        double[] location = {114.011284,22.547225};
        double[] location2 = {113.904440,22.530267};
//        String locationStr = location[0] + "," +location[1];
//        System.out.println(locationStr);
//        GaodeMapUtil.byGPSGetAddress(location);

        GaodeMapUtil.directionPlanning(location,location2);
    }
}
