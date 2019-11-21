package com.dz.utlis;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.dz.utlis.bean.AppInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *creat_user: zhengzaihong
 *email:1096877329@qq.com
 *creat_date: 2018/12/25 0025
 *creat_time: 18:07
 *describe: apk相关操作
 **/

public class ApkUtil {

	private static final String TAG = "----ApkUtil----";

	// 版本名
	public static String getVersionName(Context context) {
		return getPackageInfo(context).versionName;
	}

	// 版本号
	public static int getVersionCode(Context context) {
		return getPackageInfo(context).versionCode;
	}

	private static PackageInfo getPackageInfo(Context context) {
		PackageInfo pi = null;

		try {
			PackageManager pm = context.getPackageManager();
			pi = pm.getPackageInfo(context.getPackageName(),
					PackageManager.GET_CONFIGURATIONS);

			return pi;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pi;
	}

	// 获取手机全部安装的apk 信息
	public static List<AppInfo> queryAppInfo(Context context) {
		PackageManager pm = context.getPackageManager();
		// 查询所有已经安装的应用程序
		List<ApplicationInfo> listAppcations = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
		//   Collections.sort(listAppcations, new ApplicationInfo.DisplayNameComparator(pm));// 排序
		List<AppInfo> appInfos = new ArrayList<>();
		for (ApplicationInfo app : listAppcations) {
			AppInfo appInfo = new AppInfo();
			appInfo.setAppLabel((String) app.loadLabel(pm));
			appInfo.setAppIcon(app.loadIcon(pm));
			appInfo.setPkgName(app.packageName);
			appInfos.add(appInfo);
		}
		return appInfos;
	}


	/**
	 * 6.0以上系统 注意添加如下权限在清单文件
	 *
	 *     <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
	 *
	 *
	 *  大于 24 版本添加如下：
	 *
	 *           <provider
	 *             android:name="android.support.v4.content.FileProvider"
	 *             android:authorities="${applicationId}.utils.fileprovider"
	 *             android:exported="false"
	 *             android:grantUriPermissions="true">
	 *             <meta-data
	 *                 android:name="android.support.FILE_PROVIDER_PATHS"
	 *                 android:resource="@xml/file_paths" />
	 *         </provider>
	 *
	 *
	 * <?xml version="1.0" encoding="utf-8"?>
	 * <paths xmlns:android="http://schemas.android.com/apk/res/android">
	 *     <external-path name="external" path="" />
	 *
	 *
	 *     <files-path
	 *         name="images"
	 *         path="Pictures/" />
	 *     <!--    <external-path
	 *             name="images"
	 *             path="Pictures" />-->
	 *     <external-files-path
	 *         name="images"
	 *         path="Pictures/" />
	 *     <!--   <root-path
	 *             name="images"
	 *             path="." />-->
	 *     <!--
	 *       <files-path/>代表的根目录： Context.getFilesDir()
	 *       <external-path/>代表的根目录: Environment.getExternalStorageDirectory()
	 *       <cache-path/>代表的根目录: getCacheDir()
	 *       <external-files-path/>代表的根目录 Context.getExternalFilesDir(String)
	 *       <external-cache-path/>代表的根目录Context.getExternalCacheDir()
	 *
	 *       external-cache-path在support-v4:24.0.0这个版本并未支持，直到support-v4:25.0.0才支持
	 *       以上是官方提供的几种path类型，不过如果你想使用外置SD卡，可以用这个：
	 *       <root-path name="name" path="path" />
	 *       -->
	 * </paths>
	 *
	 *
	 * @param context
	 * @param packName 程序包名
	 * @param filePath 文件路径
	 */
	public static void install(Context context,String packName,String filePath) {
		Log.i(TAG, "开始执行安装: " + filePath);

		File apkFile = new File(filePath);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			//使用前注意初始化 ToastTool 工具
			ToastTool.get().show("开始执行安装");
			Log.w(TAG, "版本大于 N ，开始使用 fileProvider 进行安装");
			intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			Uri contentUri = FileProvider.getUriForFile(
					context
					, packName + ".utils.fileprovider"
					, apkFile);

			intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
		} else {
			Log.w(TAG, "正常进行安装");
			intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
		}
		context.startActivity(intent);
	}

}
