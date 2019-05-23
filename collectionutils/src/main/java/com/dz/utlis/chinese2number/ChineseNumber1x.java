package com.dz.utlis.chinese2number;

/**
 * creat_user: zhengzaihong
 * eamil:1096877329@qq.com
 * creat_date: 2019/5/22 0022
 * creat_time: 11:06
 * describe: 億|亿 汉字转成 阿拉伯数字
 **/
public class ChineseNumber1x {
    public final static String number1x = "億|亿";
    private final long unit = 100000000;

    private final String rex = RexUtils.and(RexUtils.more(RexUtils.or(ChineseNumber1.number0, ChineseNumber1.number1, ChineseNumber1.number2, ChineseNumber1.number3,
            ChineseNumber1.number4, ChineseNumber1.number5, ChineseNumber1.number6, ChineseNumber1.number7, ChineseNumber1.number8, ChineseNumber1.number9,
            ChineseNumber10.number10, ChineseNumber100.number100, ChineseNumber1000.number1000, ChineseNumber10000.number10000)), number1x);

    private ChineseNumber10000 number10000 = new ChineseNumber10000();
    private ChineseNumber1000 number1000 = new ChineseNumber1000();
    private ChineseNumber100 number100 = new ChineseNumber100();
    private ChineseNumber10 number10 = new ChineseNumber10();
    private ChineseNumber1 number1 = new ChineseNumber1();

    private String mData = "";

    public ChineseNumber1x(String data) {
        mData = RexUtils.getFind(data, rex);
        if (!TextUtils.isEmpty(mData)) {
            String numberData = String.valueOf(mData.subSequence(0, mData.length() - 1));

            number10000 = new ChineseNumber10000(numberData);
            number1000 = new ChineseNumber1000(numberData = numberData.replace(number10000.getData(), ""));
            number100 = new ChineseNumber100(numberData = numberData.replace(number1000.getData(), ""));
            number10 = new ChineseNumber10(numberData = numberData.replace(number100.getData(), ""));
            number1 = new ChineseNumber1(numberData.replace(number10.getData(), ""));
        }
    }

    public long getNumber() {
        long number = number10000.getNumber() + number1000.getNumber() + number100.getNumber() + number10.getNumber() + number1.getNumber();
        return number * unit;
    }

    public String getData() {
        return mData;
    }
}
