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

    public Client(String serverAddress, int serverPort) throws IOException {
        client = new Socket(serverAddress, serverPort);
        is = new DataInputStream(client.getInputStream());
        os = new DataOutputStream(client.getOutputStream());
    }

    public void send(String request) throws IOException {
        os.writeUTF(request);
        os.flush();
    }

    public String receive() throws IOException {
        return is.readUTF();
    }

    public void close() throws IOException {
        client.close();
    }
}
