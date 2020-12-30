package com.dz.utlis;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2019/5/15 0015
 * create_time: 13:51
 * describe: 检查是否有通知权限
 **/

public class CheckNotificationUtil {


    private CheckNotificationUtil() {

    }

    /**
     * 去设置开启通知界面
     * @param activity
     */
    public static void gotoNotificationSetting(Activity activity) {
        ApplicationInfo appInfo = activity.getApplicationInfo();
        String pkg = activity.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, pkg);
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, uid);
                //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
                intent.putExtra("app_package", pkg);
                intent.putExtra("app_uid", uid);
                activity.startActivityForResult(intent, 1000);
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + activity.getApplication().getPackageName()));
                activity.startActivityForResult(intent, 1000);
            } else {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                activity.startActivityForResult(intent, 1000);
            }
        } catch (Exception e) {

            IntentUtils.gotoSetting(activity);
            JavaUtils.outRedPrint("gotoNotificationSetting错误");
        }
    }


    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";


    /**
     * 判断是否开启通知状态
     * @param context
     * @return
     */
    @SuppressLint("NewApi")
    public static boolean isNotificationEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return isEnableV26(context);
        } else {
            return isEnableV19(context);
        }
    }

    /**
     * Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
     * 19及以上
     *
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean isEnableV19(Context context) {
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass;
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            JavaUtils.outRedPrint("isEnableV19权限错误");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
     * 针对8.0及以上设备
     *
     * @param context
     * @return
     */
    public static boolean isEnableV26(Context context) {
        try {
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            Method sServiceField = notificationManager.getClass().getDeclaredMethod("getService");
            sServiceField.setAccessible(true);
            Object sService = sServiceField.invoke(notificationManager);

            ApplicationInfo appInfo = context.getApplicationInfo();
            String pkg = context.getApplicationContext().getPackageName();
            int uid = appInfo.uid;

            Method method = sService.getClass().getDeclaredMethod("areNotificationsEnabledForPackage"
                    , String.class, Integer.TYPE);
            method.setAccessible(true);
            return (boolean) method.invoke(sService, pkg, uid);
        } catch (Exception e) {
            JavaUtils.outRedPrint("isEnableV26权限错误");
            e.printStackTrace();
        }
        return false;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean isNotificationEnabled1(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //8.0手机以上
            if (((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).getImportance() == NotificationManager.IMPORTANCE_NONE) {
                return false;
            }
        }

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;

        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
