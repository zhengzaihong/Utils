package com.dz.utlis;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 *creat_user: zhengzaihong
 *email:1096877329@qq.com
 *creat_date: 2018/12/3 0003
 *creat_time: 17:07
 *describe: Ui资源文件操作类
 **/
public class UiCompat {


    /**
     * 获取颜色
     * @param resources
     * @param id
     * @return
     */
    public static int getColor(Resources resources, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return resources.getColor(id, null);
        } else {
            return resources.getColor(id);
        }
    }

    /**
     * 获取 Drawable
     * @param resources
     * @param id
     * @return
     */
    public static Drawable getDrawable(Resources resources, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return resources.getDrawable(id, null);
        } else {
            return resources.getDrawable(id);
        }
    }


}
