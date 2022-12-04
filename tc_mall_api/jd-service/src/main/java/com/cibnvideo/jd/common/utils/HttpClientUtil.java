package com.cibnvideo.jd.common.utils;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: HttpClient工具类
 * @Author: WangBin
 * @Date: 2018/6/22 15:26
 */
public class HttpClientUtil {
    private static Logger log  = LoggerFactory.getLogger(HttpClientUtil.class);
    private static PoolingHttpClientConnectionManager cm = null;
    static {
        //httpclient连接池
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(50);
    }
    private static HttpClient getHttpClient(boolean isSSL) {
        HttpClient httpClient = null;
        if (isSSL) {
            httpClient = HttpClients.custom().setConnectionManager(cm).setSSLSocketFactory(createSSLConnectionSocketFactory()).build();
        } else {
            httpClient = HttpClients.custom().setConnectionManager(cm).build();
        }
        return httpClient;
    }
    /**
     * post请求（form表单形式）
     *
     * @param url
     * @param obj
     * @return
     */
    public static String post(String url, Object obj, String charset, Boolean isSSL) {
        try {
            HttpClient httpClient =getHttpClient(isSSL);
            Map<String, String> paramMap = new HashMap<>();
            if(obj instanceof Map){
                paramMap=(Map)obj;
            }else{
                try {
                    paramMap = MapUtils.java2Map(obj);
                } catch (Exception e) {
                    log.error("httpclient发送post请求中，obj对象转Map失败:"+e.getMessage());
                    throw e;
                }
            }
            List<NameValuePair> paramList = new ArrayList<>();
            if (paramMap != null && !paramMap.isEmpty()) {
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    NameValuePair nv = new BasicNameValuePair(entry.getKey(), entry.getValue());
                    paramList.add(nv);
                }
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, charset);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            log.info("postForm请求响应内容：" + result);
            return result;
        } catch (Exception e) {
            log.error("postForm请求异常："+e.getMessage());
        }
        return null;
    }

    /**
     * post请求（json形式）
     * @param url
     * @param obj
     * @param charset
     * @param isSSL
     * @return
     */
    public static String postJson(String url, Object obj, String charset, Boolean isSSL){
        try {
            Gson gson = new Gson();
            HttpClient httpClient = getHttpClient(isSSL);
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");
            String json = "";
            if (obj instanceof String) {
                json = (String) obj;
            } else {
                json = gson.toJson(obj);
            }
            httpPost.setEntity(new StringEntity(json,charset));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            String result = EntityUtils.toString(httpResponse.getEntity(), charset);
            log.info("post请求响应内容：" + result);
            return result;
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * get请求
     * @param url
     * @param obj
     * @param charset
     * @param isSSL
     * @return
     */
    public static String get(String url, Object obj, String charset, Boolean isSSL){
        try {
            HttpClient httpClient = getHttpClient(isSSL);
            Map<String, String> paramMap = new HashMap<>();
            if(obj!=null) {
                if (obj instanceof Map) {
                    paramMap = (Map) obj;
                } else {
                    try {
                        paramMap = MapUtils.java2Map(obj);
                    } catch (Exception e) {
                        log.error("httpclient发送get请求中，obj对象转Map失败:"+e.getMessage());
                        throw e;
                    }
                }
                //拼接url
                url=url+"?";
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    String key=entry.getKey();
                    String value=entry.getValue();
                    url=url+key+"="+value+"&";
                }
                url=url.substring(0,url.length()-1);
            }
            //发送请求
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            String result = EntityUtils.toString(httpResponse.getEntity(), charset);
            log.info("post请求响应内容：" + result);
            return result;
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }
    private static SSLConnectionSocketFactory createSSLConnectionSocketFactory() {
        SSLContext sslContext = null;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
        } catch (NoSuchAlgorithmException e) {
            log.error("https认证NoSuchAlgorithmException异常："+e.getMessage());
        } catch (KeyManagementException e) {
            log.error("https认证KeyManagementException异常："+e.getMessage());
        } catch (KeyStoreException e) {
            log.error("https认证KeyStoreException异常："+e.getMessage());
        }
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
        return sslsf;
    }

}

