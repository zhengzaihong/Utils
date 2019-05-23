package com.dz.utlis.chinese2number;
/**
 * creat_user: zhengzaihong
 * eamil:1096877329@qq.com
 * creat_date: 2019/5/22 0022
 * creat_time: 11:06
 * describe: 十|拾 汉字转成 阿拉伯数字
 **/
public class ChineseNumber10 {

    public final static String number10 = "十|拾";
    private final long unit = 10;

    private final String rex = RexUtils.and(RexUtils.nullOrMore(RexUtils.or("", ChineseNumber1.number0, ChineseNumber1.number1, ChineseNumber1.number2, ChineseNumber1.number3,
            ChineseNumber1.number4, ChineseNumber1.number5, ChineseNumber1.number6, ChineseNumber1.number7, ChineseNumber1.number8, ChineseNumber1.number9)),
            number10);

    private ChineseNumber1 number = new ChineseNumber1();

    private String mData = "";

    public ChineseNumber10(String data) {
        mData = RexUtils.getFind(data, rex);
        if (!TextUtils.isEmpty(mData)) {
            String numberData = String.valueOf(mData.subSequence(0, mData.length() - 1));
            if (TextUtils.isEmpty(numberData)) {
                numberData = "一";
            }
            number = new ChineseNumber1(numberData);
        }
    }

    public ChineseNumber10() {
        number = new ChineseNumber1();
    }

    public long getNumber() {
        return number.getNumber() * unit;
    }

    public String getData() {
        return mData;
    }
}
