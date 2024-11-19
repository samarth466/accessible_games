package game.boardUtils;

// Import Java modules
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import game.chess.Board;
import game.pieces.Piece;
import game.pieces.Empty;
import game.utils.FileC;
import game.utils.RankC;

public class Square extends JPanel implements Serializable {

    // Initialize instance variables
    private FileC file;
    private RankC rank;
    private Color squareColor;
    private Piece piece;
    private int squareLength;
    private boolean isEmpty;
    private boolean attacked;
    private Board window;
    private static final long serialVersionUID = 1L;

    // Constructor
    public Square(FileC file, RankC rank, Color color, Piece piece, int squareLength, boolean isEmpty, Board window) {
        this.file = file;
        this.rank = rank;
        this.squareColor = color;
        this.piece = piece;
        this.squareLength = squareLength;
        this.isEmpty = isEmpty;
        this.window = window;
        this.attacked = false;
    }

    public Square(FileC file, RankC rank, Color color, Piece piece, int squareLength, Board window) {
        this(file, rank, color, piece, squareLength, false, window);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[] coordinates = getWindowPos();
        int x = coordinates[0];
        int y = coordinates[1];
        setBounds(x,y,squareLength,squareLength);
        setBackground(computeColor(x, y));
        setForeground(computeColor(x, y));
        if (piece.getImage() != null) {
            g.drawImage(piece.getImage().getImage(), 0, 0, this);
        }
    }

    public Piece getPiece() {
        return(this.piece);
    }

    public Piece setPiece(Piece newPiece) {
        Piece oldPiece = this.piece;
        this.piece = newPiece;
        return(oldPiece);
    }

    public FileC getFile() {
        return(file);
    }

    public RankC getRank() {
        return(rank);
    }

    public void setWindow(Board window) {
        this.window = window;
    }

    public int[] getWindowPos() {
        int y = (rank.rank()-1)*squareLength;
        String POSSIBLE_FILES = "ABCDEFGH";
        int x = POSSIBLE_FILES.indexOf(file.file())*squareLength;
        return(new int[]{x,y});
    }

    public boolean empty() {
        return(isEmpty);
    }

    public boolean getAttacked() {
        return(attacked);
    }

    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }

    public Color getPieceColor() {
        if (this.piece instanceof Empty) {
            return(null);
        } else {
            return(piece.getColor());
        }
    }

    private Color computeColor(int x, int y) {
        if ((x/squareLength)%2 == (y/squareLength)%2) {
            return(Color.WHITE);
        } else {
            return(Color.BLACK);
        }
    }

    public boolean isBetween(Square s1, Square s2) {
        FileC f1 = s1.getFile();
        RankC r1 = s1.getRank();
        FileC f2 = s2.getFile();
        RankC r2 = s2.getRank();
        try {
            boolean isBetweenRanks = ((r1.compareTo(this.rank))/(Math.abs(r1.compareTo(this.rank))))*((r2.compareTo(this.rank))/(Math.abs(r2.compareTo(this.rank))))<0;
            boolean isBetweenFiles = ((f1.compareTo(this.file))/(Math.abs(f1.compareTo(this.file))))*((f2.compareTo(this.file))/(Math.abs(f2.compareTo(this.file))))<0;
            return(isBetweenFiles&&isBetweenRanks);
        } catch (ArithmeticException e) {
            return(false);
        }
    }

    public String toString() {
        return(piece.toString());
    }

    public ArrayList<ArrayList<Square>> getDiagnals(Board board) {
        ArrayList<ArrayList<Square>> diagnals = new ArrayList<>();
        ArrayList<Square> diagnal1 = new ArrayList<>();
        ArrayList<Square> diagnal2 = new ArrayList<>();
        diagnals.add(diagnal1);
        diagnals.add(diagnal2);
        return(diagnals);
    }

    public ArrayList<Square> getRanks(Board board) {
        ArrayList<Square> ranks = new ArrayList<>(8);
        return(ranks);
    }

    public ArrayList<Square> getFiles(Board board) {
        ArrayList<Square> files = new ArrayList<>(8);
        return(files);
    }

}
