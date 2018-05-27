package com.dz.utlis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2016/10/24
 * creat_time: 11:53
 * describe 界面跳转用
 **/
public class ClassTools {

    public static void toAct(Context mContext, Class<?> cls) {
        toAct(mContext, cls, null);
    }

    public static void toAct(Context mContext, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
    }

    public static void toActClearTop(Context mContext, Class<?> cls) {
        toActClearTop(mContext, cls, null);
    }

    public static void toActClearTop(Context mContext, Class<?> cls,
                                     Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mContext.startActivity(intent);
    }
}