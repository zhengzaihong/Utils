package com.dz.utlis;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.dz.utlis.bean.AppInfo;

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

}
