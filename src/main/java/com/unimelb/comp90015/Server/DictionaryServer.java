package com.unimelb.comp90015.Server;

import com.unimelb.comp90015.Server.Dictionary.IDictionary;
import com.unimelb.comp90015.Server.Dictionary.SimpleDictionary;
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
 * description:
 **/

public class DictionaryServer {

    private static int serverPort;
    private static String dictionaryFilePath;
    private static int threadPoolSize;
    /**
     * unit: second
     */
    private static int inactiveWaitTime;

    private static ThreadPool threadPool;

    public static void main(String[] args) {
        checkArgs(args);

        threadPool = new ThreadPool(threadPoolSize);
        System.out.println("Thread pool created.");
        IDictionary dictionary = new SimpleDictionary(dictionaryFilePath);
        System.out.println("Dictionary read.");

        ServerSocketFactory factory = ServerSocketFactory.getDefault();

        try(ServerSocket server = factory.createServerSocket(serverPort)) {

            System.out.println("Server created.");

            while (true) {
                ClientSocket clientSocket = new ClientSocket(server.accept(), (int) TimeUnit.SECONDS.toMillis(inactiveWaitTime));

                HandleConnectionThread connectionThread = new HandleConnectionThread(clientSocket, dictionary, threadPool);
                PriorityTaskThread connectionPriorityTaskThread = new PriorityTaskThread(connectionThread, clientSocket.getVipPriority(), new Date());
                threadPool.execute(connectionPriorityTaskThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidMessageException e) {
            System.out.println(getError(e.getCode(), e.getMessage()));
        } catch (InvalidVIPPriorityException e) {
            System.out.println(getError(e.getCode(), e.getMessage()));
        }

        threadPool.shutdown();
    }

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
