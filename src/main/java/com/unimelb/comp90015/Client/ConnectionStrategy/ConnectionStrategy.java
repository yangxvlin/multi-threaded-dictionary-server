package com.unimelb.comp90015.Client.ConnectionStrategy;

import com.google.gson.JsonObject;
import com.unimelb.comp90015.Client.Client;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
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

    private Client client;

    public ConnectionStrategy(String serverAddress, int serverPort) throws IOException {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;

        client = new Client(serverAddress, serverPort);
    }

    public void reconnect() throws IOException {
        client = new Client(serverAddress, serverPort);
    }

    @Override
    public void searchConnection(String word, JTextArea dashboard) {
        if (word.isEmpty()) {
            dashboard.setText(getError(EMPTY_WORD_CODE, EMPTY_WORD_CONTENT));
        }

        JsonObject requestJSON = new JsonObject();
        requestJSON.addProperty(TASK_CODE, SEARCH_TASK_CODE);
        requestJSON.addProperty(CONTENT, word);
        String request = requestJSON.toString();

        boolean requireReconnection = false;

        infiniteLoop: while (true) {
            try {
                if (requireReconnection) {
                    reconnect();
                }

                client.send(request);
                System.out.println("Client request sent: " + request);

                String responseCode;
                do {
                    String response = client.receive();
                    System.out.println("Client response received: " + response);
                    JSONParser jsonParser = new JSONParser();
                    JSONObject responseJSON = (JSONObject) jsonParser.parse(response);
                    responseCode = (String) responseJSON.get(RESPONSE_CODE);
                    switch (responseCode) {
                        case SERVER_NOTIFICATION_TASK_CODE:
                            String serverNotification = (String) responseJSON.get(CONTENT);
                            dashboard.setText(serverNotification);
                            break;
                        case SUCCESSFUL_SEARCH_TASK_CODE:
                            dashboard.setText((String) responseJSON.get(MEANING));
                            break infiniteLoop;
                        default:
                            String errorContent = (String) responseJSON.get(CONTENT);
                            dashboard.setText(getError(responseCode, errorContent));
                            break infiniteLoop;
                    }
                } while (responseCode.equals(SERVER_NOTIFICATION_TASK_CODE));

            } catch (IOException e) {
                if (!requireReconnection) {
                    requireReconnection = true;
                    continue;
                }
                dashboard.setText(getError(ERROR_CONNECTION_CODE, ERROR_CONNECTION_CONTENT));
            } catch (ParseException e) {
                dashboard.setText(getError(INVALID_RESPONSE_CODE, INVALID_RESPONSE_CONTENT));
            }
        }
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
            client.send(request);
            System.out.println("Client request sent: " + request);
            response = client.receive();
            System.out.println("Client response received: " + response);
            JSONParser jsonParser = new JSONParser();
            JSONObject responseJSON = (JSONObject) jsonParser.parse(response);
            String responseCode = (String) responseJSON.get(RESPONSE_CODE);
            switch (responseCode) {
                case SUCCESSFUL_ADD_TASK_CODE:
                    return (String) responseJSON.get(CONTENT);
                default:
                    String errorContent = (String) responseJSON.get(CONTENT);
                    return getError(responseCode, errorContent);
            }

        } catch (IOException e) {
            return getError(ERROR_CONNECTION_CODE, ERROR_CONNECTION_CONTENT);
        } catch (ParseException e) {
            return getError(INVALID_RESPONSE_CODE, INVALID_RESPONSE_CONTENT);
        }
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
            client.send(request);
            System.out.println("Client request sent: " + request);
            response = client.receive();
            System.out.println("Client response received: " + response);
            JSONParser jsonParser = new JSONParser();
            JSONObject responseJSON = (JSONObject) jsonParser.parse(response);
            String responseCode = (String) responseJSON.get(RESPONSE_CODE);
            switch (responseCode) {
                case SUCCESSFUL_DELETE_TASK_CODE:
                    return (String) responseJSON.get(CONTENT);
                default:
                    String errorContent = (String) responseJSON.get(CONTENT);
                    return getError(responseCode, errorContent);
            }

        } catch (IOException e) {
            return getError(ERROR_CONNECTION_CODE, ERROR_CONNECTION_CONTENT);
        } catch (ParseException e) {
            return getError(INVALID_RESPONSE_CODE, INVALID_RESPONSE_CONTENT);
        }
    }

    @Override
    public void closeConnection() throws IOException {
        client.close();
    }
}
