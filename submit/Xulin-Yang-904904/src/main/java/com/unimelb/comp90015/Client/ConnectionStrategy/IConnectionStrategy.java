package com.unimelb.comp90015.Client.ConnectionStrategy;

import javax.swing.*;
import java.io.IOException;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-27 15:33
 * description: interface for communicating with server, for extendability
 **/

public interface IConnectionStrategy {

    /**
     * @param word the word to be searched
     * @return request of search word in correct format to server
     */
    String generateSearchRequest(String word);

    /**
     * @param word    the word to be added
     * @param meaning the word's meaning
     * @return request of add word with meaning in correct format to server
     */
    String generateAddRequest(String word, String meaning);

    /**
     * @param word the word to be deleted
     * @return request of delete word in correct format to server
     */
    String generateDeleteRequest(String word);

    /**
     * send request to server and display responses on dashboard
     * @param word      word to be sent
     * @param dashboard GUI's place to display response from server
     * @param request   request string to server
     */
    void connect(String word, JTextArea dashboard, String request);

    /**
     * send request to server and display responses on dashboard
     * @param word      word to be sent
     * @param meaning   the word's meaning
     * @param dashboard GUI's place to display response from server
     * @param request   request string to server
     */
    void connect(String word, String meaning, JTextArea dashboard, String request);

    /**
     * close the socket to the server
     * @throws IOException io exception
     */
    void closeConnection() throws IOException;
}
