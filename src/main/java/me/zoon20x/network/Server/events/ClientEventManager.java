package me.zoon20x.network.Server.events;

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

}
