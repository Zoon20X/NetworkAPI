package me.zoon20x.network.Server.auth;

import java.io.BufferedReader;
import java.util.UUID;

public class Authentication {
    private static String serverToken;

    public Authentication(){
        generateServerToken();
    }

    public void generateServerToken() {
        serverToken = UUID.randomUUID().toString();
        System.out.println("Server Token: " + serverToken);
    }
    public boolean validateClientToken(String clientToken) {
        return serverToken.equals(clientToken);
    }

    public String getServerToken() {
        return serverToken;
    }
}