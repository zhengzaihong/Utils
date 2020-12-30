package com.dz.utlis;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.SparseArray;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2019/1/3 0003
 * create_time: 10:52
 * describe: android 运行时权限工具
 **/
@SuppressWarnings("all")
public class PermissionUtils {

    private static PermissionUtils permissionUtils;
    private Activity mActivity;
    //请求权限索引
    private static int mPermissionIdx = 0x10;
    //请求权限运行列表
    private static SparseArray<CallBackListener> mPermissions = new SparseArray<>();


    private String sureText = "确定";
    private String cancleText = "取消";
    private int sureTextColor = Color.BLUE;
    private int cancleTextColor = Color.BLACK;


    private PermissionUtils() {
    }

    public static PermissionUtils getInstance() {

        if (null == permissionUtils) {
            synchronized (PermissionUtils.class) {
                if (null == permissionUtils) {
                    permissionUtils = new PermissionUtils();
                }
            }
        }
        return permissionUtils;
    }

    /**
     * 危险权限组
     */
    public static String[] dangerousPermission = new String[]{
            //CALENDAR（日历）
            android.Manifest.permission.READ_CALENDAR,
            android.Manifest.permission.WRITE_CALENDAR,
            //CAMERA（相机）
            android.Manifest.permission.CAMERA,
            //CONTACTS（联系人）
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.WRITE_CONTACTS,
            android.Manifest.permission.GET_ACCOUNTS,
            //LOCATION（位置）
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            //MICROPHONE（麦克风）
            android.Manifest.permission.RECORD_AUDIO,
            //PHONE（手机）
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.CALL_PHONE,
            android.Manifest.permission.WRITE_CALL_LOG,
            android.Manifest.permission.ADD_VOICEMAIL,
            android.Manifest.permission.USE_SIP,
            android.Manifest.permission.PROCESS_OUTGOING_CALLS,
            //SENSORS（传感器）
            android.Manifest.permission.BODY_SENSORS,
            //SMS（短信）
            android.Manifest.permission.SEND_SMS,
            android.Manifest.permission.RECEIVE_SMS,
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.RECEIVE_WAP_PUSH,
            android.Manifest.permission.RECEIVE_MMS,
            //STORAGE（存储卡）
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };


    /**
     * 该方法在复写 onRequestPermissionsResult触发.可通知去设置界面开启权限
     * 如果不需要去设置界面不需要触发该方法
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @SuppressLint("Override")
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        onRequestPermissionsResult(requestCode, permissions, grantResults, false);
    }

    @SuppressLint("Override")
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults, boolean goSetting) {

        List<String> listPermission = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED &&
                    !judgePermission(mActivity, permissions[i]) &&
                    Arrays.asList(dangerousPermission).contains(permissions[i])) {
                listPermission.add(permissions[i]);
            }
        }
        if (listPermission.size() > 0) {
            //todo 用户之前拒绝了并勾选了不再询问
            mPermissions.get(requestCode).notAskPermission(listPermission, goSetting);
        }
    }

    public void requestPermission(Activity activity, String[] permissions, String reason, CallBackListener runnable) {
        if (activity == null || permissions == null || permissions.length == 0 || runnable == null) {
            return;
        }
        mActivity = activity;

        if (Build.VERSION.SDK_INT < 23) {
            runnable.onResult(true);
            return;
        }
        final int requestCode = mPermissionIdx = mPermissionIdx++;
        mPermissions.put(requestCode, runnable);

        //是否需要请求权限
        boolean granted = true;
        for (String permission : permissions) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                granted = granted && mActivity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
            }
        }

        if (granted) {
            runnable.onResult(true);
            return;
        }

        //是否需要请求弹出窗
        boolean request = true;
        for (String permission : permissions) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                request = request && !mActivity.shouldShowRequestPermissionRationale(permission);
            }
        }

        if (!request) {
            final String[] permissionTemp = permissions;
            AlertDialog dialog = new AlertDialog.Builder(mActivity)
                    .setMessage(reason)
                    .setPositiveButton(sureText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                mActivity.requestPermissions(permissionTemp, requestCode);
                            }
                        }
                    })
                    .setNegativeButton(cancleText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            GrantedResult runnable = mPermissions.get(requestCode);
                            if (runnable == null) {
                                return;
                            }
                            //TODO  用户已经拒绝过一次 才提示
                            runnable.onResult(false);
                        }
                    }).create();

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(sureTextColor);
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(cancleTextColor);
            dialog.show();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mActivity.requestPermissions(permissions, requestCode);
            }
        }
    }

    public PermissionUtils setSuerText(String sureText) {
        this.sureText = sureText;
        return this;
    }

    public PermissionUtils setCancleText(String cancleText) {
        this.cancleText = cancleText;
        return this;
    }

    public PermissionUtils setSureAndCancleTextColor(int sureTextColor,int cancleTextColor) {
        this.sureTextColor = sureTextColor;
        this.cancleTextColor = cancleTextColor;
        return this;
    }
    interface GrantedResult {

        void onResult(boolean granted);

        void notAskPermission(List<String> permission, boolean goSetting);

    }

    public static class CallBackListener implements GrantedResult {

        @Override
        public void onResult(boolean granted) {

        }

        @Override
        public void notAskPermission(List<String> permission, boolean goSetting) {

        }
    }

    /**
     * 判断是否已拒绝过权限
     *
     * @return
     * @describe :如果应用之前请求过此权限但用户拒绝，此方法将返回 true;
     * -----------如果应用第一次请求权限或 用户在过去拒绝了权限请求，
     * -----------并在权限请求系统对话框中选择了 Don't ask again 选项，此方法将返回 false。
     */
    public boolean judgePermission(Context context, String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission))
            return true;
        else
            return false;
    }


    /**
     * 检测多个权限
     *
     * @return 未授权的权限
     */
    public List<String> checkMorePermission(Context context, String[] permissions) {
        List<String> permissionList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (!checkPermission(context, permissions[i]))
                permissionList.add(permissions[i]);
        }
        return permissionList;
    }

    /**
     * 检测权限
     *
     * @return true：已授权； false：未授权；
     */
    public boolean checkPermission(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED)
            return true;
        else
            return false;
    }

    /**
     * 跳转到权限设置界面
     */
    public void toSystemSetting(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(intent);
    }

}
