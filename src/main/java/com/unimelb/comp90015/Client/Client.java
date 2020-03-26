package com.unimelb.comp90015.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-24 17:23
 * description:
 **/

public class Client {
    private Socket client;
    private DataInputStream is;
    private DataOutputStream os;

    public Client(String serverAddress, int serverPort) {
        try {
            client = new Socket(serverAddress, serverPort);
            is = new DataInputStream(client.getInputStream());
            os = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String request) {
        try {
            os.writeUTF(request);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receive() {
        try {
            return is.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void close() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
