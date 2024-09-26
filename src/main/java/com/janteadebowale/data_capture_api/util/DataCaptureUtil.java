package com.janteadebowale.data_capture_api.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**********************************************************
 2024 Copyright (C),  JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                     
 **********************************************************
 * Author    : Jante Adebowale
 * Date      : 08/09/2024
 * Time      : 18:41
 * Project   : data-capture-api
 * Package   : com.janteadebowale.data_capture_api.util
 **********************************************************/
public class DataCaptureUtil {
    public static Timestamp getCurrentSQLDateTimestamp() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdfAmerica = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sdfAmerica.setTimeZone(TimeZone.getTimeZone("Africa/Lagos"));
        String sDateInAmerica = sdfAmerica.format(date);
        Timestamp currentSQLDateTimestamp = Timestamp.valueOf(sDateInAmerica);
        return currentSQLDateTimestamp;
    }


    public static Date getCurrentDate(String timeZone) {
        Calendar calendar = Calendar.getInstance();
        TimeZone fromTimeZone = calendar.getTimeZone();
        TimeZone toTimeZone = TimeZone.getTimeZone(timeZone);
        return getDate(calendar, fromTimeZone, toTimeZone);
    }


    private static Date getDate(Calendar calendar, TimeZone fromTimeZone, TimeZone toTimeZone) {
        calendar.setTimeZone(fromTimeZone);
        calendar.add(Calendar.MILLISECOND, fromTimeZone.getRawOffset() * -1);
        if (fromTimeZone.inDaylightTime(calendar.getTime())) {
            calendar.add(Calendar.MILLISECOND, calendar.getTimeZone().getDSTSavings() * -1);
        }

        calendar.add(Calendar.MILLISECOND, toTimeZone.getRawOffset());
        if (toTimeZone.inDaylightTime(calendar.getTime())) {
            calendar.add(Calendar.MILLISECOND, toTimeZone.getDSTSavings());
        }
        return calendar.getTime();
    }

    public static Date createLifeSpanFromNowInSeconds(int lifespan) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentDate("Africa/Lagos"));
        cal.add(Calendar.SECOND, lifespan);
        return cal.getTime();
    }
}
