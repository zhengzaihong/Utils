package com.dz.utlis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2016/10/24
 * creat_time: 11:53
 * describe app 中关于act的操作
 **/
public class ClassTools {

    //记录当前app的act
    private static List<Activity> activities = new ArrayList<>();


    public static void toAct(Context mContext, Class<?> cls) {
        toAct(mContext, cls, null);
    }

    public static void toAct(Context mContext, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
    }

    public static void toActClearTop(Context mContext, Class<?> cls) {
        toActClearTop(mContext, cls, null);
    }

    public static void toActClearTop(Context mContext, Class<?> cls,
                                     Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mContext.startActivity(intent);
    }


    //添加act
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    //移除act
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 关闭Activity列表中的所有Activity，并退出当前应用
     */
    public static void finishExitActivity() {

        closeAllActivity();
        // 杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static void closeAllActivity() {
        if (activities.size() > 0) {
            for (int i = 0; i < activities.size(); i++) {
                activities.get(i).finish();
            }
        }
    }

    /**
     * 清除指定的act
     *
     * @param cls
     */
    public static void clearClass(Class... cls) {

        for (int i = 0; i < cls.length; i++) {
            String newClass = cls[i].getPackage().getName() + "." + cls[i].getClass().getSimpleName();

            for (int j = 0; j < activities.size(); j++) {
                //获取类的完全类路径
                String oldClass = activities.get(i).getPackageName() + "." + activities.get(i).getClass().getSimpleName();

                if (newClass.equals(oldClass)) {
                    activities.get(i).finish();
                }
            }
        }

    }

    /**
     * 只保留指定的act
     *
     * @param cls
     */
    public static void holdOnClass(Class cls) {

        String newClass = cls.getPackage().getName() + "." + cls.getClass().getSimpleName();
        for (int j = 0; j < activities.size(); j++) {
            //获取类的完全类路径
            String oldClass = activities.get(j).getPackageName() + "." + activities.get(j).getClass().getSimpleName();

            if (!newClass.equals(oldClass)) {
                activities.get(j).finish();
            }
        }
    }
}