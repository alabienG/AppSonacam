package com.stampicorp.AppSonacam.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Utils {
    public static Date modifyDateLayout(String inputDate) throws ParseException {
        Date date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss z").parse(inputDate);
        return date;
    }

    public static String modifyDateToString(Date date) throws ParseException {
        String myDate = new SimpleDateFormat("dd-MM-yyy").format(date);
        return myDate;
    }
}
