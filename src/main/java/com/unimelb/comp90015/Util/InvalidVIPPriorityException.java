package com.unimelb.comp90015.Util;

import static com.unimelb.comp90015.Util.Constant.*;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-04-03 0:10
 * description: the exception for invalid user vip number
 **/

public class InvalidVIPPriorityException extends Exception {
    @Override
    public String getMessage() {
        return ERROR_INVALID_VIP_NUMBER_CONTENT;
    }

    /**
     * @return error index
     */
    public String getCode() {
        return ERROR_INVALID_VIP_NUMBER_CODE;
    }
}
