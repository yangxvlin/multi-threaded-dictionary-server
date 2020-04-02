package com.unimelb.comp90015.Util;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-26 22:23
 * description:
 **/

public class Util {
    public static String getError(String errorCode, String errorContent) {
        return "Error(" + errorCode + "): " + errorContent;
    }

    public static boolean checkWrongVipPriority(int vipPriority) {
        return vipPriority < 0 || vipPriority > 10;
    }
}
