package app.model;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.io.Serializable;


public class Game implements Serializable {
    final int WHITE_WINNER = 1;
    final int BLACK_WINNER = 2;
    final int EQUAL = 0;

    private String event;
    private String site;
    private Player white;
    private Player black;
    private Integer result;
    private Date UTCDateTime;
    private Integer whiteElo;
    private Integer blackElo;
    private Integer whiteRatingDiff;
    private Integer blackRatingDiff;
    private String eco;
    private String opening;
    private String timeControl;
    private String termination;
    private Strokes strokes;


    public Game(String event, String site, String white, String black, String result, String date, String time,
                String whiteElo, String blackElo, String whiteRatingDiff, String blackRatingDiff, String eco,
                String opening, String timeControl, String termination, String strokes) throws  ParseException {
        this.event = event;
        this.site = site;
        this.white = new Player(white.trim());
        this.black = new Player(black.trim());

        // Assign the result
        if(result.equalsIgnoreCase("1-0"))
            this.result = WHITE_WINNER;
        else if(result.equalsIgnoreCase("0-1"))
            this.result = BLACK_WINNER;
        else
            this.result = EQUAL;

        this.UTCDateTime = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").parse(date + " " + time);

        try{
            this.whiteElo = Integer.parseInt(whiteElo);
        } catch(NumberFormatException nfe) { this.whiteElo = null; }
        catch(NullPointerException npe) { this.whiteElo = null; }
        try{
            this.blackElo = Integer.parseInt(blackElo);
        } catch(NumberFormatException nfe) { this.blackElo = null; }
        catch(NullPointerException npe) { this.blackElo = null; }

        try{
            this.whiteRatingDiff = Integer.parseInt(whiteRatingDiff);
        } catch(NumberFormatException nfe) { this.whiteRatingDiff = null; }
        catch(NullPointerException npe) { this.whiteRatingDiff = null; }
        try{
            this.blackRatingDiff = Integer.parseInt(blackRatingDiff);
        } catch(NumberFormatException nfe) { this.blackRatingDiff = null; }
        catch(NullPointerException npe) { this.blackRatingDiff = null; }

        this.eco = eco;
        this.opening = opening;
        this.timeControl = timeControl;

        this.termination = termination;
        this.strokes = new Strokes(strokes);
    }


    // constructor without whiteRatingDiff and blackRatingDiff
    // some games can miss them
    public Game(String event, String site, String white, String black, String result, String date, String time,
                String whiteElo, String blackElo, String eco,
                String opening, String timeControl, String termination, String strokes) throws  ParseException {
        this.event = event;
        this.site = site;
        this.white = new Player(white);
        this.black = new Player(black);

        // Assign the result
        if(result.equalsIgnoreCase("1-0"))
            this.result = WHITE_WINNER;
        else if(result.equalsIgnoreCase("0-1"))
            this.result = BLACK_WINNER;
        else
            this.result = EQUAL;

        this.UTCDateTime = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").parse(date + " " + time);

        try{this.whiteElo = Integer.parseInt(whiteElo);} catch(NumberFormatException nfe) { this.whiteElo = null; }
        try{this.blackElo = Integer.parseInt(blackElo);} catch(NumberFormatException nfe) { this.blackElo = null; }

        this.whiteRatingDiff = null;
        this.blackRatingDiff = null;

        this.eco = eco;
        this.opening = opening;
        this.timeControl = timeControl;

        this.termination = termination;
        this.strokes = new Strokes(strokes);
    }


    public Player getWhitePlayer() {
        return white;
    }

    public Player getBlackPlayer() {
        return black;
    }

    public int getStrokesNumber() {
        return strokes.getStrokesNumber();
    }

    public String getOpening() {
        return opening;
    }

    public Strokes getStrokes() {
        return strokes;
    }

    public Player getWinner() {
        if(result == WHITE_WINNER)
            return white;
        else if(result == BLACK_WINNER)
            return black;
        return null;
    }

    public Player getLoser() {
        if(result == WHITE_WINNER)
            return black;
        else if(result == BLACK_WINNER)
            return white;
        return null;
    }

    public String getSite() {
        return site;
    }


    public String toString() {
        String gameString = "";

        gameString += "Event: " + this.event + "\n";
        gameString += "Site: " + this.site + "\n";
        gameString += "White: " + this.white.getUsername() + "\n";
        gameString += "Black: " + this.black.getUsername() + "\n";
        gameString += "Result: " + this.result + "\n";
        gameString += "Date time: " + this.UTCDateTime + "\n";
        gameString += "WhiteElo: " + this.whiteElo + "\n";
        gameString += "BlackElo: " + this.blackElo + "\n";
        gameString += "WhiteRatingDiff: " + this.whiteRatingDiff + "\n";
        gameString += "BlackRatingDiff: " + this.blackRatingDiff + "\n";
        gameString += "ECO: " + this.eco + "\n";
        gameString += "Opening: " + this.opening + "\n";
        gameString += "TimeControl: " + this.timeControl + "\n";
        gameString += "Termination: " + this.termination + "\n";
        gameString += "Strokes: \n" + this.strokes + "\n";

        return gameString;
    }

}
