package app.controller;

import app.model.Game;

import java.io.*;
import java.util.*;

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
                for(File file : yearDirectory.listFiles()) {
                    if(file.getName().equals(hashtableName + "." + Constants.BINARY_EXTENSION)) {
                        hashtablesPath.add(file);
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
     * @return a list games from gamesFiles that the player played
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
        ArrayList<File> hashtablePaths = findHashtablesByName(gamesDataDirectory, Constants.A_PLAYER_GAME_OVER_A_YEAR); // list of the path of all hashtables
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



    private static ArrayList<Game> gamesWithNStrokes(File hashtablePath, int nStrokes) throws IOException, ClassNotFoundException {
        ObjectInputStream o = new ObjectInputStream(new FileInputStream(hashtablePath));

        Hashtable<Integer, ArrayList<String>> hashtable = (Hashtable<Integer, ArrayList<String>>) o.readObject();
        o.close();

        ArrayList<Game> games = new ArrayList<>();
        ArrayList<String> gameFiles = hashtable.get(nStrokes);
        if(gameFiles != null) {
            for (String gameFile : gameFiles) {
                ObjectInputStream a = new ObjectInputStream(new FileInputStream(gameFile));
                Game game;
                try {
                    do {
                        game = (Game) a.readObject();
                        if (game.getStrokesNumber() == nStrokes) {
                            games.add(game);
                        }
                    } while (game != null);
                } catch(EOFException eofe) {}

                a.close();
            }
        }
        return games;
    }

    private static ArrayList<Game> shortestGames(ArrayList<File> hashtablesPaths) throws IOException {
        ArrayList<Game> shortest = new ArrayList<>();
        for(int i=0; (i < 50) && (shortest.size() < 5); i++) {
            for(File hashtablePath : hashtablesPaths) {
                try {
                    ArrayList<Game> g = gamesWithNStrokes(hashtablePath, i);
                    for (int j = 0; (j < 5 - shortest.size()) && (j < g.size()); j++) {
                        shortest.add(g.get(j));
                    }
                } catch(ClassNotFoundException cnfe) {System.out.println("Class not found");}
            }
        }
        return shortest;
    }

    public static ArrayList<Game> findShortestGames() throws IOException {
        File gamesDataDirectory = new File(Constants.GAMES_DATA_DIRECTORY);
        ArrayList<File> hashtablePaths = findHashtablesByName(gamesDataDirectory, Constants.SHORTEST_GAMES_OVER_A_YEAR); // list of the path of all hashtables

        return shortestGames(hashtablePaths);
    }



    public static ArrayList<String> findThe5MostPlayedOpening() {
        File gamesDataDirectory = new File(Constants.GAMES_DATA_DIRECTORY);
        ArrayList<String> openings = new ArrayList<>();
        try {
            ObjectInputStream o = new ObjectInputStream(new FileInputStream(gamesDataDirectory + File.separator + Constants.MOST_PLAYED_OPENING_GAMES_ALL + "." + Constants.BINARY_EXTENSION));
            Hashtable<String, Integer> hashtable = (Hashtable<String, Integer>) o.readObject();
            Set<String> keys = hashtable.keySet();
            for (String key : keys) {
                String s = key + ": " + hashtable.get(key);
                openings.add(s);
            }
        } catch(FileNotFoundException fnfe) {
            System.out.println("Hashtable not found");
        } catch(IOException ioe) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return openings;
    }



}
