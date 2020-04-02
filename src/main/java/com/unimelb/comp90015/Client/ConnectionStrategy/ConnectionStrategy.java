package com.unimelb.comp90015.Client.ConnectionStrategy;

import com.google.gson.JsonObject;
import com.unimelb.comp90015.Util.ClientSocket;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.IOException;

import static com.unimelb.comp90015.Util.Constant.*;
import static com.unimelb.comp90015.Util.Util.getError;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-27 15:37
 * description:
 **/

public class ConnectionStrategy implements IConnectionStrategy {

    private String serverAddress;
    private int serverPort;

    private ClientSocket clientSocket;

    private int vipPriority;

    public ConnectionStrategy(String serverAddress, int serverPort, int vipPriority) throws IOException {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.vipPriority = vipPriority;

        clientSocket = new ClientSocket(serverAddress, serverPort, this.vipPriority);
    }

    public void reconnect() throws IOException {
        clientSocket = new ClientSocket(serverAddress, serverPort, this.vipPriority);
    }

    public String generateSearchRequest(String word) {
        JsonObject requestJSON = new JsonObject();
        requestJSON.addProperty(TASK_CODE, SEARCH_TASK_CODE);
        requestJSON.addProperty(CONTENT, word);
        return requestJSON.toString();
    }

    public String generateAddRequest(String word, String meaning) {
        JsonObject requestJSON = new JsonObject();
        requestJSON.addProperty(TASK_CODE, ADD_TASK_CODE);
        requestJSON.addProperty(CONTENT, word);
        requestJSON.addProperty(MEANING, meaning);
        return requestJSON.toString();
    }

    public String generateDeleteRequest(String word) {
        JsonObject requestJSON = new JsonObject();
        requestJSON.addProperty(TASK_CODE, DELETE_TASK_CODE);
        requestJSON.addProperty(CONTENT, word);
        return requestJSON.toString();
    }

    public void connect(String word, JTextArea dashboard, String request) {
        if (word.isEmpty()) {
            dashboard.setText(getError(EMPTY_WORD_CODE, EMPTY_WORD_CONTENT));
        } else {
            connect(dashboard, request);
        }

    }

    public void connect(String word, String meaning, JTextArea dashboard, String request) {
        if (word.isEmpty()) {
            dashboard.setText(getError(EMPTY_WORD_CODE, EMPTY_WORD_CONTENT));
        } else if (meaning.isEmpty()) {
            dashboard.setText(getError(EMPTY_MEANING_CODE, EMPTY_MEANING_CONTENT));
        } else {
            connect(dashboard, request);
        }
    }

    private void connect(JTextArea dashboard, String request) {
        dashboard.setText("");

        boolean requireReconnection = false;

        infiniteLoop: while (true) {
            try {
                if (requireReconnection) {
                    reconnect();
                }

                clientSocket.send(request);
                System.out.println("ClientSocket request sent: " + request);

                String responseCode;
                do {
                    String response = clientSocket.receive();
                    System.out.println("ClientSocket response received: " + response);
                    JSONParser jsonParser = new JSONParser();
                    JSONObject responseJSON = (JSONObject) jsonParser.parse(response);
                    responseCode = (String) responseJSON.get(RESPONSE_CODE);
                    switch (responseCode) {
                        case SERVER_NOTIFICATION_TASK_CODE:
                            String serverNotification = (String) responseJSON.get(CONTENT);
                            dashboard.append(serverNotification + "\n");
                            break;
                        case SUCCESSFUL_TASK_CODE:
                            dashboard.append((String) responseJSON.get(CONTENT));
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
    public void closeConnection() throws IOException {
        clientSocket.close();
    }
}
