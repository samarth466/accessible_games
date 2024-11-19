package chess.server;

// Import Java packages
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

// Class header
public class ClientHandler extends Thread {

    // Initialize instance variables
    private Socket clientSocket;
    private Server server;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket, Server server) {
        clientSocket = socket;
        server = server;
        try{
            out = new PrintWriter(clientSocket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try{
            // Read client's name
            String clientName = in.readLine();
            System.out.println("Client " + clientName + " connected.");
            // Handle game logic
            handleGame(clientName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleGame(String clientName) {
        // Check for open game
        ArrayList<GameThread> gameThreads = server.getGameThreads();   // Gets a list of existing games
        GameThread openGame = findOpenGame(gameThreads);   // Gets an open game thread if one exists otherwise this variable is set to null
        if (openGame != null) {
            // Join existing game
            openGame.addClient(this);
        } else {
            // Create new game
            GameThread newGameThread = new gameThread(server);
            server.addGame(newGameThread);
            newGameThread.addClient(this);
            newGameThread.start();
        }
    }

    private GameThread findOpenGame(ArrayList<GameThread> gameThreads) {
        for (GameThread gameThread: gameThreads) {
            if (gameThread.getNumClients() < 2) {
                return(gameThread);
            }
        }
        return(null)
    }

    public PrintWriter getOutputStream() {
        return(out);
    }

    public BufferedReader getInputStream() {
        return(in);
    }

}