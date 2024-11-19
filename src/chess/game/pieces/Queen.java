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

public class Queen extends Piece {

    private ArrayList<Square> attackers;

    public Queen(FileC file, RankC rank, Color color) {
        super("Queen", file,rank,color);
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
        int[] pos = UtilMethods.getWindowPos(this.getFile(), this.getRank(), Constants.SQUARE_WIDTH, Constants.SQUARE_WIDTH);
        ArrayList<Square> possiblePositions = getPossiblePositions(UtilMethods.getGamePos(pos[0], pos[1], Constants.SQUARE_WIDTH, Constants.SQUARE_WIDTH), squares);
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
            } else if (moveRatio == 1 || moveRatio == -1) {
                Square startSquare1 = this.getSquare(squares, this.getFile(), this.getRank());
                Square endSquare1 = this.getSquare(squares, f, r);
                ArrayList<ArrayList<Square>> diagnals = this.getSquare(squares, this.getFile(), this.getRank()).getDiagnals(null);
                boolean validated = false;
                for (Square position: possiblePositions) {
                    if (position != possiblePositions.get(diagnals.get(0).size()+diagnals.get(1).size())) {
                        break;
                    }
                    if (startSquare1 != null && endSquare1 != null && !(position.getPiece() instanceof Empty)) {
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
                            Piece temp2 = startSquare2.setPiece(endSquare2.getPiece());
                            if (!(temp2 instanceof Empty)) {
                                endSquare2.setPiece(new Empty(endSquare2.getRank(), endSquare2.getFile(), temp2.getColor()));
                            } else {
                                endSquare2.setPiece(temp2);
                            }
                            validated = !king.check(king.getFile(), king.getRank(), squares);
                            endSquare2.setPiece(startSquare2.getPiece());
                            startSquare2.setPiece(temp2);
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
                        Piece temp = endSquare.setPiece(startSquare.getPiece());
                        if (!(temp instanceof Empty)) {
                            endSquare.setPiece(new Empty(endSquare.getRank(), endSquare.getFile(), temp.getColor()));
                        } else {
                            endSquare.setPiece(temp);
                        }
                        validated = !king.check(king.getFile(), king.getRank(), squares);
                        startSquare.setPiece(endSquare.getPiece());
                        endSquare.setPiece(temp);
                    } else {
                        validated = false;
                    }
                }
            }
            return(validated);
        }
    }

}
