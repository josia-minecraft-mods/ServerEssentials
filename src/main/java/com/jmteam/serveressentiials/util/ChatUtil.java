package com.jmteam.serveressentiials.util;

public class ChatUtil {

    public static String makeColored() {
        return "\u00A7";
    }

    public static String getColored(String color) {
       return color.replaceAll("&", makeColored());
    }
}
