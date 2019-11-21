package com.dz.utlis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 *creat_user: zhengzaihong
 *email:1096877329@qq.com
 *creat_date: 2018/5/22 0022
 *creat_time: 16:51
 *describe: 日历工具
 **/



@SuppressWarnings("all")
public class CalendarUtil {

    /**
     * 计算当前日期
     *
     * @return
     */
    public static int[] getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return new int[]{calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)};
    }
    public static int[] getCurrentDate(int month) {
        Calendar calendar = Calendar.getInstance();
        return new int[]{calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1+month, calendar.get(Calendar.DAY_OF_MONTH)};
    }

    /**
     * @param year 传递下面几年 如2018 传year=1 即 获取的为2019
     * @return 返回当前年月的下一年当前月
     */
    public static int[] getNextYear(int year) {
        Calendar calendar = Calendar.getInstance();
        return new int[]{calendar.get(Calendar.YEAR)+year, calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)};
    }

    /**
     * @param year 传递下面几年 如2018 传year=1 即 获取的为2019
     * @return 返回当前年月的下一年第一月
     */
    public static int[] getNextYeartoFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        return new int[]{calendar.get(Calendar.YEAR)+year, 1, calendar.get(Calendar.DAY_OF_MONTH)};
    }



    /**
     * 根据提供的年月日获取该月份的第一天
     * @Description: (这里用一句话描述这个方法的作用)
     * @Since: 2017-1-9下午2:26:57
     * @param year
     * @param monthOfYear
     * @return
     */
    public static Date getSupportBeginDayofMonth(int year, int monthOfYear) {
        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        return firstDate;
    }

    /**
     * 根据提供的年月获取该月份的最后一天
     * @Description: (这里用一句话描述这个方法的作用)
     * @Since: 2017-1-9下午2:29:38
     * @param year
     * @param monthOfYear
     * @return
     */
    public static Date getSupportEndDayofMonth(int year, int monthOfYear) {
        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        return lastDate;
    }
    //时间格式转换
    public static String toDatePatten(String patten,Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(patten);
        String result = dateFormat.format(date);
        return result;
    }


    /**
     * 获取某周的第一天日期
     * @param week 0 当周 1 上一周 -1 下一周
     * @return
     */
    public static String weekFirstDay(int week){
        Calendar c1 = Calendar.getInstance();
        int dow = c1.get(Calendar.DAY_OF_WEEK);
        c1.add(Calendar.DATE, -dow-7*(week-1)-5 );
        String d1 = new SimpleDateFormat("yyyy-MM-dd").format(c1.getTime());
        return d1+" 00:00:00";
    }

    /**
     * 当前时间加一年
     */
    public static String addYear(int startTime){
        Date firstDate = transForDate(startTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(firstDate);
        calendar.add(Calendar.YEAR,1);
        String d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        return d1;
    }

    /**
     * 获取某周的最后一天日期
     * @param week
     * @return
     */
    public static String weekLastDay(int week){
        Calendar c1 = Calendar.getInstance();
        int dow = c1.get(Calendar.DAY_OF_WEEK);
        c1.add(Calendar.DATE, -dow-7*(week-1)+1);
        String d1 = new SimpleDateFormat("yyyy-MM-dd").format(c1.getTime());
        return d1+" 23:59:59";
    }


    /**
     * 时间戳转日期
     * @param ms
     * @return
     */
    public static Date transForDate(Integer ms){
        if(ms==null){
            ms=0;
        }
        long msl=(long)ms*1000;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date temp=null;
        if(ms!=null){
            try {
                String str=sdf.format(msl);
                temp=sdf.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return temp;
    }


}
