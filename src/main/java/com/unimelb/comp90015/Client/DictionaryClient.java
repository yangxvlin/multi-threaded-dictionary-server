package com.unimelb.comp90015.Client;

import com.unimelb.comp90015.Client.ConnectionStrategy.ConnectionStrategy;
import com.unimelb.comp90015.Client.ConnectionStrategy.IConnectionStrategy;
import com.unimelb.comp90015.Client.GUI.DictionaryGUI;

import javax.swing.*;
import java.io.IOException;

import static com.unimelb.comp90015.Util.Constant.*;
import static com.unimelb.comp90015.Util.Util.checkWrongVipPriority;
import static com.unimelb.comp90015.Util.Util.getError;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-23 15:19
 * description:
 **/

public class DictionaryClient {

    private static String serverAddress;

    private static int serverPort;

    private static int vipPriority;

    public static void main(String[] args) {
        checkArgs(args);

        IConnectionStrategy connectionStrategy = null;
        try {
            connectionStrategy = new ConnectionStrategy(serverAddress, serverPort, vipPriority);
            DictionaryGUI gui = new DictionaryGUI(APP_NAME, connectionStrategy);
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(
                    null,
                    getError(ERROR_CONNECTION_CODE, ERROR_CONNECTION_CONTENT),
                    "Error",
                    JOptionPane.OK_CANCEL_OPTION
            );
        }

    }

    private static void checkArgs(String[] args) {
        if (args.length < 3) {
            JOptionPane.showConfirmDialog(
                null,
                getError(ERROR_INVALID_CLIENT_ARGS_CODE, ERROR_INVALID_CLIENT_ARGS_CONTENT),
                "Error",
                JOptionPane.OK_CANCEL_OPTION
            );
            System.exit(1);
        } else {
            serverAddress = args[0];

            try {
                serverPort = Integer.parseInt(args[1]);
                if (checkWrongServerPort()) {
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

    private static boolean checkWrongServerPort() {
        return serverPort < 49152 || serverPort > 65535;
    }

    private static void serverPortError() {
        JOptionPane.showConfirmDialog(
                null,
                getError(ERROR_INVALID_PORT_NUMBER_CODE, ERROR_INVALID_PORT_NUMBER_CONTENT),
                "Error",
                JOptionPane.OK_CANCEL_OPTION
        );
        System.exit(1);
    }

    private static void vipPriorityError() {
        JOptionPane.showConfirmDialog(
                null,
                getError(ERROR_INVALID_VIP_NUMBER_CODE, ERROR_INVALID_VIP_NUMBER_CONTENT),
                "Error",
                JOptionPane.OK_CANCEL_OPTION
        );
        System.exit(1);
    }
}
