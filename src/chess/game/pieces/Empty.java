package game.pieces;

// Import Java modules
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import game.boardUtils.Square;
import game.utils.FileC;
import game.utils.RankC;

public class Empty extends Piece {

    public Empty(RankC rank, FileC file, Color color) {
        super("Empty", file,rank,color);
    }

    @Override
    public ArrayList<Square> getPossiblePositions(String pos, Collection<Square> squares) {
        throw new UnsupportedOperationException("This operation is not supported");
    }

    @Override
    public boolean validate(FileC f, RankC r, Collection<Square> squares, King king) {
        throw new UnsupportedOperationException("This operation is not supported");
    }

}
