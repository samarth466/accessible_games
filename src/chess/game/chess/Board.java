package game.chess;

import java.awt.AWTEventMulticaster;
// Import Java modules
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.LinkedHashMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import game.boardUtils.Square;
import game.pieces.Bishop;
import game.pieces.Empty;
import game.pieces.King;
import game.pieces.Knight;
import game.pieces.Pawn;
import game.pieces.Piece;
import game.pieces.Queen;
import game.pieces.Rook;
import game.utils.FileC;
import game.utils.RankC;

public class Board extends JFrame {

    private int windowWidth;
    private int windowHeight;
    private int squareWidth;
    private int squareHeight;
    private ArrayList<Player> players;
    private int turnIndex;
    private ArrayList<Piece> capturedPieces;
    private Character[] possibleFiles = new Character[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    private LinkedHashMap<Color,LinkedHashMap<String,ArrayList<Piece>>> matterial;
    private LinkedHashMap<String,Square> squares;
    private LinkedHashMap<String, String> pieceIdentifiers = new LinkedHashMap<>(Map.of("b", "Bishop", "k", "King", "n", "Knight", "q", "Queen", "r", "Rook"));
    private MoveEventListener moveEventListeners;

    public Board(String title, int windowWidth, int windowHeight, int squareWidth, int squareHeight, Player player1, Player player2) {
        super(title);
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.squareWidth = squareWidth;
        this.squareHeight = squareHeight;
        this.players = new ArrayList<>();
        this.players.add(player1);
        this.players.add(player2);
        this.turnIndex = 0;
        this.capturedPieces = new ArrayList<>();
        this.matterial = new LinkedHashMap<>();
        this.initializeMatterial();
        this.squares = new LinkedHashMap<>();
        this.initializeSquares();
    }

    private void initializeMatterial() {
        LinkedHashMap<String,ArrayList<Piece>> black = new LinkedHashMap<>();
        LinkedHashMap<String,ArrayList<Piece>> white = new LinkedHashMap<>();
        ArrayList<Piece> blackBishop = new ArrayList<>(
            Arrays.asList(
                new Bishop(FileC.C, RankC.EIGHT, Color.BLACK),
                new Bishop(FileC.F, RankC.EIGHT, Color.BLACK)
            )
        );
        ArrayList<Piece> blackKing = new ArrayList<>(
            Arrays.asList(
                new King(FileC.E, RankC.EIGHT, Color.BLACK)
            )
        );
        ArrayList<Piece> blackKnight = new ArrayList<>(
            Arrays.asList(
                new Knight(FileC.B, RankC.EIGHT, Color.BLACK),
                new Knight(FileC.G, RankC.EIGHT, Color.BLACK)
            )
        );
        ArrayList<Piece> blackPawn = new ArrayList<>(
            Arrays.asList(
                new Pawn(FileC.A, RankC.SEVEN, Color.BLACK),
                new Pawn(FileC.B, RankC.SEVEN, Color.BLACK),
                new Pawn(FileC.C, RankC.SEVEN, Color.BLACK),
                new Pawn(FileC.D, RankC.SEVEN, Color.BLACK),
                new Pawn(FileC.E, RankC.SEVEN, Color.BLACK),
                new Pawn(FileC.F, RankC.SEVEN, Color.BLACK),
                new Pawn(FileC.G, RankC.SEVEN, Color.BLACK),
                new Pawn(FileC.H, RankC.SEVEN, Color.BLACK)
            )
        );
        ArrayList<Piece> blackQueen = new ArrayList<>(
            Arrays.asList(
                new Queen(FileC.D, RankC.EIGHT, Color.BLACK)
            )
        );
        ArrayList<Piece> blackRook = new ArrayList<>(
            Arrays.asList(
                new Rook(FileC.A, RankC.EIGHT, Color.BLACK),
                new Rook(FileC.H, RankC.EIGHT, Color.BLACK)
            )
        );
        black.put("Bishop", blackBishop);
        black.put("King", blackKing);
        black.put("Knight", blackKnight);
        black.put("Pawn", blackPawn);
        black.put("Queen", blackQueen);
        black.put("Rook", blackRook);
        black.put("Empty", new ArrayList<>(Arrays.asList(new Empty(null, null, null), new Empty(null, null, null))));
        ArrayList<Piece> whiteBishop = new ArrayList<>(
            Arrays.asList(
                new Bishop(FileC.C, RankC.ONE, Color.WHITE),
                new Bishop(FileC.F, RankC.ONE, Color.WHITE)
            )
        );
        ArrayList<Piece> whiteKing = new ArrayList<>(
            Arrays.asList(
                new King(FileC.E, RankC.ONE, Color.WHITE)
            )
        );
        ArrayList<Piece> whiteKnight = new ArrayList<>(
            Arrays.asList(
                new Knight(FileC.B, RankC.ONE, Color.WHITE),
                new Knight(FileC.G, RankC.ONE, Color.WHITE)
            )
        );
        ArrayList<Piece> whitePawn = new ArrayList<>(
            Arrays.asList(
                new Pawn(FileC.A, RankC.TWO, Color.WHITE),
                new Pawn(FileC.B, RankC.TWO, Color.WHITE),
                new Pawn(FileC.C, RankC.TWO, Color.WHITE),
                new Pawn(FileC.D, RankC.TWO, Color.WHITE),
                new Pawn(FileC.E, RankC.TWO, Color.WHITE),
                new Pawn(FileC.F, RankC.TWO, Color.WHITE),
                new Pawn(FileC.G, RankC.TWO, Color.WHITE),
                new Pawn(FileC.H, RankC.TWO, Color.WHITE)
            )
        );
        ArrayList<Piece> whiteQueen = new ArrayList<>(
            Arrays.asList(
                new Queen(FileC.D, RankC.ONE, Color.WHITE)
            )
        );
        ArrayList<Piece> whiteRook = new ArrayList<>(
            Arrays.asList(
                new Rook(FileC.A, RankC.ONE, Color.WHITE),
                new Rook(FileC.H, RankC.ONE, Color.WHITE)
            )
        );
        white.put("Bishop", whiteBishop);
        white.put("King", whiteKing);
        white.put("Knight", whiteKnight);
        white.put("Pawn", whitePawn);
        white.put("Queen", whiteQueen);
        white.put("Rook", whiteRook);
        white.put("Empty", new ArrayList<>(Arrays.asList(new Empty(null, null, null))));
        this.matterial.put(Color.BLACK, black);
        this.matterial.put(Color.WHITE, white);
    }

    private void initializeSquares() {
        this.squares.put("A1", new Square(FileC.A, RankC.ONE, this.computeSquareColor(FileC.A, RankC.ONE), this.matterial.get(Color.WHITE).get("Rook").get(0), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("A2", new Square(FileC.A, RankC.TWO, this.computeSquareColor(FileC.A, RankC.TWO), this.matterial.get(Color.WHITE).get("Pawn").get(0), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("A3", new Square(FileC.A, RankC.THREE, this.computeSquareColor(FileC.A, RankC.THREE), this.matterial.get(this.computeSquareColor(FileC.A, RankC.THREE)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("A4", new Square(FileC.A, RankC.FOUR, this.computeSquareColor(FileC.A, RankC.FOUR), this.matterial.get(this.computeSquareColor(FileC.A, RankC.FOUR)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("A5", new Square(FileC.A, RankC.FIVE, this.computeSquareColor(FileC.A, RankC.FIVE), this.matterial.get(this.computeSquareColor(FileC.A, RankC.FIVE)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("A6", new Square(FileC.A, RankC.SIX, this.computeSquareColor(FileC.A, RankC.SIX), this.matterial.get(this.computeSquareColor(FileC.A, RankC.SIX)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("A7", new Square(FileC.A, RankC.SEVEN, this.computeSquareColor(FileC.A, RankC.SEVEN), this.matterial.get(Color.BLACK).get("Pawn").get(0), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("A8", new Square(FileC.A, RankC.EIGHT, this.computeSquareColor(FileC.A, RankC.EIGHT), this.matterial.get(Color.BLACK).get("Rook").get(0), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("B1", new Square(FileC.B, RankC.ONE, this.computeSquareColor(FileC.B, RankC.ONE), this.matterial.get(Color.WHITE).get("Knight").get(0), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("B2", new Square(FileC.B, RankC.TWO, this.computeSquareColor(FileC.B, RankC.TWO), this.matterial.get(Color.WHITE).get("Pawn").get(1), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("B3", new Square(FileC.B, RankC.THREE, this.computeSquareColor(FileC.B, RankC.THREE), this.matterial.get(this.computeSquareColor(FileC.B, RankC.THREE)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("B4", new Square(FileC.B, RankC.FOUR, this.computeSquareColor(FileC.B, RankC.FOUR), this.matterial.get(this.computeSquareColor(FileC.B, RankC.FOUR)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("B5", new Square(FileC.B, RankC.FIVE, this.computeSquareColor(FileC.B, RankC.FIVE), this.matterial.get(this.computeSquareColor(FileC.B, RankC.FIVE)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("B6", new Square(FileC.B, RankC.SIX, this.computeSquareColor(FileC.B, RankC.SIX), this.matterial.get(this.computeSquareColor(FileC.B, RankC.SIX)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("B7", new Square(FileC.B, RankC.SEVEN, this.computeSquareColor(FileC.B, RankC.SEVEN), this.matterial.get(Color.BLACK).get("Pawn").get(1), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("B8", new Square(FileC.B, RankC.EIGHT, this.computeSquareColor(FileC.B, RankC.EIGHT), this.matterial.get(Color.BLACK).get("Knight").get(0), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("C1", new Square(FileC.C, RankC.ONE, this.computeSquareColor(FileC.C, RankC.ONE), this.matterial.get(Color.WHITE).get("Bishop").get(0), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("C2", new Square(FileC.C, RankC.TWO, this.computeSquareColor(FileC.C, RankC.TWO), this.matterial.get(Color.WHITE).get("Pawn").get(2), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("C3", new Square(FileC.C, RankC.THREE, this.computeSquareColor(FileC.C, RankC.THREE), this.matterial.get(this.computeSquareColor(FileC.C, RankC.THREE)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("C4", new Square(FileC.C, RankC.FOUR, this.computeSquareColor(FileC.C, RankC.FOUR), this.matterial.get(this.computeSquareColor(FileC.C, RankC.FOUR)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("C5", new Square(FileC.C, RankC.FIVE, this.computeSquareColor(FileC.C, RankC.FIVE), this.matterial.get(this.computeSquareColor(FileC.C, RankC.FIVE)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("C6", new Square(FileC.C, RankC.SIX, this.computeSquareColor(FileC.C, RankC.SIX), this.matterial.get(this.computeSquareColor(FileC.C, RankC.SIX)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("C7", new Square(FileC.C, RankC.SEVEN, this.computeSquareColor(FileC.C, RankC.SEVEN), this.matterial.get(Color.BLACK).get("Pawn").get(2), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("C8", new Square(FileC.C, RankC.EIGHT, this.computeSquareColor(FileC.C, RankC.EIGHT), this.matterial.get(Color.BLACK).get("Bishop").get(0), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("D1", new Square(FileC.D, RankC.ONE, this.computeSquareColor(FileC.D, RankC.ONE), this.matterial.get(Color.WHITE).get("Queen").get(0), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("D2", new Square(FileC.D, RankC.TWO, this.computeSquareColor(FileC.D, RankC.TWO), this.matterial.get(Color.WHITE).get("Pawn").get(3), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("D3", new Square(FileC.D, RankC.THREE, this.computeSquareColor(FileC.D, RankC.THREE), this.matterial.get(this.computeSquareColor(FileC.D, RankC.THREE)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("D4", new Square(FileC.D, RankC.FOUR, this.computeSquareColor(FileC.D, RankC.FOUR), this.matterial.get(this.computeSquareColor(FileC.D, RankC.FOUR)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("D5", new Square(FileC.D, RankC.FIVE, this.computeSquareColor(FileC.D, RankC.FIVE), this.matterial.get(this.computeSquareColor(FileC.D, RankC.FIVE)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("D6", new Square(FileC.D, RankC.SIX, this.computeSquareColor(FileC.D, RankC.SIX), this.matterial.get(this.computeSquareColor(FileC.D, RankC.SIX)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("D7", new Square(FileC.D, RankC.SEVEN, this.computeSquareColor(FileC.D, RankC.SEVEN), this.matterial.get(Color.BLACK).get("Pawn").get(3), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("D8", new Square(FileC.D, RankC.EIGHT, this.computeSquareColor(FileC.D, RankC.EIGHT), this.matterial.get(Color.BLACK).get("Queen").get(0), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("E1", new Square(FileC.E, RankC.ONE, this.computeSquareColor(FileC.E, RankC.ONE), this.matterial.get(Color.WHITE).get("King").get(0), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("E2", new Square(FileC.E, RankC.TWO, this.computeSquareColor(FileC.E, RankC.TWO), this.matterial.get(Color.WHITE).get("Pawn").get(4), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("E3", new Square(FileC.E, RankC.THREE, this.computeSquareColor(FileC.E, RankC.THREE), this.matterial.get(this.computeSquareColor(FileC.E, RankC.THREE)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("E4", new Square(FileC.E, RankC.FOUR, this.computeSquareColor(FileC.E, RankC.FOUR), this.matterial.get(this.computeSquareColor(FileC.E, RankC.FOUR)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("E5", new Square(FileC.E, RankC.FIVE, this.computeSquareColor(FileC.E, RankC.FIVE), this.matterial.get(this.computeSquareColor(FileC.E, RankC.FIVE)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("E6", new Square(FileC.E, RankC.SIX, this.computeSquareColor(FileC.E, RankC.SIX), this.matterial.get(this.computeSquareColor(FileC.E, RankC.SIX)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("E7", new Square(FileC.E, RankC.SEVEN, this.computeSquareColor(FileC.E, RankC.SEVEN), this.matterial.get(Color.BLACK).get("Pawn").get(4), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("E8", new Square(FileC.E, RankC.EIGHT, this.computeSquareColor(FileC.E, RankC.EIGHT), this.matterial.get(Color.BLACK).get("King").get(0), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("F1", new Square(FileC.F, RankC.ONE, this.computeSquareColor(FileC.F, RankC.ONE), this.matterial.get(Color.WHITE).get("Bishop").get(1), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("F2", new Square(FileC.F, RankC.TWO, this.computeSquareColor(FileC.F, RankC.TWO), this.matterial.get(Color.WHITE).get("Pawn").get(5), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("F3", new Square(FileC.F, RankC.THREE, this.computeSquareColor(FileC.F, RankC.THREE), this.matterial.get(this.computeSquareColor(FileC.F, RankC.THREE)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("F4", new Square(FileC.F, RankC.FOUR, this.computeSquareColor(FileC.F, RankC.FOUR), this.matterial.get(this.computeSquareColor(FileC.F, RankC.FOUR)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("F5", new Square(FileC.F, RankC.FIVE, this.computeSquareColor(FileC.F, RankC.FIVE), this.matterial.get(this.computeSquareColor(FileC.F, RankC.FIVE)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("F6", new Square(FileC.F, RankC.SIX, this.computeSquareColor(FileC.F, RankC.SIX), this.matterial.get(this.computeSquareColor(FileC.F, RankC.SIX)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("F7", new Square(FileC.F, RankC.SEVEN, this.computeSquareColor(FileC.F, RankC.SEVEN), this.matterial.get(Color.BLACK).get("Pawn").get(5), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("F8", new Square(FileC.F, RankC.EIGHT, this.computeSquareColor(FileC.F, RankC.EIGHT), this.matterial.get(Color.BLACK).get("Bishop").get(1), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("G1", new Square(FileC.G, RankC.ONE, this.computeSquareColor(FileC.G, RankC.ONE), this.matterial.get(Color.WHITE).get("Knight").get(1), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("G2", new Square(FileC.G, RankC.TWO, this.computeSquareColor(FileC.G, RankC.TWO), this.matterial.get(Color.WHITE).get("Pawn").get(6), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("G3", new Square(FileC.G, RankC.THREE, this.computeSquareColor(FileC.G, RankC.THREE), this.matterial.get(this.computeSquareColor(FileC.G, RankC.THREE)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("G4", new Square(FileC.G, RankC.FOUR, this.computeSquareColor(FileC.G, RankC.FOUR), this.matterial.get(this.computeSquareColor(FileC.G, RankC.FOUR)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("G5", new Square(FileC.G, RankC.FIVE, this.computeSquareColor(FileC.G, RankC.FIVE), this.matterial.get(this.computeSquareColor(FileC.G, RankC.FIVE)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("G6", new Square(FileC.G, RankC.SIX, this.computeSquareColor(FileC.G, RankC.SIX), this.matterial.get(this.computeSquareColor(FileC.G, RankC.SIX)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("G7", new Square(FileC.G, RankC.SEVEN, this.computeSquareColor(FileC.G, RankC.SEVEN), this.matterial.get(Color.BLACK).get("Pawn").get(6), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("G8", new Square(FileC.G, RankC.EIGHT, this.computeSquareColor(FileC.G, RankC.EIGHT), this.matterial.get(Color.BLACK).get("Knight").get(1), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("H1", new Square(FileC.H, RankC.ONE, this.computeSquareColor(FileC.H, RankC.ONE), this.matterial.get(Color.WHITE).get("Rook").get(1), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("H2", new Square(FileC.H, RankC.TWO, this.computeSquareColor(FileC.H, RankC.TWO), this.matterial.get(Color.WHITE).get("Pawn").get(7), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("H3", new Square(FileC.H, RankC.THREE, this.computeSquareColor(FileC.H, RankC.THREE), this.matterial.get(this.computeSquareColor(FileC.H, RankC.THREE)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("H4", new Square(FileC.H, RankC.FOUR, this.computeSquareColor(FileC.H, RankC.FOUR), this.matterial.get(this.computeSquareColor(FileC.H, RankC.FOUR)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("H5", new Square(FileC.H, RankC.FIVE, this.computeSquareColor(FileC.H, RankC.FIVE), this.matterial.get(this.computeSquareColor(FileC.H, RankC.FIVE)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("H6", new Square(FileC.H, RankC.SIX, this.computeSquareColor(FileC.H, RankC.SIX), this.matterial.get(this.computeSquareColor(FileC.H, RankC.SIX)).get("Empty").get(0), Math.max(this.squareHeight, this.squareWidth), true, null));
        this.squares.put("H7", new Square(FileC.H, RankC.SEVEN, this.computeSquareColor(FileC.H, RankC.SEVEN), this.matterial.get(Color.BLACK).get("Pawn").get(7), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.put("H8", new Square(FileC.H, RankC.EIGHT, this.computeSquareColor(FileC.H, RankC.EIGHT), this.matterial.get(Color.BLACK).get("Rook").get(1), Math.max(this.squareHeight, this.squareWidth), null));
        this.squares.forEach((pos, square) -> square.setWindow(this));
    }

    public synchronized void addMoveListener(MoveEventListener l) {
        if (l == null) {
            return;
        }
        moveEventListeners = MoveEventMulticaster.add(moveEventListeners, l);
    }

    public synchronized void removeMoveListener(MoveEventListener l) {
        if (l == null) {
            return;
        }
        moveEventListeners = MoveEventMulticaster.remove(moveEventListeners, l);
    }

    private synchronized void triggerMoveEvent(boolean isValidMove) {
        MoveEvent event = new MoveEvent(this, isValidMove);
        moveEventListeners.moveCompleted(event);
    }

    private Color computeSquareColor(FileC f, RankC r) {
        int fileNumber = (((int) f.file())-((int) FileC.A.file()))+1;
        int rankNumber = r.rank();
        if (fileNumber%2 == 1 && rankNumber%2 ==1) {
            return(Color.WHITE);
        } else {
            return(Color.BLACK);
        }
    }

    public void move(String moveString, Player turn) {
        Color turnColor = turn.getColor();
        String firstLetter = null;
        String specifier = null;
        String promotionPiece = null;
        String[] parts = null;
        String position = null;
        if (moveString.contains("=")) {
            parts = moveString.split("=");
            moveString = parts[0];
            promotionPiece = parts[1];
            if (moveString.length() == 2) {
                position = moveString;
            } else if (moveString.length() == 3) {
                specifier = String.valueOf(moveString.charAt(0));
                position = moveString.substring(1);
            }
        } else if (moveString.contains("00") || moveString.toLowerCase().contains("oo")) {
            FileC oldKingFile = this.matterial.get(turnColor).get("King").get(0).getFile();
            FileC oldRookFile = this.matterial.get(turnColor).get("Rook").get(0).getFile();
            RankC rank = this.matterial.get(turnColor).get("King").get(0).getRank();
            boolean castled = ((King) this.matterial.get(turnColor).get("King").get(0)).castle((Rook) this.matterial.get(turnColor).get("Rook").get(0), false, new ArrayList<>(this.squares.values()));
            if (castled) {
                FileC newKingFile = this.matterial.get(turnColor).get("King").get(0).getFile();
                FileC newRookFile = this.matterial.get(turnColor).get("Rook").get(0).getFile();
                Piece king = this.squares.get(oldKingFile.toString() + rank.toString()).setPiece(this.matterial.get(turnColor).get("Empty").get(0));
                Piece rook = this.squares.get(oldRookFile.toString() + rank.toString()).setPiece(new Empty(null, null, null));
                this.squares.get(newKingFile.toString() + rank.toString()).setPiece(king);
                this.squares.get(newRookFile.toString() + rank.toString()).setPiece(rook);
                this.squares.get(oldRookFile.toString() + rank.toString()).setPiece(this.matterial.get(turnColor).get("Empty").get(0));
                this.triggerMoveEvent(true);
                return;
            } else {
                this.triggerMoveEvent(false);
                return;
            }
        } else if (moveString.contains("000") || moveString.toLowerCase().contains("ooo")) {
            FileC oldKingFile = this.matterial.get(turnColor).get("King").get(0).getFile();
            FileC oldRookFile = this.matterial.get(turnColor).get("Rook").get(1).getFile();
            RankC rank = this.matterial.get(turnColor).get("King").get(0).getRank();
            boolean castled = ((King) this.matterial.get(turnColor).get("King").get(0)).castle((Rook) this.matterial.get(turnColor).get("Rook").get(1), true, new ArrayList<>(this.squares.values()));
            if (castled) {
                FileC newKingFile = this.matterial.get(turnColor).get("King").get(0).getFile();
                FileC newRookFile = this.matterial.get(turnColor).get("Rook").get(1).getFile();
                Piece king = this.squares.get(oldKingFile.toString() + rank.toString()).setPiece(this.matterial.get(turnColor).get("Empty").get(0));
                Piece rook = this.squares.get(oldRookFile.toString() + rank.toString()).setPiece(this.matterial.get(turnColor).get("Empty").get(0));
                this.squares.get(oldKingFile.toString() + rank.toString()).setPiece(this.matterial.get(turnColor).get("Empty").get(0));
                this.squares.get(newKingFile.toString() + rank.toString()).setPiece(king);
                this.squares.get(newRookFile.toString() + rank.toString()).setPiece(rook);
                this.squares.get(oldRookFile.toString() + rank.toString()).setPiece(this.matterial.get(turnColor).get("Empty").get(0));
                this.triggerMoveEvent(true);
                return;
            } else {
                this.triggerMoveEvent(false);
                return;
            }
        } else if (moveString.length() == 4) {
            firstLetter = String.valueOf(moveString.charAt(0));
            specifier = String.valueOf(moveString.charAt(1));
            position = moveString.substring(3);
        } else if (moveString.length() == 3) {
            firstLetter = String.valueOf(moveString.charAt(0));
            position = moveString.substring(1);
        }
        King king = (King) this.matterial.get(turnColor).get("King").get(0);
        if (position != null && specifier == null && firstLetter == null) {
            final char positionFirstLetter = position.charAt(0);
            if (position.equals(moveString) && Arrays.stream(this.possibleFiles).anyMatch(x -> x == positionFirstLetter)) {
                for (Piece pawn: this.matterial.get(turnColor).get("Pawn")) {
                    if (pawn.validate(FileC.valueOf(String.valueOf(position.charAt(0))), RankC.valueOf(String.valueOf(position.charAt(1))), new ArrayList<>(this.squares.values()), king)) {
                        if (promotionPiece == null) {
                            this.squares.get(position).setPiece(pawn);
                            this.squares.get(pawn.getFile().toString() + pawn.getRank().toString()).setPiece(this.matterial.get(turnColor).get("Empty").get(0));
                            pawn.setFile(FileC.valueOf(String.valueOf(position.charAt(0))));
                            pawn.setRank(RankC.valueOf(String.valueOf(position.charAt(1))));
                            this.triggerMoveEvent(true);
                            return;
                        } else if (this.pieceIdentifiers.containsKey(promotionPiece)) {
                            Piece promotedPiece = ((Pawn) pawn).promotion(promotionPiece);
                            this.squares.get(pawn.getFile().toString() + pawn.getRank().toString()).setPiece(this.matterial.get(turnColor).get("Empty").get(0));
                            this.squares.get(position).setPiece(promotedPiece);
                            this.matterial.get(turnColor).get("Pawn").remove(pawn);
                            this.matterial.get(turnColor).get(this.pieceIdentifiers.get(promotionPiece)).add(promotedPiece);
                            this.triggerMoveEvent(true);
                            return;
                        } else {
                            this.triggerMoveEvent(false);
                            return;
                        }
                    }
                }
                this.triggerMoveEvent(false);
                return;
            } else {
                this.triggerMoveEvent(false);
                return;
            }
        } else if (position != null && specifier != null && firstLetter == null) {
            int oldRank;
            if (turnColor == Color.WHITE) {
                oldRank = Character.getNumericValue(position.charAt(1))-1;
            } else {
                oldRank = Character.getNumericValue(position.charAt(1))+1;
            }
            if (!this.squares.get(position).empty() && this.squares.get(position).getPieceColor() != turnColor) {
                Piece piece = this.squares.get(specifier + Integer.toString(oldRank)).getPiece();
                if (piece.getName().equals("Pawn")) {
                    if (piece.validate(FileC.valueOf(String.valueOf(position.charAt(0))), RankC.valueOf(String.valueOf(position.charAt(1))), this.squares.values(), king)) {
                        if (promotionPiece == null) {
                            this.squares.get(position).setPiece(piece);
                            this.squares.get(piece.getFile().toString() + piece.getRank().toString()).setPiece(this.matterial.get(turnColor).get("Empty").get(0));
                            piece.setFile(FileC.valueOf(String.valueOf(position.charAt(0))));
                            piece.setRank(RankC.valueOf(String.valueOf(position.charAt(1))));
                            this.triggerMoveEvent(true);
                            return;
                        } else if (this.pieceIdentifiers.containsKey(promotionPiece)) {
                            Piece promotedPiece = ((Pawn) piece).promotion(promotionPiece);
                            this.squares.get(piece.getFile().toString() + piece.getRank().toString()).setPiece(this.matterial.get(turnColor).get("Empty").get(0));
                            this.squares.get(position).setPiece(promotedPiece);
                            this.matterial.get(turnColor).get("Pawn").remove(piece);
                            this.matterial.get(turnColor).get(this.pieceIdentifiers.get(promotionPiece)).add(promotedPiece);
                            this.triggerMoveEvent(true);
                            return;
                        } else {
                            this.triggerMoveEvent(false);
                            return;
                        }
                    } else {
                        this.triggerMoveEvent(false);
                        return;
                    }
                } else {
                    this.triggerMoveEvent(false);
                    return;
                }
            } else if (!this.squares.get(String.valueOf(position.charAt(0)) + Integer.toString(oldRank)).empty() && this.squares.get(String.valueOf(position.charAt(0)) + Integer.toString(oldRank)).getPieceColor() != turnColor) {
                Piece piece = this.squares.get(String.valueOf(specifier.charAt(0)) + Integer.toString(oldRank)).getPiece();
                if (piece.getName().equals("Pawn")) {
                    if (Math.abs(((int) specifier.charAt(0))-((int) position.charAt(0))) == 1) {
                        this.squares.get(specifier + Integer.toString(oldRank)).setPiece(this.matterial.get(turnColor).get("Empty").get(0));
                        Piece removedPiece = this.squares.get(String.valueOf(position.charAt(0)) + Integer.toString(oldRank)).setPiece(this.matterial.get(turnColor).get("Empty").get(0));
                        this.matterial.get(removedPiece.getColor()).get(removedPiece.getName()).remove(removedPiece);
                        this.squares.get(position).setPiece(piece);
                        piece.setFile(FileC.valueOf(String.valueOf(position.charAt(0))));
                        piece.setRank(RankC.valueOf(String.valueOf(position.charAt(1))));
                        this.triggerMoveEvent(true);
                        return;
                    } else {
                        this.triggerMoveEvent(false);
                        return;
                    }
                } else {
                    this.triggerMoveEvent(false);
                    return;
                }
            } else {
                this.triggerMoveEvent(false);
                return;
            }
        } else if (this.pieceIdentifiers.containsKey(firstLetter)) {
            for (Piece piece: this.matterial.get(turnColor).get(this.pieceIdentifiers.get(firstLetter))) {
                if (specifier != null) {
                    if (Character.isDigit(specifier.charAt(0))) {
                        if (piece.getRank().equals(RankC.valueOf(specifier))) {
                            if (piece.validate(FileC.valueOf(String.valueOf(position.charAt(0))), RankC.valueOf(String.valueOf(position.charAt(1))), this.squares.values(), king)) {
                                this.squares.get(piece.getFile().toString() + piece.getRank().toString()).setPiece(this.matterial.get(turnColor).get("Empty").get(0));
                                if (!this.squares.get(position).empty()) {
                                    Piece removedPiece = this.squares.get(position).getPiece();
                                    this.matterial.get(removedPiece.getColor()).get(removedPiece.getName()).remove(removedPiece);
                                }
                                this.squares.get(position).setPiece(piece);
                                this.triggerMoveEvent(true);
                                return;
                            } else {
                                this.triggerMoveEvent(false);
                                return;
                            }
                        }
                    } else if (Character.isAlphabetic(specifier.charAt(0))) {
                        if (piece.getFile().equals(FileC.valueOf(specifier))) {
                            if (piece.validate(FileC.valueOf(String.valueOf(position.charAt(0))), RankC.valueOf(String.valueOf(position.charAt(1))), this.squares.values(), king)) {
                                this.squares.get(piece.getFile().toString() + piece.getRank().toString()).setPiece(this.matterial.get(turnColor).get("Empty").get(0));
                                if (!this.squares.get(position).empty()) {
                                    Piece removedPiece = this.squares.get(position).getPiece();
                                    this.matterial.get(removedPiece.getColor()).get(removedPiece.getName()).remove(removedPiece);
                                }
                                this.squares.get(position).setPiece(piece);
                                this.triggerMoveEvent(true);
                                return;
                            } else {
                                this.triggerMoveEvent(false);
                                return;
                            }
                        }
                    } else {
                        this.triggerMoveEvent(false);
                        return;
                    }
                } else {
                    if (piece.validate(FileC.valueOf(String.valueOf(position.charAt(0))), RankC.valueOf(String.valueOf(position.charAt(1))), this.squares.values(), king)) {
                        this.squares.get(piece.getFile().toString() + piece.getRank().toString()).setPiece(this.matterial.get(turnColor).get("Empty").get(0));
                        if (!this.squares.get(position).empty()) {
                            Piece removedPiece = this.squares.get(position).getPiece();
                            this.matterial.get(removedPiece.getColor()).get(removedPiece.getName()).remove(removedPiece);
                        }
                        this.squares.get(position).setPiece(piece);
                        this.triggerMoveEvent(true);
                        return;
                    } else {
                        this.triggerMoveEvent(false);
                        return;
                    }
                }
            }
            this.triggerMoveEvent(false);
            return;
        } else {
            this.triggerMoveEvent(false);
            return;
        }
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        this.setBackground(Color.GRAY);
        for (Square square: this.squares.values()) {
            square.setBounds(((int) square.getFile().file())*this.squareWidth, square.getRank().rank()*this.squareHeight, this.squareWidth, this.squareHeight);
            this.add(square);
        }
    }

    public Player end() {
        if (((King) this.matterial.get(Color.BLACK).get("King").get(0)).checkmate(new ArrayList<>(this.matterial.get(Color.WHITE).values()), new ArrayList<>(this.squares.values()))) {
            return(this.getPlayerByColor(Color.WHITE));
        } else if (((King) this.matterial.get(Color.WHITE).get("King").get(0)).checkmate(new ArrayList<>(this.matterial.get(Color.BLACK).values()), new ArrayList<>(this.squares.values()))) {
            return(this.getPlayerByColor(Color.BLACK));
        } else {
            return(null);
        }
    }

    private Player getPlayerByColor(Color color) {
        for (Player player: this.players) {
            if (color.equals(player.getColor())) {
                return(player);
            }
        }
        return(null);
    }

    public void prevTurn() {
        this.turnIndex = ((this.turnIndex-1)%2+2)%2;
    }

    public void nextTurn() {
        this.turnIndex = (this.turnIndex+1)%2;
    }

    public Player getCurrentPlayer() {
        Player currentPlayer = this.players.get(this.turnIndex);
        this.nextTurn();
        return(currentPlayer);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess");
        JLabel label = new JLabel("Enter your name: ");
        JTextField nameField = new JTextField(20);
        frame.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(nameField);
        frame.add(panel, BorderLayout.CENTER);
        nameField.addKeyListener(new KeyAdapter() {
            
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String name = nameField.getText().trim();
                    if (!name.isEmpty()) {
                        frame.dispose();
                        Board board = new Board("Chess", 800, 800, 100, 100, new Player(name, Color.WHITE), new Player(name, Color.BLACK));
                        run(board);
                        board.setSize(800, 800);
                        board.setLocationRelativeTo(null);
                        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        board.setFocusable(true);
                        board.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void run(Board board) {
        Player currentPlayer = board.getCurrentPlayer();
        final JPanel inputPane = createInputPane(board, currentPlayer);
        board.setGlassPane(inputPane);
        board.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_T) {
                    toggleInputPane(inputPane);
                }
            }

        });
        TurnManager turnManager = new TurnManager(board);
        board.addMoveListener(turnManager);
    }

    private static void toggleInputPane(JPanel inputPane) {
        boolean isVisible = inputPane.isVisible();
        inputPane.setVisible(!isVisible);
        if (!isVisible) {
            inputPane.requestFocusInWindow();
        }
    }

    private static JPanel createInputPane(Board board, Player currentPlayer) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 0, 0, 150));
        panel.setVisible(false);

        JPanel inputPanel;

        JTextField moveField = new JTextField();
        moveField.addKeyListener(new KeyAdapter() {
            
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String moveInput = moveField.getText();
                    moveField.setText("");
                    if (2 <= moveInput.length() && moveInput.length() <= 4) {
                        toggleInputPane(panel);
                        board.move(moveInput, currentPlayer);
                    } else {
                        JOptionPane.showMessageDialog(panel, "The move must be 2-4 characters long!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        });

        inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Enter a move: "));
        inputPanel.add(moveField);

        panel.add(inputPanel, BorderLayout.CENTER);

        return(panel);
    }

}
