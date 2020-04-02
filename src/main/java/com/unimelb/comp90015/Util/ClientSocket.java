package com.unimelb.comp90015.Util;

import com.google.gson.JsonObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.unimelb.comp90015.Util.Constant.VIP_PRIORITY;
import static com.unimelb.comp90015.Util.Util.checkWrongVipPriority;

/**
 * Xulin Yang, 904904
 *
 * @create 2020-03-24 17:23
 * description:
 **/

public class ClientSocket {
    private Socket client;
    private DataInputStream is;
    private DataOutputStream os;
    private int vipPriority;

    public ClientSocket(String serverAddress, int serverPort, int vipPriority) throws IOException {
        client = new Socket(serverAddress, serverPort);
        is = new DataInputStream(client.getInputStream());
        os = new DataOutputStream(client.getOutputStream());
        this.vipPriority = vipPriority;
        sendVIPNumber();
    }

    public ClientSocket(Socket client, int inactiveWaitTime) throws IOException, InvalidMessageException, InvalidVIPPriorityException {
        this.client = client;
        this.client.setSoTimeout(inactiveWaitTime);
        is = new DataInputStream(client.getInputStream());
        os = new DataOutputStream(client.getOutputStream());
        receiveVIPNumber();
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
        is.close();
        os.close();
    }

    private void sendVIPNumber() throws IOException {
        send(generateVIPNumberRequest());
    }

    private String generateVIPNumberRequest() {
        JsonObject requestJSON = new JsonObject();
        requestJSON.addProperty(VIP_PRIORITY, Integer.toString(vipPriority));
        return requestJSON.toString();
    }

    private void receiveVIPNumber() throws IOException, InvalidMessageException, InvalidVIPPriorityException {
        String response = receive();
        JSONParser jsonParser = new JSONParser();
        JSONObject requestJSON = null;
        try {
            requestJSON = (JSONObject) jsonParser.parse(response);
            String vipPriorityString = (String) requestJSON.get(VIP_PRIORITY);
            this.vipPriority = Integer.parseInt(vipPriorityString);
            if (checkWrongVipPriority(this.vipPriority)) {
                throw new InvalidVIPPriorityException();
            }

        } catch (ParseException | NumberFormatException e) {
            throw new InvalidMessageException();
        }
    }

    public int getVipPriority() {
        return vipPriority;
    }
}
