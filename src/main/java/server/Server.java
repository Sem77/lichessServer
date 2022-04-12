package server;

import app.controller.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;
    private ServerSocket serverSocket;
    private boolean run = true;
    public int nbClientConnected = 0;

    public Server(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
    }

    public void startServer() throws IOException {
        while(run) {
            if(nbClientConnected < Constants.NB_THREAD_MAX) {
                synchronized (this) {
                    this.notify();
                }
            }
            Socket connexionSocket = serverSocket.accept();
            System.out.println(connexionSocket);
            ClientRequestManager clientRequestManager = new ClientRequestManager(connexionSocket, this);
            clientRequestManager.start();
        }
    }

    public synchronized void connect() {
        if(nbClientConnected >= Constants.NB_THREAD_MAX) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        nbClientConnected++;
    }

    public synchronized void disconnect() {
        nbClientConnected--;
    }

    public void stopServer() {
        run = false;
    }
}
