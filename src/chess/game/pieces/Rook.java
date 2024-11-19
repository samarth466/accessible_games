package game.pieces;

// Import Java modules
import java.util.ArrayList;
import java.util.Collection;
import java.awt.Color;
import game.boardUtils.Square;
import game.utils.Constants;
import game.utils.FileC;
import game.utils.RankC;
import game.utils.UtilMethods;

public class Rook extends Piece {

    private ArrayList<Square> attackers;
    private boolean hasMoved;

    public Rook(FileC file, RankC rank, Color color) {
        super("Rook", file,rank,color);
        attackedPieces = new ArrayList<>();
        attackers = new ArrayList<>();
        hasMoved = false;
    }

    public ArrayList<Square> getAttackers() {
        return(attackers);
    }

    public boolean hasMoved() {
        return(hasMoved);
    }

    @Override
    public ArrayList<Square> getPossiblePositions(String pos, Collection<Square> squares) {
        ArrayList<Square> possiblePositions = new ArrayList<>(8);
        FileC file = FileC.valueOf(pos.substring(0, 1));
        RankC rank = RankC.valueOf(pos.substring(1));
        Square square = this.getSquare(squares, file, rank);
        if (square != null) {
            ArrayList<Square> ranks = square.getRanks(null);
            ArrayList<Square> files = square.getFiles(null);
            possiblePositions.addAll(ranks);
            possiblePositions.addAll(files);
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
            if (moveRatio == 0) {
                boolean validated = false;
                int step = (((int) f.file())-((int) this.getFile().file()))/(Math.abs(((int) f.file())-((int) this.getFile().file())));
                for (int i = (int) this.getFile().file(); i != (int) f.file(); i+=step) {
                    FileC currFile = FileC.valueOf(Character.toString((char) i));
                    Square s = this.getSquare(squares, currFile, r);
                    if (s != null && !(s.getPiece() instanceof Empty)) {
                        validated = possiblePositions.contains(s);
                    } else {
                        validated = false;
                        break;
                    }
                }
                if (validated) {
                    Square startSquare = this.getSquare(squares, this.getFile(), this.getRank());
                    Square endSquare = this.getSquare(squares, f, r);
                    if (endSquare == null || endSquare.getPieceColor() == this.getColor()) {
                        validated = false;
                    } else {
                        if (possiblePositions.contains(endSquare)) {
                            Piece temp1 = startSquare.setPiece(endSquare.getPiece());
                            if (!(temp1 instanceof Empty)) {
                                endSquare.setPiece(new Empty(endSquare.getRank(), endSquare.getFile(), temp1.getColor()));
                            } else {
                                endSquare.setPiece(temp1);
                            }
                            validated = !king.check(king.getFile(), king.getRank(), squares);
                            endSquare.setPiece(startSquare.getPiece());
                            startSquare.setPiece(temp1);
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
            int step = (r.rank()-this.getRank().rank())/(Math.abs(r.rank()-this.getRank().rank()));
            boolean validated = false;
            for (int i=this.getRank().rank(); i != r.rank(); i+=step) {
                RankC currRank = RankC.valueOf(Integer.toString(i));
                Square s = this.getSquare(squares, f, currRank);
                if (s != null && !(s.getPiece() instanceof Empty)) {
                    validated = possiblePositions.contains(s);
                } else {
                    validated = false;
                    break;
                }
            }
            if (validated) {
                Square startSquare = this.getSquare(squares, this.getFile(), this.getRank());
                Square endSquare = this.getSquare(squares, f, r);
                if (endSquare == null || endSquare.getPieceColor() == this.getColor()) {
                    validated = false;
                } else {
                    if (possiblePositions.contains(endSquare)) {
                        Piece temp2 = endSquare.setPiece(startSquare.getPiece());
                        if (!(temp2 instanceof Empty)) {
                            endSquare.setPiece(new Empty(endSquare.getRank(), endSquare.getFile(), temp2.getColor()));
                        } else {
                            endSquare.setPiece(temp2);
                        }
                        validated = !king.check(king.getFile(), king.getRank(), squares);
                        startSquare.setPiece(endSquare.getPiece());
                        endSquare.setPiece(temp2);
                    } else {
                        validated = false;
                    }
                }
            }
            return(validated);
        }
    }

}
