package com.dz.utlis.chinese2number;

import android.text.TextUtils;

/**
 * create_user: zhengzaihong
 * eamil:1096877329@qq.com
 * create_date: 2019/5/22 0022
 * create_time: 11:06
 * describe: 佰|百 汉字转成 阿拉伯数字
 **/
public class ChineseNumber100 {
    public final static String number100 = "佰|百";
    private final long unit = 100;

    private final String rex = RexUtils.and(RexUtils.more(RexUtils.or(ChineseNumber1.number0, ChineseNumber1.number1, ChineseNumber1.number2, ChineseNumber1.number3,
            ChineseNumber1.number4, ChineseNumber1.number5, ChineseNumber1.number6, ChineseNumber1.number7, ChineseNumber1.number8, ChineseNumber1.number9,
            ChineseNumber10.number10)), number100);

    private ChineseNumber1 number = new ChineseNumber1();

    private String mData = "";

    public ChineseNumber100(String data) {
        mData = RexUtils.getFind(data, rex);
        if (!TextUtils.isEmpty(mData)) {
            number = new ChineseNumber1(String.valueOf(mData.subSequence(0, mData.length() - 1)));
        }
    }

    public ChineseNumber100() {
        number = new ChineseNumber1();
    }

    public long getNumber() {
        return number.getNumber() * unit;
    }

    public String getData() {
        return mData;
    }
}
