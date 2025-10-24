package me.zoon20x.network.Server.auth;

import me.zoon20x.network.File.Config;
import me.zoon20x.network.logging.Logger;
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


    public Authentication(Config config){
        this.config = config;
        initializeToken();
    }

    public void initializeToken() {
        if(config.getConfigFile().exists()){
            config.load();
            serverToken = config.getConfigurationSection("Server").getString("Token");
            Logger.debug("Server Token: " + serverToken);
        }else{
            generateServerToken();
            config.getConfigurationSection("Server").set("Token", serverToken);
            config.save();
        }
    }
    public void generateServerToken() {
        serverToken = UUID.randomUUID().toString();
        Logger.debug("Server Token: " + serverToken);
    }
    public boolean validateClientToken(String clientToken) {
        return serverToken.equals(clientToken);
    }

    public String getServerToken() {
        return serverToken;
    }
}