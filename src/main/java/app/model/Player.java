package app.model;

import java.io.Serializable;

public class Player implements Serializable {
    private String username;

    public Player(String username) {
        this.username = username;
    }


    public String getUsername() {
        return username;
    }
}
