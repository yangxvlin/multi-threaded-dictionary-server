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

import static com.unimelb.comp90015.Util.Util.getError;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-23 15:19
 * description:
 **/

public class DictionaryServer {

    private static ThreadPool threadPool;

    public static void main(String[] args) {
        int serverPort = Integer.parseInt(args[0]);
        String dictionaryFilePath = args[1];
        int threadPoolSize = Integer.parseInt(args[2]);
        int inactiveWaitTime = Integer.parseInt(args[3]);  // second

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
}
