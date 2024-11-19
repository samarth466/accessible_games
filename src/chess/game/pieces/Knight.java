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

public class Knight extends Piece {

    private ArrayList<Square> attackers;

    public Knight(FileC file, RankC rank, Color color) {
        super("Knight",file,rank,color);
        attackedPieces = new ArrayList<>();
        attackers = new ArrayList<>();
    }

    public ArrayList<Square> getAttackers() {
        return(attackers);
    }

    @Override
    public ArrayList<Square> getPossiblePositions(String pos, Collection<Square> squares) {
        ArrayList<Square> possiblePositions = new ArrayList<>();
        FileC currFile = FileC.valueOf(pos.substring(0, 1));
        RankC currRank = RankC.valueOf(pos.substring(1));
        Square backLeft1 = this.getSquare(squares, FileC.valueOf(Character.toString((char) (((int) currFile.file())-1))), RankC.valueOf(Integer.toString(currRank.rank()-2)));
        Square backRight1 = this.getSquare(squares, FileC.valueOf(Character.toString((char) (((int) currFile.file())+1))), RankC.valueOf(Integer.toString(currRank.rank()-2)));
        Square backRight2 = this.getSquare(squares, FileC.valueOf(Character.toString((char) (((int) currFile.file())+2))), RankC.valueOf(Integer.toString(currRank.rank()-1)));
        Square forwartRight2 = this.getSquare(squares, FileC.valueOf(Character.toString((char) (((int) currFile.file())+2))), RankC.valueOf(Integer.toString(currRank.rank()+1)));
        Square forwardRight1 = this.getSquare(squares, FileC.valueOf(Character.toString((char) (((int) currFile.file())+1))), RankC.valueOf(Integer.toString(currRank.rank()+2)));
        Square ForwardLeft1 = this.getSquare(squares, FileC.valueOf(Character.toString((char) (((int) currFile.file())-1))), RankC.valueOf(Integer.toString(currRank.rank()+2)));
        Square forwardLeft2 = this.getSquare(squares, FileC.valueOf(Character.toString((char) (((int) currFile.file())-2))), RankC.valueOf(Integer.toString(currRank.rank()+1)));
        Square backLeft2 = this.getSquare(squares, FileC.valueOf(Character.toString((char) (((int) currFile.file())-2))), RankC.valueOf(Integer.toString(currRank.rank()-1)));
        possiblePositions.add(backLeft1);
        possiblePositions.add(backLeft2);
        possiblePositions.add(backRight1);
        possiblePositions.add(backRight2);
        possiblePositions.add(ForwardLeft1);
        possiblePositions.add(forwardLeft2);
        possiblePositions.add(forwardRight1);
        possiblePositions.add(forwartRight2);
        return(possiblePositions);
    }

    @Override
    public boolean validate(FileC f, RankC r, Collection<Square> squares, King king) {
        int[] pos = UtilMethods.getWindowPos(this.getFile(), this.getRank(), Constants.SQUARE_WIDTH, Constants.SQUARE_HEIGHT);
        ArrayList<Square> possiblePositions = this.getPossiblePositions(UtilMethods.getGamePos(pos[0], pos[1], Constants.SQUARE_WIDTH, Constants.SQUARE_HEIGHT), squares);
        Square startingSquare = this.getSquare(squares, this.getFile(), this.getRank());
        Square endSquare = this.getSquare(squares, f, r);
        if (startingSquare != null && endSquare != null) {
            if (endSquare.getPieceColor() != this.getColor()) {
                Piece temp = startingSquare.setPiece(endSquare.getPiece());
                if (!(temp instanceof Empty)) {
                    endSquare.setPiece(new Empty(startingSquare.getRank(), startingSquare.getFile(), temp.getColor()));
                } else {
                    endSquare.setPiece(temp);
                }
                boolean valid = !king.check(king.getFile(), king.getRank(), squares);
                endSquare.setPiece(startingSquare.getPiece());
                startingSquare.setPiece(temp);
                return(valid);
            } else {
                return(false);
            }
        } else {
            return(false);
        }
    }

}
