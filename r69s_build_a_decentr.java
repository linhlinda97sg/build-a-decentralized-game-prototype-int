import java.util.*;

class Player {
    String id;
    String name;
    int score;

    public Player(String id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }
}

class Game {
    String id;
    String name;
    List<Player> players;
    Map<String, Integer> leaderboard;

    public Game(String id, String name) {
        this.id = id;
        this.name = name;
        this.players = new ArrayList<>();
        this.leaderboard = new HashMap<>();
    }

    public void addPlayer(Player player) {
        players.add(player);
        leaderboard.put(player.id, player.score);
    }

    public void updateScore(String playerId, int score) {
        leaderboard.put(playerId, score);
    }

    public Map<String, Integer> getLeaderboard() {
        return leaderboard;
    }
}

interface DecentralizedGameNetwork {
    void broadcastGameCreation(Game game);
    void broadcastPlayerJoin(Game game, Player player);
    void broadcastScoreUpdate(Game game, String playerId, int score);
}

class DecentralizedGamePrototypeIntegrator {
    DecentralizedGameNetwork network;
    Map<String, Game> games;

    public DecentralizedGamePrototypeIntegrator(DecentralizedGameNetwork network) {
        this.network = network;
        this.games = new HashMap<>();
    }

    public void createGame(String gameId, String gameName) {
        Game game = new Game(gameId, gameName);
        games.put(gameId, game);
        network.broadcastGameCreation(game);
    }

    public void joinGame(String gameId, Player player) {
        Game game = games.get(gameId);
        game.addPlayer(player);
        network.broadcastPlayerJoin(game, player);
    }

    public void updateScore(String gameId, String playerId, int score) {
        Game game = games.get(gameId);
        game.updateScore(playerId, score);
        network.broadcastScoreUpdate(game, playerId, score);
    }

    public void displayLeaderboard(String gameId) {
        Game game = games.get(gameId);
        Map<String, Integer> leaderboard = game.getLeaderboard();
        System.out.println("Leaderboard for game " + game.name + ":");
        for (Map.Entry<String, Integer> entry : leaderboard.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        DecentralizedGameNetwork network = new DecentralizedGameNetworkImpl();
        DecentralizedGamePrototypeIntegrator integrator = new DecentralizedGamePrototypeIntegrator(network);

        integrator.createGame("game1", "Decentralized Game");
        integrator.createGame("game2", "Blockchain Battle");

        Player player1 = new Player("player1", "Alice", 0);
        Player player2 = new Player("player2", "Bob", 0);

        integrator.joinGame("game1", player1);
        integrator.joinGame("game1", player2);
        integrator.joinGame("game2", player1);

        integrator.updateScore("game1", "player1", 10);
        integrator.updateScore("game1", "player2", 20);
        integrator.updateScore("game2", "player1", 30);

        integrator.displayLeaderboard("game1");
        integrator.displayLeaderboard("game2");
    }
}

class DecentralizedGameNetworkImpl implements DecentralizedGameNetwork {
    @Override
    public void broadcastGameCreation(Game game) {
        System.out.println("Broadcasting game creation: " + game.name);
    }

    @Override
    public void broadcastPlayerJoin(Game game, Player player) {
        System.out.println("Broadcasting player join: " + player.name + " joined " + game.name);
    }

    @Override
    public void broadcastScoreUpdate(Game game, String playerId, int score) {
        System.out.println("Broadcasting score update: " + playerId + " scored " + score + " in " + game.name);
    }
}