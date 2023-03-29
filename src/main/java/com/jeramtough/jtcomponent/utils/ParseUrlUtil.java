package com.jeramtough.jtcomponent.utils;

import java.util.HashMap;

/**
 * <pre>
 * Created on 2022/9/13 下午9:01
 * by @author WeiBoWen
 * </pre>
 */
public class ParseUrlUtil {


    private ParseUrlUtil() {
    }

    public static String getParam(String url, String query) {

        HashMap<String, String> strUrlParas = new HashMap<>(5);
        parser(strUrlParas, url);
        return strUrlParas.get(query);
    }

    /**
     * 解析日志url
     */
    private static void parser(HashMap<String, String> strUrlParas, String url) {
        strUrlParas.clear();
//		传递的URL参数
        String strUrl = "";
        String strUrlParams = "";


//		解析访问地址
        if (url.contains("?")) {
            String[] strUrlPatten = url.split("\\?");
            strUrl = strUrlPatten[0];
            strUrlParams = strUrlPatten[1];

        }
        else {
            strUrl = url;
            strUrlParams = url;
        }

        strUrlParas.put("URL", strUrl);
//		解析参数
        String[] params;

        if (strUrlParams.contains("&")) {
            params = strUrlParams.split("&");
        }
        else {
            params = new String[]{strUrlParams};
        }

//		保存参数到参数容器
        for (String p : params) {
            if (p.contains("=")) {
                String[] param = p.split("=");
                if (param.length == 1) {
                    strUrlParas.put(param[0], "");
                }
                else {

                    String key = param[0];
                    String value = param[1];

                    strUrlParas.put(key, value);
                }
            }
            else {
                strUrlParas.put("errorParam", p);
            }
        }

    }


}
