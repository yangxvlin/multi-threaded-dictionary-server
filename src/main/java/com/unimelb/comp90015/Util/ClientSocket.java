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
 * description: the class to handle the socket creation as well as notification
 *              of user's VIP level
 **/

public class ClientSocket {
    /**
     * client's socket
     */
    private Socket client;

    /**
     * client's input data stream
     */
    private DataInputStream is;

    /**
     * client's output data stream
     */
    private DataOutputStream os;

    /**
     * client's VIP level for the current connection
     */
    private int vipPriority;

    /**
     * socket creation on client's side
     */
    public ClientSocket(String serverAddress, int serverPort, int vipPriority) throws IOException {
        client = new Socket(serverAddress, serverPort);
        is = new DataInputStream(client.getInputStream());
        os = new DataOutputStream(client.getOutputStream());
        this.vipPriority = vipPriority;
        sendVIPNumber();
    }

    /**
     * socket creation on server side
     */
    public ClientSocket(Socket client, int inactiveWaitTime) throws IOException, InvalidMessageException, InvalidVIPPriorityException {
        this.client = client;
        this.client.setSoTimeout(inactiveWaitTime);
        is = new DataInputStream(client.getInputStream());
        os = new DataOutputStream(client.getOutputStream());
        receiveVIPNumber();
    }

    /**
     * send request message through socket
     * @param request request message
     * @throws IOException io exception
     */
    public void send(String request) throws IOException {
        os.writeUTF(request);
        os.flush();
    }

    /**
     * @return received message from the socket
     * @throws IOException io exception
     */
    public String receive() throws IOException {
        return is.readUTF();
    }

    /**
     * close the socket
     * @throws IOException io exception
     */
    public void close() throws IOException {
        client.close();
        is.close();
        os.close();
    }

    /**
     * send client's vip number to server
     * @throws IOException io exception
     */
    private void sendVIPNumber() throws IOException {
        send(generateVIPNumberRequest());
    }

    /**
     * @return generated VIP number communication message
     */
    private String generateVIPNumberRequest() {
        JsonObject requestJSON = new JsonObject();
        requestJSON.addProperty(VIP_PRIORITY, Integer.toString(vipPriority));
        return requestJSON.toString();
    }

    /**
     * server receive client's vip number
     * @throws IOException io exception
     * @throws InvalidMessageException message in wrong format
     * @throws InvalidVIPPriorityException invalid vip number
     */
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

    /**
     * @return client's VIP level for the current connection
     */
    public int getVipPriority() {
        return vipPriority;
    }
}
