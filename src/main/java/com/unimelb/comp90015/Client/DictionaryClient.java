package com.unimelb.comp90015.Client;

import com.unimelb.comp90015.Client.ConnectionStrategy.ConnectionStrategy;
import com.unimelb.comp90015.Client.ConnectionStrategy.IConnectionStrategy;
import com.unimelb.comp90015.Client.GUI.DictionaryGUI;

import java.io.IOException;

import static com.unimelb.comp90015.Util.Constant.*;
import static com.unimelb.comp90015.Util.Util.*;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-23 15:19
 * description:
 **/

public class DictionaryClient {

    /**
     * server's address
     */
    private static String serverAddress;

    /**
     * server's port number
     */
    private static int serverPort;

    /**
     * client's VIP number
     */
    private static int vipPriority;

    public static void main(String[] args) {
        checkArgs(args);

        IConnectionStrategy connectionStrategy = null;
        try {
            // establish connection to server
            connectionStrategy = new ConnectionStrategy(serverAddress, serverPort, vipPriority);
            // start GUI
            DictionaryGUI gui = new DictionaryGUI(APP_NAME+"VIP:lv-"+vipPriority, connectionStrategy);
        } catch (IOException e) {
            // connection to server error
            popupErrorDialog(ERROR_CONNECTION_CODE, ERROR_CONNECTION_CONTENT);
        }

    }

    /**
     * check whether client's inputs are correct
     * @param args command line arguments
     */
    private static void checkArgs(String[] args) {
        if (args.length < 3) {
            popupErrorDialog(ERROR_INVALID_CLIENT_ARGS_CODE, ERROR_INVALID_CLIENT_ARGS_CONTENT);
        } else {
            serverAddress = args[0];

            try {
                serverPort = Integer.parseInt(args[1]);
                if (checkWrongServerPort(serverPort)) {
                    serverPortError();
                }
            } catch (NumberFormatException e) {
                serverPortError();
            }

            try {
                vipPriority = Integer.parseInt(args[2]);
                if (checkWrongVipPriority(vipPriority)) {
                    vipPriorityError();
                }
            } catch (NumberFormatException e) {
                vipPriorityError();
            }
        }
    }

    /**
     * invoke dialog for vip priority number error
     */
    private static void vipPriorityError() {
        popupErrorDialog(ERROR_INVALID_VIP_NUMBER_CODE, ERROR_INVALID_VIP_NUMBER_CONTENT);
    }
}
