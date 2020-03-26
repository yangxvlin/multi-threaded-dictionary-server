package com.unimelb.comp90015.Server.Dictionary;

import static com.unimelb.comp90015.Constant.NO_SUCH_WORD;
import static com.unimelb.comp90015.Constant.NO_SUCH_WORD_CODE;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-24 17:42
 * description:
 **/

public class WordNotFoundException extends Exception {
    public WordNotFoundException() {

    }

    @Override
    public String getMessage() {
        return NO_SUCH_WORD;
    }

    public String getCode() {
        return NO_SUCH_WORD_CODE;
    }
}
