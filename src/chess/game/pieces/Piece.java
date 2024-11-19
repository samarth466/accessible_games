package game.pieces;

// Import Java modules
import java.awt.Image;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.awt.Color;
import javax.swing.ImageIcon;
import game.boardUtils.Square;
import game.utils.Constants;
import game.utils.FileC;
import game.utils.RankC;
import game.utils.UtilMethods;

public abstract class Piece implements Serializable {

    // Initialize instance variables
    private String filename;
    private RankC rank;
    private FileC file;
    private Color color;
    private ImageIcon image;
    private String id;
    protected ArrayList<Square> attackedPieces;
    private static final String[] POSSIBLE_FILES;

    static {
        StringBuilder sb = new StringBuilder();
        for (char c = 'A'; c <= 'H'; c++) {
            sb.append(c);
        }
        POSSIBLE_FILES = sb.toString().split("");
    }

    // Constructor
    public Piece(String filename, FileC file, RankC rank, Color color) {
        this.filename = filename;
        String COLOR_LETTERING = (color == Color.WHITE)?"W":"B";
        Path path = Paths.get("chessmen",String.format("%s_%s.png", COLOR_LETTERING, filename));
        if (filename != null) {
            image = new ImageIcon(new ImageIcon(path.toString()).getImage().getScaledInstance(Constants.SQUARE_WIDTH, Constants.SQUARE_HEIGHT, Image.SCALE_SMOOTH));
        } else {
            image = null;
        }
        this.rank = rank;
        this.file = file;
        this.color = color;
        this.id = String.format("%s_%s", colorId(), filename);
    }

    public FileC getFile() {
        return(file);
    }

    public void setFile(FileC file) {
        this.file = file;
    }

    public RankC getRank() {
        return(rank);
    }

    public void setRank(RankC rank) {
        this.rank = rank;
    }

    public Color getColor() {
        return(color);
    }

    public ImageIcon getImage() {
        return(image);
    }

    public ArrayList<Square> getAttackedPieces() {
        return(attackedPieces);
    }

    public String getName() {
        return(filename);
    }

    public ArrayList<Square> updateAttackedPieces(Collection<Square> squares) {
        int[] coordinate = UtilMethods.getWindowPos(file, rank, Constants.SQUARE_WIDTH, Constants.SQUARE_HEIGHT);
        int x = coordinate[0];
        int y = coordinate[1];
        attackedPieces.addAll(getPossiblePositions(UtilMethods.getGamePos(x, y, Constants.SQUARE_WIDTH, Constants.SQUARE_HEIGHT), squares));
        return(attackedPieces);
    }

    public String toString() {
        return(String.format("%s%i:%s",file.file(), rank.rank(), id));
    }

    private String colorId() {
        return((color==Color.WHITE)?"White":"Black");
    }

    protected Square getSquare(Collection<Square> squares, FileC f, RankC r) {
        for (Square square: squares) {
            if (f.equals(square.getFile()) && r.equals(square.getRank())) {
                return(square);
            }
        }
        return(null);
    }

    public abstract ArrayList<Square> getPossiblePositions(String pos, Collection<Square> squares);

    public abstract boolean validate(FileC f, RankC r, Collection<Square> squares, King king);

}
