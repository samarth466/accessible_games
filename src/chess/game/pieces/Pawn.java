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

public class Pawn extends Piece {

    private ArrayList<Square> attackers;
    private boolean doubleMovedOnFirstTurn;

    public Pawn(FileC file, RankC rank, Color color) {
        super("Pawn", file,rank,color);
        attackedPieces = new ArrayList<>();
        attackers = new ArrayList<>();
        doubleMovedOnFirstTurn = false;
    }

    public ArrayList<Square> getAttackers() {
        return(attackers);
    }
    public boolean hasDoubleMovedOnFirstTurn() {
        return(this.doubleMovedOnFirstTurn);
    }

    @Override
    public ArrayList<Square> getPossiblePositions(String pos, Collection<Square> squares) {
        ArrayList<Square> possiblePositions = new ArrayList<>();
        FileC f = FileC.valueOf(pos.substring(0, 1));
        RankC r = RankC.valueOf(pos.substring(1));
        if (this.getColor() == Color.BLACK) {
            possiblePositions.add(this.getSquare(squares, f, RankC.valueOf(Integer.toString(r.rank()-1))));
            if (this.getRank() == RankC.SEVEN) {
                possiblePositions.add(this.getSquare(squares, f, RankC.valueOf(Integer.toString(r.rank()-2))));
            }
        } else {
            possiblePositions.add(this.getSquare(squares, f, RankC.valueOf(Integer.toString(r.rank()+1))));
            if (this.getRank() == RankC.TWO) {
                possiblePositions.add(this.getSquare(squares, f, RankC.valueOf(Integer.toString(r.rank()+2))));
            }
        }
        return(possiblePositions);
    }

    @Override
    public boolean validate(FileC f, RankC r, Collection<Square> squares, King king) {
        int[] pos = UtilMethods.getWindowPos(this.getFile(), this.getRank(), Constants.SQUARE_WIDTH, Constants.SQUARE_HEIGHT);
        ArrayList<Square> possiblePositions = getPossiblePositions(UtilMethods.getGamePos(pos[0], pos[1], Constants.SQUARE_WIDTH, Constants.SQUARE_HEIGHT), squares);
        Square endSquare = this.getSquare(squares, f, r);
        if (possiblePositions.size() == 2 && endSquare == possiblePositions.get(1)) {
            this.doubleMovedOnFirstTurn = true;
        }
        if (possiblePositions.contains(endSquare)) {
            Square startSquare = this.getSquare(squares, f, r);
            Piece temp = endSquare.setPiece(startSquare.getPiece());
            if (!(temp instanceof Empty)) {
                endSquare.setPiece(new Empty(endSquare.getRank(), endSquare.getFile(), temp.getColor()));
            } else {
                endSquare.setPiece(temp);
            }
            boolean validated = !king.check(king.getFile(), king.getRank(), squares);
            endSquare.setPiece(startSquare.getPiece());
            startSquare.setPiece(temp);
            return(validated);
        } else {
            return(false);
        }
    }

    public Piece promotion(String promotedPiece) {
        Piece piece;
        switch (promotedPiece.toLowerCase()) {
            case "bishop":
                piece = new Bishop(this.getFile(), this.getRank(), this.getColor());
                break;
            case "knight":
                piece = new Knight(this.getFile(), this.getRank(), this.getColor());
                break;
            case "queen":
                piece = new Queen(this.getFile(), this.getRank(), this.getColor());
                break;
            case "rook":
                piece = new Rook(this.getFile(), this.getRank(), this.getColor());
                break;
            default:
                throw new IllegalArgumentException("Invalid piece type for promotion");
        }
        return(piece);
    }

    public ArrayList<Square> enPassante(Collection<Square> squares) {
        Square previousSquare = this.getSquare(squares, FileC.valueOf(Character.toString(((int) this.getFile().file())-1)), this.getRank());
        Square nextSquare = this.getSquare(squares, FileC.valueOf(Character.toString(((int) this.getFile().file()+1))), this.getRank());
        if (this.getColor() == Color.WHITE) {
            if (this.getRank() == RankC.FIVE) {
                if (previousSquare.getPiece().getClass() == this.getClass()) {
                    if (((Pawn) previousSquare.getPiece()).hasDoubleMovedOnFirstTurn()) {
                        this.setRank(RankC.SIX);
                        this.setFile(previousSquare.getFile());
                        this.attackedPieces.add(previousSquare);
                    }
                } else if (nextSquare.getPiece().getClass() == this.getClass()) {
                    if (((Pawn) nextSquare.getPiece()).hasDoubleMovedOnFirstTurn()) {
                        this.setRank(RankC.SIX);
                        this.setFile(nextSquare.getFile());
                        this.attackedPieces.add(nextSquare);
                    }
                }
            }
        } else if (this.getColor() == Color.BLACK) {
            if (this.getRank() == RankC.FOUR) {
                if (previousSquare.getPiece().getClass() == this.getClass()) {
                    if (((Pawn) previousSquare.getPiece()).hasDoubleMovedOnFirstTurn()) {
                        this.setRank(RankC.THREE);
                        this.setFile(previousSquare.getFile());
                        this.attackedPieces.add(previousSquare);
                    }
                } else if (nextSquare.getPiece().getClass() == this.getClass()) {
                    if (((Pawn) nextSquare.getPiece()).hasDoubleMovedOnFirstTurn()) {
                        this.setRank(RankC.THREE);
                        this.setFile(nextSquare.getFile());
                        this.attackedPieces.add(nextSquare);
                    }
                }
            }
        }
        return(this.attackedPieces);
    }

}