package com.dz.utlis.view;

import android.graphics.Color;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/11/20
 * creat_time: 16:43
 * describe toast的配置
 **/
public class ToastConfig {


    //背景颜色
    private int toastViewGroupBgColor = Color.parseColor("#5A8BF4");

    //背景圆角度数
    private int radiusBg = 30;

    // 边框线宽
    private int strokeWidth = 0;
    // 边框颜色
    private int strokeColor = Color.TRANSPARENT;

    //两次toast显示最小间隔时间 单位毫秒
    private int interval = 0;

    //toast文本的颜色 和 字体大小
    private int toastTextColor = Color.WHITE;
    private int toastTextSize = 16;

    //短时间显示Toast
    private boolean shortToast = true;

    //设置背景的 内距
    private int bgPadding = 0;

    // 设置圆角类型
    private RadiusType radiusType = RadiusType.ALL_RADIUS;

    public enum RadiusType {
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

    public int getBgPadding() {
        return bgPadding;
    }

    public ToastConfig setBgPadding(int bgPadding) {
        this.bgPadding = bgPadding;
        return this;
    }

    public boolean isShortToast() {
        return shortToast;
    }

    public ToastConfig setShortToast(boolean shortToast) {
        this.shortToast = shortToast;
        return this;
    }


    public int getToastTextColor() {
        return toastTextColor;
    }

    public ToastConfig setToastTextColor(int toastTextColor) {
        this.toastTextColor = toastTextColor;
        return this;
    }

    public int getToastTextSize() {
        return toastTextSize;
    }

    public ToastConfig setToastTextSize(int toastTextSize) {
        this.toastTextSize = toastTextSize;
        return this;
    }

    public int getInterval() {
        return interval;
    }

    public ToastConfig setInterval(int interval) {
        this.interval = interval;
        return this;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public ToastConfig setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        return this;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public ToastConfig setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        return this;
    }

    public int getToastViewGroupBgColor() {
        return toastViewGroupBgColor;
    }

    public ToastConfig setToastViewGroupBgColor(int toastViewGroupBgColor) {
        this.toastViewGroupBgColor = toastViewGroupBgColor;
        return this;
    }

    public int getRadiusBg() {
        return radiusBg;
    }

    public ToastConfig setRadiusBg(int radiusBg) {
        this.radiusBg = radiusBg;
        return this;
    }

    public RadiusType getRadiusType() {
        return radiusType;
    }

    public ToastConfig setRadiusType(RadiusType radiusType) {
        this.radiusType = radiusType;
        return this;
    }
}
