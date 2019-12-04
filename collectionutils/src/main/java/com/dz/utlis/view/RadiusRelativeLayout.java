package com.dz.utlis.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import static com.dz.utlis.view.ToastConfig.RadiusType.ALL_RADIUS;
import static com.dz.utlis.view.ToastConfig.RadiusType.LEFT_BOTTOM_RADIUS;
import static com.dz.utlis.view.ToastConfig.RadiusType.LEFT_TOP_BOTTOM_RADIUS;
import static com.dz.utlis.view.ToastConfig.RadiusType.LEFT_TOP_RADIUS;
import static com.dz.utlis.view.ToastConfig.RadiusType.NONE_RADIUS;
import static com.dz.utlis.view.ToastConfig.RadiusType.RIGHT_BOOTOM_RADIUS;
import static com.dz.utlis.view.ToastConfig.RadiusType.RIGHT_TOP_BOOTOM_RADIUS;
import static com.dz.utlis.view.ToastConfig.RadiusType.RIGHT_TOP_RADIUS;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/11/20
 * creat_time: 16:42
 * describe Toast的背景View 支持圆角
 **/
public class RadiusRelativeLayout extends RelativeLayout {

    private ToastConfig config = new ToastConfig();

    public RadiusRelativeLayout(Context context) {
        this(context, null);
    }

    public RadiusRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadiusRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 设置样式配置参数
     *
     * @param configBean
     */
    public void setConfig(ToastConfig configBean) {
        this.config = configBean;
        // 设置背景
        initView();
    }

    private void initView() {

        int padding = config.getBgPadding();

        this.setPadding(padding, padding, padding, padding);

        Drawable drawable = createShape(config);

        StateListDrawable stateListDrawable = new StateListDrawable();

        stateListDrawable.addState(new int[]{}, drawable);

        setBackground(stateListDrawable);
    }


    public static GradientDrawable createShape(ToastConfig config) {
        GradientDrawable drawable = new GradientDrawable();
        ToastConfig.RadiusType radiusType = config.getRadiusType();
        float radius = config.getRadiusBg();
        if (radiusType == LEFT_TOP_BOTTOM_RADIUS) {
            //1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
            drawable.setCornerRadii(new float[]{radius, radius, 0, 0, 0, 0, radius, radius});
        } else if (radiusType == RIGHT_TOP_BOOTOM_RADIUS) {
            drawable.setCornerRadii(new float[]{0, 0, radius, radius, radius, radius, 0, 0});
        } else if (radiusType == LEFT_BOTTOM_RADIUS) {
            drawable.setCornerRadii(new float[]{0, 0, 0, 0, 0, 0, radius, radius});
        } else if (radiusType == LEFT_TOP_RADIUS) {
            drawable.setCornerRadii(new float[]{radius, radius, 0, 0, 0, 0, 0, 0});
        } else if (radiusType == RIGHT_TOP_RADIUS) {
            drawable.setCornerRadii(new float[]{0, 0, radius, radius, 0, 0, 0, 0});
        } else if (radiusType == RIGHT_BOOTOM_RADIUS) {
            drawable.setCornerRadii(new float[]{0, 0, 0, 0, radius, radius, 0, 0});
        } else if (radiusType == ALL_RADIUS) {
            //设置4个角的弧度
            drawable.setCornerRadius(radius);
        } else if (radiusType == NONE_RADIUS) {
            drawable.setCornerRadius(0);
        }
        // 设置背景颜色
        drawable.setColor(config.getToastViewGroupBgColor());
        // 设置边框颜色
        drawable.setStroke(config.getStrokeWidth(), config.getStrokeColor());
        return drawable;
    }

}
