package cn.com.pfinfo.demo.util;

import com.alibaba.fastjson.JSONObject;

/**
 *
 * @author cuitpf
 * @since 1.0
 * @version 1.0
 */
public class JsonUtil {
    public static JSONObject strToJsonObject(String data){
        return JSONObject.parseObject(data);
    }
    public static JSONObject bytesToJsonObject(byte[] data){
        return strToJsonObject(new String(data));
    }
}
