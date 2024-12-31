package me.zoon20x.network.Packets;

import java.io.Serializable;

public class ServerData implements Serializable {

    private final String name;

    private final String ip;
    private final int port;


    public ServerData(String name, String ip, int port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
