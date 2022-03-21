package server;

import app.controller.Constants;
import app.controller.Controller;
import app.model.Game;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ClientRequestManager extends Thread {
    private Socket connectionSocket;
    private BufferedReader inStream;
    private ObjectOutputStream outStream;

    public ClientRequestManager(Socket connectionSocket) throws IOException {
        this.connectionSocket = connectionSocket;
        inStream = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        outStream = new ObjectOutputStream(connectionSocket.getOutputStream());
    }

    public void run() {
        try {
            String clientRequest = inStream.readLine();
            StringTokenizer st = new StringTokenizer(clientRequest);

            int choice = Integer.parseInt(st.nextToken());
            ArrayList<String> criteria = new ArrayList<>();

            while(st.hasMoreTokens()) {
                criteria.add(st.nextToken());
            }

            if(choice == Constants.VIEW_A_PLAYER_S_GAMES) {
                ArrayList<Game> games = Controller.findAPlayerSGames(criteria.get(0));
                outStream.writeObject(games);
            }

            inStream.close();
            outStream.close();

        } catch (IOException ioe) {

        }
    }
}
