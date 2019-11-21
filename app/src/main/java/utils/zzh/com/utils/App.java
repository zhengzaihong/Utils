package utils.zzh.com.utils;

import android.app.Application;
import android.graphics.Color;

import com.dz.utlis.FontUtils;
import com.dz.utlis.JavaUtils;
import com.dz.utlis.ScreenUtils;
import com.dz.utlis.ToastTool;
import com.dz.utlis.view.ToastConfig;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        String font = "fonts/Lobster-1.4.otf";

        FontUtils.getInstance().replaceSystemDefaultFontFromAsset(this, font); // .otf 字体文件也可


        //开启打印
        JavaUtils.isdebug = true;

        // Toast 配置
        ToastConfig config = new ToastConfig()
            .setInterval(2000)
            .setRadiusBg((int) ScreenUtils.dip2px(this,30))
            .setToastTextColor(Color.RED)
            .setToastViewGroupBgColor(Color.GREEN)
            .setToastTextSize(16)
            .setBgPadding((int) ScreenUtils.dip2px(this,15))
            .setShortToast(false)
            .setStrokeWidth(3)
            .setRadiusType(ToastConfig.RadiusType.ALL_RADIUS)
            .setStrokeColor(Color.GREEN);

        //初始化 Toast工具
        ToastTool.get().initConfig(this,config);
    }
}
