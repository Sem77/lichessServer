package app.controller;

import app.model.Game;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class Controller {

    /**
     *
     * @param baseDirectory
     * @param hashtableName
     * @return a list of File denoting the paths of all hashtables matching hashtableName in baseDirectory, recursively
     *         null if it didn't find any hashtable matching hashtableName
     */
    private static ArrayList<File> findHashtablesByName(File baseDirectory, String hashtableName) {
        ArrayList<File> hashtablesPath = new ArrayList<>();

        // browse by year
        for(File yearDirectory : baseDirectory.listFiles()) {
            if(yearDirectory.isDirectory()) {

                // browse by month
                for(File monthDirectory : yearDirectory.listFiles()) {
                    if(monthDirectory.isDirectory()) {

                        // browse to find hashtables directory
                        for(File fileInMonthDirectory : monthDirectory.listFiles()) {
                            if(fileInMonthDirectory.getName().equals("hashtables")) {

                                // browse to find the right hashtable
                                for(File fileInHashtableDir : fileInMonthDirectory.listFiles()) {
                                    if(fileInHashtableDir.getName().equals(hashtableName + "." + Constants.BINARY_EXTENSION)) {
                                        hashtablesPath.add(fileInHashtableDir);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if(hashtablesPath.size() > 0)
            return hashtablesPath;
        else
            return null;
    }

    /**
     *
     * @param hashtablePath
     * @param username
     * @return a list of the files where we can find the games of the player
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static ArrayList<String> gameFilesOfAPlayer(File hashtablePath, String username) throws IOException, ClassNotFoundException {
        ObjectInputStream o = new ObjectInputStream(new FileInputStream(hashtablePath));
        Hashtable<String, ArrayList<String>> hashtable = (Hashtable<String, ArrayList<String>>) o.readObject(); // loading the hashtable
        ArrayList<String> gameFiles = hashtable.get(username);

        o.close();

        if(gameFiles.size() > 0)
            return gameFiles;
        else
            return null;
    }

    /**
     *
     * @param gameFiles
     * @param username
     * @return a list a games from gamesFiles that the player played
     */
    private static ArrayList<Game> gamesOfAPlayer(ArrayList<String> gameFiles, String username) {
        ArrayList<Game> games = new ArrayList<>();

        for(String gameFilePath : gameFiles) {
            try {
                ObjectInputStream o = new ObjectInputStream(new FileInputStream(gameFilePath));
                Game game;
                do {
                    game = (Game) o.readObject();
                    if(game.getWhitePlayer().getUsername().equals(username) || game.getBlackPlayer().getUsername().equals(username))
                        games.add(game);
                } while(game != null);
                o.close();
            } catch (FileNotFoundException fnfe) {
                System.out.println("Un fichier de jeux n'a pas été trouvé");
            } catch (IOException ioe) {

            } catch (ClassNotFoundException cnfe) {
                cnfe.printStackTrace();
            }
        }

        if(games.size() > 0)
            return games;
        else
            return null;
    }

    public static ArrayList<Game> findAPlayerSGames(String username) {
        File gamesDataDirectory = new File(Constants.GAMES_DATA_DIRECTORY);
        ArrayList<File> hashtablePaths = findHashtablesByName(gamesDataDirectory, Constants.A_PLAYER_GAME); // list of the path of all hashtables
        ArrayList<String> gameFilesPaths = new ArrayList<>(); //

        for(File hashtablePath : hashtablePaths) {
            if(hashtablePath != null) {
                try {
                    ArrayList<String> s = gameFilesOfAPlayer(hashtablePath, username);
                    if(s != null) {
                        gameFilesPaths.addAll(s);
                    }
                } catch (ClassNotFoundException cnfe) {

                } catch (IOException ioe) {

                } catch (NullPointerException npe) {
                    return null;
                }
            }
        }

        ArrayList<Game> games = gamesOfAPlayer(gameFilesPaths, username);

        return games;
    }


    public static ArrayList<String> findThe5BestOpenings() {
        File gamesDataDirectory = new File(Constants.GAMES_DATA_DIRECTORY);
        ArrayList<File> hashtablePaths = findHashtablesByName(gamesDataDirectory, Constants.MOST_PLAYED_OPENING_GAMES); // list of the path of all hashtables

        return null;
    }
}
