package me.zoon20x.network.Client;

import me.zoon20x.network.Client.events.ServerEventManager;
import me.zoon20x.network.SerializeData;
import me.zoon20x.network.Server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;

public class ServerHandler implements Runnable{

    private Server server;
    private ServerEventManager serverEventManager;
    private BufferedReader in;


    private boolean hasPacket;
    private String packet;


    public ServerHandler(ServerEventManager serverEventManager, BufferedReader in){
        this.serverEventManager = serverEventManager;
        this.in = in;
    }

    @Override
    public void run() {
        try {
            if(in.ready()){
                hasPacket = true;
                packet = in.readLine();
                serverEventManager.dispatchMessage(SerializeData.setData(packet));
            }else{
                hasPacket = false;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean hasPacket() {
        return hasPacket;
    }

    public String getPacket() {
        return packet;
    }
}
