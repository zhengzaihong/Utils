package com.dz.utlis;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2017/5/22 0022
 * create_time: 16:56
 * describe: 打开或关闭软键盘
 **/


public class KeyBoardUtils {
    /**
     * 打开软键盘
     * 魅族可能会有问题
     *
     * @param mEditText
     * @param mContext
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText
     * @param mContext
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }


    /**
     * des:隐藏软键盘,这种方式参数为activity
     *
     * @param activity
     */
    public static void hideInputForce(Activity activity) {
        if (activity == null || activity.getCurrentFocus() == null)
            return;

        ((InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 打开键盘
     **/
    public static void showInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }

    /**
     * @param context 上下文
     * @return isOpen若返回true，则表示输入法打开
     */
    public static boolean InputMethodManagerStatus(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        return isOpen;
    }


    /**
     * 关闭输入法
     *
     * @param context
     * @param activity 当前的activity
     */
    public static void closeInputMethodManagerStatus(Context context, Activity activity) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏软键盘
     *
     * @param @param context
     */
    public static void hiddenKeyBoard(Context context) {
        final View v = ((Activity) context).getWindow().peekDecorView();
        if (v != null && v.getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘 用线程才能开启
     *
     * @param editText
     */
    public static void showKeyBoard(final EditText editText) {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(200);
                    InputMethodManager inputManager = (InputMethodManager) editText
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(editText, 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void showKeyBoard3(final View view) {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(200);
                    InputMethodManager inputManager = (InputMethodManager) view
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(view, 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


}