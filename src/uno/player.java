package uno;

// Import Java modules
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// Class header
public class Player extends Person {

    private static final int CARD_WIDTH = 100;
    private static final int CARD_HEIGHT = 145;
    private int initialCardX = 10;
    private int initialCardY = 20;
    private ArrayList<ImageIcon> playerCards;
    private JTextArea output;
    private JTextField input;
    
    // Constructor
    public Player(String name) {
        super(name);
        this.playerCards = new ArrayList<>();
        this.input = new JTextField();
        this.output = new JTextArea();
        for (Card card: this.getHand().getHand()) {
            this.listenForSelection(card);
        }
    }

    private void displayText(String text) {
        output.setText(text);
    }

    private String getText() {
        String text = input.getText();
        input.setText("");
        return(text);
    }

    private void selectCard(Card card) {
        // UNIMPLEMENTED
    }

    private void listenForSelection(Card card) {
        // Handle mouse interactions
        card.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                Player.this.selectCard(card);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setCursor(Cursor.getDefaultCursor());
            }

        });

        // Handle keyboard interactions
        card.addKeyListener(new KeyAdapter() {
            
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (card.getCursor() == Cursor.getDefaultCursor()) {
                        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        Player.this.selectCard(card);
                    } else {
                        card.setCursor(Cursor.getDefaultCursor());
                    }
                }
            }

        });
    }
    
    public void printHand(Game game) {
        System.out.println("Printing hand...");
        for (int i = 0; i < super.getHand().getNumberOfCards(); i++) {
            Card card = super.getHand().getCard(i);
            card.setBounds(this.initialCardX, this.initialCardY, this.CARD_WIDTH, this.CARD_HEIGHT);
            game.add(card);
            initialCardX += 50;
            initialCardY -= 18;
        }
    }
    
    private void printTopCard(Card topCard, Color currentColor, Game game) {
        ImageIcon image = new ImageIcon(topCard.getImage().getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH));
        JPanel innerPanel = new JPanel() {

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
            }

        };
        innerPanel.setBounds(150, 150, this.CARD_WIDTH, this.CARD_HEIGHT);
        game.add(innerPanel);
        if (topCard.getColor() == Color.UNDEFINED) {
            this.displayText("The wild color is " + currentColor);
        }
    }
    
    // Allows the user to pick a card to play
    @Override
    public Color chooseCard(Card topCard, Deck deck, Deck discardPile, Color currentColor, boolean light, Game game) {
        int cardIndex = -1;
        boolean getNum = true;
        printTopCard(topCard,currentColor,game);
        while (getNum) {
            try {
                if (cardIndex == -1) {
                    ArrayList<Card> temp = super.getHand().getHand();
                    super.getHand().takeCardFromDeck(deck);
                    for (Card card: super.getHand().getHand()) {
                        if (!temp.contains(card)) {
                            this.displayText("You drew: " + card);
                            if (cardIsPlayable(topCard,currentColor, card)) {
                                this.displayText("Press 1 if you'd like to play this card, 0 otherwise");
                                int userChoice = Integer.parseInt(this.getText());
                                if (userChoice == 1) {
                                    super.getHand().removeCard(card);
                                    discardPile.add(card);
                                    super.getHand().removeCard(card);
                                    if (card.getColor() == Color.UNDEFINED) {
                                        return(chooseColor(light));
                                    } else {
                                        return(card.getColor());
                                    }
                                } else {
                                    this.displayText("Your turn is over. You have " + super.getHand().getNumberOfCards() + " cards.");
                                }
                            } else {
                                this.displayText("You cannot play this card. Your turn is over. You have " + super.getHand().getNumberOfCards() + " cards.");
                                return(Color.UNDEFINED);
                            }
                        }
                    }
                } else if (cardIndex == 0) {
                    printTopCard(topCard,currentColor, game);
                } else if (cardIndex <= super.getHand().getNumberOfCards() && cardIndex > 0) {
                    Card cardToPlay = super.getHand().getHand().get(cardIndex);
                    if (cardIsPlayable(topCard,currentColor,cardToPlay)) {
                        discardPile.add(cardToPlay);
                        super.getHand().removeCard(cardToPlay);
                        if (cardToPlay.getColor() == Color.UNDEFINED) {
                            return(chooseColor(light));
                        } else {
                            return(cardToPlay.getColor());
                        }
                    }
                }
            } catch (Exception e) {
                //System.out.println(e);
                this.displayText("Invalid!");
            }
        }
        return(Color.UNDEFINED);
    }
    
    public boolean cardIsPlayable(Card topCard, Color currentColor, Card cardToPlay) {
        if (cardToPlay.getCardValue().cardName().contains("Wild")) {
            return(true);
        } else if (topCard.getCardValue().cardName().contains("Wild") && currentColor == cardToPlay.getColor()) {
            return(true);
        } else if (topCard.getColor() == cardToPlay.getColor()) {
            return(true);
        } else if (topCard.getCardValue() == cardToPlay.getCardValue()) {
            return(true);
        } else {
            return(false);
        }
    }
    
    public Color chooseColor(boolean light) {
        Color color;
        String output = "Press ";
        if (light) {
            for (int i=0; i<CardType.LIGHT.colors().size()-1; i++) {
                output += i+1 + " for " + CardType.LIGHT.colors().get(i) + ", ";
            }
            output += CardType.LIGHT.colors().size() + " for " + CardType.LIGHT.colors().get(CardType.LIGHT.colors().size()-1) + ".";
        } else {
            for (int i=0; i<CardType.DARK.colors().size()-1; i++) {
                output += i+1 + " for " + CardType.DARK.colors().get(i) + ", ";
            }
            output += CardType.DARK.colors().size() + " for " + CardType.DARK.colors().get(CardType.DARK.colors().size()-1) + ".";
        }
        while(true) {
            try {
                this.displayText(output);
                int colorIndex = Integer.parseInt(this.getText());
                if (light) {
                    color = CardType.LIGHT.colors().get(colorIndex-1);
                    break;
                } else {
                    color = CardType.DARK.colors().get(colorIndex-1);
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                this.displayText("Invalid input. Try again.");
            }
        }
        return(color);
    }
    
}
