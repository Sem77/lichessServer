package app.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Request implements Serializable {

    public final static int VIEW_A_GAME = 1;
    public final static int VIEW_A_PLAYER_S_GAMES = 2;
    public final static int VIEW_THE_5_MOST_PLAYED_OPENING = 3;
    public final static int VIEW_THE_SHORTEST_GAMES = 4;
    public final static int VIEW_THE_MOST_ACTIVE_PLAYERS = 5;
    public final static int VIEW_THE_BEST_PLAYERS = 6;

    private int requestNumber;
    private ArrayList<String> criterias;

    public Request(int requestNumber, ArrayList<String> criterias) {
        this.requestNumber = requestNumber;
        this.criterias = criterias;
    }

    public Request(int requestNumber) {
        this.requestNumber = requestNumber;
    }

    public int getRequestNumber() {
        return requestNumber;
    }

    public ArrayList<String> getCriterias() {
        return criterias;
    }
}
