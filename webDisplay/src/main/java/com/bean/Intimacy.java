package com.bean;

public class Intimacy implements Comparable<Intimacy> {
    /**
     * 1.the most intimacy friend
     * 2.the degree of intimacy
     */
    private String intimcayFriend;
    private int totalTime;

    public String getIntimcayFriend() {
        return intimcayFriend;
    }

    public void setIntimcayFriend(String intimcayFriend) {
        this.intimcayFriend = intimcayFriend;
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

    @Override
    public String toString() {
        return this.intimcayFriend+" "+this.totalTime;
    }
}
