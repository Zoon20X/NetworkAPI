package me.zoon20x.network.Packets;

import me.zoon20x.network.Client.Client;

import java.io.Serializable;

public class AuthPacket implements Serializable {
    private final String token;
    private Client client;

    public AuthPacket(String token, Client client) {
        this.token = token;
        this.client = client;
    }


    public String getToken() {
        return token;
    }

    public Client getClient() {
        return client;
    }
}
