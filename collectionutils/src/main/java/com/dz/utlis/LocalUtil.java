package com.dz.utlis;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
* create_user: zhengzaihong 
* Email:1096877329@qq.com
* create_date: 2016/3/26
* create_time: 15:49
* describe 当前系统缓存获取
**/
public class LocalUtil {


    private LocalUtil(){
    }

    /**
     * 计算缓存的大小,
     * @return
     */
    public static String getCacheSize(Context context) {
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = context.getApplicationContext().getFilesDir();// /data/data/package_name/files
        File cacheDir = context.getCacheDir();// /data/data/package_name/cache
        fileSize += getDirSize(filesDir);
        fileSize += getDirSize(cacheDir);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            File externalCacheDir = getExternalCacheDir(context);// "<sdcard>/Android/data/<package_name>/cache/"
            fileSize += getDirSize(externalCacheDir);
        }
        if (fileSize > 0)
            cacheSize = formatFileSize(fileSize);
        return cacheSize;
    }

    /**
     * 清除app缓存
     */
    public static void clearAppCache(Context context) {
        // 清除数据缓存
        clearCacheFolder(context.getFilesDir(), System.currentTimeMillis());
        clearCacheFolder(context.getCacheDir(), System.currentTimeMillis());
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            clearCacheFolder(getExternalCacheDir(context),
                    System.currentTimeMillis());
        }
    }
    /**
     * 清除缓存目录
     *
     * @param dir     目录
     * @param curTime 当前系统时间
     * @return
     */
    private static int clearCacheFolder(File dir, long curTime) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, curTime);
                    }
                    if (child.lastModified() < curTime) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }


    /**
     * 获取目录文件大小
     *
     * @param dir
     * @return
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }
    /**
     * 将二进制长度转换成文件大小
     *
     * @param length
     * @return
     */
    public static String formatFileSize(long length) {
        String result = null;
        int sub_string = 0;
        if (length >= 1073741824) {
            sub_string = String.valueOf((float) length / 1073741824).indexOf(
                    ".");

            result = ((float) length / 1073741824 + "000").substring(0,
                    sub_string + 3) + "GB";


        } else if (length >= 1048576) {
            sub_string = String.valueOf((float) length / 1048576).indexOf(".");
            result = ((float) length / 1048576 + "000").substring(0,
                    sub_string + 3) + "MB";
        } else if (length >= 1024) {
            sub_string = String.valueOf((float) length / 1024).indexOf(".");
            result = ((float) length / 1024 + "000").substring(0,
                    sub_string + 3) + "KB";
        } else if (length < 1024)
            result = Long.toString(length) + "B";
        return result;
    }


    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }
    public static File getExternalCacheDir(Context context) {
        // return context.getExternalCacheDir(); API level 8
        // e.g. "<sdcard>/Android/data/<package_name>/cache/"
        return context.getExternalCacheDir();
    }

    /**
     * 清楚sd卡上所有的缓存
     * @param context
     */

    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

}
