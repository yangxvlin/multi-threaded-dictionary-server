package com.unimelb.comp90015.Client;

import com.unimelb.comp90015.Client.ConnectionStrategy.ConnectionStrategy;
import com.unimelb.comp90015.Client.ConnectionStrategy.IConnectionStrategy;
import com.unimelb.comp90015.Constant;

import javax.swing.*;
import java.io.IOException;

import static com.unimelb.comp90015.Constant.ERROR_CONNECTION_CODE;
import static com.unimelb.comp90015.Constant.ERROR_CONNECTION_CONTENT;
import static com.unimelb.comp90015.Util.getError;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-23 15:19
 * description:
 **/

public class DictionaryClient {

    public static void main(String[] args) {
        String serverAddress = args[0];
        int serverPort = Integer.parseInt(args[1]);

        IConnectionStrategy connectionStrategy = null;
        try {
            connectionStrategy = new ConnectionStrategy(serverAddress, serverPort);
            DictionaryGUI gui = new DictionaryGUI(Constant.APP_NAME, connectionStrategy);
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(
                    null,
                    getError(ERROR_CONNECTION_CODE, ERROR_CONNECTION_CONTENT),
                    "Error",
                    JOptionPane.OK_CANCEL_OPTION
            );
        }

    }
}
