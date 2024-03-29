package app.controller;

import app.model.Game;
import app.model.OccurrenceString;
import app.model.Player;

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
    /*private static ArrayList<File> findHashtablesByName(File baseDirectory, String hashtableName) {
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
    }*/

    /**
     *
     * @param hashtablePath
     * @param username
     * @return a list of the files where we can find the games of the player
     * @throws IOException
     * @throws ClassNotFoundException
     */
    /*private static TreeSet<String> gameFilesOfAPlayer(File hashtablePath, String username) throws IOException, ClassNotFoundException {
        ObjectInputStream o = new ObjectInputStream(new FileInputStream(hashtablePath));
        Hashtable<String, TreeSet<String>> hashtable = (Hashtable<String, TreeSet<String>>) o.readObject(); // loading the hashtable
        TreeSet<String> gameFiles = hashtable.get(username);

        o.close();

        if(gameFiles.size() > 0)
            return gameFiles;
        else
            return null;
    }*/

    /**
     *
     * @param gameFiles
     * @param username
     * @return a list games from gamesFiles that the player played
     */
    private static ArrayList<Game> gamesOfAPlayer(TreeSet<String> gameFiles, String username) {
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


    /*public static ArrayList<Game> findAPlayerSGames(String username) {
        File gamesDataDirectory = new File(Constants.GAMES_DATA_DIRECTORY);
        ArrayList<File> hashtablePaths = findHashtablesByName(gamesDataDirectory, Constants.A_PLAYER_GAME_OVER_A_YEAR); // list of the path of all hashtables
        TreeSet<String> gameFilesPaths = new TreeSet<>(); //

        for(File hashtablePath : hashtablePaths) {
            if(hashtablePath != null) {
                try {
                    TreeSet<String> s = gameFilesOfAPlayer(hashtablePath, username);
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
    }*/


    /**
     * Interroge la bonne table de hashage pour trouver toutes les parties d'un certain joueur
     * @param username nom d'utilisateur du joueur concerné
     * @return une arraylist des parties jouées
     */
    public static ArrayList<String> findAPlayerSGames(String username) {
        File gamesDataDirectory = new File(Constants.GAMES_DATA_DIRECTORY);
        try {
            ObjectInputStream o = new ObjectInputStream(new FileInputStream(gamesDataDirectory + File.separator + Constants.A_PLAYER_GAME_ALL + "." + Constants.BINARY_EXTENSION));
            Hashtable<String, TreeSet<String>> hashtable = (Hashtable<String, TreeSet<String>>) o.readObject();
            o.close();

            TreeSet<String> gamesURLS = hashtable.get(username);
            return new ArrayList<>(gamesURLS);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     *
     * @return
     * @throws IOException
     * @param nbShortestGames
     */
    public static ArrayList<String> findShortestGames(Integer nbShortestGames) throws IOException {
        File gamesDataDirectory = new File(Constants.GAMES_DATA_DIRECTORY);
        File shortestGamesPath = new File(gamesDataDirectory + File.separator + Constants.ORDER_SHORTEST_GAMES + "." + Constants.BINARY_EXTENSION); // list of the path of all hashtables
        ArrayList<String> gamesURLS = new ArrayList<>();

        ObjectInputStream o = new ObjectInputStream(new FileInputStream(shortestGamesPath));

        String gameURL;
        int nbGamesFound = 0;
        try {
            do {
                gameURL = (String) o.readObject();
                gamesURLS.add(gameURL);
                nbGamesFound++;
            } while (gameURL != null && nbGamesFound < nbShortestGames);
        } catch(EOFException eofe) {}
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        o.close();

        return gamesURLS;
    }


    public static ArrayList<String> findThe5MostPlayedOpening() {
        File gamesDataDirectory = new File(Constants.GAMES_DATA_DIRECTORY);
        ArrayList<String> openings = new ArrayList<>();
        try {
            ObjectInputStream o = new ObjectInputStream(new FileInputStream(gamesDataDirectory + File.separator + Constants.ORDER_MOST_PLAYED_OPENING_GAMES_ALL + "." + Constants.BINARY_EXTENSION));
            OccurrenceString os;
            try {
                do {
                    os = (OccurrenceString) o.readObject();
                    openings.add(os.toString());
                } while (os != null && openings.size() < 5);
            } catch(EOFException eofe) {}
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            o.close();

        } catch(FileNotFoundException fnfe) {
            System.out.println("Hashtable not found");
        } catch(IOException ioe) {}
        return openings;
    }


    public static ArrayList<String> findTheNMostActivePlayers(Integer nbPlayers) {
        File gamesDataDirectory = new File(Constants.GAMES_DATA_DIRECTORY);
        ArrayList<String> players = new ArrayList<>();
        try {
            ObjectInputStream o = new ObjectInputStream(new FileInputStream(gamesDataDirectory + File.separator + Constants.ORDER_MOST_ACTIVE_PLAYERS_ALL + "." + Constants.BINARY_EXTENSION));
            OccurrenceString os;
            try {
                do {
                    os = (OccurrenceString) o.readObject();
                    players.add(os.toString());
                } while (os != null && players.size() < nbPlayers);
            } catch(EOFException eofe) {}
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            o.close();

        } catch(FileNotFoundException fnfe) {
            System.out.println("Hashtable not found");
        } catch(IOException ioe) {}
        return players;
    }


    public static ArrayList<Player> findTheNBestPlayersPR(Integer nbPlayers) {
        File gamesDataDirectory = new File(Constants.GAMES_DATA_DIRECTORY);
        ArrayList<Player> players = new ArrayList<>();
        try {
            ObjectInputStream o = new ObjectInputStream(new FileInputStream(gamesDataDirectory + File.separator + Constants.ORDER_BEST_PLAYERS_ALL_PAGE_RANK + "." + Constants.BINARY_EXTENSION));
            Player player;
            try {
                do {
                    player = (Player) o.readObject();
                    players.add(player);
                } while (player != null && players.size() < nbPlayers);
            } catch(EOFException eofe) {}
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            o.close();
        } catch(FileNotFoundException fnfe) {
            System.out.println("Hashtable not found");
        } catch(IOException ioe) {}
        return players;
    }


    public static ArrayList<Player> findTheNBestPlayersHits(Integer nbPlayers) {
        File gamesDataDirectory = new File(Constants.GAMES_DATA_DIRECTORY);
        ArrayList<Player> players = new ArrayList<>();
        try {
            ObjectInputStream o = new ObjectInputStream(new FileInputStream(gamesDataDirectory + File.separator + Constants.ORDER_BEST_PLAYERS_ALL_HITS + "." + Constants.BINARY_EXTENSION));
            Player player;
            try {
                do {
                    player = (Player) o.readObject();
                    players.add(player);
                } while (player != null && players.size() < nbPlayers);
            } catch(EOFException eofe) {}
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            o.close();
        } catch(FileNotFoundException fnfe) {
            System.out.println("Hashtable not found");
        } catch(IOException ioe) {}
        return players;
    }


    public static Game findAGameWithURL(String url) {
        File gameDataDirectory = new File(Constants.GAMES_DATA_DIRECTORY);
        try {
            ObjectInputStream o = new ObjectInputStream(new FileInputStream(gameDataDirectory + File.separator + Constants.GAME_LINK_ALL + "." + Constants.BINARY_EXTENSION));
            Hashtable<String, String> hashtable = (Hashtable<String, String>) o.readObject();
            o.close();
            String gameLocation = hashtable.get(url);

            ObjectInputStream gameFile = new ObjectInputStream(new FileInputStream(gameLocation));

            Game game;
            do {
                game = (Game) gameFile.readObject();
                if(game.getSite().equals(url))
                    return game;
            } while(game != null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
