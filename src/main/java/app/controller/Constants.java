package app.controller;

public class Constants {
    public final static int VIEW_A_GAME = 1;
    public final static int VIEW_A_PLAYER_S_GAMES = 2;

    public static final String A_PLAYER_GAME = "a_player_game_hastable";
    public static final String SHORTEST_GAMES = "shortest_games_hashtable";
    public static final String MOST_PLAYED_OPENING_GAMES = "most_played_opening_games_hashtable";
    public static final String MOST_ACTIVE_PLAYERS = "most_active_players_hashtable";

    public static final int NUMBER_OF_GAMES_PER_FILE = 20;
    public static final String BINARY_EXTENSION = "dat";
    public static final String ROOT_DATA = "/home/ubuntu/IdeaProjects/database";
    public static final String GAMES_DATA_DIRECTORY = ROOT_DATA + "/data_dest/games_data";
    public static final String GAMES_HASTABLES = ROOT_DATA + "/data_dest/hashtables";
}
