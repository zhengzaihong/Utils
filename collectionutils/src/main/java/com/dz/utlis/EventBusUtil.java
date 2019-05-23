package com.dz.utlis;

import com.dz.utlis.interfaces.EventBusListener;

import java.util.HashSet;
import java.util.Set;

/**
 * creat_user: zhengzaihong
 * email:1096877329@qq.com
 * creat_date: 2019/1/3 0003
 * creat_time: 14:10
 * describe: 简易bus
 **/

public class EventBusUtil {

    static EventBusUtil mXEventBus;
    //保证不重复
    static Set<EventBusListener> mListeners = new HashSet<>();

    private EventBusUtil() {

    }

    /**
     * 获取实例
     */
    public synchronized static EventBusUtil getEvent() {
        if (mXEventBus == null) {
            return new EventBusUtil();
        } else {
            return mXEventBus;
        }
    }

    public void register(EventBusListener listener) {
        mListeners.add(listener);

    }

    public void unregister(EventBusListener listener) {
        if (mListeners.contains(listener)) {
            mListeners.remove(listener);
        }
    }

    /**
     * 推送数据
     */
    public void post(Object object) {
        for (EventBusListener eventListener : mListeners) {
            eventListener.registerMessage(object);
        }
    }
}
