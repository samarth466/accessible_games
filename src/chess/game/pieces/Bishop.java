package game.pieces;

// Import Java modules
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import game.boardUtils.Square;
import game.utils.Constants;
import game.utils.FileC;
import game.utils.RankC;
import game.utils.UtilMethods;

public class Bishop extends Piece {

    private ArrayList<Square> attackers;

    public Bishop(FileC file, RankC rank, Color color) {
        super("Bishop", file,rank,color);
        attackedPieces = new ArrayList<>();
        attackers = new ArrayList<>();
    }

    public ArrayList<Square> getAttackers() {
        return(attackers);
    }

    @Override
    public ArrayList<Square> getPossiblePositions(String pos, Collection<Square> squares) {
        ArrayList<Square> possiblePositions = new ArrayList<>(8);
        FileC file = FileC.valueOf(pos.substring(0, 1));
        RankC rank = RankC.valueOf(pos.substring(1));
        Square square = this.getSquare(squares, file, rank);
        if (square != null) {
            ArrayList<ArrayList<Square>> diagnals = square.getDiagnals(null);
            possiblePositions.addAll(diagnals.get(0));
            possiblePositions.addAll(diagnals.get(1));
            return(possiblePositions);
        }
        return(null);
    }

    @Override
    public boolean validate(FileC f, RankC r, Collection<Square> squares, King king) {
        int[] pos = UtilMethods.getWindowPos(this.getFile(), this.getRank(), Constants.SQUARE_WIDTH, Constants.SQUARE_HEIGHT);
        ArrayList<Square> possiblePositions = getPossiblePositions(UtilMethods.getGamePos(pos[0], pos[1], Constants.SQUARE_WIDTH, Constants.SQUARE_HEIGHT), squares);
        try{
            int moveRatio = (r.rank()-this.getRank().rank())/(((int) f.file())-((int) this.getFile().file()));
            if (moveRatio == 1 || moveRatio == -1) {
                Square startSquare1 = this.getSquare(squares, this.getFile(), this.getRank());
                Square endSquare1 = this.getSquare(squares, f, r);
                boolean validated = false;
                for (Square position: possiblePositions) {
                    if (position != null && startSquare1 != null && endSquare1 != null && !(position.getPiece() instanceof Empty)) {
                        validated = position.isBetween(startSquare1, endSquare1);
                        if (!validated) {
                            break;
                        }
                    } else {
                        validated = false;
                        break;
                    }
                }
                if (validated) {
                    Square startSquare2 = this.getSquare(squares, this.getFile(), this.getRank());
                    Square endSquare2 = this.getSquare(squares, f, r);
                    if (endSquare2 == null || endSquare2.getPieceColor() == this.getColor()) {
                        validated = false;
                    } else {
                        if (possiblePositions.contains(endSquare2)) {
                            Piece temp = startSquare2.setPiece(endSquare2.getPiece());
                            if (!(temp instanceof Empty)) {
                                endSquare2.setPiece(new Empty(endSquare2.getRank(), endSquare2.getFile(), temp.getColor()));
                            } else {
                                endSquare2.setPiece(temp);
                            }
                            validated = !king.check(king.getFile(), king.getRank(), squares);
                            endSquare2.setPiece(startSquare2.getPiece());
                            startSquare2.setPiece(temp);
                        } else {
                            validated = false;
                        }
                    }
                }
                return(validated);
            } else {
                return(false);
            }
        } catch (ArithmeticException e) {
            return(false);
        }
    }

}
