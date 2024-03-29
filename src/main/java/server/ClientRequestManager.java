package server;

import app.controller.Controller;
import app.model.Game;
import app.model.Player;
import app.model.Request;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientRequestManager extends Thread {
    private Socket connectionSocket;
    private ObjectInputStream inStream;
    private ObjectOutputStream outStream;
    private Server server;

    public ClientRequestManager(Socket connectionSocket, Server server) throws IOException {
        this.connectionSocket = connectionSocket;
        inStream = new ObjectInputStream(connectionSocket.getInputStream());
        outStream = new ObjectOutputStream(connectionSocket.getOutputStream());
        this.server = server;
    }

    public void run() {
        server.connect();
        try {
            Request clientRequest = (Request) inStream.readObject();

            int choice = clientRequest.getRequestNumber();
            ArrayList<String> criteria = clientRequest.getCriterias();

            if(choice == Request.VIEW_A_GAME) {
                Game games = Controller.findAGameWithURL(criteria.get(0));
                outStream.writeObject(games);
            }
            else if(choice == Request.VIEW_A_PLAYER_S_GAMES) {
                ArrayList<String> games = Controller.findAPlayerSGames(criteria.get(0));
                outStream.writeObject(games);
            }
            else if(choice == Request.VIEW_THE_5_MOST_PLAYED_OPENING) {
                ArrayList<String> openings = Controller.findThe5MostPlayedOpening();
                outStream.writeObject(openings);
            }
            else if(choice == Request.VIEW_THE_SHORTEST_GAMES) {
                ArrayList<String> gamesURLS = Controller.findShortestGames(Integer.parseInt(criteria.get(0)));
                outStream.writeObject(gamesURLS);
            }
            else if(choice == Request.VIEW_THE_MOST_ACTIVE_PLAYERS) {
                ArrayList<String> players = Controller.findTheNMostActivePlayers(Integer.parseInt(criteria.get(0)));
                outStream.writeObject(players);
            }
            else if(choice == Request.VIEW_THE_BEST_PLAYERS_PR) {
                ArrayList<Player> players = Controller.findTheNBestPlayersPR(Integer.parseInt(criteria.get(0)));
                outStream.writeObject(players);
            }
            else if(choice == Request.VIEW_THE_BEST_PLAYERS_HITS) {
                ArrayList<Player> players = Controller.findTheNBestPlayersHits(Integer.parseInt(criteria.get(0)));
                outStream.writeObject(players);
            }
            inStream.close();
            outStream.close();

        } catch (IOException ioe) {

        } catch (ClassNotFoundException cnfe) {
            System.out.println("Format de requête non reconnu");
        }
        server.disconnect();
    }
}
