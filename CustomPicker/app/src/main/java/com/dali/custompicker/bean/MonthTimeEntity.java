package com.dali.custompicker.bean;

/**
 * Created by acer on 2017/11/15.
 */

/**
 * Created by xqx on 2017/1/17.
 * 代表日历上的每一个月份
 */
public class MonthTimeEntity {

    private int year;         //该月份 属于哪一年
    private int month;        // 该月 是哪一个月份
    private String sticky;
    public MonthTimeEntity(int year, int month,String sticky) {
        this.year = year;
        this.month = month;
        this.sticky = sticky;
    }

    public String getSticky() {
        return sticky;
    }

    public void setSticky(String sticky) {
        this.sticky = sticky;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
