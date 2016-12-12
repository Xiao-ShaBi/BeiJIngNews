package com.wzf.beijingnews.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/12/12.
 */

public class CacheUtils {

    public static boolean MainOrNot(Context context) {

        SharedPreferences sp = context.getSharedPreferences("sysInfo", Context.MODE_PRIVATE);
        String version = sp.getString("version", "");
        if ("".equals(version) || !version.equals(VersionUtils.getVersion(context))) {
            return false;
        }
        return true;
    }

    public static void setVersion(Context context, String value) {
        SharedPreferences sp = context.getSharedPreferences("sysInfo", Context.MODE_PRIVATE);
        sp.edit().putString("version", value).commit();
    }

}
