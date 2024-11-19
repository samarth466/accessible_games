package chess.server;

// Import Java modules
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    
    // Initialize instance variables
    private ServerSocket serverSocket;    // A ServerSocket object that handles the lower-level socket logic
    private ArrayList<GameThread> gameThreads;    // A list of GameThread objects

    public Server(int port) {
        try{
            serverSocket = new ServerSocket(port);
            gameThreads = new ArrayList<GameThread>();
            System.out.println("Server started on port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        while (true) {
            try{
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                // Handle new client connection
                handleClient(clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleClient(Socket clientSocket) {
        // Logic for handling clients
        ClientHandler clientHandler = new ClientHandler(clientSocket,this);
        clientHandler.start();
    }

    public synchronized void addGame(GameThread gameThread) {
        gameThreads.add(gameThread);
    }

    public synchronized void removeGame(GameThread gameThread) {
        gameThreads.remove(gameThread);
    }

    public synchronized ArrayList<GameThread> getGameThreads() {
        return( ArrayList<GameThread>(gameThreads);
    }

    public static void main(String[] args) {
        int port = 5050;
        Server server = new Server(port);
        server.start();
    }

}
