package com.dz.utlis;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.support.annotation.RequiresApi;


import com.dz.utlis.bean.StorageInfo;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *creat_user: zhengzaihong
 *email:1096877329@qq.com
 *creat_date: 2018/5/27
 *creat_time: 2:10
 *describe: 手机sdcard
 **/
public class SdCardUtil {

	/*
	 * 获取SD卡的剩余容量
	 */
	public static long getSDFreeSize() {
		// 取得SD卡文件路径
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		// 获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		// 空闲的数据块的数量
		long freeBlocks = sf.getAvailableBlocks();
		// 返回SD卡空闲大小
		// return freeBlocks * blockSize; //单位Byte
		// return (freeBlocks * blockSize)/1024; //单位KB
		return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
	}

	/*
	 * 获取SD卡的总容量
	 */
	public static long getSDAllSize() {
		// 取得SD卡文件路径
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		// 获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		// 获取所有数据块数
		long allBlocks = sf.getBlockCount();
		// 返回SD卡大小
		// return allBlocks * blockSize; //单位Byte
		// return (allBlocks * blockSize)/1024; //单位KB
		return (allBlocks * blockSize) / 1024 / 1024; // 单位MB
	}

	/**
	 * 获得机身内存大小
	 * 
	 * @return
	 */
	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
	public static long getRomTotalSize() {
		File path = Environment.getDataDirectory();
		StatFs statFs = new StatFs(path.getPath());
		long blockSize = statFs.getBlockSizeLong();
		long tatalBlocks = statFs.getBlockCountLong();
		return blockSize * tatalBlocks;
	}
	
	
	 /**
     * 获得机身可用内存
     * @return
     */
	 @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
	 public static long getRomAvailableSize()
    {
        File path= Environment.getDataDirectory();
        StatFs statFs=new StatFs(path.getPath());
        long blockSize=statFs.getBlockSizeLong();
        long availableBlocks=statFs.getAvailableBlocksLong();
        return blockSize*availableBlocks;
    }

    //获取某路径下空间大小
   @SuppressWarnings("deprecation")
    public static long getTotalSpace(File path) {
	   if (path == null) {
		   return -1;
	   }
	   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
		   return path.getTotalSpace();
	   } else {
		   if (!path.exists()) {
			   return 0;
		   } else {
			   final StatFs stats = new StatFs(path.getPath());
			   // Using deprecated method in low API level system,
			   // add @SuppressWarnings("description") to suppress the warning
			   return (long) stats.getBlockSize() * (long) stats.getBlockCount();
		   }
	   }
   }

	private Activity mActivity;
	private StorageManager mStorageManager;
	private Method mMethodGetPaths;

	public SdCardUtil(Activity activity) {
		mActivity = activity;
		if (mActivity != null) {
			mStorageManager = (StorageManager) mActivity
					.getSystemService(Activity.STORAGE_SERVICE);
			try {
				mMethodGetPaths = mStorageManager.getClass().getMethod(
						"getVolumePaths");
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}

	public String[] getVolumePaths() {
		String[] paths = null;
		try {
			paths = (String[]) mMethodGetPaths.invoke(mStorageManager);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return paths;
	}

	@SuppressLint("NewApi")
	public static List<StorageInfo> listAvaliableStorage(Context context) {
		ArrayList<StorageInfo> storagges = new ArrayList<StorageInfo>();
		StorageManager storageManager = (StorageManager) context
				.getSystemService(Context.STORAGE_SERVICE);
		try {
			Class<?>[] paramClasses = {};
			Method getVolumeList = StorageManager.class.getMethod(
					"getVolumeList", paramClasses);
			getVolumeList.setAccessible(true);
			Object[] params = {};
			Object[] invokes = (Object[]) getVolumeList.invoke(storageManager,
					params);
			if (invokes != null) {
				StorageInfo info = null;
				for (int i = 0; i < invokes.length; i++) {
					Object obj = invokes[i];
					Method getPath = obj.getClass().getMethod("getPath"
					);
					String path = (String) getPath.invoke(obj);
					info = new StorageInfo(path);
					File file = new File(info.path);
					if ((file.exists()) && (file.isDirectory())
							&& (file.canWrite())) {
						Method isRemovable = obj.getClass().getMethod(
								"isRemovable");
						String state = null;
						try {
							Method getVolumeState = StorageManager.class
									.getMethod("getVolumeState", String.class);
							state = (String) getVolumeState.invoke(
									storageManager, info.path);
							info.state = state;
						} catch (Exception e) {
							e.printStackTrace();
						}

						if (info.isMounted()) {
							info.isRemoveable = ((Boolean) isRemovable.invoke(
									obj)).booleanValue();
							storagges.add(info);
						}
					}
				}
			}
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		storagges.trimToSize();

		return storagges;
	}


	public static File getAppRootPath(Context context) {
		if (sdCardIsAvailable()) {
			return Environment.getExternalStorageDirectory();
		} else {
			return context.getFilesDir();
		}
	}

	public static boolean sdCardIsAvailable() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File sd = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
			return sd.canWrite();
		} else
			return false;
	}
}
