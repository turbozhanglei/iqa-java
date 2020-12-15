package com.yechtech.dac.traceability.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 时间工具类
 */
public class DateUtil {

    public static final String FULL_TIME_PATTERN = "yyyyMMddHHmmss";

    public static final String FULL_TIME_SPLIT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_TIME_PATTERN = "MM/dd/yyyy";

    public static final String DATE_TIME = "yyyyMMdd";

    public static final String YEAR_DATE_PATTERN = "yyyy/MM/dd";
    public static final String DATE_TIME_SPLIT_PATTERN = "yyyy-MM-dd";
    public static final String DATE_PATTERN = "yyyy-MM-dd";


    public static String formatFullTime(LocalDateTime localDateTime) {
        return formatFullTime(localDateTime, FULL_TIME_PATTERN);
    }

    public static String formatTime(LocalDateTime localDateTime) {
        return formatFullTime(localDateTime, FULL_TIME_SPLIT_PATTERN);
    }

    public static String formatYearDate(LocalDate localDate) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(YEAR_DATE_PATTERN);
        return localDate.format(fmt);
    }

    public static String formatFullTime(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(dateTimeFormatter);
    }

    public static String formatDate(LocalDateTime localDateTime) {
        return formatFullTime(localDateTime, DATE_PATTERN);
    }

    public static String formatDate(LocalDate localDate) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(fmt);
    }

    private static String getDateFormat(Date date, String dateFormatType) {
        SimpleDateFormat simformat = new SimpleDateFormat(dateFormatType);
        return simformat.format(date);
    }

    public static String formatCSTTime(String date, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date d = sdf.parse(date);
        return DateUtil.getDateFormat(d, format);
    }

    public static LocalDate getLocalDate(String date){
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }

    public static LocalDate getLocalDateWithIncline(String date){
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_TIME_SPLIT_PATTERN));
    }

    public static LocalDateTime getLocalDateTime(String date){
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(FULL_TIME_SPLIT_PATTERN));
    }

    public static String formatDateTime(LocalDateTime localDateTime){
        return formatFullTime(localDateTime, DATE_TIME);
    }

    public static LocalDateTime getDateByHourMin(String date){
        return LocalDateTime.parse(LocalDate.now().toString()+" "+date, DateTimeFormatter.ofPattern(FULL_TIME_SPLIT_PATTERN));
    }
    public static LocalDate formatData(String date)  {
        LocalDate result = LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_PATTERN));
        return result;
    }

    public static String changeDate(String timeDate){
        return StringUtils.replace(timeDate,"-","/").substring(5,10);
    }

    /*
    当前日期减num天的值
    */
    public String getPreNumDay(String time,int num){
        return DateUtil.formatData(time).minusDays(num).toString();
    }


    public static LocalDateTime convertToLocalDateTime(Date date) {
        //An instantaneous point on the time-line.(时间线上的一个瞬时点。)
        Instant instant = date.toInstant();
        //A time-zone ID, such as {@code Europe/Paris}.(时区)
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();

        return localDateTime;
    }

    /**
     * 将“形如yyyy-MM-dd hh:mm:ss#yyyy-MM-dd hh:mm:ss 以及yyyy-MM-dd hh:mm:ss#yyyy-MM-dd格式的字符串转换为两个日期输出”
     * @param date
     * @return
     */
    public static Map convertStringToLocalDateTime(String date) {
        String[] dateArray = date.split("#");
        HashMap<String, String> localDateTimeMap = new HashMap<>();
        for(int i=0;i<dateArray.length;i++){
            String tempDate = dateArray[i];
            if(date.contains(":")){
                LocalDateTime localDateTime = getLocalDateTime(tempDate);
                if(i==0){
                    localDateTimeMap.put("beforeTime",localDateTime.toString());
                }else{
                    localDateTimeMap.put("afterTime",localDateTime.toString());
                }
            }else{
                LocalDate localDate =  getLocalDateWithIncline(tempDate);
                if(i==0){
                    localDateTimeMap.put("beforeTime",localDate.toString());
                }else{
                    localDateTimeMap.put("afterTime",localDate.toString());
                }
            }
        }
        return localDateTimeMap;
    }

}
