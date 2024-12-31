package me.zoon20x.network.Server.events;

import me.zoon20x.network.Client.Client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientEventManager {
    private final List<ClientListener> listeners = new ArrayList<>();
    public void addListener(ClientListener listener) {
        listeners.add(listener);
    }
    public void removeListener(ClientListener listener) {
        listeners.remove(listener);
    }

    public void dispatchConnect(Socket clientAddress) {
        ClientConnectEvent event = new ClientConnectEvent(clientAddress);
        for (ClientListener listener : listeners) {
            listener.onClientConnect(event);
        }
    }
    public void dispatchSignIn(Client client) {
        ClientSignInEvent event = new ClientSignInEvent(client);
        for (ClientListener listener : listeners) {
            listener.onClientSignIn(event);
        }
    }
    public void dispatchMessage(Client client, Object value, PrintWriter out) {
        ClientMessageEvent event = new ClientMessageEvent(client, value, out);
        for (ClientListener listener : listeners) {
            listener.onClientMessageEvent(event);
        }
    }

}
