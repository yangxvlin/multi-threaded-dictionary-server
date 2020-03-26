package com.unimelb.comp90015.Server;

import com.unimelb.comp90015.Server.Dictionary.IDictionary;
import com.unimelb.comp90015.Server.Dictionary.SimpleDictionary;
import com.unimelb.comp90015.Server.ThreadPool.HandleConnectionThread;
import com.unimelb.comp90015.Server.ThreadPool.PriorityRunnableTask;
import com.unimelb.comp90015.Server.ThreadPool.ThreadPool;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

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

        threadPool = new ThreadPool(threadPoolSize);
        System.out.println("Thread pool created.");
        IDictionary dictionary = new SimpleDictionary(dictionaryFilePath);
        System.out.println("Dictionary read.");

        ServerSocketFactory factory = ServerSocketFactory.getDefault();

        try(ServerSocket server = factory.createServerSocket(serverPort)) {

            System.out.println("Server created.");

            while (true) {
                Socket client = server.accept();

                HandleConnectionThread connectionThread = new HandleConnectionThread(client, dictionary, threadPool);
                PriorityRunnableTask connectionPriorityRunnableTask = new PriorityRunnableTask(connectionThread, 1, new Date());
                threadPool.execute(connectionPriorityRunnableTask);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        threadPool.shutdown();
    }
}
