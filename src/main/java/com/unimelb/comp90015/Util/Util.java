package com.unimelb.comp90015.Util;

import javax.swing.*;

import static com.unimelb.comp90015.Util.Constant.*;

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

    public static boolean checkWrongServerPort(int serverPort) {
        return serverPort < 49152 || serverPort > 65535;
    }

    public static void serverPortError() {
        popupErrorDialog(ERROR_INVALID_PORT_NUMBER_CODE, ERROR_INVALID_PORT_NUMBER_CONTENT);
    }

    public static void popupErrorDialog(String code, String content) {
        JOptionPane.showConfirmDialog(
                null,
                getError(code, content),
                "Error",
                JOptionPane.OK_CANCEL_OPTION
        );
        System.exit(1);
    }

    public static boolean checkWrongThreadPoolSize(int size) {
        return size <= 0 || size >= Integer.MAX_VALUE;
    }

    public static void threadPoolSizeError() {
        popupErrorDialog(ERROR_INVALID_THREAD_POOL_SIZE_CODE, ERROR_INVALID_THREAD_POOL_SIZE_CONTENT);
    }

    public static boolean checkWrongInactiveTime(int second) {
        return second < 1 || second > 1000;
    }

    public static void inactiveTimeError() {
        popupErrorDialog(ERROR_INVALID_INACTIVE_TIME_CODE, ERROR_INVALID_INACTIVE_TIME_CONTENT);
    }
}
