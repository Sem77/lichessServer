package server;

import app.controller.Controller;
import app.model.Game;
import app.model.Request;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientRequestManager extends Thread {
    private Socket connectionSocket;
    private ObjectInputStream inStream;
    private ObjectOutputStream outStream;

    public ClientRequestManager(Socket connectionSocket) throws IOException {
        this.connectionSocket = connectionSocket;
        inStream = new ObjectInputStream(connectionSocket.getInputStream());
        outStream = new ObjectOutputStream(connectionSocket.getOutputStream());
    }

    public void run() {
        try {
            Request clientRequest = (Request) inStream.readObject();

            int choice = clientRequest.getRequestNumber();
            ArrayList<String> criteria = clientRequest.getCriterias();

            if(choice == Request.VIEW_A_PLAYER_S_GAMES) {
                ArrayList<Game> games = Controller.findAPlayerSGames(criteria.get(0));
                outStream.writeObject(games);
            }
            else if(choice == Request.VIEW_THE_5_MOST_PLAYED_OPENING) {
                ArrayList<String> openings = Controller.findThe5MostPlayedOpening();
                outStream.writeObject(openings);
            }
            else if(choice == Request.VIEW_THE_SHORTEST_GAMES) {
                ArrayList<Game> games = Controller.findShortestGames();
                outStream.writeObject(games);
            }
            else if(choice == Request.VIEW_THE_MOST_ACTIVE_PLAYERS) {

            }
            else if(choice == Request.VIEW_THE_BEST_PLAYERS) {

            }
            inStream.close();
            outStream.close();

        } catch (IOException ioe) {

        } catch (ClassNotFoundException cnfe) {
            System.out.println("Unknown request format");
        }
    }
}
