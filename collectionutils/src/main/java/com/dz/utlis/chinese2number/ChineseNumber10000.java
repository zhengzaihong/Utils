package com.dz.utlis.chinese2number;

import android.text.TextUtils;

/**
* create_user: zhengzaihong
* eamil:1096877329@qq.com
* create_date: 2019/5/22 0022
* create_time: 11:05
* describe: 万|萬 文字转换成阿拉伯
**/
public class ChineseNumber10000{
    private final long unit = 10000;
    public final static String number10000 = "万|萬";

    private final String rex = RexUtils.and(RexUtils.more(RexUtils.or(ChineseNumber1.number0, ChineseNumber1.number1, ChineseNumber1.number2, ChineseNumber1.number3,
            ChineseNumber1.number4, ChineseNumber1.number5, ChineseNumber1.number6, ChineseNumber1.number7, ChineseNumber1.number8, ChineseNumber1.number9,
            ChineseNumber10.number10, ChineseNumber100.number100, ChineseNumber1000.number1000)), number10000);

    private ChineseNumber1000 number1000 = new ChineseNumber1000();
    private ChineseNumber100 number100 = new ChineseNumber100();
    private ChineseNumber10 number10 = new ChineseNumber10();
    private ChineseNumber1 number1 = new ChineseNumber1();

    private String mData = "";

    public ChineseNumber10000() {
        number1000 = new ChineseNumber1000();
        number100 = new ChineseNumber100();
        number10 = new ChineseNumber10();
        number1 = new ChineseNumber1();
    }

    public ChineseNumber10000(String data) {
        mData = RexUtils.getFind(data, rex);
        if (!TextUtils.isEmpty(mData)) {
            String numberData = String.valueOf(mData.subSequence(0, mData.length() - 1));
            number1000 = new ChineseNumber1000(numberData);
            number100 = new ChineseNumber100(numberData = numberData.replace(number1000.getData(), ""));
            number10 = new ChineseNumber10(numberData = numberData.replace(number100.getData(), ""));
            number1 = new ChineseNumber1(numberData.replace(number10.getData(), ""));
        }
    }

    public long getNumber() {
        long number = number1000.getNumber() + number100.getNumber() + number10.getNumber() + number1.getNumber();
        return number * unit;
    }

    public String getData() {
        return mData;
    }
}
