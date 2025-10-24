package me.zoon20x.network.Server;

import me.zoon20x.network.Client.Client;
import me.zoon20x.network.Server.events.*;
import me.zoon20x.network.logging.Logger;
import me.zoon20x.network.logging.Severity;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerUtils {


    private ScheduledExecutorService scheduler;

    private Server server;

    private ClientEventManager clientEventManager;

    public ServerUtils(Server server){
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.server = server;

        this.clientEventManager = new ClientEventManager();
    }

    public void startClientCheck(){
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket clientSocket = server.getServerSocket().accept();
                    Logger.debug("New connection from " + clientSocket.getInetAddress());
                    clientEventManager.dispatchConnect(clientSocket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    public void run(ClientHandler clientHandler){
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        clientHandler.setService(scheduler);
        scheduler.scheduleAtFixedRate(clientHandler, 0, 100, TimeUnit.MILLISECONDS);
    }

    public Server getServer() {
        return server;
    }

    public ClientEventManager getClientEventManager() {
        return clientEventManager;
    }
}
