package com.dz.utlis;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2018/5/23
 * creat_time: 18:21
 * describe
 **/
public class JavaUtils {

    public static boolean isdebug = false;

    /**
     * @param s // 打印输出
     */
    public static void outPrint(String s) {
        if (isdebug)
            System.out.println("输出信息:" + s);
    }

    public static void outRedPrint(String s) {
        if (isdebug)
            Log.e("输出信息：", s);
    }

    /**
     * @param jsonString json字符串
     * @return 将json字符串转换成map的集合
     */
    public static Map getMap4Json(String jsonString) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator keyIter = jsonObject.keys();
        String key;
        Object value;
        Map valueMap = new HashMap();
        while (keyIter.hasNext()) {
            key = (String) keyIter.next();
            try {
                value = jsonObject.get(key);
                valueMap.put(key, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return valueMap;
    }


    /**
     * @return 返回中间部分被隐藏的字符串
     */
    public static String getPhoneHide(String number) {
        StringBuffer buffer = new StringBuffer("");
        if (number.length() > 10) {

            for (int i = 0; i < number.length(); i++) {
                if (i > 2 && i < 7) {
                    buffer.append("*");
                    continue;
                }
                buffer.append(number.charAt(i));

            }
        }
        return buffer.toString();
    }

    /**
     * @return 只返回后面部分显示的
     */
    public static String getPhoneStartHide(String number) {
        StringBuffer buffer = new StringBuffer("");
        if (number.length() > 10) {
            for (int i = 0; i < number.length(); i++) {
                if (i >= 0 && i < 7) {
                    buffer.append("*");
                    continue;
                }
                buffer.append(number.charAt(i));
            }
        }
        return buffer.toString();
    }


    /**
     * @param str 判断一个字符串是不是纯字母
     * @return
     */
    public static boolean justisAz(String str) {
        String reg = "^[a-zA-Z\\s]*$";
        boolean isCract = str.matches(reg);
        return isCract;
    }

    /**
     * @param str 判断一个字符串是不是纯数字
     * @return
     */
    public static boolean justNumber(String str) {
        String reg = "^[0-9\\.]*$";
        boolean isCract = str.matches(reg);
        return isCract;
    }

    /**
     * @param str 判断一个字符串是不是只有数字 字母和/
     * @return
     */
    public static boolean justNumAz(String str) {
        String reg = "^[A-Za-z0-9/]+$";
        boolean isCract = str.matches(reg);
        return isCract;
    }


    /**
     * 将数字转化汉字显示
     *
     * @param input
     * @return
     */
    public static String getChinese(String input) {
        String[] num = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] unit = new String[]{"", "十", "百", "千", "万", "亿"};
        String[] mid = new String[input.length()];
        String output = "";

        for (int i = 0; i < input.length(); i++) {
            mid[i] = num[Integer.parseInt("" + input.charAt(i))];
            output += mid[i];
        }

        String str = "";
        String result = "";
        for (int i = 0; i < output.length(); i++) {
            if (output.length() - i - 1 == 0) {
                str = "" + output.charAt(i);
            } else if ((output.length() - i - 1 + 4) % 8 == 0) {
                str = output.charAt(i) + unit[4];
            } else if ((output.length() - i - 1) % 8 == 0) {
                str = output.charAt(i) + unit[5];
            } else {
                str = output.charAt(i) + unit[(output.length() - i - 1) % 4];
            }
            result += str;
        }

        result = result.replaceAll("零[千百十]", "零");
        result = result.replaceAll("亿零+万", "亿零");
        result = result.replaceAll("万零+亿", "万亿");
        result = result.replaceAll("零+", "零");
        result = result.replaceAll("零万", "万");
        result = result.replaceAll("零亿", "亿");
        result = result.replaceAll("^一十", "十");
        result = result.replaceAll("零$", "");
        return result;
    }
}
