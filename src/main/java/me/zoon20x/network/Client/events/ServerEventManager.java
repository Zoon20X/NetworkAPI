package me.zoon20x.network.Client.events;

import me.zoon20x.network.Server.Server;
import me.zoon20x.network.Server.events.ClientListener;
import me.zoon20x.network.Server.events.ClientMessageEvent;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ServerEventManager {
    private final List<ServerListener> listeners = new ArrayList<>();

    public void addListener(ServerListener listener) {
        listeners.add(listener);
    }
    public void removeListener(ServerListener listener) {
        listeners.remove(listener);
    }
    public void dispatchMessage(Object value) {
        ServerMessageEvent event = new ServerMessageEvent(value);
        for (ServerListener listener : listeners) {
            listener.onServerMessageEvent(event);
        }
    }
}
