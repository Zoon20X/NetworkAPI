package me.zoon20x.network.Packets;

import java.io.Serializable;

public class TransferPacket implements Serializable {

    private final String id;


    private final Object data;


    public TransferPacket(String id, Object data) {
        this.id = id;
        this.data = data;
    }

    public String getID() {
        return id;
    }

    public Object getData() {
        return data;
    }
}
