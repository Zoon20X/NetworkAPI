package me.zoon20x.network.Server;

import me.zoon20x.network.Client.Client;
import me.zoon20x.network.Packets.DisconnectPacket;
import me.zoon20x.network.SerializeData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ScheduledExecutorService;

public class ClientHandler implements Runnable{
    private Server server;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private int count;

    private ScheduledExecutorService service;


    public ClientHandler(Server server, Socket socket){
        this.server = server;
        this.socket = socket;
        this.count = 0;
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {

        //System.out.println(count);
        count++;
        if(count>=100){
            closeConnection();
        }
        try {
            if (in.ready()) {
                String value = in.readLine();
                if(server.hasClient(socket)){
                    System.out.println("Client Connected");
                    server.getServerUtils().getClientEventManager().dispatchMessage(server.getClient(socket), SerializeData.setData(value), out);
                }else{
                    System.out.println("client does not exist");
                    Object data = SerializeData.setData(value);
                    if(data instanceof Client) {
                        Client client = (Client) data;
                        server.addClient(socket, client);
                        server.getServerUtils().getClientEventManager().dispatchSignIn(client);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeConnection() {
        try {
            server.removeClient(socket);
            out.println(SerializeData.toString(new DisconnectPacket()));
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();

            System.out.println("Connection closed");
            service.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setService(ScheduledExecutorService service){
        this.service = service;
    }
}
