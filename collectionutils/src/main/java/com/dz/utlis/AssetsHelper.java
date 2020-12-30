package com.dz.utlis;

import android.content.Context;


import java.io.IOException;
import java.io.InputStream;

/**
* create_user: zhengzaihong
* Email:1096877329@qq.com
* create_date: 2018/6/12
* create_time: 9:39
* describe Assets帮助类
**/
public class AssetsHelper {

    private AssetsHelper() {
        throw new AssertionError();
    }

    /**
     * 读取 assets 文件
     * @param context
     * @param fileName
     * @return
     */
    public static String readData(Context context, String fileName) {
        InputStream inStream = null;
        String data = null;
        try {
            inStream = context.getAssets().open(fileName);     //打开assets目录中的文本文件
            byte[] bytes = new byte[inStream.available()];  //inStream.available()为文件中的总byte数
            inStream.read(bytes);
            inStream.close();
            data = new String(bytes, "utf-8");        //将bytes转为utf-8字符串
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
