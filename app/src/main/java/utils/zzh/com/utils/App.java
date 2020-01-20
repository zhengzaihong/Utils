package utils.zzh.com.utils;

import android.app.Application;
import android.graphics.Color;
import android.view.Gravity;

import com.dz.utlis.FontUtils;
import com.dz.utlis.JavaUtils;
import com.dz.utlis.ScreenUtils;
import com.dz.utlis.ToastTool;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        String font = "fonts/Lobster-1.4.otf";

        FontUtils.getInstance().replaceSystemDefaultFontFromAsset(this, font); // .otf 字体文件也可

        //开启打印
        JavaUtils.isdebug = true;


        //初始化 Toast工具
        ToastTool.options()
                .setInterval(2000)
                .setRadius((int) ScreenUtils.dip2px(this, 15))
                .setTextColor(Color.WHITE)
                .setBackGroundColor(Color.parseColor("#e0a0d0"))
                .setTextSize(16)
                .setPadding((int) ScreenUtils.dip2px(this, 15))
                .setGravity(Gravity.CENTER)
                .setLongTime(false)
                .setStrokeWidth(0)
                .setRadiusType(ToastTool.RadiusType.ALL_RADIUS)
                .setStrokeColor(Color.TRANSPARENT)
                .build(this);
    }
}
