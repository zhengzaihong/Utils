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

