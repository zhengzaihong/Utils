package com.dz.utlis;

import android.content.Context;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;

/**
* creat_user: zhengzaihong
* Email:1096877329@qq.com
* creat_date: 2016/6/11
* creat_time: 9:32
* describe webview 工具类
**/

@SuppressWarnings("all")
public class WebUtils {
    /**
     * 设置WebView
     *
     * @param @param context
     * @param @param webView
     * @return void
     * @Title: setWebView
     * @Description: TODO
     */
    public static void loadWeb(Context context, WebView webView, String url) {
        setWebView(context, webView);
        webView.loadUrl(url);
    }

    /**
     * 设置WebView
     *
     * @param @param context
     * @param @param webView
     * @return void
     * @Title: setWebView
     * @Description: TODO
     */
    public static void setWebView(final Context context, final WebView webView) {
        webView.setVisibility(View.VISIBLE);
        WebSettings webSettings = webView.getSettings();
        webSettings.setBlockNetworkImage(true);
        webSettings.setRenderPriority(RenderPriority.HIGH);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                view.getSettings().setBlockNetworkImage(false);

                super.onPageFinished(view, url);

            }
        });
    }

    /**
     * 加载WebView
     *
     * @param context 上下文对象
     * @param webView 加载内容的WebView
     * @param content 内容
     * @return void
     * @Title: LoadWeb
     * @Description: TODO
     */
    public static void loadcontentWeb(Context context, WebView webView,
                                      String content) {
        setWebView(context, webView);
        try {
            webView.loadDataWithBaseURL(
                    "fake://not/needed",
                    "<html><head><meta http-equiv='content-type' content='text/html;charset=utf-8'><style type=\"text/css\">img{ width:100%}</style><STYLE TYPE=\"text/css\"> BODY { margin:0; padding: 5px 3px 5px 5px; background-color:#ffffff;} </STYLE><BODY TOPMARGIN=5 rightMargin=0 MARGINWIDTH=0 MARGINHEIGHT=0></head><body>"
                            + new String(content.getBytes("utf-8"))
                            + "</body></html>", "text/html", "utf-8", "");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void loadcontentWebNoPadding(Context context, WebView webView,
                                               String content) {
        setWebView(context, webView);
        try {
            webView.loadDataWithBaseURL(
                    "fake://not/needed",
                    "<html><head><meta http-equiv='content-type' content='text/html;charset=utf-8'><style type=\"text/css\">img{ width:100%}</style><STYLE TYPE=\"text/css\"> BODY { margin:0; padding: 0px 0px 0px 0px; background-color:#ffffff;} </STYLE><BODY TOPMARGIN=5 rightMargin=0 MARGINWIDTH=0 MARGINHEIGHT=0></head><body>"
                            + new String(content.getBytes("utf-8"))
                            + "</body></html>", "text/html", "utf-8", "");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * url 加载WebView
     *
     * @param context 上下文对象
     * @param webView 加载内容的WebView
     * @param url     url地址
     */
    public static void loadWebByUrl(Context context, WebView webView, String url) {
        setWebView(context, webView);
        webView.loadUrl(url);
    }


}
