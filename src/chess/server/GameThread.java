package chess.server;

// Import Java modules
import java.util.ArrayList;

public class GameThread extends Thread {

    // Initialize instance variables
    private Server server;
    private Game game;

    public GameThread(Server server) {
        server = server;
        game = new Game();
    }

    @Override
    public void run() {
        // Ensure game does not start before all players have connected to server
        while (!game.isReady()) {
            // Wait for more players to play
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Start the game
        game.loop();
    }

    public synchronized void addClient(ClientHandler client) {
        game.addPlayer(client);
        if (game.isReady()) {
            start();
        }
    }

    public int getNumClients() {
        return(game.isReady()?2:1);
    }

    public void removeClient(ClientHandler client) {
        game.removePlayer(client);
        if (!game.isRunning()) {
            server.removeGame(this);
        }
    }

}
