package me.zoon20x.network.Server.events;

public interface ClientListener {
    void onClientConnect(ClientConnectEvent event);
    void onClientSignIn(ClientSignInEvent event);
    void onClientMessageEvent(ClientMessageEvent event);
}
