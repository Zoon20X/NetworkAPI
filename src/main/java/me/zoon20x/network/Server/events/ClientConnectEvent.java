package me.zoon20x.network.Server.events;

import me.zoon20x.network.Client.Client;

import java.net.Socket;

public class ClientConnectEvent {

    private final Socket socket;

    public ClientConnectEvent(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }
}
