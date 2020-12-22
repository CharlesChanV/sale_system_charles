package com.dgut.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Charles
 * @version 1.0
 * @date 2020/6/16
 */
public class NetUtils {
    public static String post(String apiUrl,String paramStr) throws Exception {
        HttpURLConnection conn = null;
        URL url = new URL(apiUrl);

        conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setInstanceFollowRedirects(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("accept", "text/json");
        conn.addRequestProperty("Content-type", "application/x-www-form-urlencoded");
        //发送数据

        conn.getOutputStream().write(paramStr.getBytes("UTF-8"));
        conn.getOutputStream().flush();
        conn.getOutputStream().close();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "UTF-8"));

        String line;
        StringBuffer responseData = new StringBuffer();
        while((line = reader.readLine()) != null) {
            responseData.append(line);
        }

        reader.close();

        return responseData.toString();
    }

    public static String getCliectIp(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ip.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ip = str;
                break;
            }
        }
        return ip;
    }
}
