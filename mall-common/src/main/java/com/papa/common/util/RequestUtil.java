package com.papa.common.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class RequestUtil {

    public static String getRequestIp(HttpServletRequest request){
        String ipAddress=request.getHeader("x-forwarded-for");
        if(ipAddress!=null&&ipAddress.length()>0&&!ipAddress.equalsIgnoreCase("unknown")){
            ipAddress=ipAddress.split(",")[0];
        }
        if(ipAddress==null||ipAddress.length()==0||ipAddress.equalsIgnoreCase("unknown")){
            ipAddress=request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1")||ipAddress.equals("0:0:0:0:0:0:0:1")){
                //说明是本机发出的请求
                InetAddress inetAddress=null;
                try {
                    inetAddress = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inetAddress.getHostAddress();
            }
        }
        return ipAddress;



    }
    public static String getBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) {
            return "Unknown";
        }

        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            return "Internet Explorer";
        } else if (userAgent.contains("Firefox")) {
            return "Firefox";
        } else if (userAgent.contains("Chrome")) {
            return "Chrome";
        } else if (userAgent.contains("Opera")) {
            return "Opera";
        } else if (userAgent.contains("Safari")) {
            return "Safari";
        }else if(userAgent.contains("Edge")){
            return "Edge";
        }
        return "Unknown";
    }


}
