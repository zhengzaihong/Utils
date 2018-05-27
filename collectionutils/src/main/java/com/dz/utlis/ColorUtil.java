package com.dz.utlis;

import android.graphics.Color;

/**
* creat_user: zhengzaihong
* Email:1096877329@qq.com
* creat_date: 2018/5/23
* creat_time: 14:17
* describe 颜色处理类
**/
public class ColorUtil {
    /**
     * 解析颜色
     *
     * @param colorStr #ffffff 颜色字符串
     * @param alpha    0-255 透明度
     * @return
     */

    public static int parserColor(String var0, int var1) {
        int var2 = Color.parseColor(var0);
        int var3 = (var2 & 16711680) >> 16;
        int var4 = (var2 & '\uff00') >> 8;
        int var5 = var2 & 255;
        return Color.argb(var1, var3, var4, var5);
    }

    public static int parserColor(int var0, int var1) {
        int var2 = (var0 & 16711680) >> 16;
        int var3 = (var0 & '\uff00') >> 8;
        int var4 = var0 & 255;
        return Color.argb(var1, var2, var3, var4);
    }

    /**
     * 解析颜色
     * @param color Color.WHITE
     * @param alpha 0-255 透明度
     * @return
     */

    public static int parserColor(int var0) {
        int var1 = (var0 & 16711680) >> 16;
        int var2 = (var0 & '\uff00') >> 8;
        int var3 = var0 & 255;
        return Color.argb(255, var1, var2, var3);
    }

    /**
     * 解析颜色
     * @param colorStr #ffffff 颜色字符串
     * @return
     */
    public static int parserColor(String var0) {
        return Color.parseColor(var0);
    }
}
