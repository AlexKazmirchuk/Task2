package com.alexkaz.task2.util;

import org.joda.time.DateTime;

public class Utils {

    private static String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public static String formatDate(String date){
        if (date == null) throw new IllegalArgumentException();

        DateTime then = new DateTime(date);
        DateTime now = new DateTime();

        String result = "Updated ";

        long thenMillis = then.getMillis();
        long nowMillis = now.getMillis();

        long diff = nowMillis - thenMillis;

        if (diff < 1000L * 60L){
            if (diff / 1000L == 1)
                return result + "a second ago";
            return result + diff / 1000L + " seconds ago";
        }

        if (diff < 1000L * 60L * 60L){
            if (diff / (1000L * 60L) == 1)
                return result + "a minute ago";
            return result + diff / (1000 * 60) + " minutes ago";
        }

        if (diff < 1000L * 60L * 60L * 24L){
            if (diff / (1000L * 60L * 60L) == 1)
                return result + "an hour ago";
            return result + diff / (1000L * 60L * 60L) + " hours ago";
        }

        if (diff <= (1000L * 60L * 60L * 24L * now.dayOfMonth().getMaximumValue())){
            if (diff / (1000L * 60L * 60L * 24L) == 1)
                return result + "a day ago";
            return result + diff / (1000L * 60L * 60L * 24L) + " days ago";
        }

        if (then.getYear() == now.getYear())
            return result + "on " + then.getDayOfMonth() + " " + months[then.getMonthOfYear()-1];
        else
            return result + "on " + then.getDayOfMonth() + " " + months[then.getMonthOfYear()-1] + " " + then.getYear();
    }

    public static String formatNumber(int number){
        if (number < 0) throw new IllegalArgumentException("Argument must be > 0 !");

        if (number < 1_000)
            return String.valueOf(number);
        if (number < 1_000_000){
            int remainder = number % 1_000;
            if (remainder >= 500)
                return ((number / 1_000) + 1) + "k";
            else
                return number / 1_000 + "k";
        } else{
            int remainder = number % 1_000_000;
            if (remainder >= 500_000)
                return ((number / 1_000_000) + 1) + "m";
            else
                return number / 1_000_000 + "m";
        }
    }

}
