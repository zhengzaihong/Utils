package com.dz.utlis;

import android.app.Application;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.dz.utlis.view.RadiusRelativeLayout;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/11/20
 * creat_time: 16:58
 * describe 吐司工具，使用前一定的先初始化，
 * 初始化 Toast 放在 application 或者启动页
 *
 * 配合本库的 ToastUtil 可完成炫酷toast
 **/
@SuppressWarnings("all")
public class ToastTool {

    private static long lastToastTime = 0;

    private static volatile Options sOptions;

    private static Application application;


    //是否开启 Toast,在一些特殊场合可能需要动态 修改提不提示问题标志位
    public static boolean enableToast = true;


    private ToastTool() {
    }


    public static Options options() {
        if (sOptions == null) {
            sOptions = new Options();
        }
        return sOptions;
    }


    /**
     * 弹出Toast
     *
     * @param msg
     */
    public static void show(String msg) {
        showContent(msg, options());
    }

    public static void show(String msg, Options options) {
        showContent(msg, options);
    }


    private static void showContent(String msg, Options options) {

        if (!enableToast) {
            return;
        }
        if (null == application) {
            throw new IllegalArgumentException("Please initialize ToastTool before use");
        }

        if (System.currentTimeMillis() - lastToastTime >= options.getInterval()) {
            lastToastTime = System.currentTimeMillis();

            RadiusRelativeLayout toastBackGround = (RadiusRelativeLayout) AndroidUtils.getView(application, R.layout.layout_toast_view);
            toastBackGround.setOptions(options);

            TextView tvContent = toastBackGround.findViewById(R.id.tv_contnet);
            tvContent.setTextColor(options.getTextColor());
            tvContent.setTextSize(options.getTextSize());

            tvContent.setGravity(Gravity.CENTER);
            tvContent.setText(msg);

            ToastUtil toastUtil = new ToastUtil(application, toastBackGround, options.isLongTime() ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
            toastUtil.getToast().setGravity(options.getGravity(), 0, 0);
            toastUtil.show();
        }
    }


    // Toast的配置
    public static class Options {

        //背景颜色
        private int backGroundColor = Color.parseColor("#5A8BF4");

        //背景圆角度数
        private int radius = 30;

        // 边框线宽
        private int strokeWidth = 0;
        // 边框颜色
        private int strokeColor = Color.TRANSPARENT;

        //两次toast显示最小间隔时间 单位毫秒
        private int interval = 0;

        //toast文本的颜色 和 字体大小
        private int textColor = Color.WHITE;
        private int textSize = 16;

        //长时间的Toast
        private boolean longTime = true;

        //设置背景的 内距
        private int padding = 0;

        // 设置圆角类型
        private RadiusType radiusType = RadiusType.ALL_RADIUS;

        //toast显示的位置
        private int gravity = Gravity.CENTER;


        public int getBackGroundColor() {
            return backGroundColor;
        }

        public Options setBackGroundColor(int backGroundColor) {
            this.backGroundColor = backGroundColor;
            return this;
        }

        public int getRadius() {
            return radius;
        }

        public Options setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public int getStrokeWidth() {
            return strokeWidth;
        }

        public Options setStrokeWidth(int strokeWidth) {
            this.strokeWidth = strokeWidth;
            return this;
        }

        public int getStrokeColor() {
            return strokeColor;
        }

        public Options setStrokeColor(int strokeColor) {
            this.strokeColor = strokeColor;
            return this;
        }

        public int getInterval() {
            return interval;
        }

        public Options setInterval(int interval) {
            this.interval = interval;
            return this;
        }

        public int getTextColor() {
            return textColor;
        }

        public Options setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public int getTextSize() {
            return textSize;
        }

        public Options setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public boolean isLongTime() {
            return longTime;
        }

        public Options setLongTime(boolean longTime) {
            this.longTime = longTime;
            return this;
        }

        public int getPadding() {
            return padding;
        }

        public Options setPadding(int padding) {
            this.padding = padding;
            return this;
        }

        public RadiusType getRadiusType() {
            return radiusType;
        }

        public Options setRadiusType(RadiusType radiusType) {
            this.radiusType = radiusType;
            return this;
        }

        public int getGravity() {
            return gravity;
        }

        public Options setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }


        public Options build(Application application) {
            create(application);
            return this;
        }
    }

    private static void create(Application context) {
        application = context;
    }


    public static enum RadiusType {
        /**
         * 左边_上_下圆角
         */
        LEFT_TOP_BOTTOM_RADIUS(),
        /**
         * 右边_上_下圆角
         */
        RIGHT_TOP_BOOTOM_RADIUS,
        /**
         * 左边_上圆角
         */
        LEFT_TOP_RADIUS,
        /**
         * 左边_下圆角
         */
        LEFT_BOTTOM_RADIUS,

        /**
         * 右边_上圆角
         */
        RIGHT_TOP_RADIUS,

        /**
         * 右边_下圆角
         */
        RIGHT_BOOTOM_RADIUS,

        /**
         * 四边圆角
         */
        ALL_RADIUS,

        /**
         * 无圆角
         */
        NONE_RADIUS

    }
}
