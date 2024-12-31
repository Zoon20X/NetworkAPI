package me.zoon20x.network.Server;

import me.zoon20x.network.Client.Client;
import me.zoon20x.network.Server.auth.Authentication;
import me.zoon20x.network.logging.Logging;
import me.zoon20x.network.logging.Severity;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {

    private final String ip;
    private final int port;

    private ServerSocket serverSocket;
    private ServerUtils serverUtils;

    private Authentication authentication;

    private final HashMap<Socket, Client> connectedClients = new HashMap<>();

    public Server(String ip, int port){
        this.ip = ip;
        this.port = port;
        this.authentication = new Authentication();

    }

    public void start(){
        try {
            this.serverSocket = new ServerSocket(this.port);
            Logging.log("Server running on port:" +this.port, Severity.Debug);
            this.serverUtils = new ServerUtils(this);
            serverUtils.startClientCheck();
        } catch (IOException e) {
            Logging.log("Failed to load server", Severity.Critical);
            throw new RuntimeException(e);
        }
    }

    public String getIP() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public ServerUtils getServerUtils() {
        return serverUtils;
    }

    public void addClient(Socket socket, Client client){
        connectedClients.put(socket, client);
    }
    public void removeClient(Socket socket){
        connectedClients.remove(socket);
    }
    public boolean hasClient(Socket socket){
        return connectedClients.containsKey(socket);
    }
    public Client getClient(Socket socket){
        return connectedClients.get(socket);
    }

    public HashMap<Socket, Client> getConnectedClients() {
        return connectedClients;
    }

    public Authentication getAuthentication() {
        return authentication;
    }
}
