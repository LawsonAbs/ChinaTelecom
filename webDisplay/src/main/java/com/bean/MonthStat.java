package com.bean;

/**
 * 1.查询结果的字段放到这个类中
 * 2.这个类对应实际的MySQL中的一张表
 * 3.18907263863
 */
public class MonthStat implements Comparable<MonthStat>{
    /**
     * 1.电话号码【是前端传过来的值】
     * 2.年月
     * 3.每个月的通话时长
     */
    private String yearMonth;
    private int callDuration;

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public int getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(int callDuration) {
        this.callDuration = callDuration;
    }

    @Override
    public int compareTo(MonthStat s1) {
        return this.getYearMonth().compareTo(s1.getYearMonth());
    }
}
