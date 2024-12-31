package me.zoon20x.network.Server.events;

import me.zoon20x.network.Client.Client;
import me.zoon20x.network.SerializeData;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

public class ClientMessageEvent {

    private final Client client;

    private final Object value;

    private PrintWriter out;

    public ClientMessageEvent(Client client, Object value, PrintWriter out) {
        this.client = client;
        this.value = value;
        this.out = out;
    }

    public Client getClient() {
        return client;
    }

    public Object getValue() {
        return value;
    }

    public void sendResponse(Object object){
        try {
            out.println(SerializeData.toString((Serializable) object));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
