package com.dz.utlis;

import android.app.Application;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.dz.utlis.view.ToastConfig;
import com.dz.utlis.view.ToastViewGroup;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/11/20
 * creat_time: 16:58
 * describe 一个单例吐司
 * 配合本库的 ToastUtil 可完成炫酷toast
 **/
public class ToastTool {

    private static ToastTool toastTool;

    private static long toastTime = 0;

    private ToastConfig config = new ToastConfig();

    private  Application application;

    public static ToastTool get() {

        if (null == toastTool) {
            synchronized (ToastTool.class) {

                if (null == toastTool) {

                    toastTool = new ToastTool();
                }
            }
        }
        return toastTool;
    }


    /**
     * 初始化 Toast 放在 application 或者启动的第一个界面
     * @param application
     * @param config
     */
    public void initConfig(Application application,ToastConfig config) {
        this.application = application;
        this.config = config;

    }

    /**
     * toast
     * @param msg
     */
    public void show(String msg) {

        if (System.currentTimeMillis() - toastTime <= config.getInterval()) {
            toastTime = System.currentTimeMillis();
            return;
        }

        ToastViewGroup toastViewGroup = (ToastViewGroup) AndroidUtils.getView(application, R.layout.layout_toast_view);
        toastViewGroup.setConfig(config);

        TextView tvContent = toastViewGroup.findViewById(R.id.tv_contnet);
        tvContent.setTextColor(config.getToastTextColor());
        tvContent.setTextSize(config.getToastTextSize());

        tvContent.setGravity(Gravity.CENTER);
        tvContent.setText(msg);

        ToastUtil toastUtil = new ToastUtil(application, toastViewGroup, config.isShortToast()?Toast.LENGTH_SHORT:Toast.LENGTH_LONG);
        toastUtil.getToast().setGravity(Gravity.CENTER, 0, 0);
        toastUtil.show();

    }


    /**
     * 临时toast
     * @param msg
     * @param config
     */

    public void show(String msg,ToastConfig config) {

        if (System.currentTimeMillis() - toastTime <= config.getInterval()) {
            toastTime = System.currentTimeMillis();
            return;
        }

        ToastViewGroup toastViewGroup = (ToastViewGroup) AndroidUtils.getView(application, R.layout.layout_toast_view);
        toastViewGroup.setConfig(config);

        TextView tvContent = toastViewGroup.findViewById(R.id.tv_contnet);
        tvContent.setTextColor(config.getToastTextColor());
        tvContent.setTextSize(config.getToastTextSize());

        tvContent.setGravity(Gravity.CENTER);
        tvContent.setText(msg);

        ToastUtil toastUtil = new ToastUtil(application, toastViewGroup, config.isShortToast()?Toast.LENGTH_SHORT:Toast.LENGTH_LONG);
        toastUtil.getToast().setGravity(Gravity.CENTER, 0, 0);
        toastUtil.show();
    }


}
