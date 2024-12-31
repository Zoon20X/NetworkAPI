package me.zoon20x.network.Response;

import java.io.Serializable;

public class Response implements Serializable {

    private NetworkResponse networkResponse;

    public Response(NetworkResponse networkResponse) {
        this.networkResponse = networkResponse;
    }

    public NetworkResponse getNetworkResponse() {
        return networkResponse;
    }

}
