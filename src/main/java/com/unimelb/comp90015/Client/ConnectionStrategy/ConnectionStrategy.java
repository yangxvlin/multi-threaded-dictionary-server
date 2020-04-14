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
import static com.unimelb.comp90015.Util.Util.stringIsAlnum;
import static com.unimelb.comp90015.Util.Util.stringIsAlpha;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-27 15:37
 * description: the simple connection strategy to connect the the dictionary server
 **/

public class ConnectionStrategy implements IConnectionStrategy {

    /**
     * server's address
     */
    private String serverAddress;

    /**
     * server's port number
     */
    private int serverPort;

    /**
     * socket to the server
     */
    private ClientSocket clientSocket;

    /**
     * client's VIP number
     */
    private int vipPriority;

    public ConnectionStrategy(String serverAddress, int serverPort, int vipPriority) throws IOException {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.vipPriority = vipPriority;

        clientSocket = new ClientSocket(serverAddress, serverPort, this.vipPriority);
    }

    /**
     * reconnect to server if there is an inactivity disconnection in server
     * @throws IOException io exception
     */
    public void reconnect() throws IOException {
        clientSocket = new ClientSocket(serverAddress, serverPort, this.vipPriority);
    }

    /**
     * @param word the word to be searched
     * @return request of search word in correct format to server
     */
    public String generateSearchRequest(String word) {
        JsonObject requestJSON = new JsonObject();
        requestJSON.addProperty(TASK_CODE, SEARCH_TASK_CODE);
        requestJSON.addProperty(CONTENT, word);
        return requestJSON.toString();
    }

    /**
     * @param word    the word to be added
     * @param meaning the word's meaning
     * @return request of add word with meaning in correct format to server
     */
    public String generateAddRequest(String word, String meaning) {
        JsonObject requestJSON = new JsonObject();
        requestJSON.addProperty(TASK_CODE, ADD_TASK_CODE);
        requestJSON.addProperty(CONTENT, word);
        requestJSON.addProperty(MEANING, meaning);
        return requestJSON.toString();
    }

    /**
     * @param word the word to be deleted
     * @return request of delete word in correct format to server
     */
    public String generateDeleteRequest(String word) {
        JsonObject requestJSON = new JsonObject();
        requestJSON.addProperty(TASK_CODE, DELETE_TASK_CODE);
        requestJSON.addProperty(CONTENT, word);
        return requestJSON.toString();
    }

    /**
     * send request to server and display responses on dashboard
     * @param word      word to be sent
     * @param dashboard GUI's place to display response from server
     * @param request   request string to server
     */
    public void connect(String word, JTextArea dashboard, String request) {
        if (word.isEmpty()) {
            dashboard.setText(getError(EMPTY_WORD_CODE, EMPTY_WORD_CONTENT));
        } else if (word.length() > MAX_WORD_LENGTH) {
            dashboard.setText(getError(ERROR_WORD_TOO_LONG_CODE, ERROR_WORD_TOO_LONG_CONTENT));
        } else if (!stringIsAlpha(word)) {
            dashboard.setText(getError(ERROR_BAD_WORD_INPUT_CODE, ERROR_BAD_WORD_INPUT_CONTENT));
        } else {
            connect(dashboard, request);
        }
    }

    /**
     * send request to server and display responses on dashboard
     * @param word      word to be sent
     * @param meaning   the word's meaning
     * @param dashboard GUI's place to display response from server
     * @param request   request string to server
     */
    public void connect(String word, String meaning, JTextArea dashboard, String request) {
        if (word.isEmpty()) {
            dashboard.setText(getError(EMPTY_WORD_CODE, EMPTY_WORD_CONTENT));
        } else if (word.length() > MAX_WORD_LENGTH) {
            dashboard.setText(getError(ERROR_WORD_TOO_LONG_CODE, ERROR_WORD_TOO_LONG_CONTENT));
        } else if (meaning.isEmpty()) {
            dashboard.setText(getError(EMPTY_MEANING_CODE, EMPTY_MEANING_CONTENT));
        } else if (!stringIsAlpha(word)) {
            dashboard.setText(getError(ERROR_BAD_WORD_INPUT_CODE, ERROR_BAD_WORD_INPUT_CONTENT));
        } else if (!stringIsAlnum(meaning)) {
            System.out.println(meaning);
            dashboard.setText(getError(ERROR_BAD_MEANING_INPUT_CODE, ERROR_BAD_MEANING_INPUT_CONTENT));
        } else {
            connect(dashboard, request);
        }
    }

    /**
     * pure fabricated sending request to server logic
     * @param dashboard GUI's place to display response from server
     * @param request   request string to server
     */
    private void connect(JTextArea dashboard, String request) {
        // clear dashboard
        dashboard.setText("");

        // haven't tried to reconnect
        boolean requireReconnection = false;

        infiniteLoop: while (true) {
            try {
                // sending message to server failed, try to reconnect
                if (requireReconnection) {
                    reconnect();
                }

                // send request to server
                clientSocket.send(request);
                System.out.println("ClientSocket request sent: " + request);

                String responseCode;
                // before task result, there will be server notification response;
                // so keeps listening until receive task's response or and error occurs
                do {
                    // receive response from server
                    String response = clientSocket.receive();
                    System.out.println("ClientSocket response received: " + response);

                    // parse response and display on dashboard
                    JSONParser jsonParser = new JSONParser();
                    JSONObject responseJSON = (JSONObject) jsonParser.parse(response);
                    responseCode = (String) responseJSON.get(RESPONSE_CODE);
                    switch (responseCode) {
                        case SERVER_NOTIFICATION_TASK_CODE:
                            String serverNotification = (String) responseJSON.get(CONTENT);
                            System.out.println(serverNotification);
                            dashboard.append(serverNotification + "\n");
                            dashboard.update(dashboard.getGraphics());
                            break;
                        case SUCCESSFUL_TASK_CODE:
                            dashboard.append((String) responseJSON.get(CONTENT));
                            dashboard.update(dashboard.getGraphics());
                            break infiniteLoop;
                        default:
                            String errorContent = (String) responseJSON.get(CONTENT);
                            dashboard.append(getError(responseCode, errorContent));
                            dashboard.update(dashboard.getGraphics());
                            break infiniteLoop;
                    }
                } while (responseCode.equals(SERVER_NOTIFICATION_TASK_CODE));

            // there is a connection error, requires reconnect
            // otherwise reconnection failed and an error occurs
            } catch (IOException e) {
                if (!requireReconnection) {
                    requireReconnection = true;
                    continue;
                }
                dashboard.setText(getError(ERROR_CONNECTION_CODE, ERROR_CONNECTION_CONTENT));
                break infiniteLoop;
            } catch (ParseException e) {
                dashboard.setText(getError(INVALID_RESPONSE_CODE, INVALID_RESPONSE_CONTENT));
                break infiniteLoop;
            }
        }
    }

    /**
     * close the socket to the server
     * @throws IOException io exception
     */
    @Override
    public void closeConnection() throws IOException {
        clientSocket.close();
    }
}
