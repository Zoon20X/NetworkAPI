package me.zoon20x.network.Client;

import me.zoon20x.network.Packets.AuthPacket;
import me.zoon20x.network.Packets.DisconnectPacket;
import me.zoon20x.network.SerializeData;
import me.zoon20x.network.Server.Server;
import me.zoon20x.network.Server.events.ClientListener;
import me.zoon20x.network.Server.events.ClientMessageEvent;
import me.zoon20x.network.logging.Logging;
import me.zoon20x.network.logging.Severity;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientUtils {
    private Client client;

    private ScheduledExecutorService service;

    private PrintWriter out;
    private BufferedReader in;

    private String ip;
    private int port;

    public ClientUtils(Client client, String ip, int port){
        this.client = client;
        this.ip = ip;
        this.port = port;
        setupShutdownHook();
    }
    private void setupShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Logging.log("Application is shutting down...", Severity.Debug);
            DisconnectPacket disconnectPacket = new DisconnectPacket("Client Shutdown");
            sendToServer(disconnectPacket);
        }));
    }
    public void run(ServerHandler serverHandler){
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        service = scheduler;
        scheduler.scheduleAtFixedRate(serverHandler, 0, 100, TimeUnit.MILLISECONDS);
    }

    public boolean connect(String token){
        Socket socket = null;
        try {
            socket = new Socket(ip, port);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String connectString = SerializeData.toString(new AuthPacket(token, client));
            out.println(connectString);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void sendToServer(Object value){
        try {
            String serialized = SerializeData.toString((Serializable) value);
            out.println(serialized);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public BufferedReader getServerMessages() {
        return in;
    }

    public ScheduledExecutorService getService() {
        return service;
    }
}
