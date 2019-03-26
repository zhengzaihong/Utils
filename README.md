本库工具集合：

![](https://github.com/zhengzaihong/Utils/blob/master/images/1553578288.jpg)



依赖地址：


 compile 'com.zzh:rjtools:xx.xx.xx'


例:应用请求权限处理，这里用kotlin演示，java同学放心使用

    //请求权限
    private fun requestPermission() {

        PermissionUtils.getInstance().requestPermission(this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION),
                "主人,我需要权限哦！",
                object : PermissionUtils.CallBackListener() {

                    override fun onResult(granted: Boolean) {
                        
                        if(granted){
                            //TODO  获得了相关权限处理逻辑
                        }
                    }
                    override fun notAskPermission(permission: List<String>, goSetting: Boolean) {

                        if(goSetting){
                            PermissionUtils.toAppSetting(this@MainActivity)
                        }
                    }

                })
    }

requestPermission方法参数介绍：

参数1：activity

参数2：一个或多个权限数组

参数3：需要权限的原因（该提示在用户非永久拒绝后再次打开应用会提示用户）

参数4：是否授权的相关回调信息

实际情况中，在一些操作必须要用户允许权限后才能操作，然而用户却拒绝了操作，此时需要跳转到设置去打开，可以如下操作：

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        PermissionUtils.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults, true)
    }

复写onRequestPermissionsResult 方法，调用PermissionUtils中的onRequestPermissionsResult方法，最后一个参数传入true 则会在CallBackListener的notAskPermission方法中进行返回是否需要跳转。不跳转可以不传或者false。当然该工具类不止这些。


#更多工具请查看源码。




android 小贴士：
 
在开发中许多时候用户打开输入法,更多的是希望不需要每次都去点击输入法的关闭按钮才关闭,输入法千奇百怪,有点并不好操作,而是希望在打开输入法的时候点击以外区域自动关闭，那怎么操作呢？很简单，在你的基类activity中加入如下代码即可解决。


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
          
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                if(hideInputMethod(this, v)) {
                    return true; //隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    /**
     * 点击屏幕非输入框区域关闭软键盘
     * @param context
     * @param v
     * @return
     */
    public static Boolean hideInputMethod(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return false;
    }


    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            v.getLocationInWindow(leftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
