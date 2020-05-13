package com.xywg.admin.flow.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatCom {
    /**
     * 日期格式转化
     * @param oldDateStr
     * @return
     * @throws ParseException
     */
    public  String dealDateFormat(String oldDateStr) throws ParseException {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = df.parse(oldDateStr);
        SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
        Date date1 =  df1.parse(date.toString());
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df2.format(date1);

    }

    /*
     * 获取当前时并格式化
     * */
    public static String getDateFormat(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }


    public static   Date formatStrToDate(String oldDate)  {

        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;

    }

    /**
     * 把指定类型的字符串转化为Date类型
     *
     * @Author zhangjianping
     * @Date 2019/3/30 8:55
     */
    public static Date StrToDate(String  format)  {
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }
}
