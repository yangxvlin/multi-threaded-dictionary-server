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
    void searchConnection(String word, JTextArea dashboard);

    String addConnection(String word, String meaning);

    String deleteConnection(String word);

    void closeConnection() throws IOException;
}
