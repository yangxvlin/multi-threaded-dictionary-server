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
 * description: the thread to execute client's request at server
 **/

public class HandleConnectionThread implements Runnable {
    /**
     * client's socket
     */
    private ClientSocket client;

    /**
     * dictionary object
     */
    private IDictionary dictionary;

    /**
     * thread pool object used to get runtime queued tasks number
     */
    private ThreadPool threadPool;

    public HandleConnectionThread(ClientSocket client, IDictionary dictionary, ThreadPool threadPool) throws IOException {
        this.client = client;

        this.dictionary = dictionary;

        this.threadPool = threadPool;

        // task queued notification to client
        sendRequestQueued();
    }

    @Override
    public void run() {
        System.out.println("client connected with vip:" + client.getVipPriority());

        try {
            // task to be executed notification
            sendRequestExecuted();

            // continuously interact with the client
            while (true) {

                // receive request form client
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

                // process client's request
                JSONParser jsonParser = new JSONParser();
                JSONObject requestJSON = null;
                try {
                    requestJSON = (JSONObject) jsonParser.parse(requestString);
                } catch (ParseException e) {
                    System.out.println(getError(ERROR_INVALID_MESSAGE_FORMAT_CODE, ERROR_INVALID_MESSAGE_FORMAT_CONTENT));
                }
                String taskCode = (String) requestJSON.get(TASK_CODE);
                String word = (String) requestJSON.get(CONTENT);

                // response to various task
                switch (taskCode) {
                    case SEARCH_TASK_CODE:
                        search(word);
                        break;
                    case ADD_TASK_CODE:
                        String meaning = (String) requestJSON.get(MEANING);
                        add(word, meaning);
                        break;
                    case DELETE_TASK_CODE:
                        remove(word);
                        break;
                    default:
                        System.out.println("error unknown request task");
                }
            }

            client.close();
        } catch (IOException e) {
            System.out.println("IOException.");
        }
    }

    /**
     * notify client's task is queued and number of tasks queued in thread pool
     * @throws IOException io exception
     */
    private void sendRequestQueued() throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(RESPONSE_CODE, SERVER_NOTIFICATION_TASK_CODE);
        jsonObject.addProperty(CONTENT, "Your request is received and is queued." +
                " Currently there are " + (threadPool.getQueueSize()+1) + " tasks queued.");
        String result = jsonObject.toString();
        client.send(result);
    }

    /**
     * notify client's task is under execution
     * @throws IOException io exception
     */
    private void sendRequestExecuted() throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(RESPONSE_CODE, SERVER_NOTIFICATION_TASK_CODE);
        jsonObject.addProperty(CONTENT, "Your request is being executed.");
        String result = jsonObject.toString();
        client.send(result);
    }

    /**
     * send successful search word's meaning or error message to client
     * @param word word to be searched
     * @throws IOException io exception
     */
    private void search(String word) throws IOException {
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

    /**
     * send successful add word or error message to client
     * @param word    word to be added
     * @param meaning word's meaning
     * @throws IOException io exception
     */
    private void add(String word, String meaning) throws IOException {
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

    /**
     * send successful delete word or error message to client
     * @param word word to be removed
     * @throws IOException io exception
     */
    private void remove(String word) throws IOException {
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
