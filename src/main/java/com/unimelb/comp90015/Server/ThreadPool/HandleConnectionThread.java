package com.unimelb.comp90015.Server.ThreadPool;

import com.google.gson.JsonObject;
import com.unimelb.comp90015.Server.Dictionary.DuplicateWordException;
import com.unimelb.comp90015.Server.Dictionary.IDictionary;
import com.unimelb.comp90015.Server.Dictionary.WordNotFoundException;
import com.unimelb.comp90015.Util.ClientSocket;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.SocketTimeoutException;

import static com.unimelb.comp90015.Util.Constant.*;
import static com.unimelb.comp90015.Util.Util.getError;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-26 22:01
 * description:
 **/

public class HandleConnectionThread extends Thread {
    private ClientSocket client;

    private IDictionary dictionary;

    private ThreadPool threadPool;

    public HandleConnectionThread(ClientSocket client, IDictionary dictionary, ThreadPool threadPool) throws IOException {
        this.client = client;

        this.dictionary = dictionary;

        this.threadPool = threadPool;

        // task queued notification
        sendRequestQueued();
    }

    @Override
    public void run() {
        System.out.println("client connected with vip:" + client.getVipPriority());

        try {
            // task to be executed notification
            sendRequestExecuted();

            while (true) {
                String requestString = null;
                try {
                    requestString = client.receive();
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
                    System.out.println(getError(ERROR_INVALID_MESSAGE_FORMAT_CODE, ERROR_INVALID_MESSAGE_FORMAT_CONTENT));
                }

                String taskCode = (String) requestJSON.get(TASK_CODE);
                String word = (String) requestJSON.get(CONTENT);

                switch (taskCode) {
                    case SEARCH_TASK_CODE:
                        search(dictionary, word);
                        break;
                    case ADD_TASK_CODE:
                        String meaning = (String) requestJSON.get(MEANING);
                        add(dictionary, word, meaning);
                        break;
                    case DELETE_TASK_CODE:
                        remove(dictionary, word);
                        break;
                    default:
                        System.out.println("error unknown request task");
                }
            }

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequestQueued() throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(RESPONSE_CODE, SERVER_NOTIFICATION_TASK_CODE);
        jsonObject.addProperty(CONTENT, "Your request is received and is queued. Currently there are " + threadPool.getQueueSize() + " tasks queued.");
        String result = jsonObject.toString();
        client.send(result);
    }

    private void sendRequestExecuted() throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(RESPONSE_CODE, SERVER_NOTIFICATION_TASK_CODE);
        jsonObject.addProperty(CONTENT, "Your request is being executed.");
        String result = jsonObject.toString();
        client.send(result);
    }

    private void search(IDictionary dictionary, String word) throws IOException {
        String result;
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(RESPONSE_CODE, SUCCESSFUL_TASK_CODE);
            jsonObject.addProperty(CONTENT, dictionary.search(word));
            result = jsonObject.toString();
        } catch (WordNotFoundException e) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(RESPONSE_CODE, e.getCode());
            jsonObject.addProperty(CONTENT, e.getMessage());

            result = jsonObject.toString();
        }

        client.send(result);
    }

    private void add(IDictionary dictionary, String word, String meaning) throws IOException {
        String result;
        try {
            dictionary.add(word, meaning);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(RESPONSE_CODE, SUCCESSFUL_TASK_CODE);
            jsonObject.addProperty(CONTENT, SUCCESSFUL_ADD_TASK_CONTENT);
            result = jsonObject.toString();
        } catch (DuplicateWordException e) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(RESPONSE_CODE, e.getCode());
            jsonObject.addProperty(CONTENT, e.getMessage());
            result = jsonObject.toString();
        }

        client.send(result);
    }

    private void remove(IDictionary dictionary, String word) throws IOException {
        String result;
        try {
            dictionary.remove(word);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(RESPONSE_CODE, SUCCESSFUL_TASK_CODE);
            jsonObject.addProperty(CONTENT, SUCCESSFUL_DELETE_TASK_CONTENT);
            result = jsonObject.toString();
        } catch (WordNotFoundException e) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(RESPONSE_CODE, e.getCode());
            jsonObject.addProperty(CONTENT, e.getMessage());
            result = jsonObject.toString();
        }

        client.send(result);
    }
}
