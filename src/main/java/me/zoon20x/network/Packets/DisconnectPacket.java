package me.zoon20x.network.Packets;

import java.io.Serializable;

public class DisconnectPacket implements Serializable {

    private String reason;

    public DisconnectPacket(String reason){
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
