package me.zoon20x.network.Server.auth;

import me.zoon20x.network.File.Config;
import me.zoon20x.network.logging.Logging;
import me.zoon20x.network.logging.Severity;
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
    private Config config;
    private String serverIP = "127.0.0.1";
    private int serverPort = 12345;

    public Authentication(Config config){
        this.config = config;
        initializeToken();
    }

    public void initializeToken() {
        if(config.getConfigFile().exists()){
            config.load();
            serverToken = config.getConfigurationSection("Server").getString("Token");
            Logging.log(serverToken, Severity.Debug);
        }else{
            generateServerToken();
            config.getConfigurationSection("Server").set("Token", serverToken);
            config.save();
        }

    }
    public void generateServerToken() {
        serverToken = UUID.randomUUID().toString();
        Logging.log("Server Token: " + serverToken, Severity.Debug);
    }
    public boolean validateClientToken(String clientToken) {
        return serverToken.equals(clientToken);
    }

    public String getServerToken() {
        return serverToken;
    }
}