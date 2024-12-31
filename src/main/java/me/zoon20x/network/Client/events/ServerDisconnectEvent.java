package me.zoon20x.network.Client.events;

import me.zoon20x.network.Packets.DisconnectPacket;

public class ServerDisconnectEvent {


    private final DisconnectPacket packet;

    public ServerDisconnectEvent(DisconnectPacket packet) {
        this.packet = packet;
    }

    public DisconnectPacket getPacket() {
        return packet;
    }
}
