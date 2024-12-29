package game.chess;

// Import Java modules
import java.awt.AWTEvent;

public class MoveEvent extends AWTEvent {

    public static final int MOVE_EVENT_ID = AWTEvent.RESERVED_ID_MAX + 1;
    private final boolean isValidMove;

    public MoveEvent(Object source, boolean isValidMove) {
        super(source, MOVE_EVENT_ID);
        this.isValidMove = isValidMove;
    }

    public boolean isValidMove() {
        return(this.isValidMove);
    }
    
}