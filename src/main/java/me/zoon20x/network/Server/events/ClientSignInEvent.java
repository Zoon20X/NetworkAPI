package me.zoon20x.network.Server.events;

import me.zoon20x.network.Client.Client;

import java.net.Socket;

public class ClientSignInEvent {

    private final Client client;

    public ClientSignInEvent(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }
}
