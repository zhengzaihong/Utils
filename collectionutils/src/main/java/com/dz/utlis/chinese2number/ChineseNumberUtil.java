package com.dz.utlis.chinese2number;

import java.util.regex.Pattern;

/**
 * creat_user: zhengzaihong
 * eamil:1096877329@qq.com
 * creat_date: 2019/5/22 0022
 * creat_time: 11:06
 * describe: 将具体的中文数字 字符串转成阿拉伯数字
 **/
public class ChineseNumberUtil {

    /**
     * 中文数字正则
     */
    private final static String regEx = "[^'一'-'二'-'三'-'四'-'五'-'六'-'七'-'八'-'九'-'十'-'零'" +
            "-'壹'-'两'-'貳'-'贰'-'叁'-'參'-'肆'-'伍'-'陸'-'陆'-'柒'-'捌'-'玖'-'拾'" +
            "-'佰'-'百'-'千'-'仟'-'万'-'萬']+";


    /**
     * 转换整段文字中包含有中文数字的内容为阿拉伯数字
     *
     * @param content 红很帅今年十八岁，六个女朋友，三千万存款
     * @return 红很帅今年18岁，6个女朋友，30000000存款
     */
    public static String chinese2num(String content) {

        return handleContent(content, regEx);
    }

    /**
     * 转换整段文字中包含有中文数字的内容为阿拉伯数字
     *
     * @param content 同上
     * @param regEx   自定义正则
     * @return
     */
    public static String chinese2num(String content, String regEx) {

        return handleContent(content, regEx);
    }

    private static String handleContent(String content, String regEx) {

        if (TextUtils.isEmpty(content)) {
            return "";
        }
        Pattern pattern = Pattern.compile(regEx);
        String[] cs = pattern.split(content);

        for (int i = 0; i < cs.length; i++) {
            long turnResult = getNumbers(cs[i]);

            if (turnResult == 0) {
                continue;
            }
            content = content.replace(cs[i], turnResult + "");
        }

        return content;
    }



    /**
     * 将数字转化汉字显示
     * @param input
     * @return
     */
    public static String number2chinese(String input) {

        String[] num = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] unit = new String[]{"", "十", "百", "千", "万", "亿"};
        String[] mid = new String[input.length()];
        String output = "";

        for (int i = 0; i < input.length(); i++) {
            mid[i] = num[Integer.parseInt("" + input.charAt(i))];
            output += mid[i];
        }

        String str = "";
        String result = "";
        for (int i = 0; i < output.length(); i++) {
            if (output.length() - i - 1 == 0) {
                str = "" + output.charAt(i);
            } else if ((output.length() - i - 1 + 4) % 8 == 0) {
                str = output.charAt(i) + unit[4];
            } else if ((output.length() - i - 1) % 8 == 0) {
                str = output.charAt(i) + unit[5];
            } else {
                str = output.charAt(i) + unit[(output.length() - i - 1) % 4];
            }
            result += str;
        }

        result = result.replaceAll("零[千百十]", "零");
        result = result.replaceAll("亿零+万", "亿零");
        result = result.replaceAll("万零+亿", "万亿");
        result = result.replaceAll("零+", "零");
        result = result.replaceAll("零万", "万");
        result = result.replaceAll("零亿", "亿");
        result = result.replaceAll("^一十", "十");
        result = result.replaceAll("零$", "");
        return result;
    }

    /**
     * @param value 需要转换的字符串 如二十
     * @return 返回 20
     */
    public static long getNumbers(String value) {
        value = value.replace("零", "");
        ChineseNumber1x chineseNumber1x = new ChineseNumber1x(value);
        ChineseNumber10000 chineseNumber10000 = new ChineseNumber10000(value = value.replace(chineseNumber1x.getData(), ""));
        ChineseNumber1000 chineseNumber1000 = new ChineseNumber1000(value = value.replace(chineseNumber10000.getData(), ""));
        ChineseNumber100 chineseNumber100 = new ChineseNumber100(value = value.replace(chineseNumber1000.getData(), ""));
        ChineseNumber10 chineseNumber10 = new ChineseNumber10(value = value.replace(chineseNumber100.getData(), ""));
        ChineseNumber1 chineseNumber1 = new ChineseNumber1(value.replace(chineseNumber10.getData(), ""));

        long number1x = chineseNumber1x.getNumber();
        long number10000 = chineseNumber10000.getNumber();
        long number1000 = chineseNumber1000.getNumber();
        long number100 = chineseNumber100.getNumber();
        long number10 = chineseNumber10.getNumber();
        long number1 = chineseNumber1.getNumber();

        long total = number1 + number10 + number100 + number1000 + number10000 + number1x;
        return total;
    }
}
