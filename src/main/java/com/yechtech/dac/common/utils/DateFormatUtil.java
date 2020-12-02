package com.yechtech.dac.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ChenYu
 * @create 2020-09-11
 * @tag I love java better than girl
 */
public class DateFormatUtil {

    //将日期转换为字符串
    public static String DateToString(Date date, String pattern) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(date);

    }
}
