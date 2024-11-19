package game.chess;

// Import Java modules
import java.awt.Color;

public class Player {
    
    private String username;
    private Color color;

    public Player(String username, Color color) {
        this.username = username;
        this.color = color;
    }

    public String getUsername() {
        return(this.username);
    }

    public Color getColor() {
        return(this.color);
    }

    private String colorToString() {
        if (this.color.equals(Color.BLACK)) {
            return("Black");
        } else if (this.color.equals(Color.WHITE)) {
            return("White");
        } else {
            throw new IllegalArgumentException("The 'color' argument must be 'Color.BLACK' or 'Color.White'");
        }
    }

    public String toString() {
        return(username + " (" + this.colorToString() + ")");
    }

}
