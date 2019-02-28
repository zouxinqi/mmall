package com.mmall.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class DateTimeUtil {

    //joda-time

    //str->Date
    //Date->str

    private static final String DEFAULTTIMEFORMAT="yyyy-MM-dd hh:mm";

    public static Date strToDate(String dateTimeStr,String formateStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formateStr);
        DateTime datetime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return datetime.toDate();
    }

    public static String dateToStr(Date date,String formatStr){
        if (date==null){
            return StringUtils.EMPTY ;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }

    public static Date strToDate(String dateTimeStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DEFAULTTIMEFORMAT);
        DateTime datetime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return datetime.toDate();
    }

    public static String dateToStr(Date date){
        if (date==null){
            return StringUtils.EMPTY ;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(DEFAULTTIMEFORMAT);
    }

}
