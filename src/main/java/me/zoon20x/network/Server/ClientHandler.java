package me.zoon20x.network.Server;

import me.zoon20x.network.Client.Client;
import me.zoon20x.network.Packets.AuthPacket;
import me.zoon20x.network.Packets.DisconnectPacket;
import me.zoon20x.network.SerializeData;
import me.zoon20x.network.logging.Logger;
import me.zoon20x.network.logging.Severity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ScheduledExecutorService;

public class ClientHandler implements Runnable{
    private Server server;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private int count;

    private ScheduledExecutorService service;

    private boolean hasPacket;
    private String packet;

    private int timeOut;
    private boolean useTimeOut;


    public ClientHandler(Server server, Socket socket){
        this.server = server;
        this.socket = socket;
        this.count = 0;
        useTimeOut = false;
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        if(useTimeOut) {
            count++;
            if (count >= timeOut) {
                closeConnection("Server Closed Connection");
            }
        }
        try {
            if (in.ready()) {
                hasPacket = true;
                packet = in.readLine();
                if(server.hasClient(socket)){
                    Logger.debug("Packet Received");
                    Object data = SerializeData.setData(packet);
                    if(data instanceof DisconnectPacket) {
                        closeConnection(((DisconnectPacket) data).getReason());
                        return;
                    }
                    server.getServerUtils().getClientEventManager().dispatchMessage(server.getClient(socket), data, out);
                    count = 0;
                }else{
                    Logger.warn("client does not exist");
                    Object data = SerializeData.setData(packet);
                    if(data instanceof AuthPacket){
                        AuthPacket authPacket = (AuthPacket) data;
                        if(server.getAuthentication().validateClientToken(authPacket.getToken())){
                            Client client = authPacket.getClient();
                            server.addClient(socket, client);
                            server.getServerUtils().getClientEventManager().dispatchSignIn(client);
                        }else{
                            closeConnection("Incorrect Token");
                        }
                    }else{
                        closeConnection("Failed Auth Check");
                    }
                }
            }else{
                hasPacket = false;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void closeConnection(String reason) {
        try {
            server.removeClient(socket);
            out.println(SerializeData.toString(new DisconnectPacket(reason)));
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();

            Logger.warn("Connection closed");
            service.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setService(ScheduledExecutorService service){
        this.service = service;
    }

    public boolean hasPacket() {
        return hasPacket;
    }

    public String getPacket() {
        return packet;
    }

    public void enableTimeOut(int timeOut){
        this.timeOut = timeOut;
        this.useTimeOut = true;
    }
}
