package com.hjw.keytools.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import com.hjw.keytools.ui.UiConstants;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


import java.util.logging.XMLFormatter;

public class JSONFormatUtil {
    public static String jsonFormat(String tempJsonStr) {
        String jsonString = "";
        if (!tempJsonStr.equals(UiConstants.HINT_TEXT)) {

            JSONObject jsonObj = JSONObject.parseObject(tempJsonStr);
            jsonString = JSONObject.toJSONString(jsonObj,
                    SerializerFeature.PrettyFormat,
                    SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteDateUseDateFormat);
            return jsonString;
        }
        return jsonString;
    }

    public void xmlFormat(String tempJsonStr) {
//        XMLSerializer xmlSerializer = new XMLSerializer();

    }


    public static void main(String[] args) throws Exception {
        String str = "{\"status\":\"1\",\"info\":\"OK\",\"infocode\":\"10000\",\"count\":\"1\",\"geocodes\":[{\"formatted_address\":\"广东省深圳市南山区桂湾三道龙海家园\",\"country\":\"中国\",\"province\":\"广东省\",\"citycode\":\"0755\",\"city\":\"深圳市\",\"district\":\"南山区\",\"township\":[],\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"adcode\":\"440305\",\"street\":[],\"number\":[],\"location\":\"113.904440,22.530267\",\"level\":\"兴趣点\"}]}";
//        System.out.println(JSONFormatUtil.jsonFormat(str));
        JSONObject job = JSONObject.parseObject(str);
    }
}
