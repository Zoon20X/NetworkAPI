package me.zoon20x.network.Client.events;

import me.zoon20x.network.Server.Server;
import me.zoon20x.network.Server.events.ClientConnectEvent;
import me.zoon20x.network.Server.events.ClientMessageEvent;
import me.zoon20x.network.Server.events.ClientSignInEvent;

public interface ServerListener {

    void onServerMessageEvent(ServerMessageEvent event);
    void onServerDisconnectEvent(ServerDisconnectEvent event);
}
