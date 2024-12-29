package game.chess;

// Import Java modules
import java.util.EventListener;

public interface MoveEventListener extends EventListener {

    void moveCompleted(MoveEvent e);
    
}
