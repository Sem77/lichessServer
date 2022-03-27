package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;
    private ServerSocket serverSocket;
    private boolean run = true;
    public int nbThreadsWorking;

    public Server(int port) throws IOException {
        this.port = port;
        nbThreadsWorking = 0;
        serverSocket = new ServerSocket(port);
    }

    public void startServer() throws IOException {
        while(run) {
            Socket connexionSocket = serverSocket.accept();
            System.out.println(connexionSocket);
            ClientRequestManager clientRequestManager = new ClientRequestManager(connexionSocket);
            clientRequestManager.start();
        }
    }

    public void stopServer() {
        run = false;
    }


    public synchronized void takeToken() {

    }
}
