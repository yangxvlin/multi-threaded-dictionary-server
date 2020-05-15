package com.unimelb.comp90015.Server.Dictionary;

import static com.unimelb.comp90015.Util.Constant.DUPLICATE_WORD_CODE;
import static com.unimelb.comp90015.Util.Constant.DUPLICATE_WORD_CONTENT;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-31 16:22
 * description: the exception for duplicate word to be added to the dictionary
 **/

public class DuplicateWordException extends Exception {
    public DuplicateWordException() {
    }

    @Override
    public String getMessage() {
        return DUPLICATE_WORD_CONTENT;
    }

    /**
     * @return error index
     */
    public String getCode() {
        return DUPLICATE_WORD_CODE;
    }
}
