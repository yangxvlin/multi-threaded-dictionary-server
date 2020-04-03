package com.unimelb.comp90015.Util;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-23 15:36
 * description: class with constants
 **/

public class Constant {
    /**
     * the application name
     */
    public static final String APP_NAME = "SimpleDictionary";

    /**
     * the path to the application icon
     */
    public static final String APP_ICON_PATH = "./src/main/java/com/unimelb/comp90015/asset/dictionary.jpg";

    // *************************** Task *****************************
    /**
     * the key for task code in message
     */
    public static final String TASK_CODE = "task_code";

    /**
     * value of task for receiving notification from the server
     */
    public static final String SERVER_NOTIFICATION_TASK_CODE = "0";

    /**
     * value of task for search task
     */
    public static final String SEARCH_TASK_CODE = "1";

    /**
     * value of successful task
     */
    public static final String SUCCESSFUL_TASK_CODE = "-1";

    /**
     * value of task for add task
     */
    public static final String ADD_TASK_CODE = "2";

    /**
     * description for successful add task
     */
    public static final String SUCCESSFUL_ADD_TASK_CONTENT = "Successfully add new word to the dictionary!";

    /**
     * value of task for delete task
     */
    public static final String DELETE_TASK_CODE = "3";

    /**
     * description for successful delete task
     */
    public static final String SUCCESSFUL_DELETE_TASK_CONTENT = "Successfully delete the word in the dictionary!";

    // *************************** Content *****************************
    /**
     * the key for content in message
     */
    public static final String CONTENT = "content";

    /**
     * the key for word meaning in message
     */
    public static final String MEANING = "meaning";

    /**
     * the key for user VIP level in message
     */
    public static final String VIP_PRIORITY = "vip";

    // *************************** Response ***************************
    /**
     * the key for response message result code in message
     */
    public static final String RESPONSE_CODE = "response_code";

    /**
     * the description for no word found in dictionary error
     */
    public static final String NO_SUCH_WORD_CONTENT = "No such word!";

    /**
     * the index for no word found in dictionary error
     */
    public static final String NO_SUCH_WORD_CODE = "404";

    /**
     * the description for empty word to be processed error
     */
    public static final String EMPTY_WORD_CONTENT = "The words should not be empty!";

    /**
     * the index for empty word to be processed error
     */
    public static final String EMPTY_WORD_CODE = "400";

    /**
     * the description for unsuccessful connection error
     */
    public static final String ERROR_CONNECTION_CONTENT = "Connection to server failed!";

    /**
     * the index for unsuccessful connection error
     */
    public static final String ERROR_CONNECTION_CODE = "401";

    /**
     * the description for empty meaning to be processed error
     */
    public static final String EMPTY_MEANING_CONTENT = "The word's meaning should not be empty!";

    /**
     * the index for empty meaning to be processed error
     */
    public static final String EMPTY_MEANING_CODE = "402";

    /**
     * the description for duplicate word to be added error
     */
    public static final String DUPLICATE_WORD_CONTENT = "The word to be added already exists in the dictionary!";

    /**
     * the index for duplicate word to be added error
     */
    public static final String DUPLICATE_WORD_CODE = "403";

    /**
     * the description for invalid message format error
     */
    public static final String INVALID_RESPONSE_CONTENT = "Invalid response from the server!";

    /**
     * the index for invalid message format error
     */
    public static final String INVALID_RESPONSE_CODE = "405";

    /**
     * the description for invalid message format during communication error
     */
    public static final String ERROR_INVALID_MESSAGE_FORMAT_CONTENT = "Invalid message format received!";

    /**
     * the index for invalid message format during communication error
     */
    public static final String ERROR_INVALID_MESSAGE_FORMAT_CODE = "409";

    // *************************** Args ***************************
    /**
     * the index for insufficient client arguments error
     */
    public static final String ERROR_INVALID_CLIENT_ARGS_CODE = "406";

    /**
     * the description for insufficient client arguments error
     */
    public static final String ERROR_INVALID_CLIENT_ARGS_CONTENT = "Invalid command line arguments. Should be:\n<Server address> <Port number> <VIP level>";

    /**
     * the index for wrong port number input error
     */
    public static final String ERROR_INVALID_PORT_NUMBER_CODE = "407";

    /**
     * the description for wrong port number input error
     */
    public static final String ERROR_INVALID_PORT_NUMBER_CONTENT = "The port number should be an integer between 49152-65535.";

    /**
     * the index for wrong user VIP number input error
     */
    public static final String ERROR_INVALID_VIP_NUMBER_CODE = "408";

    /**
     * the description for wrong user VIP number input error
     */
    public static final String ERROR_INVALID_VIP_NUMBER_CONTENT = "The VIP level number should be an integer between 0-10";

    /**
     * the index for insufficient server arguments error
     */
    public static final String ERROR_INVALID_SERVER_ARGS_CODE = "410";

    /**
     * the description for insufficient server arguments error
     */
    public static final String ERROR_INVALID_SERVER_ARGS_CONTENT = "Invalid command line arguments. Should be:\n<Port number> <Dictionary file path> <Thread pool size> <Inactive time>";

    /**
     * the index for wrong thread pool size input error
     */
    public static final String ERROR_INVALID_THREAD_POOL_SIZE_CODE = "411";

    /**
     * the description for wrong thread pool size input error
     */
    public static final String ERROR_INVALID_THREAD_POOL_SIZE_CONTENT = "The thread pool size should be an integer between 1-" + Integer.toString(Integer.MAX_VALUE);

    /**
     * the index for wrong inactive time input input error
     */
    public static final String ERROR_INVALID_INACTIVE_TIME_CODE = "412";

    /**
     * the description for wrong inactive time input input error
     */
    public static final String ERROR_INVALID_INACTIVE_TIME_CONTENT = "The inactive time should be an integer with second as unit between 1-1000";

    /**
     * the index for wrong dictionary file path input input error
     */
    public static final String ERROR_INVALID_DICTIONARY_PATH_CODE = "413";

    /**
     * the description for wrong dictionary file path input input error
     */
    public static final String ERROR_INVALID_DICTIONARY_PATH_CONTENT = "Can't read dictionary from given path!";

    /**
     * the index for wrong dictionary file path input input error
     */
    public static final String ERROR_INVALID_DICTIONARY_FORMAT_CODE = "414";

    /**
     * the description for wrong dictionary file path input input error
     */
    public static final String ERROR_INVALID_DICTIONARY_FORMAT_CONTENT = "The dictionary should be json formatted!";
}
