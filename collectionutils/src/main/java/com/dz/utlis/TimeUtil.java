package com.dz.utlis;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2018/6/12
 * create_time: 9:53
 * describe 时间工具类
 **/
@SuppressWarnings("all")
public class TimeUtil {

    /**
     * @param time1
     * @param time2
     * @return 判断两个时间大小 等于0相等 小于0time1<time2,大于0反之
     */
    public static int comperTime(String time1, String time2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(time1));
            c2.setTime(df.parse(time2));
        } catch (ParseException e) {
            System.err.println("格式不正确");
        }
        int result = c1.compareTo(c2);
        if (result == 0)
            System.out.println("c1相等c2");
        else if (result < 0)
            System.out.println("c1小于c2");
        else
            System.out.println("c1大于c2");

        return result;
    }

    /**
     * @param pattern 时间格式
     * @return 返回时间的字符串
     */
    public static String getNowTime(String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String result = dateFormat.format(new Date(System.currentTimeMillis()));
        return result;
    }

    /**
     * @param time
     * @param pattern yyyy/MM/dd HH:mm:ss
     * @return 将时间戳转成时间
     */
    public static String stampstoTime(String time, String pattern) {
        String t = "";
        try {
            t = new SimpleDateFormat(pattern).format(new Date(Long.parseLong(time) * 1000L));
        } catch (Exception e) {
            e.printStackTrace();
            t = stampstoTime(currentTimeStamp().toString(), "yyyy-MM-dd");
        }
        return t;
    }
    public static String stampstoTime(Long time, String pattern) {
        String t = "";
        try {
            t = new SimpleDateFormat(pattern).format(new Date(time * 1000L));
        } catch (Exception e) {
            e.printStackTrace();
            t = stampstoTime(currentTimeStamp().toString(), "yyyy-MM-dd");
        }
        return t;
    }

    /**
     *
     * @param pattern
     * @param day
     * @return 返回几天后的时间戳
     */
    public static String getAfterDayTimeStamp(String pattern, int day) {
        return TimeUtil.transForMilliSecond(getSpecifiedDayAfter(cstToYmd(new Date().toString()), pattern, day), "yyyy-MM-dd").toString();
    }

    public static String cstToYmd(String dateStr) {

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String formatStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return formatStr;
    }


    /**
     * 把时间转换成Date
     * @param pattern
     * @param time
     * @return
     */
    public static Date getDate(String pattern, String time) {

        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;

        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }


    /**
     * @param date  时间补位 如传进来2018-3-8 返回 2018-03-08
     * @param splid 时间分隔符
     * @return
     */
    public static String dateTodate(String date, String splid) {
        String[] times = date.split(splid);
        StringBuffer buffer = new StringBuffer(times[0] + "-");
        int month = Integer.parseInt(times[1]);
        int day = Integer.parseInt(times[2]);
        if (month < 10) {
            buffer.append("0" + month).append(splid);
        } else {
            buffer.append(month).append(splid);
        }

        if (day < 10) {
            buffer.append("0" + day);
        } else {
            buffer.append(day);
        }
        return buffer.toString();

    }


    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) //闰年
                {
                    timeDistance += 366;
                } else //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }


    /**
     * @param endDate 结束时间
     * @param nowDate 开始时间
     * @return 时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * @param miutes 分钟转小时
     * @return
     */
    public static String getHourMinutes(int miutes) {
        int hour = miutes / 60;
        int minute = miutes % 60;
        return hour + "小时" + minute + "分钟";
    }

    /**
     * @param datetime 时间
     * @return 返回周几
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


    /**
     * 根据年月日计算年龄,birthTimeString:"1994-11-14"
     *
     * @param birthTimeString
     * @return
     */
    public static int getAge(String birthTimeString) {
        // 先截取到字符串中的年、月、日
        String strs[] = birthTimeString.trim().split("-");
        int selectYear = Integer.parseInt(strs[0]);
        int selectMonth = Integer.parseInt(strs[1]);
        int selectDay = Integer.parseInt(strs[2]);
        // 得到当前时间的年、月、日
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayNow = cal.get(Calendar.DATE);

        // 用当前年月日减去生日年月日
        int yearMinus = yearNow - selectYear;
        int monthMinus = monthNow - selectMonth;
        int dayMinus = dayNow - selectDay;

        int age = yearMinus;// 先大致赋值
        if (yearMinus < 0) {// 选了未来的年份
            age = 0;
        } else if (yearMinus == 0) {// 同年的，要么为1，要么为0
            if (monthMinus < 0) {// 选了未来的月份
                age = 0;
            } else if (monthMinus == 0) {// 同月份的
                if (dayMinus < 0) {// 选了未来的日期
                    age = 0;
                } else if (dayMinus >= 0) {
                    age = 1;
                }
            } else if (monthMinus > 0) {
                age = 1;
            }
        } else if (yearMinus > 0) {
            if (monthMinus < 0) {// 当前月>生日月
            } else if (monthMinus == 0) {// 同月份的，再根据日期计算年龄
                if (dayMinus < 0) {
                } else if (dayMinus >= 0) {
                    age = age + 1;
                }
            } else if (monthMinus > 0) {
                age = age + 1;
            }
        }
        return age;
    }

    /**
     * 根据时间戳计算年龄
     *
     * @param time 时间戳
     * @return 年龄
     */
    public static int getAgeFromBirthTime(String time) {
        return getAge(stampstoTime(time, "yyyy-MM-dd"));
    }


    /**
     * @param specifiedDay 时间格式字符串 2019-12-10
     * @param pattern       设置返回的时间格式 yyyy-MM-dd
     * @param days         设置几天前
     * @return 返回几天前的时间
     */
    public static String getSpecifiedDayBefore(String specifiedDay, String pattern, int days) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(pattern).parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - days);

        String dayBefore = new SimpleDateFormat(pattern).format(c.getTime());
        return dayBefore;
    }

    /**
     * @param specifiedDay 时间格式字符串
     * @param pattern       设置返回的时间格式
     * @param days         设置几天后
     * @return 返回几天后的时间
     */
    public static String getSpecifiedDayAfter(String specifiedDay, String pattern, int days) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(pattern).parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + days);

        String dayAfter = new SimpleDateFormat(pattern).format(c.getTime());
        return dayAfter;
    }

    /**
     * 获取晚上9点半的时间戳
     *
     * @return
     */
    public static int getTimes(int day, int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    /**
     * 获取当前时间往上的整点时间
     *
     * @return
     */
    public static int getIntegralTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, 1);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static int getIntegralTimeEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis() / 1000);
    }


    /**
     * 判断开始和结束时间是否属于今天
     *
     * @param starttime 开始时间
     * @param endtime   结束时间
     * @return
     */
    public static boolean isToday(long starttime, long endtime) {
        try {
            boolean b1 = false;
            boolean b2 = false;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar now = Calendar.getInstance();
            long ms = 1000 * (now.get(Calendar.HOUR_OF_DAY) * 3600 + now.get(Calendar.MINUTE) * 60 + now.get(Calendar.SECOND));//毫秒数
            long ms_now = now.getTimeInMillis();
            if (ms_now - starttime < ms) {
                b1 = true;
            }
            if (ms_now - endtime < ms) {
                b2 = true;
            }
            return b1 & b2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 日期转时间戳
     *
     * @param date
     * @return
     */
    public static Integer transForMilliSecond(Date date) {
        if (date == null) return null;
        return (int) (date.getTime() / 1000);
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static Integer currentTimeStamp() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * 日期字符串转时间戳
     *
     * @param dateStr
     * @return
     */
    public static Integer transForMilliSecond(String dateStr) {
        Date date = TimeUtil.formatDate(dateStr);
        return date == null ? null : TimeUtil.transForMilliSecond(date);
    }

    /**
     * 日期字符串转时间戳
     *
     * @param dateStr
     * @return
     */
    public static Integer transForMilliSecond(String dateStr, String format) {
        Date date = TimeUtil.formatDate(dateStr, format);
        return date == null ? null : TimeUtil.transForMilliSecond(date);
    }

    /**
     * 日期字符串转时间戳
     *
     * @param dateStr
     * @param 格式      如"yyyy-mm-dd"
     * @return
     */
    public static Integer transForMilliSecondByTim(String dateStr, String tim) {
        SimpleDateFormat sdf = new SimpleDateFormat(tim);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date == null ? null : TimeUtil.transForMilliSecond(date);
    }

    /**
     * 字符串转日期，格式为："yyyy-MM-dd HH:mm:ss"
     *
     * @param dateStr
     * @return
     */
    public static Date formatDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date result = null;
        try {
            result = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 字符串转日期，格式为："yyyy-MM-dd HH:mm:ss"
     *
     * @param dateStr
     * @return
     */
    public static Date formatDate(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date result = null;
        try {
            result = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 日期转字符串
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = null;
        result = sdf.format(date);
        return result;
    }

    /**
     * 日期转字符串
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String result = null;
        result = sdf.format(date);
        return result;
    }


    /**
     * 获取某周的第一天日期
     *
     * @param week 0 当周 1 上一周 -1 下一周
     * @return
     */
    public static String weekFirstDay(int week) {
        Calendar c1 = Calendar.getInstance();
        int dow = c1.get(Calendar.DAY_OF_WEEK);
        c1.add(Calendar.DATE, -dow - 7 * (week - 1) - 5);
        String d1 = new SimpleDateFormat("yyyy-MM-dd").format(c1.getTime());
        return d1 + " 00:00:00";
    }


    /**
     * 获取某周的最后一天日期
     *
     * @param week
     * @return
     */
    public static String weekLastDay(int week) {
        Calendar c1 = Calendar.getInstance();
        int dow = c1.get(Calendar.DAY_OF_WEEK);
        c1.add(Calendar.DATE, -dow - 7 * (week - 1) + 1);
        String d1 = new SimpleDateFormat("yyyy-MM-dd").format(c1.getTime());
        return d1 + " 23:59:59";
    }


    /**
     * HH:mm:ss格式时间转换为1970-01-01日的时间戳（也就是只有时间没有日期的情况要求使用时间戳表示时间）
     *
     * @author DingJiaCheng
     */
    public static int transFromTime(String time) {
        return transForMilliSecond("1970-01-01 " + time, "yyyy-mm-dd HH:mm:ss");
    }


    /**
     * 发表文章几小时前之类
     *
     * @param time
     * @return
     */
    public static String toTureTime(String time, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String result = dateFormat.format(new Date(System.currentTimeMillis()));
        String now = result;
        int artilcYear = Integer.parseInt(time.substring(0, 4));
        int artilcMonth = Integer.parseInt(time.substring(5, 7));
        int artilcDay = Integer.parseInt(time.substring(8, 10));
        int artilcHours = Integer.parseInt(time.substring(11, 13));
        int artilcMin = Integer.parseInt(time.substring(14, 16));
        int artilcSec = Integer.parseInt(time.substring(17, time.length()));

        int nowYear = Integer.parseInt(now.substring(0, 4));
        int nowMonth = Integer.parseInt(now.substring(5, 7));
        int nowDay = Integer.parseInt(now.substring(8, 10));
        int nowHours = Integer.parseInt(now.substring(11, 13));
        int nowMin = Integer.parseInt(now.substring(14, 16));
        int nowSec = Integer.parseInt(now.substring(17, time.length()));
        StringBuilder sb = new StringBuilder();

        if (nowYear - artilcYear > 0) {
            sb.append(artilcYear).append("-").append(artilcMonth).append("-").append(artilcDay).append("");
            return sb.toString();
        } else if (nowMonth - artilcMonth > 0) {
            sb.append(artilcMonth).append("-").append(artilcDay).append("");
            return sb.toString();
        } else if (nowDay - artilcDay == 0) {
            if (nowHours - artilcHours == 0) {
                if (nowMin - artilcMin <= 1) {
                    sb.append("刚刚");
                    return sb.toString();
                } else {
                    sb.append(nowMin - artilcMin).append("分钟前");
                    return sb.toString();
                }
            } else {
                if (nowHours - artilcHours < 0) {
                    int tt = nowHours - artilcHours;
                    tt = Math.abs(tt);
                    sb.append("" + tt).append("小时前");
                } else {
                    sb.append(nowHours - artilcHours).append("小时前");
                }
                return sb.toString();
            }
        } else if (nowDay - artilcDay == 1) {
            sb.append("昨天");
            return sb.toString();
        } else if (nowDay - artilcDay == 2) {
            sb.append("前天");
            return sb.toString();
        } else {
            sb.append(artilcMonth).append("-").append(artilcDay).append("");
            return sb.toString();
        }
    }


    /**
     * 准备第一个模板，从字符串中提取出日期数字
     */
    private static String pat1 = "yyyy-MM-dd HH:mm:ss";
    /**
     * 准备第二个模板，将提取后的日期数字变为指定的格式
     */
    private static String pat2 = "yyyy年MM月dd日 HH:mm:ss";
    /**
     * 实例化模板对象
     */
    private static SimpleDateFormat sdf1 = new SimpleDateFormat(pat1);
    private static SimpleDateFormat sdf2 = new SimpleDateFormat(pat2);
    private static long timeMilliseconds;

    public static Long farmatTime(String string) {
        Date date = null;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = Date(sf.parse(string));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    private static Date Date(Date date) {
        Date datetimeDate;
        datetimeDate = new Date(date.getTime());
        return datetimeDate;
    }

    /**
     * 日期转字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String parseDateToString(Date date) {
        if (date == null) {
            return null;
        }
        try {
            DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateformat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param commitDate
     * @return 时间多久前
     */
    public static String getTime(String commitDate) {
        // 在主页面中设置当天时间
        Date nowTime = new Date();
        String currDate = sdf1.format(nowTime);
        Date date = null;
        try {
            if (commitDate.length() > 19) {
                commitDate = commitDate.substring(0, 18);
            }
            if (commitDate.length() == 16) {
                StringBuffer buffer = new StringBuffer(commitDate);
                buffer.append(":00");
                commitDate = buffer.toString();
            }
            // 将给定的字符串中的日期提取出来
            date = sdf1.parse(commitDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int nowDate = Integer.valueOf(currDate.substring(8, 10));
        int commit = Integer.valueOf(commitDate.substring(8, 10));

        String monthDay = sdf2.format(date).substring(5, 12);
        String yearMonthDay = sdf2.format(date).substring(0, 12);
        int month = Integer.valueOf(monthDay.substring(0, 2));
        int day = Integer.valueOf(monthDay.substring(3, 5));
        if (month < 10 && day < 10) {
            monthDay = monthDay.substring(1, 3) + monthDay.substring(4);
        } else if (month < 10) {
            monthDay = monthDay.substring(1);
        } else if (day < 10) {
            monthDay = monthDay.substring(0, 3) + monthDay.substring(4);
        }
        int yearMonth = Integer.valueOf(yearMonthDay.substring(5, 7));
        int yearDay = Integer.valueOf(yearMonthDay.substring(8, 10));
        if (yearMonth < 10 && yearDay < 10) {
            yearMonthDay = yearMonthDay.substring(0, 5)
                    + yearMonthDay.substring(6, 8) + yearMonthDay.substring(9);
        } else if (yearMonth < 10) {
            yearMonthDay = yearMonthDay.substring(0, 5)
                    + yearMonthDay.substring(6);
        } else if (yearDay < 10) {
            yearMonthDay = yearMonthDay.substring(0, 8)
                    + yearMonthDay.substring(9);
        }
        String str = " 00:00:00";
        float currDay = farmatTime(currDate.substring(0, 10) + str);
        float commitDay = farmatTime(commitDate.substring(0, 10) + str);
        int currYear = Integer.valueOf(currDate.substring(0, 4));
        int commitYear = Integer.valueOf(commitDate.substring(0, 4));
        int flag = (int) (farmatTime(currDate) / 1000 - farmatTime(commitDate) / 1000);
        String des = null;
        String hourMin = commitDate.substring(11, 16);
        int temp = flag;
        if (temp < 60) {
            if (commitDay < currDay) {
                des = "昨天  " + hourMin;
            } else {
                des = "刚刚";
            }
        } else if (temp < 60 * 60) {
            if (commitDay < currDay) {
                des = "昨天  " + hourMin;
            } else {
                des = temp / 60 + "分钟前";
            }
        } else if (temp < 60 * 60 * 24) {
            int hour = temp / (60 * 60);
            if (commitDay < currDay) {
                des = "昨天  " + hourMin;
            } else {
                if (hour < 6) {
                    des = hour + "小时前";
                } else {
                    des = hourMin;
                }
            }
        } else if (temp < (60 * 60 * 24 * 2)) {
            if (nowDate - commit == 1) {
                des = "昨天  " + hourMin;
            } else {
                des = "前天  " + hourMin;
            }
        } else if (temp < 60 * 60 * 60 * 3) {
            if (nowDate - commit == 2) {
                des = "前天  " + hourMin;
            } else {
                if (commitYear < currYear) {
                    des = yearMonthDay + hourMin;
                } else {
                    des = monthDay + hourMin;
                }
            }
        } else {
            if (commitYear < currYear) {
                des = yearMonthDay + hourMin;
            } else {
                des = monthDay + hourMin;
            }
        }
        if (des == null) {
            des = commitDate;
        }
        return des;
    }


    /**
     * 如果在1分钟之内发布的显示"刚刚" 如果在1个小时之内发布的显示"XX分钟之前" 如果在1天之内发布的显示"XX小时之前"
     * 如果在今年的1天之外的只显示“月-日”，例如“05-03” 如果不是今年的显示“年-月-日”，例如“2014-03-11”
     *
     * @param time
     * @return
     */
    public static String translateTime(String time) {
        // 在主页面中设置当天时间
        Date nowTime = new Date();
        String currDate = sdf1.format(nowTime);
        long currentMilliseconds = nowTime.getTime();// 当前日期的毫秒值
        Date date = null;
        try {
            // 将给定的字符串中的日期提取出来
            date = sdf1.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
            return time;
        }
        if (date != null) {
            timeMilliseconds = date.getTime();
        }

        long timeDifferent = currentMilliseconds - timeMilliseconds;
        if (timeDifferent < 60000) {// 一分钟之内

            return "刚刚";
        }
        if (timeDifferent < 3600000) {// 一小时之内
            long longMinute = timeDifferent / 60000;
            int minute = (int) (longMinute % 100);
            return minute + "分钟之前";
        }
        long l = 24 * 60 * 60 * 1000; // 每天的毫秒数
        if (timeDifferent < l) {// 小于一天
            long longHour = timeDifferent / 3600000;
            int hour = (int) (longHour % 100);
            return hour + "小时之前";
        }
        if (timeDifferent >= l) {
            String currYear = currDate.substring(0, 4);
            String year = time.substring(0, 4);
            if (!year.equals(currYear)) {
                return time.substring(0, 10);
            }
            return time.substring(5, 10);
        }
        return time;
    }

    /**
     * 获取当前日期
     */
    public static String getDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new Date());
        return date;
    }


    /**
     * @param time 开始时间 如 2019-05-21 14:03:25
     * @param min  多少分钟 如 20
     * @return 返回重开始时间到min后的时间 2019-05-21 14:23
     */
    public static String addDateMinut(String time, int min) {
        // 24小时制
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //出参的格式 24小时制
        SimpleDateFormat newFormat = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (date == null) return "";
        System.out.println("front:" + format.format(date));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 24小时制
        cal.add(Calendar.MINUTE, min);
        //得到结算后的结果 yyyy-MM-dd HH:mm
        date = cal.getTime();
        System.out.println("after:" + format.format(date));
        cal = null;
        //转换结果的格式 HH:mm
        return newFormat.format(date);

    }

    /**
     * 比较日期与当前日期的大小
     */
    public static boolean DateCompare(String s1) throws ParseException {
        //设定时间的模板
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //得到指定模范的时间
        Date d1 = sdf.parse(s1);
        Date d2 = sdf.parse(getDate());
        //比较
        if (((d1.getTime() - d2.getTime()) / (24 * 3600 * 1000)) >= 1) {
            return true;
        } else {
            return false;
        }
    }


    public static String timeFormat(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date date = null;
        try {
            // 将给定的字符串中的日期提取出来
            date = sdf.parse(time);
        } catch (Exception e) {
            return time;
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf1.format(date);
    }


    public static String timeFormatStr(String time) {
        //
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date = null;
        try {
            // 将给定的字符串中的日期提取出来
            date = sdf.parse(time);
        } catch (Exception e) {
            return time;
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf1.format(date);
    }


    public static String timeFormatYYYYMMDD(String time) {
        //
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date date = null;
        try {
            // 将给定的字符串中的日期提取出来
            date = sdf.parse(time);
        } catch (Exception e) {
            return time;
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        return sdf1.format(date);
    }
/*
    public static void main(String[] args) {
        //System.out.println(getIntegralTimeEnd());
        System.out.println(transForDate2(transForMilliSecond("2015-02-25 00:00:00")));
        System.out.println(transForMilliSecond("2018-01-25","yyyy-mm-dd"));
        System.out.println(transForDate(transForMilliSecond("2018-01-25","yyyy-mm-dd")));
        //System.out.println(transForDate1(transForMilliSecond("1970-01-01 00:00:00","yyyy-mm-dd HH:mm:ss")));
        //System.out.println(currentTimeStamp());
        //System.out.println(transForDate(currentTimeStamp()));
        //System.out.println(new Date());
        //System.out.println(DateUtils.getDate());
      //  System.out.println(transFromTime("00:00:01"));
       // System.out.println(transToTime(transFromTime("15:01:13")));
    }*/
}