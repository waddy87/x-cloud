package com.sugon.cloudview.cloudmanager.util;

import java.util.Collection;
import java.util.Date;

import com.sugon.cloudview.cloudmanager.common.DateJsonValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

/**
 * Json工具类
 * 
 * @author zhangdapeng
 *
 */
public class JsonUtil {

    public static String toJson(Object targetObj) {
        String jsonString = null;
        if (targetObj == null)
            return null;
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        if (targetObj instanceof Collection) {
            jsonString = JSONArray.fromObject(targetObj, config).toString();
        } else {
            jsonString = JSONObject.fromObject(targetObj, config).toString();
        }
        return jsonString;
    }

    public static String error(String message, Exception e) {
        JSONObject resp = new JSONObject();
        resp.put("flag", false);
        resp.put("message", message);
        resp.put("details", e.getLocalizedMessage());
        return resp.toString();
    }

    public static String success(String message) {
        JSONObject resp = new JSONObject();
        resp.put("flag", true);
        resp.put("message", message);
        return resp.toString();
    }

}
