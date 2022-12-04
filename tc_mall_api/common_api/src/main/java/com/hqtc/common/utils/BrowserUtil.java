package com.hqtc.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/10/9 12:16
 */
public class BrowserUtil {
    public static String getBrowserType(HttpServletRequest request){
        String UserAgent = request.getHeader("USER-AGENT").toLowerCase();
        if (UserAgent != null) {
            if (UserAgent.indexOf("msie") >= 0)
                return "IE";
            if (UserAgent.indexOf("firefox") >= 0)
                return "FF";
            if (UserAgent.indexOf("safari") >= 0)
                return "SF";
        }
        return null;
    }

    public static void setDownloadName(HttpServletRequest request, HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        if("FF".equals(BrowserUtil.getBrowserType(request))){
            fileName=new String(fileName.getBytes("UTF-8"), "ISO8859-1");

        }
        else{
            fileName= URLEncoder.encode(fileName, "utf-8");

        }
        response.setHeader("Content-disposition","attachment;filename=" +fileName);
    }
}
