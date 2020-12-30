package com.dz.utlis;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.Window;

import androidx.annotation.RequiresApi;

/**
 *create_user: zhengzaihong
 *email:1096877329@qq.com
 *create_date: 2018/5/27
 *create_time: 12:15
 *describe: 常用测量工具
 **/

public class MeasureUtils {


    //获取控件在屏幕上的坐标
    public static int[] getViewLocation(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        return location;
    }

    /**
     * 获得状态栏的高度 三个方法
     * @param context
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    public static int getStatusBarHeight2(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
    //getStatusBarHeight3 使用此方法不不再onstart()方法之前返回 可以使用异步方式
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public int getStatusBarHeight3(final Activity activity){
        Rect rectangle= new Rect();
        Window window= activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight=rectangle.top;
        return statusBarHeight;
    }

    //获取标题栏高度
    public static int getTitleBarHeight(Activity activity, int statusBarHeight ){
        int contentTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        //statusBarHeight是上面所求的状态栏的高度
        int titleBarHeight = contentTop - statusBarHeight;
        return titleBarHeight;
    }
}
