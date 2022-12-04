package com.hqtc.common.utils;

import com.google.gson.Gson;
import com.hqtc.common.response.ResultData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;

public class ServletOperate {

    private static Map<String, String[]> getParameterMap(HttpServletRequest httpServletRequest){
        Map<String, String[]> parameterMap = new HashMap<String, String[]>(httpServletRequest.getParameterMap());
        if (parameterMap.size() == 0){
            parameterMap.putAll(((HttpServletRequestWrapper) httpServletRequest).getRequest().getParameterMap());
        }
        return parameterMap;
    }
    public static String getRequestParams(HttpServletRequest httpServletRequest){
        String requestParams = "";
        Map<String, String[]> parameterMap = getParameterMap(httpServletRequest);
        if(parameterMap.size() > 0){
            parameterMap.remove("opaque");
            List<Map.Entry<String, String[]>> params = new ArrayList<Map.Entry<String, String[]>>(parameterMap.entrySet());
            Collections.sort(params, new Comparator<Map.Entry<String, String[]>>() {
                public int compare(Map.Entry<String, String[]> o1, Map.Entry<String, String[]> o2) {
                    return o1.getKey().compareTo(o2.getKey());
                }
            });
            for (int i = 0; i < params.size(); i++) {
                Map.Entry<String, String[]> entry = params.get(i);
                for(int j=0; j<entry.getValue().length; j++)
                {
                    requestParams += (entry.getKey().toString() + "=" + entry.getValue()[j] + "&");
                }
            }
            requestParams = requestParams.substring(0, requestParams.length() - 1);
        }
        return requestParams;
    }
    public static String calcOpaque(HttpServletRequest httpServletRequest, String secureToken) {
        String params = getRequestParams(httpServletRequest);
        String uri = httpServletRequest.getRequestURI();
        if(secureToken == null){
            return "";
        }
        String opaqueUri = uri + "?" + params + "&key=" + secureToken;
        return Tools.md5(opaqueUri);
    }

    public static String toParamsString(HashMap<String,String> param) {
        String string = "";
        List<Map.Entry<String, String>> params = new ArrayList<Map.Entry<String, String>>(param.entrySet());
        Collections.sort(params, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        for (int i = 0; i < params.size(); i++) {
            Map.Entry<String, String> entry = params.get(i);
            string += (entry.getKey().toString() + "=" + entry.getValue() + "&");
        }
        return string.substring(0, string.length() - 1);
    }

    public static String genUri(String uri, String token, String secureKey) {
        HashMap<String, String> params = genCommomParams(token);
        if(secureKey == null){
            return "";
        }
        String opaqueParams = toParamsString(params);
        String opaqueUri = uri + "?" + opaqueParams + "&key=" + secureKey;
        String opaque = Tools.md5(opaqueUri);
        return uri + "?" + opaqueParams + "&opaque=" + opaque;
    }

    public static HashMap<String, String> genCommomParams(String token){
        HashMap<String, String> params = new HashMap<String, String>();
        Random random = new Random();
        int rand = random.nextInt(99999) % (99999 - 1000 + 1) + 1000;
        long timeStamp = System.currentTimeMillis() / 1000;
        params.put("rand", rand+"");
        params.put("timestamp", timeStamp+"");
        params.put("token", token);
        return params;
    }

    public static void writeResult(HttpServletResponse httpServletResponse, int errorCode, String msg) throws Exception{
        ResultData resultData = new ResultData<Object>();
        resultData.setError(errorCode);
        resultData.setMsg(msg);
        Gson gson = new Gson();
        httpServletResponse.setHeader("Content-Type", "application/json; charset=UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = httpServletResponse.getWriter();
        printWriter.write(gson.toJson(resultData));
        printWriter.close();
    }
}
