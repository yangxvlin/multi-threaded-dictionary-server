package com.unimelb.comp90015.Client;

import com.unimelb.comp90015.Constant;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-23 15:19
 * description:
 **/

public class DictionaryClient {

    public static void main(String[] args) {
        String serverAddress = args[0];
        int serverPort = Integer.parseInt(args[1]);

        DictionaryGUI gui = new DictionaryGUI(Constant.APP_NAME, serverAddress, serverPort);
    }
}
