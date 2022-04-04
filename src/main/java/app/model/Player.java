package app.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.TreeSet;

public class Player implements Serializable, Comparable<Player> {
    private String username;
    private Double pageRank;
    private Integer nbDefeats;
    private TreeSet<String> losersAgainst;

    public Player(String username) {
        this.username = username;
        this.pageRank = 1.0;
        this.nbDefeats = 0;
        this.losersAgainst = new TreeSet<>();
    }


    public String getUsername() {
        return username;
    }

    public Double getPageRank() {
        return pageRank;
    }

    public void setPageRank(Double pageRank) {
        this.pageRank = pageRank;
    }

    public void increaseDefeats() {
        nbDefeats++;
    }

    public void increaseDefeats(Integer nbDefeats) {
        this.nbDefeats += nbDefeats;
    }

    public Integer getNbDefeats() {
        return nbDefeats;
    }

    public void addLoser(String loserUsername) {
        losersAgainst.add(loserUsername);
    }

    public void addLoser(TreeSet<String> losersUsername) {
        losersAgainst.addAll(losersUsername);
    }

    public TreeSet<String> getLosersAgainst() {
        return losersAgainst;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return username.equals(player.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public int compareTo(Player other) {
        if(other.getPageRank() > this.getPageRank())
            return 1;
        else if(other.getPageRank() < this.getPageRank())
            return -1;
        return 0;
    }

    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                ", pageRank=" + pageRank +
                ", nbDefeats=" + nbDefeats +
                ", losersAgainst=" + losersAgainst +
                '}';
    }
}
