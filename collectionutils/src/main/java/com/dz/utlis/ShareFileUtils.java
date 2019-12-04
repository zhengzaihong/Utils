package com.dz.utlis;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/11/20
 * creat_time: 11:26
 * describe 系统分享功能
 * 使用该工具 请先初始化 ToastTool 工具，或者禁用 ToastTool提示
 **/
@SuppressWarnings("all")
public class ShareFileUtils {

    private static final String TAG = "---ShareFileUtils---";

    /**
     * 分享文本
     *
     * @param context
     * @param content
     */
    public static void shareContent(Context context, String content) {
        if (TextUtils.isEmpty(content)) {
            ToastTool.showContent("分享内容不能为空");
            return;
        }

        checkFileUriExposure();
        Intent it = new Intent(Intent.ACTION_SEND);
        it.putExtra(Intent.EXTRA_TEXT, content);
        it.setType("text/plain");
        context.startActivity(Intent.createChooser(it, "分享到"));
    }

    /**
     * 分享文件
     *
     * @param context
     * @param file
     */
    public static void shareFile(Context context, String file) {
        if (!FileUtils.isFile(file)) {
            ToastTool.showContent("分享文件地址不正确");
            return;
        }

        checkFileUriExposure();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(file)));  //传输图片或者文件 采用流的方式
        intent.setType("*/*");   //分享文件
        context.startActivity(Intent.createChooser(intent, "分享"));
    }

    /**
     * 分享单张图片
     *
     * @param context
     * @param path
     */
    public static void shareImage(Context context, String path) {
        shareImage(context, path, null, null, null);
    }

    /**
     * 分享多张图片
     *
     * @param context
     * @param pathList
     */
    public static void shareImage(Context context, List<String> pathList) {
        shareImage(context, null, pathList, null, null);
    }

    /**
     * 分享到微信好友，单图
     */
    public static void shareImageToWeChat(Context context, String path) {
        //判断是否安装微信，如果没有安装微信 又没有判断就直达微信分享是会挂掉的
        if (!isAppInstall(context, "com.tencent.mm")) {

            ToastTool.showContent("您还没有安装微信");

            return;
        }
        shareImage(context, path, null, "com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
    }

    /**
     * 分享到微信好友，多图
     */
    public static void shareImageToWeChat(final Context context, List<String> pathList) {
        //判断是否安装微信，如果没有安装微信 又没有判断就直达微信分享是会挂掉的
        if (!isAppInstall(context, "com.tencent.mm")) {
            ToastTool.showContent("您还没有安装微信");
            return;
        }
        shareImage(context, null, pathList, "com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
    }

    /**
     * 分享到微信朋友圈，单图
     */
    public static void shareImageToWeChatFriend(Context context, String path) {
        if (!isAppInstall(context, "com.tencent.mm")) {
            ToastTool.showContent("您还没有安装微信");
            return;
        }
        shareImage(context, path, null, "com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
    }

    /**
     * 分享到微信朋友圈，多图
     */
    public static void shareImageToWeChatFriend(Context context, List<String> pathList) {
        if (!isAppInstall(context, "com.tencent.mm")) {
            ToastTool.showContent("您还没有安装微信");
            return;
        }
        shareImage(context, null, pathList, "com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
    }

    /**
     * 分享图片给QQ好友，单图
     */
    public static void shareImageToQQ(Context context, String path) {
        if (!isAppInstall(context, "com.tencent.mobileqq")) {
            ToastTool.showContent("您还没有安装QQ");
            return;
        }
        shareImage(context, path, null, "com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
    }


    /**
     * 分享图片给QQ好友，多图
     */
    public static void shareImageToQQ(Context context, List<String> pathList) {
        if (!isAppInstall(context, "com.tencent.mobileqq")) {
            ToastTool.showContent("您还没有安装QQ");
            return;
        }
        shareImage(context, null, pathList, "com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
    }


    /**
     * 分享图片到QQ空间，单图
     */
    public static void shareImageToQZone(Context context, String path) {
        if (!isAppInstall(context, "com.qzone")) {
            ToastTool.showContent("您还没有安装QQ空间");
            return;
        }
        shareImage(context, path, null, "com.qzone", "com.qzonex.module.operation.ui.QZonePublishMoodActivity");
    }


    /**
     * 分享图片到QQ空间，多图
     */
    public static void shareImageToQZone(Context context, List<String> pathList) {
        if (!isAppInstall(context, "com.qzone")) {
            ToastTool.showContent("您还没有安装QQ空间");
            return;
        }
        shareImage(context, null, pathList, "com.qzone", "com.qzonex.module.operation.ui.QZonePublishMoodActivity");
    }

    /**
     * 分享图片到微博，单图
     */
    public static void shareImageToWeibo(Context context, String path) {
        if (!isAppInstall(context, "com.sina.weibo")) {
            ToastTool.showContent("您还没有安装新浪微博");
            return;
        }
        shareImage(context, path, null, "com.sina.weibo", "com.sina.weibo.EditActivity");
    }


    /**
     * 分享图片到微博，多图
     */
    public static void shareImageToWeibo(Context context, List<String> pathList) {
        if (!isAppInstall(context, "com.sina.weibo")) {
            ToastTool.showContent("您还没有安装新浪微博");
            return;
        }
        shareImage(context, null, pathList, "com.sina.weibo", "com.sina.weibo.EditActivity");
    }

    /**
     * 检测手机是否安装某个应用
     *
     * @param context
     * @param appPackageName 应用包名
     * @return true-安装，false-未安装
     */
    public static boolean isAppInstall(Context context, String appPackageName) {
        PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (appPackageName.equals(pn)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 分享前必须执行本代码，主要用于兼容SDK18以上的系统
     */
    private static void checkFileUriExposure() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
    }

    /**
     * @param context  上下文
     * @param path     不为空的时候，表示分享单张图片，会检验图片文件是否存在
     * @param pathList 不为空的时候表示分享多张图片，会检验每一张图片是否存在
     * @param pkg      分享到的指定app的包名
     * @param cls      分享到的页面（微博不需要指定页面）
     */
    private static void shareImage(Context context, String path, List<String> pathList, String pkg, String cls) {
        if (path == null && pathList == null) {
            ToastTool.showContent("找不到您要分享的图片文件");
            return;
        }

        checkFileUriExposure();

        try {
            if (path != null) {
                //单张图片
                if (!FileUtils.isFile(path)) {
                    ToastTool.showContent("图片不存在，请检查后重试");
                    return;
                }

                Intent intent = new Intent();
                if (pkg != null && cls != null) {
                    //指定分享到的app
                    if (pkg.equals("com.sina.weibo")) {
                        //微博分享的需要特殊处理
                        intent.setPackage(pkg);
                    } else {
                        ComponentName comp = new ComponentName(pkg, cls);
                        intent.setComponent(comp);
                    }
                }
                intent.setAction(Intent.ACTION_SEND);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)));
                intent.setType("image/*");   //分享文件
                context.startActivity(Intent.createChooser(intent, "分享"));
            } else {
                //多张图片
                ArrayList<Uri> uriList = new ArrayList<>();
                for (int i = 0; i < pathList.size(); i++) {
                    if (!FileUtils.isFile(pathList.get(i))) {
                        ToastTool.showContent("第" + (i + 1) + "张图片不存在，请检查后重试");
                        return;
                    }
                    uriList.add(Uri.fromFile(new File(pathList.get(i))));
                }

                Intent intent = new Intent();

                if (pkg != null && cls != null) {
                    //指定分享到的app
                    if (pkg.equals("com.sina.weibo")) {
                        //微博分享的需要特殊处理
                        intent.setPackage(pkg);
                    } else {
                        ComponentName comp = new ComponentName(pkg, cls);
                        intent.setComponent(comp);
                    }
                }
                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setType("image/*");
                context.startActivity(Intent.createChooser(intent, "分享"));
            }

        } catch (Exception e) {
            ToastTool.showContent("分享失败，未知错误");

        }
    }


}
