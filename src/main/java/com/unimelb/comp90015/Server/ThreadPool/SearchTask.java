package com.unimelb.comp90015.Server.ThreadPool;

import com.unimelb.comp90015.Server.Dictionary.IDictionary;
import com.unimelb.comp90015.Server.Dictionary.WordNotFoundException;

import java.io.DataOutputStream;
import java.io.IOException;

import static com.unimelb.comp90015.Constant.*;
import static com.unimelb.comp90015.Util.wrapWithQuotation;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-26 17:28
 * description:
 **/

public class SearchTask extends Thread {
    private IDictionary dictionary;
    private String word;
    private DataOutputStream os;

    public SearchTask(IDictionary dictionary, String word, DataOutputStream os) {
        this.dictionary = dictionary;
        this.word = word;
        this.os = os;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     */
    @Override
    public void run() {
        String result;
        try {
            result = dictionary.search(word);
        } catch (WordNotFoundException e) {
            result = "{" + wrapWithQuotation(RESPONSE_CODE) + ":" + wrapWithQuotation(e.getCode()) + "," +
                    wrapWithQuotation(CONTENT) + ":" + wrapWithQuotation(e.getMessage()) + "}\n";
        }
        System.out.println("    client's response string: " + result);
        try {
            os.writeUTF(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
