package com.jairath.gcs.poc.common.util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CommonHelper {
    public static String getCurrentDateInGivenPattern(String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);

        return sdf.format(new Date());
    }
}
