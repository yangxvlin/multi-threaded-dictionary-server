package com.unimelb.comp90015.Server.ThreadPool;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unimelb.comp90015.Server.Dictionary.IDictionary;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

import static com.unimelb.comp90015.Constant.CONTENT;
import static com.unimelb.comp90015.Constant.SEARCH_TASK_CODE;
import static com.unimelb.comp90015.Constant.TASK_CODE;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-26 22:01
 * description:
 **/

public class HandleConnectionThread extends Thread {
    private Socket client;

    private IDictionary dictionary;

    private ThreadPool threadPool;

    public HandleConnectionThread(Socket client, IDictionary dictionary, ThreadPool threadPool) {
        this.client = client;

        this.dictionary = dictionary;

        this.threadPool = threadPool;
    }

    @Override
    public void run() {
        System.out.println("client connected!");
        DataInputStream is = null;
        DataOutputStream os = null;
        String requestString = null;
        try {
            is = new DataInputStream(client.getInputStream());
            os = new DataOutputStream(client.getOutputStream());
            requestString = is.readUTF();
            System.out.println("    client's request: " + requestString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(requestString);

        assert requestString != null;
        JSONParser jsonParser = new JSONParser();
        JSONObject requestJSON = null;
        try {
            System.out.println(requestString);
            requestJSON = (JSONObject) jsonParser.parse(requestString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String taskCode = (String) requestJSON.get(TASK_CODE);
        switch (taskCode) {
            case SEARCH_TASK_CODE:
                String word = (String) requestJSON.get(CONTENT);

                SearchTask task = new SearchTask(dictionary, word, os);
                PriorityRunnableTask priorityTask = new PriorityRunnableTask(task, 1, new Date());
                threadPool.execute(priorityTask);
                break;
            default:
                System.out.println("error unknown request task");
        }
    }
}
