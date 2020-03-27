package com.unimelb.comp90015.Client.ConnectionStrategy;

import com.google.gson.JsonObject;
import com.unimelb.comp90015.Client.Client;

import java.io.IOException;

import static com.unimelb.comp90015.Constant.*;
import static com.unimelb.comp90015.Util.getError;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-27 15:37
 * description:
 **/

public class ConnectionStrategy implements IConnectionStrategy {

    private String serverAddress;
    private int serverPort;

    public ConnectionStrategy(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort =serverPort;
    }

    @Override
    public String searchConnection(String word) {
        if (word.isEmpty()) {
            return getError(EMPTY_WORD_CODE, EMPTY_WORD_CONTENT);
        }

        JsonObject requestJSON = new JsonObject();
        requestJSON.addProperty(TASK_CODE, SEARCH_TASK_CODE);
        requestJSON.addProperty(CONTENT, word);
        String request = requestJSON.toString();

        String response;
        try {
            Client searchClient = new Client(this.serverAddress, this.serverPort);
            searchClient.send(request);
            System.out.println("Client request sent: " + request);
            response = searchClient.receive();
            System.out.println("Client response received: " + response);
            searchClient.close();
        } catch (IOException e) {
            return getError(ERROR_CONNECTION_CODE, ERROR_CONNECTION_CONTENT);
        }


        return response;
    }

    @Override
    public String addConnection(String word, String meaning) {
        if (word.isEmpty()) {
            return getError(EMPTY_WORD_CODE, EMPTY_WORD_CONTENT);
        }
        if (meaning.isEmpty()) {
            return getError(EMPTY_MEANING_CODE, EMPTY_MEANING_CONTENT);
        }

        JsonObject requestJSON = new JsonObject();
        requestJSON.addProperty(TASK_CODE, ADD_TASK_CODE);
        requestJSON.addProperty(CONTENT, word);
        requestJSON.addProperty(MEANING, meaning);
        String request = requestJSON.toString();

        String response;
        try {
            Client searchClient = new Client(this.serverAddress, this.serverPort);
            searchClient.send(request);
            System.out.println("Client request sent: " + request);
            response = searchClient.receive();
            System.out.println("Client response received: " + response);
            searchClient.close();
        } catch (IOException e) {
            return getError(ERROR_CONNECTION_CODE, ERROR_CONNECTION_CONTENT);
        }


        return response;
    }

    @Override
    public String deleteConnection(String word) {
        if (word.isEmpty()) {
            return getError(EMPTY_WORD_CODE, EMPTY_WORD_CONTENT);
        }

        JsonObject requestJSON = new JsonObject();
        requestJSON.addProperty(TASK_CODE, DELETE_TASK_CODE);
        requestJSON.addProperty(CONTENT, word);
        String request = requestJSON.toString();

        String response;
        try {
            Client searchClient = new Client(this.serverAddress, this.serverPort);
            searchClient.send(request);
            System.out.println("Client request sent: " + request);
            response = searchClient.receive();
            System.out.println("Client response received: " + response);
            searchClient.close();
        } catch (IOException e) {
            return getError(ERROR_CONNECTION_CODE, ERROR_CONNECTION_CONTENT);
        }


        return response;
    }
}
