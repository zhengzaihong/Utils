package com.dz.utlis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.dz.utlis.bean.Battery;
import com.dz.utlis.interfaces.OnBatteryChangeListener;

/**
 * creat_user: zhengzaihong
 * email:1096877329@qq.com
 * creat_date: 2018/5/27
 * creat_time: 2:19
 * describe: 获取手机的电池信息
 **/

public class BatteryUtil {

    private MessageReceiver mMessageReceivers;
    public final String MESSAGE_RECEIVED_ACTION = "MESSAGE_RECEIVED_ACTION_MobileUtil";
    // 目前电量
    private int BatteryN;
    // 电池电压
    private int BatteryV;
    // 电池温度
    private double BatteryT;
    // 电池状态
    private String BatteryStatus;
    // 电池使用情况
    private String BatteryTemp;
    public String info;

    private Battery mobileBean = new Battery();

    public void registerMessageReceiver(Context context) {
        mMessageReceivers = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        context.registerReceiver(mMessageReceivers, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                BatteryN = intent.getIntExtra("level", 0); // 目前电量
                BatteryV = intent.getIntExtra("voltage", 0); // 电池电压
                BatteryT = intent.getIntExtra("temperature", 0); // 电池温度
                switch (intent.getIntExtra("status",
                        BatteryManager.BATTERY_STATUS_UNKNOWN)) {
                    case BatteryManager.BATTERY_STATUS_CHARGING:
                        BatteryStatus = "充电状态";
                        break;
                    case BatteryManager.BATTERY_STATUS_DISCHARGING:
                        BatteryStatus = "放电状态";
                        break;
                    case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                        BatteryStatus = "未充电";
                        break;
                    case BatteryManager.BATTERY_STATUS_FULL:
                        BatteryStatus = "充满电";
                        break;
                    case BatteryManager.BATTERY_STATUS_UNKNOWN:
                        BatteryStatus = "未知道状态";
                        break;
                }
                switch (intent.getIntExtra("health",
                        BatteryManager.BATTERY_HEALTH_UNKNOWN)) {
                    case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                        BatteryTemp = "未知";
                        break;
                    case BatteryManager.BATTERY_HEALTH_GOOD:
                        BatteryTemp = "状态良好";
                        break;
                    case BatteryManager.BATTERY_HEALTH_DEAD:
                        BatteryTemp = "电池没有电";
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                        BatteryTemp = "电池电压过高";
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                        BatteryTemp = "电池过热";
                        break;
                }
                info = "目前电量为" + BatteryN + "% --- " + BatteryStatus + "\n"
                        + "电压为" + BatteryV + "mV --- " + BatteryTemp + "\n"
                        + "温度为" + (BatteryT * 0.1) + "℃";

                mobileBean.setBatteryN(BatteryN);
                mobileBean.setBatteryStatus(BatteryStatus);
                mobileBean.setBatteryV(BatteryN);
                mobileBean.setBatteryTemp(BatteryTemp);
                mobileBean.setBatteryT((BatteryT * 0.1));
                mobileBean.setInfo(info);
                if (null != listener) {
                    listener.changeListener(mobileBean);
                }
            }
        }
    }

    private OnBatteryChangeListener listener;

    public void setOnBatteryChangeListener(OnBatteryChangeListener listener) {
        this.listener = listener;
    }



}
