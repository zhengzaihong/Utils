package com.dz.utlis;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *create_user: zhengzaihong
 *email:1096877329@qq.com
 *create_date: 2017/5/22 0022
 *create_time: 14:50
 *describe: 获得屏幕相关的辅助类
 **/



public class ScreenUtils
{
	private ScreenUtils()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}


	public static final int DISPLAY_SMALL = 0;
	public static final int DISPLAY_MIDDLE = 1;
	public static final int DISPLAY_LARGE = 2;
	public static final int DISPLAY_XLARGE = 3;

	private static boolean isInitialize = false;
	/**
	 * 屏幕宽度
	 **/
	public static int screenWidth;
	/**
	 * 屏幕高度
	 **/
	public static int screenHeight;
	/**
	 * 状态栏高度
	 */
	public static int statusBarHeight;
	/**
	 * 屏幕密度
	 **/
	public static int screenDpi;
	/**
	 * 逻辑密度, 屏幕缩放因子
	 */
	public static float density = 1;
	/**
	 * 字体缩放因子
	 */
	public static float scaledDensity;
	/**
	 * 屏幕类型
	 */
	public static int displayType;

	/**
	 * 初始化屏幕宽度和高度
	 */
	public static void initScreen(Activity activity) {
		if (isInitialize) return;

		isInitialize = true;
		Display display = activity.getWindowManager().getDefaultDisplay();
		DisplayMetrics metric = new DisplayMetrics();

		// 屏幕宽度、高度、密度、缩放因子
		if (Build.VERSION.SDK_INT >= 17) {
			display.getRealMetrics(metric);
		} else {
			try {
				Class<?> clazz = Class.forName("android.view.Display");
				Method method = clazz.getMethod("getRealMetrics", DisplayMetrics.class);
				method.invoke(display, metric);
			} catch (Throwable e) {
				display.getMetrics(metric);
			}
		}

		try {
			// 状态栏高度
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Field field = clazz.getField("status_bar_height");
			int x = Integer.parseInt(field.get(clazz.newInstance()).toString());
			statusBarHeight = activity.getResources().getDimensionPixelSize(x);
		} catch (Throwable e) {
			e.printStackTrace();
			Rect outRect = new Rect();
			activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
			statusBarHeight = outRect.top;
		}

		screenWidth = metric.widthPixels;
		screenHeight = metric.heightPixels;
		screenDpi = metric.densityDpi;
		density = metric.density;
		scaledDensity = metric.scaledDensity;

		if (screenWidth >= 320 && screenWidth < 480) {
			displayType = DISPLAY_SMALL;
		} else if (screenWidth >= 480 && screenWidth < 720) {
			displayType = DISPLAY_MIDDLE;
		} else if (screenWidth >= 720 && screenWidth < 1080) {
			displayType = DISPLAY_LARGE;
		} else {
			displayType = DISPLAY_XLARGE;
		}
	}

	/**
	 * 是否是横屏
	 */
	public static boolean isHorizontal() {
		return screenWidth > screenHeight;
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * @param pxValue
	 * @return
	 */
	public static float px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 *
	 * @param spValue
	 * @return
	 */
	public static float sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (spValue * fontScale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static float dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static float px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (pxValue / scale + 0.5f);
	}

	/**
	 * 获得屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context)
	{
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/**
	 * 获得屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context)
	{
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}




	/**
	 * 判断是否锁屏
	 */
	public static boolean isCloseSrceen(Context context) {
		KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
		boolean locked = km.inKeyguardRestrictedInputMode();
		return locked;
	}


	/**
	 * 获得状态栏的高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context)
	{

		int statusHeight = -1;
		try
		{
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return statusHeight;
	}

	/**
	 * 获取当前屏幕截图，包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity)
	{
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity)
	{
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return bp;

	}


	/**
	 * 获取当前屏幕密度
	 * @param activity
	 * @return
	 */
	public static int getcurrentDimens(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int mDensity = metrics.densityDpi;
		return mDensity;
	}

}
