package com;


import java.util.Calendar;


public class Main {
    public static void main(String[] args) {
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        System.out.println(year);
    }

}
