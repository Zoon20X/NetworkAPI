package me.zoon20x.network.Server.auth;

import me.zoon20x.network.File.Config;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Authentication {
    private String serverToken;
    private String serverIP = "127.0.0.1";
    private int serverPort = 12345;

    public Authentication(){
        initializeToken();
    }

    public void initializeToken() {
        generateServerToken();

    }
    public void generateServerToken() {
        serverToken = UUID.randomUUID().toString();
        System.out.println("Server Token: " + serverToken);
        Config config = new Config("config.yml");
    }
    public boolean validateClientToken(String clientToken) {
        return serverToken.equals(clientToken);
    }

    public String getServerToken() {
        return serverToken;
    }
}