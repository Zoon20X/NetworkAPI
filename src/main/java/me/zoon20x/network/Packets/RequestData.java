package me.zoon20x.network.Packets;

import java.io.Serializable;

public class RequestData implements Serializable {

    private final String id;

    public RequestData(String id) {
        this.id = id;
    }
    public String getID() {
        return id;
    }
}
