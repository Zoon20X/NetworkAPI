package me.zoon20x.network.Client;

import java.io.Serializable;

public class Client implements Serializable {
    private final String clientID;


    public Client(String clientID) {
        this.clientID = clientID;
    }

    public String getClientID() {
        return clientID;
    }
}
