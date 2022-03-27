package app.model;

import java.util.ArrayList;

public class Request {

    public final static int VIEW_A_GAME = 1;
    public final static int VIEW_A_PLAYER_S_GAMES = 2;
    public final static int VIEW_THE_5_MOST_PLAYED_OPENING = 3;

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
