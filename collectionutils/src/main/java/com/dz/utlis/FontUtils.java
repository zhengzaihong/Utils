package com.dz.utlis;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2019/11/21
 * create_time: 9:56
 * describe 项目 字体全部替换工具，只限 TextView以及子类。自定义View 不支持
 * <p>
 * <p>
 * 字体放在 assets 文件夹下
 * FontUtils.getInstance().replaceSystemDefaultFontFromAsset(this,"fonts/xxx.ttf"); // .otf 字体文件也可
 * <p>
 * 　主题代码为 application 中的theme属性的 style 里面添加如下属性
 * <item name="android:typeface">monospace</item>
 **/
public class FontUtils {

    private Map<String, SoftReference<Typeface>> mCache = new HashMap<>();

    private static FontUtils sSingleton = null;

    public static Typeface DEFAULT = Typeface.DEFAULT;

    // disable instantiate
    private FontUtils() {
    }

    public static FontUtils getInstance() {
        // double check
        if (sSingleton == null) {
            synchronized (FontUtils.class) {
                if (sSingleton == null) {
                    sSingleton = new FontUtils();
                }
            }
        }
        return sSingleton;
    }

    /**
     * <p>Replace the fonts of specified view and it's children</p>
     *
     * @param root     The root view.
     * @param fontPath fonts file path relative to 'assets' directory.
     */
    public void replaceFontFromAsset(View root, String fontPath) {
        replaceFont(root, createTypefaceFromAsset(root.getContext(), fontPath));
    }

    /**
     * <p>Replace the fonts of specified view and it's children</p>
     *
     * @param root     The root view.
     * @param fontPath fonts file path relative to 'assets' directory.
     * @param style    One of {@link Typeface#NORMAL}, {@link Typeface#BOLD}, {@link Typeface#ITALIC}, {@link Typeface#BOLD_ITALIC}
     */
    public void replaceFontFromAsset(View root, String fontPath, int style) {
        replaceFont(root, createTypefaceFromAsset(root.getContext(), fontPath), style);
    }

    /**
     * <p>Replace the fonts of specified view and it's children</p>
     *
     * @param root     The root view.
     * @param fontPath The full path to the fonts data.
     */
    public void replaceFontFromFile(View root, String fontPath) {
        replaceFont(root, createTypefaceFromFile(fontPath));
    }

    /**
     * <p>Replace the fonts of specified view and it's children</p>
     *
     * @param root     The root view.
     * @param fontPath The full path to the fonts data.
     * @param style    One of {@link Typeface#NORMAL}, {@link Typeface#BOLD}, {@link Typeface#ITALIC}, {@link Typeface#BOLD_ITALIC}
     */
    public void replaceFontFromFile(View root, String fontPath, int style) {
        replaceFont(root, createTypefaceFromFile(fontPath), style);
    }

    /**
     * <p>Replace the fonts of specified view and it's children with specified typeface</p>
     */
    private void replaceFont(View root, Typeface typeface) {
        if (root == null || typeface == null) {
            return;
        }

        if (root instanceof TextView) { // If view is TextView or it's subclass, replace it's fonts
            TextView textView = (TextView) root;
            // Extract previous style of TextView
            int style = Typeface.NORMAL;
            if (textView.getTypeface() != null) {
                style = textView.getTypeface().getStyle();
            }
            textView.setTypeface(typeface, style);
        } else if (root instanceof ViewGroup) { // If view is ViewGroup, apply this method on it's child views
            ViewGroup viewGroup = (ViewGroup) root;
            for (int i = 0; i < viewGroup.getChildCount(); ++i) {
                replaceFont(viewGroup.getChildAt(i), typeface);
            }
        } // else return
    }

    /**
     * <p>Replace the fonts of specified view and it's children with specified typeface and text style</p>
     *
     * @param style One of {@link Typeface#NORMAL}, {@link Typeface#BOLD}, {@link Typeface#ITALIC}, {@link Typeface#BOLD_ITALIC}
     */
    private void replaceFont(View root, Typeface typeface, int style) {
        if (root == null || typeface == null) {
            return;
        }
        if (style < 0 || style > 3) {
            style = Typeface.NORMAL;
        }

        if (root instanceof TextView) { // If view is TextView or it's subclass, replace it's fonts
            TextView textView = (TextView) root;
            textView.setTypeface(typeface, style);
        } else if (root instanceof ViewGroup) { // If view is ViewGroup, apply this method on it's child views
            ViewGroup viewGroup = (ViewGroup) root;
            for (int i = 0; i < viewGroup.getChildCount(); ++i) {
                replaceFont(viewGroup.getChildAt(i), typeface, style);
            }
        } // else return
    }

    /**
     * <p>Create a Typeface instance with specified fonts file</p>
     *
     * @param fontPath fonts file path relative to 'assets' directory.
     * @return Return created typeface instance.
     */
    private Typeface createTypefaceFromAsset(Context context, String fontPath) {
        SoftReference<Typeface> typefaceRef = mCache.get(fontPath);
        Typeface typeface = null;
        if (typefaceRef == null || (typeface = typefaceRef.get()) == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), fontPath);
            typefaceRef = new SoftReference<Typeface>(typeface);
            mCache.put(fontPath, typefaceRef);
        }
        return typeface;
    }

    private Typeface createTypefaceFromFile(String fontPath) {
        SoftReference<Typeface> typefaceRef = mCache.get(fontPath);
        Typeface typeface = null;
        if (typefaceRef == null || (typeface = typefaceRef.get()) == null) {
            typeface = Typeface.createFromFile(fontPath);
            typefaceRef = new SoftReference<Typeface>(typeface);
            mCache.put(fontPath, typefaceRef);
        }
        return typeface;
    }

    /**
     * <p>Replace system default fonts. <b>Note:</b>you should also add code below to your app theme in styles.xml. </p>
     * {@code <item name="android:typeface">monospace</item>}
     * <p>The best place to call this method is {@link Application#onCreate()}, it will affect
     * whole app fonts.If you call this method after view is visible, you need to invalid the view to make it effective.</p>
     *
     * @param context  {@link Context Context}
     * @param fontPath fonts file path relative to 'assets' directory.
     */
    public void replaceSystemDefaultFontFromAsset(Context context, String fontPath) {
        replaceSystemDefaultFont(createTypefaceFromAsset(context, fontPath));
    }

    /**
     * <p>Replace system default fonts. <b>Note:</b>you should also add code below to your app theme in styles.xml. </p>
     * {@code <item name="android:typeface">monospace</item>}
     * <p>The best place to call this method is {@link Application#onCreate()}, it will affect
     * whole app fonts.If you call this method after view is visible, you need to invalid the view to make it effective.</p>
     *
     * @param context  {@link Context Context}
     * @param fontPath The full path to the fonts data.
     */
    public void replaceSystemDefaultFontFromFile(Context context, String fontPath) {
        replaceSystemDefaultFont(createTypefaceFromFile(fontPath));
    }

    /**
     * <p>Replace system default fonts. <b>Note:</b>you should also add code below to your app theme in styles.xml. </p>
     * {@code <item name="android:typeface">monospace</item>}
     * <p>The best place to call this method is {@link Application#onCreate()}, it will affect
     * whole app fonts.If you call this method after view is visible, you need to invalid the view to make it effective.</p>
     */
    private void replaceSystemDefaultFont(Typeface typeface) {
        modifyObjectField(null, "monospace", typeface);
    }

    private void modifyObjectField(Object obj, String fieldName, Object value) {
        try {
            Field defaultField = Typeface.class.getDeclaredField(fieldName);
            defaultField.setAccessible(true);
            defaultField.set(obj, value);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 为给定的字符串添加HTML红色标记，当使用Html.fromHtml()方式显示到TextView 的时候其将是红色的
     *
     * @param string 给定的字符串
     * @return
     */
    public String addHtmlRedFlag(String string) {
        return "<font color=\"red\">" + string + "</font>";
    }

    /**
     * 将给定的字符串中所有给定的关键字标红
     *
     * @param sourceString 给定的字符串
     * @param keyword      给定的关键字
     * @return 返回的是带Html标签的字符串，在使用时要通过Html.fromHtml()转换为Spanned对象再传递给TextView对象
     */
    public String keywordMadeRed(String sourceString, String keyword) {
        String result = "";
        if (sourceString != null && !"".equals(sourceString.trim())) {
            if (keyword != null && !"".equals(keyword.trim())) {
                result = sourceString.replaceAll(keyword, "<font color=\"red\">" + keyword + "</font>");
            } else {
                result = sourceString;
            }
        }
        return result;
    }

    /**
     * <p>Replace the font of specified view and it's children</p>
     *
     * @param root     The root view.
     * @param fontPath font file path relative to 'assets' directory.
     */
    public void replaceFont(@NonNull View root, String fontPath) {
        if (root == null || TextUtils.isEmpty(fontPath)) {
            return;
        }


        if (root instanceof TextView) { // If view is TextView or it's subclass, replace it's font
            TextView textView = (TextView) root;
            int style = Typeface.NORMAL;
            if (textView.getTypeface() != null) {
                style = textView.getTypeface().getStyle();
            }
            textView.setTypeface(createTypeface(root.getContext(), fontPath), style);
        } else if (root instanceof ViewGroup) { // If view is ViewGroup, apply this method on it's child views
            ViewGroup viewGroup = (ViewGroup) root;
            for (int i = 0; i < viewGroup.getChildCount(); ++i) {
                replaceFont(viewGroup.getChildAt(i), fontPath);
            }
        }
    }

    /**
     * <p>Replace the font of specified view and it's children</p>
     * 通过递归批量替换某个View及其子View的字体改变Activity内部控件的字体(TextView,Button,EditText,CheckBox,RadioButton等)
     *
     * @param context  The view corresponding to the activity.
     * @param fontPath font file path relative to 'assets' directory.
     */
    public void replaceFont(@NonNull Activity context, String fontPath) {
        replaceFont(getRootView(context), fontPath);
    }


    /*
     * Create a Typeface instance with your font file
     */
    public Typeface createTypeface(Context context, String fontPath) {
        return Typeface.createFromAsset(context.getAssets(), fontPath);
    }

    /**
     * 从Activity 获取 rootView 根节点
     *
     * @param context
     * @return 当前activity布局的根节点
     */
    public View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

    /**
     * 通过改变App的系统字体替换App内部所有控件的字体(TextView,Button,EditText,CheckBox,RadioButton等)
     *
     * @param context
     * @param fontPath 需要修改style样式为monospace：
     */
//    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
//    <!-- Customize your theme here. -->
//    <!-- Set system default typeface -->
//    <item name="android:typeface">monospace</item>
//    </style>

//    noraml （普通字体,系统默认使用的字体）
//    sans（非衬线字体）
//    serif （衬线字体）
//    monospace（等宽字体）
    public void replaceSystemDefaultFont(@NonNull Context context, String fontName, @NonNull String fontPath) {
        replaceTypefaceField(fontName, createTypeface(context, fontPath));
    }

    /**
     * <p>Replace field in class Typeface with reflection.</p>
     */
    private void replaceTypefaceField(String fieldName, Object value) {
        try {
            Field defaultField = Typeface.class.getDeclaredField(fieldName);
            defaultField.setAccessible(true);
            defaultField.set(null, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
