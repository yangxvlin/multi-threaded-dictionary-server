package com.unimelb.comp90015.Server;

import com.unimelb.comp90015.Server.ThreadPool.ThreadPool;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-23 15:19
 * description:
 **/

public class DictionaryServer {

    private static ThreadPool threadPool;

    public static void main(String[] args) {
        String serverAddress = args[0];
        int serverPort = Integer.parseInt(args[1]);
        String dictionaryFilePath = args[2];
        int threadPoolSize = Integer.parseInt(args[3]);

        threadPool = new ThreadPool(threadPoolSize);
        Dictionary dictionary = new Dictionary(dictionaryFilePath);
    }
}
