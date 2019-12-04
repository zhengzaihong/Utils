package com.dz.utlis.interfaces;


import com.dz.utlis.bean.Battery;

/**
 * creat_user: zhengzaihong
 * email:1096877329@qq.com
 * creat_date: 2018/5/27
 * creat_time: 2:19
 * describe: 电池状态信息的监听
 **/
public interface OnBatteryChangeListener {

    void changeListener(Battery battery);

}
