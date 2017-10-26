package com.alexkaz.task2.util;

import org.joda.time.DateTime;

public class Utils {

    private static String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public static String getParsedDate(String date){
        DateTime then = new DateTime(date);
        DateTime now = new DateTime();

        String result = "Updated ";

        if (then.getYear() == now.getYear()){
            if (then.getMonthOfYear() == now.getMonthOfYear()){
                if (then.getDayOfMonth() == now.getDayOfMonth()){
                    if (then.getHourOfDay() == now.getHourOfDay()){
                        if (then.getMinuteOfHour() == now.getMinuteOfHour()){
                            result += "a minute ago";
                        } else result += (now.getMinuteOfHour() - then.getMinuteOfHour()) + " minutes ago";
                    } else result += (now.getHourOfDay() - then.getHourOfDay()) + " hours ago";
                } else result += (now.getDayOfMonth() - then.getDayOfMonth()) + " days ago";
            } else result += "on " + then.getDayOfMonth() + " " + months[then.getMonthOfYear()-1];
        } else result += "on " + then.getDayOfMonth() + " " + months[then.getMonthOfYear()-1] + " " + then.getYear();

        return result;
    }

}
