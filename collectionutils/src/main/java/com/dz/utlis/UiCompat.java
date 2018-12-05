package com.dz.utlis;

import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

import java.lang.reflect.Field;

/**
 *creat_user: zhengzaihong
 *email:1096877329@qq.com
 *creat_date: 2018/12/3 0003
 *creat_time: 17:07
 *describe: Ui资源文件操作类
 **/
public class UiCompat {

    public static int getColor(Resources resources, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return resources.getColor(id, null);
        } else {
            return resources.getColor(id);
        }
    }

}
