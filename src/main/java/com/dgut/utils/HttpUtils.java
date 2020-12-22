package com.dgut.utils;

import com.alibaba.fastjson.JSONObject;
import io.netty.util.CharsetUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.DigestUtils;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * @author Charles
 * @version 1.0
 * @date 2020/6/16
 */
public class HttpUtils {

    private static final class DefaultTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    private static HttpsURLConnection getHttpsURLConnection(String uri, String method) throws IOException {
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SSLSocketFactory ssf = ctx.getSocketFactory();

        URL url = new URL(uri);
        HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();
        httpsConn.setSSLSocketFactory(ssf);
        httpsConn.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });
        httpsConn.setRequestMethod(method);
        httpsConn.setDoInput(true);
        httpsConn.setDoOutput(true);
        return httpsConn;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getAccessToken());
    }

    public static String getAccessToken() throws IOException{
        URL url = new URL("https://api.css.dgut.edu.cn/getToken");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setInstanceFollowRedirects(false);
        connection.connect();
        OutputStreamWriter out = new OutputStreamWriter(connection
                .getOutputStream(), "utf-8");
        Map<String,String> param = new HashMap<>();
        param.put("appId","css-report");
        param.put("appSecret","6ef35a06");
        out.write(getParamString(param));
        //remember to clean up
        out.flush();
        out.close();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), CharsetUtil.UTF_8));
        String ss = null;
        StringBuilder total = new StringBuilder();
        while ((ss = bufferedReader.readLine()) != null) {
            total.append(ss);
        }
        connection.disconnect();
        return Optional.ofNullable(JSONObject.parseObject(total.toString()).getString("data"))
                .orElse(null);
    }

    private static String getParamString(Map<String, String> paramMap){
        if(null==paramMap || paramMap.isEmpty()){
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for(String key : paramMap.keySet() ){
            builder.append("&")
                    .append(key).append("=").append(paramMap.get(key));
        }
        return builder.deleteCharAt(0).toString();
    }

    public static String httpPost(SortedMap<Object, Object> parameters, String url) throws Exception {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            NameValuePair pair=new BasicNameValuePair(k, v);
            pairs.add(pair);
        }
        //请求参数
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
        System.out.println(pairs.toString());
        //请求头
        String result = createSign(parameters);
        if (result.length() == 0) {
            result = "key=2245f3d8cfb3770b037fbd2558a510b5";
        }else {
            result=result+"&key="+"2245f3d8cfb3770b037fbd2558a510b5";
        }
        System.out.println(result);
        String md5Result= DigestUtils.md5DigestAsHex(result.getBytes()).toUpperCase();
        System.out.println(md5Result);
        httpPost.setHeader("apiKey","jxdajHuJenFK0qG3mbv");
        httpPost.setHeader("signature",md5Result);
        httpPost.setHeader("Content-type","application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                // 解析响应体
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                return content;
            }
        }
        finally {
            if (response != null) {
                response.close();
            }
            // 关闭浏览器
            httpclient.close();
        }
        return "返回数据失败！";
    }

    private static String createSign(SortedMap<Object, Object> parameters) {
        if (parameters.size() == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();  //所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            //空值不传递，不参与签名组串
            if (null != v && !"".equals(v) && !"0".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }
        String result = sb.toString().substring(0,sb.toString().length()-1);
        //排序后的字符串
        System.out.println("待签名字符串:" + result);
        return result ;
    }
}
