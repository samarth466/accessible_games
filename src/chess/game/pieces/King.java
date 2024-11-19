package game.pieces;

// Import Java modules
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Collection;
import java.awt.Color;
import game.boardUtils.Square;
import game.utils.Constants;
import game.utils.FileC;
import game.utils.RankC;
import game.utils.UtilMethods;

public class King extends Piece {

    private ArrayList<Square> attackers;
    private boolean hasMoved;

    public King(FileC file, RankC rank, Color color) {
        super("King", file,rank,color);
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
        FileC file = FileC.valueOf(pos.substring(0,1));
        RankC rank = RankC.valueOf(pos.substring(1));
        ArrayList<Square> possiblePositions = new ArrayList<>(63);
        Square topLeftSquare = this.getSquare(squares, FileC.valueOf(Character.toString((char) (((int) file.file())-1))), RankC.valueOf(Integer.toString(rank.rank()-1)));
        if (topLeftSquare != null) possiblePositions.add(topLeftSquare);
        Square leftSquare = this.getSquare(squares, FileC.valueOf(Character.toString((char) (((int) file.file())-1))), rank);
        if (leftSquare != null) possiblePositions.add(leftSquare);
        Square bottomLeftSquare = this.getSquare(squares, FileC.valueOf(Character.toString((char) (((int) file.file())-1))), RankC.valueOf(Integer.toString(rank.rank()+1)));
        if (bottomLeftSquare != null) possiblePositions.add(bottomLeftSquare);
        Square topSquare = this.getSquare(squares, file, RankC.valueOf(Integer.toString(rank.rank()-1)));
        if (topSquare != null) possiblePositions.add(topSquare);
        Square bottomSquare = this.getSquare(squares, file, RankC.valueOf(Integer.toString(rank.rank()+1)));
        if (bottomSquare != null) possiblePositions.add(bottomSquare);
        Square topRightSquare = this.getSquare(squares, FileC.valueOf(Character.toString((char) (((int) file.file())+1))), RankC.valueOf(Integer.toString(rank.rank()-1)));
        if (topRightSquare != null) possiblePositions.add(topRightSquare);
        Square rightSquare = this.getSquare(squares, FileC.valueOf(Character.toString((char) (((int) file.file())+1))), rank);
        if (rightSquare != null) possiblePositions.add(rightSquare);
        Square bottomRightSquare = this.getSquare(squares, FileC.valueOf(Character.toString((char) (((int) file.file())+1))), RankC.valueOf(Integer.toString(rank.rank()+1)));
        if (bottomRightSquare != null) possiblePositions.add(bottomRightSquare);
        return(possiblePositions);
    }

    @Override
    public boolean validate(FileC f, RankC r, Collection<Square> squares, King king) {
        int[] pos = UtilMethods.getWindowPos(this.getFile(), this.getRank(), Constants.SQUARE_WIDTH, Constants.SQUARE_HEIGHT);
        ArrayList<Square> possiblePositions = getPossiblePositions(UtilMethods.getGamePos(pos[0], pos[1], Constants.SQUARE_WIDTH, Constants.SQUARE_HEIGHT), squares);
        Square s = this.getSquare(squares,f,r);
        if (s != null && !s.getAttacked() && s.getPieceColor() != this.getColor()) {
            return(possiblePositions.contains(s) && !king.check(f, r, squares));
        }
        return(false);
    }

    public boolean validate(FileC f, RankC r, Collection<Square> squares) {
        return(this.validate(f, r, squares, this));
    }

    public boolean check(FileC f, RankC r,Collection<Square>  squares) {
        if (f == null || r == null || squares == null) {
            throw new UnsupportedOperationException("The 'f', 'r' or 'squares' parameters cannot be 'null'");
        } else {
            Square s = this.getSquare(squares, f, r);
            if (s != null && s.getAttacked()) {
                return(true);
            }
            return(false);
        }
    }

    public boolean check(FileC f, RankC r, ArrayList<ArrayList<Piece>> pieces, Collection<Square> squares) {
        if (f == null || r == null || pieces == null || squares == null) {
            throw new UnsupportedOperationException("The 'f', 'r', 'pieces' or 'squares' parameters cannot be 'null'");
        } else {
            for (ArrayList<Piece> pieceList: pieces) {
                for (Piece piece: pieceList) {
                    if (!attackers.isEmpty() || piece.getAttackedPieces().contains(getSquare(squares,f,r))) {
                        return(true);
                    }
                }
            }
            return(false);
        }
    }

    public boolean checkmate(ArrayList<ArrayList<Piece>> pieces, ArrayList<Square> squares) {
        if (check(getFile(), getRank(), pieces, squares)) {
            int[] pos = UtilMethods.getWindowPos(this.getFile(), this.getRank(), Constants.SQUARE_WIDTH, Constants.SQUARE_HEIGHT);
            ArrayList<Square> possiblePositions = this.getPossiblePositions(UtilMethods.getGamePos(pos[0], pos[1], Constants.SQUARE_WIDTH, Constants.SQUARE_HEIGHT), squares);
            ArrayList<Square> filteredPossiblePositions = new ArrayList<>(possiblePositions.size());
            for (int i=0; i<possiblePositions.size(); i++) {
                Square square = possiblePositions.get(i);
                if (check(square.getFile(), square.getRank(), squares)) {
                    filteredPossiblePositions.add(square);
                }
            }
            if (filteredPossiblePositions.size() == possiblePositions.size()) {
                return(true);
            }
            return(false);
        }
        return(false);
    }

    public boolean castle(Rook rook, boolean longCastle, ArrayList<Square> squares) {
        if (longCastle) {
            if (this.getSquare(squares, FileC.D, this.getRank()).empty() && this.getSquare(squares, FileC.C, this.getRank()).empty() && this.getSquare(squares, FileC.B, this.getRank()).empty()) {
                if (!hasMoved && rook.hasMoved()) {
                    if (!check(getFile(), getRank(), squares) && !check(FileC.D, getRank(), squares) && !check(FileC.C, getRank(), squares) && !check(FileC.B, getRank(), squares)) {
                        setFile(FileC.C);
                        rook.setFile(FileC.D);
                        return(true);
                    }
                    return(false);
                }
                return(false);
            }
            return(false);
        } else {
            if (this.getSquare(squares, FileC.F, this.getRank()).empty() && this.getSquare(squares, FileC.G, this.getRank()).empty()) {
                if (!hasMoved && !rook.hasMoved()) {
                    if (!check(getFile(), getRank(), squares) && !check(FileC.F, getRank(), squares) && !check(FileC.G, getRank(), squares)) {
                        setFile(FileC.G);
                        rook.setFile(FileC.F);
                        return(true);
                    }
                    return(false);
                }
                return(false);
            }
            return(false);
        }
    }

}
