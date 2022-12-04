package com.hqtc.common.utils;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.springframework.cglib.beans.BeanMap;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Tools {
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (Exception e) {
            return "";
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static String base64Encode(String string){
        return new BASE64Encoder().encode(string.getBytes());
    }

    public static String base64Decode(String string) throws Exception{
        return new String(new BASE64Decoder().decodeBuffer(string));
    }

    public static String encryptString(String s){
        String temp = s + "&7ab3240b-dfaf-47ef-98c9-5dfe800b3ae4";
        return md5(s);
    }

    public static ResultData getThreadResultData(){
        ResultData resultData = (ResultData) ThreadObjectHolder.getSetClass("getThreadResultData", ResultData.class);
        resultData.setMsg("ok");
        resultData.setData(null);
        resultData.setError(ErrorCode.SUCCESS);
        return resultData;
    }

    public static DataList getThreadDataList(){
        DataList dataList = (DataList) ThreadObjectHolder.getSetClass("getThreadDataList", DataList.class);
        dataList.setData(null);
        dataList.setTotalRows(0);
        return dataList;
    }

    /**
     * 将map转换为javaBean对象
     * @param map
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    /**
     * 将对象转换为map
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> LinkedHashMap<String, Object> beanToMap(T bean) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

}
