package com.alexkaz.task2;

import com.alexkaz.task2.util.Utils;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TestUtils {

    private static String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    @Test
    public void test_formatNumber(){
        String result = Utils.formatNumber(0);
        assertThat(result, is("0"));

        result = Utils.formatNumber(1);
        assertThat(result, is("1"));

        result = Utils.formatNumber(345);
        assertThat(result, is("345"));

        result = Utils.formatNumber(999);
        assertThat(result, is("999"));

        result = Utils.formatNumber(1000);
        assertThat(result, is("1k"));

        result = Utils.formatNumber(1900);
        assertThat(result, is("2k"));

        result = Utils.formatNumber(9500);
        assertThat(result, is("10k"));

        result = Utils.formatNumber(1_000_000);
        assertThat(result, is("1m"));

        result = Utils.formatNumber(2_500_000);
        assertThat(result, is("3m"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_formatNumber_invalid_arg(){
        Utils.formatNumber(-2);
    }

    @Test
    public void test_formatDate(){
        DateTime dateTime = new DateTime();

        dateTime = dateTime.minus(new Period(0,0,25,0));

        String result = Utils.formatDate(dateTime.toString());
        assertThat(result, is("Updated 25 seconds ago"));

        dateTime = new DateTime();
        dateTime = dateTime.minus(new Period(0,0,1,0));
        result = Utils.formatDate(dateTime.toString());
        assertThat(result, is("Updated a second ago"));

        dateTime = new DateTime();
        dateTime = dateTime.minus(new Period(0,0,59,0));
        result = Utils.formatDate(dateTime.toString());
        assertThat(result, is("Updated 59 seconds ago"));

        dateTime = new DateTime();
        dateTime = dateTime.minus(new Period(0,1,0,0));
        result = Utils.formatDate(dateTime.toString());
        assertThat(result, is("Updated a minute ago"));

        dateTime = new DateTime();
        dateTime = dateTime.minus(new Period(0,10,0,0));
        result = Utils.formatDate(dateTime.toString());
        assertThat(result, is("Updated 10 minutes ago"));

        dateTime = new DateTime();
        dateTime = dateTime.minus(new Period(0,45,0,0));
        result = Utils.formatDate(dateTime.toString());
        assertThat(result, is("Updated 45 minutes ago"));

        dateTime = new DateTime();
        dateTime = dateTime.minus(new Period(1,0,0,0));
        result = Utils.formatDate(dateTime.toString());
        assertThat(result, is("Updated an hour ago"));

        dateTime = new DateTime();
        dateTime = dateTime.minus(new Period(0,0,0,1,0,0,0,0));
        result = Utils.formatDate(dateTime.toString());
        assertThat(result, is("Updated a day ago"));

        dateTime = new DateTime();
        dateTime = dateTime.minus(new Period(0,0,0,30,0,0,0,0));
        result = Utils.formatDate(dateTime.toString());
        assertThat(result, is("Updated 30 days ago"));

        dateTime = new DateTime();
        dateTime = dateTime.minus(new Period(0,2,0,0,0,0,0,0));
        result = Utils.formatDate(dateTime.toString());
        assertThat(result, is("Updated on " + dateTime.getDayOfMonth() + " " + months[dateTime.getMonthOfYear()-1]));

        dateTime = new DateTime();
        dateTime = dateTime.minus(new Period(2,0,0,0,0,0,0,0));
        result = Utils.formatDate(dateTime.toString());
        assertThat(result, is("Updated on " + dateTime.getDayOfMonth() + " " + months[dateTime.getMonthOfYear()-1] + " " + dateTime.getYear()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_formatDate_invalid_arg(){
        Utils.formatDate("invalid date");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_formatDate_empty_arg(){
        Utils.formatDate("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_formatDate_null_arg(){
        Utils.formatDate(null);
    }


}
