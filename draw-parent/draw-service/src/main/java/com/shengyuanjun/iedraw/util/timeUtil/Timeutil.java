package com.shengyuanjun.iedraw.util.timeUtil;


import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: participate
 * @description: 时间格式工具类
 * @author: gq544
 * @create: 2019-08-06 10:54
 */
public class Timeutil {
    public static String getTime(String[] args) {
        //SimpleDateFormat bartDateFormat = new SimpleDateFormat("EEEE-MMMM-dd-yyyy HH:MM:SS");
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String nowtime = bartDateFormat.format(date);
        System.out.println("现在时间 ： " + nowtime);
        return nowtime;
    }

    public static String getLongTime() {
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        SimpleDateFormat d = new SimpleDateFormat("dd");
        SimpleDateFormat h = new SimpleDateFormat("HH");
        SimpleDateFormat mm = new SimpleDateFormat("mm");

        Date date = new Date();
        String nowtime = y.format(date) + m.format(date) + d.format(date) + h.format(date) + mm.format(date);
        return nowtime;
    }

    public static String getTimeLTT(String time){
            String beginDate = "1435845268096";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String sd = sdf.format(new Date(Long.parseLong(beginDate)));
            return sd;
    }

    //换算日期
        public static String  getDayTime(String time){

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                String sd = sdf.format(new Date(Long.parseLong(time))); // 时间戳转换日期

                return sd;
        }

        public static void main(String[] args) {



            Date d = new Date();
            String beginDate = "1435845268096";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            String sd = sdf.format(new Date(Long.parseLong(beginDate))); // 时间戳转换日期
            System.out.println(d.getTime());
            System.out.println(sd);
            String sdt = sdf.format(d);
            System.out.println("当前系统时间:" + sdt);
            System.out.println("系统时间戳:" + d.getTime());
            System.out.println("时间戳转换日期:" + sd);
            try {
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                d = sf.parse("2015-07-03 22:20:20");// 日期转换为时间戳
            } catch (ParseException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
            long timeStemp = d.getTime();
            System.out.println("日期转换时间戳:" + timeStemp);

                Timeutil t = new Timeutil();
            System.out.println(">>>>>>>>>>>>>//时间戳转换日期>"+ t.stampToTime("1435845268096"));
            System.out.println("<<<<<<<<<<<<<<<<//日期转换为时间戳<"+ t.timeToStamp("2015-07-03"));
        }

        /* //时间戳转换日期 */
        public String stampToTime(String stamp) {
            String sd = "";
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sd = sdf.format(new Date(Long.parseLong(stamp))); // 时间戳转换日期
            return sd;
        }

        /* //日期转换为时间戳 */
        public long timeToStamp(String timers) {
            Date d = new Date();
            long timeStemp = 0;
            try {
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                d = sf.parse(timers);// 日期转换为时间戳
            } catch (ParseException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
            timeStemp = d.getTime();
            return timeStemp;
        }

}