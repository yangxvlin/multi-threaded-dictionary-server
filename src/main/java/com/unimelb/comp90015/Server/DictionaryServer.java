package com.unimelb.comp90015.Server;

import com.unimelb.comp90015.Server.Dictionary.DictionaryFactory;
import com.unimelb.comp90015.Server.Dictionary.IDictionary;
import com.unimelb.comp90015.Server.GUI.ServerGUI;
import com.unimelb.comp90015.Server.ThreadPool.HandleConnectionThread;
import com.unimelb.comp90015.Server.ThreadPool.PriorityTaskThread;
import com.unimelb.comp90015.Server.ThreadPool.ThreadPool;
import com.unimelb.comp90015.Util.ClientSocket;
import com.unimelb.comp90015.Util.InvalidMessageException;
import com.unimelb.comp90015.Util.InvalidVIPPriorityException;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.unimelb.comp90015.Util.Constant.ERROR_INVALID_SERVER_ARGS_CODE;
import static com.unimelb.comp90015.Util.Constant.ERROR_INVALID_SERVER_ARGS_CONTENT;
import static com.unimelb.comp90015.Util.Util.*;
import static com.unimelb.comp90015.Util.Util.serverPortError;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-23 15:19
 * description: the multi-threaded server with thread-pool architecture, thread
 *              per connection with a period of inactive time
 **/

public class DictionaryServer {
    /**
     * the server's port number
     */
    private static int serverPort;

    /**
     * dictionary file's path on disk
     */
    private static String dictionaryFilePath;

    /**
     * thread pool's size
     */
    private static int threadPoolSize;

    /**
     * client's connection's inactive waiting time; unit: second
     */
    private static int inactiveWaitTime;

    public static void main(String[] args) {
        // check inputs
        checkArgs(args);

        // create thread pool
        ThreadPool threadPool = new ThreadPool(threadPoolSize);
        System.out.println("Thread pool created.");

        // create dictionary from disk
        IDictionary dictionary = DictionaryFactory.getInstance()
                                                    .createSimpleDictionary(dictionaryFilePath);
        System.out.println("Dictionary read.");

        // create server
        ServerSocketFactory factory = ServerSocketFactory.getDefault();

        // create GUI
        ServerGUI serverGUI = new ServerGUI(threadPool, dictionary);
        System.out.println("GUI created");

        try(ServerSocket server = factory.createServerSocket(serverPort)) {
            System.out.println("Server created.");

            while (true) {
                // accept client's connection and receive VIP number
                ClientSocket clientSocket = new ClientSocket(server.accept(), (int) TimeUnit.SECONDS.toMillis(inactiveWaitTime));

                // create thread
                HandleConnectionThread connectionThread = new HandleConnectionThread(clientSocket, dictionary, threadPool);

                // add thread to thread pool
                PriorityTaskThread connectionPriorityTaskThread = new PriorityTaskThread(connectionThread, clientSocket.getVipPriority(), new Date());
                threadPool.execute(connectionPriorityTaskThread);
            }

        } catch (IOException e) {
            System.out.println("IOException.");
        } catch (InvalidMessageException e) {
            System.out.println(getError(e.getCode(), e.getMessage()));
        } catch (InvalidVIPPriorityException e) {
            System.out.println(getError(e.getCode(), e.getMessage()));
        }

        // close thread pool
        threadPool.shutdown();
    }

    /**
     * check whether server's inputs are correct
     * @param args command line arguments
     */
    private static void checkArgs(String[] args) {
        if (args.length < 4) {
            popupErrorDialog(ERROR_INVALID_SERVER_ARGS_CODE, ERROR_INVALID_SERVER_ARGS_CONTENT);
        }

        try {
            serverPort = Integer.parseInt(args[0]);
            if (checkWrongServerPort(serverPort)) {
                serverPortError();
            }
        } catch (NumberFormatException e) {
            serverPortError();
        }

        dictionaryFilePath = args[1];

        try {
            threadPoolSize = Integer.parseInt(args[2]);
            if (checkWrongThreadPoolSize(threadPoolSize)) {
                threadPoolSizeError();
            }
        } catch (NumberFormatException e) {
            threadPoolSizeError();
        }

        try {
            inactiveWaitTime = Integer.parseInt(args[3]);
            if (checkWrongInactiveTime(inactiveWaitTime)) {
                inactiveTimeError();
            }
        } catch (NumberFormatException e) {
            inactiveTimeError();
        }
    }
}
