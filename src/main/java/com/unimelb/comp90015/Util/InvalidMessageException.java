package com.unimelb.comp90015.Util;

import static com.unimelb.comp90015.Util.Constant.ERROR_INVALID_MESSAGE_FORMAT_CODE;
import static com.unimelb.comp90015.Util.Constant.ERROR_INVALID_MESSAGE_FORMAT_CONTENT;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-04-02 23:54
 * description:
 **/

public class InvalidMessageException extends Exception {

    @Override
    public String getMessage() {
        return ERROR_INVALID_MESSAGE_FORMAT_CONTENT;
    }

    public String getCode() {
        return ERROR_INVALID_MESSAGE_FORMAT_CODE;
    }
}
