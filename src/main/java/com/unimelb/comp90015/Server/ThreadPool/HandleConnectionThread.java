package com.unimelb.comp90015.Server.ThreadPool;

import com.google.gson.JsonObject;
import com.unimelb.comp90015.Server.Dictionary.DuplicateWordException;
import com.unimelb.comp90015.Server.Dictionary.IDictionary;
import com.unimelb.comp90015.Server.Dictionary.WordNotFoundException;
import com.unimelb.comp90015.Server.Timer.CountDownTimer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

import static com.unimelb.comp90015.Constant.*;

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

    public HandleConnectionThread(Socket client, IDictionary dictionary, ThreadPool threadPool, int inactiveWaitTime) {
        this.client = client;
        try {
            this.client.setSoTimeout(inactiveWaitTime);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO #task queued notification

        this.dictionary = dictionary;

        this.threadPool = threadPool;
    }

    @Override
    public void run() {
        System.out.println("client connected!");
        // TODO task to be executed notification

        DataInputStream is = null;
        DataOutputStream os = null;
        try {
            is = new DataInputStream(client.getInputStream());
            os = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            String requestString = null;
            try {
                requestString = is.readUTF();
            // client already disconnect
            } catch (SocketTimeoutException e) {
                System.out.println("client inactive, so disconnect");
                break;
            } catch (IOException e) {
                System.out.println("client already disconnect");
                break;
            }
            System.out.println("    client's request: " + requestString);
            System.out.println(requestString);

            JSONParser jsonParser = new JSONParser();
            JSONObject requestJSON = null;
            try {
                requestJSON = (JSONObject) jsonParser.parse(requestString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String taskCode = (String) requestJSON.get(TASK_CODE);
            String word = (String) requestJSON.get(CONTENT);
            switch (taskCode) {
                case SEARCH_TASK_CODE:
                    search(dictionary, word, os);
//                    SearchTaskThread task = new SearchTaskThread(dictionary, word, os);
//                    PriorityTaskThread priorityTask = new PriorityTaskThread(task, 1, new Date());
//                    threadPool.execute(priorityTask);
                    break;
                case ADD_TASK_CODE:
                    String meaning = (String) requestJSON.get(MEANING);
                    add(dictionary, word, meaning, os);
                    break;
                case DELETE_TASK_CODE:
                    remove(dictionary, word, os);
                    break;
                default:
                    System.out.println("error unknown request task");
            }
        }

        try {
            is.close();
            os.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(String response, DataOutputStream os) {
        System.out.println("    client's response string: " + response);
        try {
            os.writeUTF(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void search(IDictionary dictionary, String word, DataOutputStream os) {
        String result;
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(RESPONSE_CODE, SUCCESSFUL_SEARCH_TASK_CODE);
            jsonObject.addProperty(MEANING, dictionary.search(word));
            result = jsonObject.toString();
        } catch (WordNotFoundException e) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(RESPONSE_CODE, e.getCode());
            jsonObject.addProperty(CONTENT, e.getMessage());

            result = jsonObject.toString();
        }

        sendResponse(result, os);
    }

    private void add(IDictionary dictionary, String word, String meaning, DataOutputStream os) {
        String result;
        try {
            dictionary.add(word, meaning);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(RESPONSE_CODE, SUCCESSFUL_ADD_TASK_CODE);
            jsonObject.addProperty(CONTENT, SUCCESSFUL_ADD_TASK_CONTENT);
            result = jsonObject.toString();
        } catch (DuplicateWordException e) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(RESPONSE_CODE, e.getCode());
            jsonObject.addProperty(CONTENT, e.getMessage());
            result = jsonObject.toString();
        }

        sendResponse(result, os);
    }

    private void remove(IDictionary dictionary, String word, DataOutputStream os) {
        String result;
        try {
            dictionary.remove(word);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(RESPONSE_CODE, SUCCESSFUL_DELETE_TASK_CODE);
            jsonObject.addProperty(CONTENT, SUCCESSFUL_DELETE_TASK_CONTENT);
            result = jsonObject.toString();
        } catch (WordNotFoundException e) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(RESPONSE_CODE, e.getCode());
            jsonObject.addProperty(CONTENT, e.getMessage());
            result = jsonObject.toString();
        }

        sendResponse(result, os);
    }
}
