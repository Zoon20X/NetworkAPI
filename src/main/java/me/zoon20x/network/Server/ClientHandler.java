package me.zoon20x.network.Server;

import me.zoon20x.network.Client.Client;
import me.zoon20x.network.Packets.AuthPacket;
import me.zoon20x.network.Packets.DisconnectPacket;
import me.zoon20x.network.SerializeData;
import me.zoon20x.network.logging.Logging;
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


    public ClientHandler(Server server, Socket socket){
        this.server = server;
        this.socket = socket;
        this.count = 0;
        timeOut = 10*300;
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        count++;
        if(count>=timeOut){
            closeConnection("Server Closed Connection");
        }
        try {
            if (in.ready()) {
                hasPacket = true;
                packet = in.readLine();
                if(server.hasClient(socket)){
                    Logging.log("Packet Received", Severity.Debug);
                    server.getServerUtils().getClientEventManager().dispatchMessage(server.getClient(socket), SerializeData.setData(packet), out);
                    count = 0;
                }else{
                    Logging.log("client does not exist", Severity.Warning);
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

            Logging.log("Connection closed", Severity.Warning);
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
}
