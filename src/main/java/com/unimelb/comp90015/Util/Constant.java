package com.unimelb.comp90015.Util;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-23 15:36
 * description: class with constants
 **/

public class Constant {
    public static final String APP_NAME = "SimpleDictionary";

    public static final String APP_ICON_PATH = "./src/main/java/com/unimelb/comp90015/asset/dictionary.jpg";

    public static final long INACTIVITY_PERIOD_SECOND = 60;

    // *************************** Task *****************************
    public static final String TASK_CODE = "task_code";


    /**
     * task for receiving notification from the server
     */
    public static final String SERVER_NOTIFICATION_TASK_CODE = "0";

    public static final String SEARCH_TASK_CODE = "1";

    public static final String SUCCESSFUL_TASK_CODE = "-1";

    public static final String ADD_TASK_CODE = "2";

    public static final String SUCCESSFUL_ADD_TASK_CONTENT = "Successfully add new word to the dictionary!";

    public static final String DELETE_TASK_CODE = "3";

    public static final String SUCCESSFUL_DELETE_TASK_CONTENT = "Successfully delete the word in the dictionary!";


    // *************************** Content *****************************
    public static final String CONTENT = "content";

    public static final String MEANING = "meaning";

    public static final String VIP_PRIORITY = "vip";

    // *************************** Response ***************************
    public static final String RESPONSE_CODE = "response_code";

    public static final String NO_SUCH_WORD_CONTENT = "No such word!";

    public static final String NO_SUCH_WORD_CODE = "404";

    public static final String EMPTY_WORD_CONTENT = "The words should not be empty!";

    public static final String EMPTY_WORD_CODE = "400";

    public static final String ERROR_CONNECTION_CONTENT = "Connection to server failed!";

    public static final String ERROR_CONNECTION_CODE = "401";

    public static final String EMPTY_MEANING_CONTENT = "The word's meaning should not be empty!";

    public static final String EMPTY_MEANING_CODE = "402";

    public static final String DUPLICATE_WORD_CONTENT = "The word to be added already exists in the dictionary!";

    public static final String DUPLICATE_WORD_CODE = "403";

    public static final String INVALID_RESPONSE_CONTENT = "Invalid response from the server!";

    public static final String INVALID_RESPONSE_CODE = "405";

    public static final String ERROR_INVALID_MESSAGE_FORMAT_CONTENT = "Invalid message format received!";

    public static final String ERROR_INVALID_MESSAGE_FORMAT_CODE = "409";

    // *************************** Args ***************************
    public static final String ERROR_INVALID_CLIENT_ARGS_CODE = "406";

    public static final String ERROR_INVALID_CLIENT_ARGS_CONTENT = "Invalid command line arguments. Should be:\n<Server address> <Port number> <VIP level>";

    public static final String ERROR_INVALID_PORT_NUMBER_CODE = "407";

    public static final String ERROR_INVALID_PORT_NUMBER_CONTENT = "The port number should be an integer between 49152-65535.";

    public static final String ERROR_INVALID_VIP_NUMBER_CODE = "408";

    public static final String ERROR_INVALID_VIP_NUMBER_CONTENT = "The VIP level number should be an integer between 0-10";
}
