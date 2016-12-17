package com.wzf.beijingnews.utils;

/**
 * 作者：尚硅谷-杨光福 on 2016/12/13 09:18
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：配置联网路径
 */
public class ConstantsUtils {
    /**
     * 联网请求的ip和端口
     */
//    public static final String BASE_URL = "http://192.168.1.165:8080/web_home";
    //自带本地的模拟器访问地址
//    public static final String BASE_URL = "http://10.0.2.2:8080/web_home";
    public static final String BASE_URL = "http://192.168.14.14:8080/web_home";

    /**
     * 新闻中心的网络地址
     */
    public static final String NEWSCENTER_PAGER_URL = BASE_URL + "/static/api/news/categories.json";

}
