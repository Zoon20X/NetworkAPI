package me.zoon20x.network.Client.events;

import me.zoon20x.network.Client.Client;
import me.zoon20x.network.Server.Server;

public class ServerMessageEvent {


    private final Object value;

    public ServerMessageEvent(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
