package app.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.TreeSet;

public class Player implements Serializable, Comparable<Player> {
    private String username;
    private Double pageRank;
    private Double authority;
    private Double hub;
    private Integer nbDefeats;
    private TreeSet<String> losersAgainst;
    private TreeSet<String> winnersAgainst;

    public Player(String username) {
        this.username = username;
        this.pageRank = 1.0;
        this.authority = 1.0;
        this.hub = 1.0;
        this.nbDefeats = 0;
        this.losersAgainst = new TreeSet<>();
        this.winnersAgainst = new TreeSet<>();
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

    public Double getAuthority() {
        return authority;
    }

    public void setAuthority(Double authority) {
        this.authority = authority;
    }

    public void setHub(Double hub) {
        this.hub = hub;
    }

    public Double getHub() {
        return hub;
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

    public void addWinner(String winnerUsername) {
        winnersAgainst.add(winnerUsername);
    }

    public void addWinner(TreeSet<String> winnnersUsername) {
        winnersAgainst.addAll(winnnersUsername);
    }

    public TreeSet<String> getLosersAgainst() {
        return losersAgainst;
    }

    public TreeSet<String> getWinnersAgainst() {
        return winnersAgainst;
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
                ", authority=" + authority +
                ", hub=" + hub +
                ", nbDefeats=" + nbDefeats +
                ", losersAgainst=" + losersAgainst +
                ", winnersAgainst=" + winnersAgainst +
                '}';
    }
}
