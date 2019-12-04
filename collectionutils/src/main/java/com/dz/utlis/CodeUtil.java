package com.dz.utlis;

import java.io.UnsupportedEncodingException;

/**
 *creat_user: zhengzaihong
 *email:1096877329@qq.com
 *creat_date: 2019/5/22 0022
 *creat_time: 14:48
 *describe: 格式转换
 **/

public class CodeUtil {

	//将gbk字符串转utf-8
	public static String getUTF8StringFromGBKString(String gbkStr) {
        try {  
            return new String(getUTF8BytesFromGBKString(gbkStr), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new InternalError();
        }  
    }  
	//将gbk字符串转utf-8
    public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
        int n = gbkStr.length();  
        byte[] utfBytes = new byte[3 * n];  
        int k = 0;  
        for (int i = 0; i < n; i++) {  
            int m = gbkStr.charAt(i);  
            if (m < 128 && m >= 0) {  
                utfBytes[k++] = (byte) m;  
                continue;  
            }  
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));  
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));  
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));  
        }  
        if (k < utfBytes.length) {  
            byte[] tmp = new byte[k];  
            System.arraycopy(utfBytes, 0, tmp, 0, k);
            return tmp;  
        }  
        return utfBytes;  
    }  
}
