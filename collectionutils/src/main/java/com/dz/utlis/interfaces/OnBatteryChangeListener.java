package com.dz.utlis.interfaces;


import com.dz.utlis.bean.Battery;

/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2018/5/27
 * create_time: 2:19
 * describe: 电池状态信息的监听
 **/
public interface OnBatteryChangeListener {

    void changeListener(Battery battery);

}
