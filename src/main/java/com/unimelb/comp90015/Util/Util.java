package com.unimelb.comp90015.Util;

import javax.swing.*;

import java.io.*;
import java.net.URL;

import static com.unimelb.comp90015.Util.Constant.*;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-26 22:23
 * description: some helper functions
 **/

public class Util {
    /**
     * @param errorCode    the index of the error
     * @param errorContent the description of the error
     * @return formatted error message
     */
    public static String getError(String errorCode, String errorContent) {
        return "Error(" + errorCode + "): " + errorContent;
    }

    /**
     * @param vipPriority the vip level
     * @return true if vipPriority is in 1-10; false otherwise
     */
    public static boolean checkWrongVipPriority(int vipPriority) {
        return vipPriority < 0 || vipPriority > 10;
    }

    /**
     * @param serverPort the port number
     * @return true if port number is in 49152-65535; false otherwise
     */
    public static boolean checkWrongServerPort(int serverPort) {
        return serverPort < 49152 || serverPort > 65535;
    }

    /**
     * invoke dialog for port number error
     */
    public static void serverPortError() {
        popupErrorDialog(ERROR_INVALID_PORT_NUMBER_CODE, ERROR_INVALID_PORT_NUMBER_CONTENT);
    }

    /**
     * @param code    the index of the error
     * @param content the description of the error
     * invoke dialog for error
     */
    public static void popupErrorDialog(String code, String content) {
        JOptionPane.showConfirmDialog(
                null,
                getError(code, content),
                "Error",
                JOptionPane.OK_CANCEL_OPTION
        );
        System.exit(1);
    }

    /**
     * @param size the thread pool size
     * @return true if thread pool size is in 1-Integer.MAX_VALUE; false otherwise
     */
    public static boolean checkWrongThreadPoolSize(int size) {
        return size <= 0 || size >= Integer.MAX_VALUE;
    }

    /**
     * invoke dialog for thread pool size error
     */
    public static void threadPoolSizeError() {
        popupErrorDialog(ERROR_INVALID_THREAD_POOL_SIZE_CODE, ERROR_INVALID_THREAD_POOL_SIZE_CONTENT);
    }

    /**
     * @param second inactive thread waiting time
     * @return true if time is between 1-1000 second; false otherwise
     */
    public static boolean checkWrongInactiveTime(int second) {
        return second < 1 || second > 1000;
    }

    public static boolean checkWrongThreadPoolQueueLimit(int threadPoolQueueLimit, int threadPoolSize) {
        return threadPoolQueueLimit < threadPoolSize || threadPoolQueueLimit >= Integer.MAX_VALUE;
    }

    /**
     * invoke dialog for thread pool queue limit error
     */
    public static void threadPoolQueueLimitError() {
        popupErrorDialog(ERROR_INVALID_THREAD_POOL_QUEUE_LIMIT_CODE, ERROR_INVALID_THREAD_POOL_QUEUE_LIMIT_CONTENT);
    }

    /**
     * invoke dialog for inactive time error
     */
    public static void inactiveTimeError() {
        popupErrorDialog(ERROR_INVALID_INACTIVE_TIME_CODE, ERROR_INVALID_INACTIVE_TIME_CONTENT);
    }

    /**
     * @param string input string to be tested
     * @return true if a string is made up of alphabet or number.
     */
    public static boolean stringIsAlnum(String string) {
        for (String str: string.split("\n")) {
            if (!str.matches(".*[a-zA-Z]+.*")) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param string input string to be tested
     * @return true if a string is made up of alphabet.
     */
    public static boolean stringIsAlpha(String string) {
        return string.matches("[a-zA-Z]+");
    }

    /**
     * load a file in jar file to a temp file and return the path of the temp file
     * @param res resource path
     * @param input input stream
     * @return the path to temp read file
     * code modify https://stackoverflow.com/a/14612564
     */
    public static File loadFileFromJar(URL res, InputStream input) {
        File file = null;
        if (res.getProtocol().equals("jar")) {
            try {
                file = File.createTempFile("tempfile", ".tmp");
                OutputStream out = new FileOutputStream(file);
                int read;
                byte[] bytes = new byte[1024];

                while ((read = input.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.close();
                file.deleteOnExit();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            //this will probably work in your IDE, but not from a JAR
            file = new File(res.getFile());
        }

        if (file != null && !file.exists()) {
            throw new RuntimeException("Error: File " + file + " not found!");
        }

        return file;
    }
}
