package com.unimelb.comp90015.Client.ConnectionStrategy;

import javax.swing.*;
import java.io.IOException;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-27 15:33
 * description:
 **/

public interface IConnectionStrategy {

    String generateSearchRequest(String word);

    String generateAddRequest(String word, String meaning);

    String generateDeleteRequest(String word);

    void connect(String word, JTextArea dashboard, String request);

    void connect(String word, String meaning, JTextArea dashboard, String request);

    void closeConnection() throws IOException;
}
