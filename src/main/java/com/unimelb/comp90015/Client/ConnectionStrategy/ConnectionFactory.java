package com.unimelb.comp90015.Client.ConnectionStrategy;

import java.io.IOException;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-4-12 18:52:12
 * description: the simple factory to crete connection strategy
 **/

public class ConnectionFactory {
    /**
     * the singleton instance
     */
    private static ConnectionFactory ourInstance = new ConnectionFactory();

    /**
     * @return the singleton factory instance
     */
    public static ConnectionFactory getInstance() {
        return ourInstance;
    }

    private ConnectionFactory() {
    }

    /**
     * @param serverAddress server's address
     * @param serverPort    server's port number
     * @param vipPriority   client's VIP number
     * @return the created connection strategy object
     */
    public ConnectionStrategy createConnectionStrategy(String serverAddress,
                                                       int serverPort,
                                                       int vipPriority) throws IOException {
        return new ConnectionStrategy(serverAddress, serverPort, vipPriority);
    }
}
