package com.unimelb.comp90015.Server.Dictionary;

import static com.unimelb.comp90015.Constant.DUPLICATE_WORD_CODE;
import static com.unimelb.comp90015.Constant.DUPLICATE_WORD_CONTENT;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-31 16:22
 * description:
 **/

public class DuplicateWordException extends Exception {
    public DuplicateWordException() {

    }

    @Override
    public String getMessage() {
        return DUPLICATE_WORD_CONTENT;
    }

    public String getCode() {
        return DUPLICATE_WORD_CODE;
    }
}
