package com.bean;

public class Intimacy implements Comparable<Intimacy> {
    /**
     * 1.the most intimacy callee
     * 2.the degree of intimacy
     */
    private String callee;
    private int totalTime;

    public String getCallee() {
        return callee;
    }

    public void setCallee(String callee) {
        this.callee = callee;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public int compareTo(Intimacy inti) {
        return inti.totalTime - this.totalTime;
    }
}
