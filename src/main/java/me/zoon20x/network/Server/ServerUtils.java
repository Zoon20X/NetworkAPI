package me.zoon20x.network.Server;

import me.zoon20x.network.Client.Client;
import me.zoon20x.network.Server.events.ClientConnectEvent;
import me.zoon20x.network.Server.events.ClientListener;
import me.zoon20x.network.Server.events.ClientMessageEvent;
import me.zoon20x.network.Server.events.ClientSignInEvent;

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

    public ServerUtils(Server server){
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.server = server;
    }




    public void startClientCheck(){
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket clientSocket = server.getServerSocket().accept();
                    System.out.println("New connection from " + clientSocket.getInetAddress());
                    dispatchConnect(clientSocket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    public void run(ClientHandler clientHandler){
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        clientHandler.setService(scheduler);
        scheduler.scheduleAtFixedRate(clientHandler, 0, 1, TimeUnit.SECONDS);
    }

    public Server getServer() {
        return server;
    }
}
