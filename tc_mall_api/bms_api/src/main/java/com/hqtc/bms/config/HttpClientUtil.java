package com.hqtc.bms.config;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description:发送post请求
 * Created by laiqingchuang on 18-7-3 .
 */
public class HttpClientUtil {
    public final static Logger log = Logger.getLogger(HttpClientUtil.class);
    private static PoolingHttpClientConnectionManager cm = null;

    static {
        //httpclient连接池
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(50);
    }

    /**
     * post请求
     * @param url
     * @param obj
     * @return
     */
    public static String post(String url, Object obj, String charset, Boolean isSSL) {
        try {
            HttpClient httpClient = null;
            if (isSSL) {
                httpClient = HttpClients.custom().setConnectionManager(cm).setSSLSocketFactory(createSSLConnectionSocketFactory()).build();
            } else {
                httpClient = HttpClients.custom().setConnectionManager(cm).build();
            }
            Map<String, String> paramMap = MapUtils.java2Map(obj);
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
            log.info("post请求响应内容：" + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
        return sslsf;
    }

}


