package com.hqtc.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Component
public class JsonTools {
    private Gson gson;

    private JsonTools() {
        gson = new GsonBuilder().serializeNulls().create();
    }

    public Object stringToObject(String s, Object o) {
        return gson.fromJson(s, o.getClass());
    }

    public JSONObject stringToObject(String s) {
        return new JSONObject(s);
    }

    public Object stringToObject(String s, Class cls) {
        return gson.fromJson(s, cls);
    }

    public String objectToString(Object o) {
        return gson.toJson(o, o.getClass());
    }

    private static class JsonToolsHolder {
        private static JsonTools instance = new JsonTools();
    }

    public Map convertObjToMap(Object obj) {
        Map<String, Object> reMap = new HashMap<String, Object>();
        if (obj == null) {
            return null;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < fields.length; i++) {
                try {
                    Field f = obj.getClass().getDeclaredField(fields[i].getName());
                    f.setAccessible(true);
                    Object o = f.get(obj);
                    reMap.put(fields[i].getName(), o);
                } catch (NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return reMap;
    }

    public static JsonTools getInstance() {
        return JsonToolsHolder.instance;
    }

    public JSONObject HttpServletRequestToObject(HttpServletRequest httpServletRequest) throws IOException {
        BufferedInputStream stream = new BufferedInputStream(httpServletRequest.getInputStream());
        int len = 0;
        byte[] bytes = new byte[1024 * 1024];
        StringBuffer jsonObj = new StringBuffer();
        while (len != -1){
            len = stream.read(bytes);
            jsonObj.append(new String(bytes,"utf-8"));
        }
        return new JSONObject(jsonObj.toString());
    }

}
