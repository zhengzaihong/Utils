package com.dz.utlis.bean;


/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2018/5/27
 * create_time: 2:19
 * describe: 电池状态信息
 **/
public class Battery {

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

    @Override
    public String toString() {
        return "MobileBean{" +
                "BatteryN=" + BatteryN +
                ", BatteryV=" + BatteryV +
                ", BatteryT=" + BatteryT +
                ", BatteryStatus='" + BatteryStatus + '\'' +
                ", BatteryTemp='" + BatteryTemp + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    public int getBatteryN() {
        return BatteryN;
    }

    public void setBatteryN(int batteryN) {
        BatteryN = batteryN;
    }

    public int getBatteryV() {
        return BatteryV;
    }

    public void setBatteryV(int batteryV) {
        BatteryV = batteryV;
    }

    public double getBatteryT() {
        return BatteryT;
    }

    public void setBatteryT(double batteryT) {
        BatteryT = batteryT;
    }

    public String getBatteryStatus() {
        return BatteryStatus;
    }

    public void setBatteryStatus(String batteryStatus) {
        BatteryStatus = batteryStatus;
    }

    public String getBatteryTemp() {
        return BatteryTemp;
    }

    public void setBatteryTemp(String batteryTemp) {
        BatteryTemp = batteryTemp;
    }
}
