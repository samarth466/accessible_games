package uno;

// Import Java modules
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

// Class header
public class Card extends JPanel implements Comparable<Card> {
    
    // Initialize instance variables
    private Color color;
    private NumberOrAction cardValue;
    private CardType cardType;
    private ImageIcon image;
    private Color wildColor;
    
    // Constructor
    public Card(Color c, NumberOrAction v) {
        this.color = c;
        this.cardValue = v;
        this.image = new ImageIcon(this.loadImage().getImage().getScaledInstance(100, 400, Image.SCALE_SMOOTH));
        this.assignCardType();
        this.wildColor = Color.UNDEFINED;
    }
    
    // Constructor
    public Card(Color c, NumberOrAction v, CardType t) {
        this.color = c;
        this.cardValue = v;
        this.cardType = t;
        this.image = new ImageIcon(this.loadImage().getImage().getScaledInstance(100, 400, Image.SCALE_SMOOTH));
        this.wildColor = Color.UNDEFINED;
    }
    
    // Constructor
    public Card(Card card) {
        this.color = card.getColor();
        this.cardValue = card.getCardValue();
        this.cardType = card.getCardType();
        this.image = new ImageIcon(this.loadImage().getImage().getScaledInstance(100, 400, Image.SCALE_SMOOTH));
        this.wildColor = Color.UNDEFINED;
    }

    // Paints the component
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }

    // Adds mouse listener to select this Card object
    private void listenForSelection() {
        // Handle mouse interactions
        this.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseEntered(MouseEvent e) {
                Card.this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Card.this.setCursor(Cursor.getDefaultCursor());
            }

        });

        // Handle keyboard interactions
        this.addKeyListener(new KeyAdapter() {
            
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (Card.this.getCursor() == Cursor.getDefaultCursor()) {
                        Card.this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    } else {
                        Card.this.setCursor(Cursor.getDefaultCursor());
                    }
                }
            }

        });
    }
    
    // Getter methods
    
    // Returns the color of the card
    public Color getColor() {
        return(color);
    }
    
    // Gets the value of the card
    public NumberOrAction getCardValue() {
        return(cardValue);
    }
    
    // Gets the number of points the card is worth
    public int getScore() {
        return(cardValue.score());
    }
    
    // Gets the type of the card
    public CardType getCardType() {
        return(cardType);
    }

    // Returns the image of the card
    public ImageIcon getImage() {
        return(this.image);
    }
    
    // Sets the card's type based on its color
    private void assignCardType() {
        if (cardValue == NumberOrAction.WILD_DRAW_TWO) {
            cardType = CardType.LIGHT;
        } else if (cardValue == NumberOrAction.WILD_DRAW_COLOR) {
            cardType = CardType.DARK;
        } else {
            for (CardType value: CardType.values()) {
                if (value.colors().contains(color)) {
                    cardType = value;
                    break;
                }
            }
        }
    }
    
    // Returns a String representation of the Card
    public String toString() {
        return(getCardValue() + " " + getColor());
    }

    // Parses path for image
    private String parseImagePath(String basePath) {
        String filename = "";
        if (color != Color.UNDEFINED) {
            if (cardValue.score()<10) {
                filename = String.format("%s_%d.png", color.colorName().substring(0,2),cardValue.score());
            } else {
                filename = String.format("%s_%s.png", color.colorName().substring(0,2), cardValue.cardName().contains(" ")?"" + cardValue.cardName().split(" ")[0].charAt(0)+"_"+cardValue.cardName().split(" ")[1].charAt(0):cardValue.cardName().substring(0,2));
                
            }
        } else {
            if (cardValue == NumberOrAction.WILD) {
                filename = String.format("%s_WLD.png", (cardType==CardType.LIGHT)?"L":"D");
            } else if (cardValue == NumberOrAction.WILD_DRAW_COLOR) {
                filename = "W_D_C.png";
            } else if (cardValue == NumberOrAction.WILD_DRAW_TWO) {
                filename = "W_D_2.png";
            }
        }
        return(Paths.get(basePath,filename).toString());
    }

    private ImageIcon loadImage() {
        ImageIcon image = null;
        try (InputStream is = Card.class.getResourceAsStream("../" + parseImagePath("images"))) {
            if (is != null) {
                image = new ImageIcon(ImageIO.read(is));
                return(image);
            } else {
                System.out.println("Failed to load");
                return(null);
            }
        } catch (IOException e) {
            return(image);
        }
    }

    @Override
    public int compareTo(Card other) {
        if (this.color == other.getColor() && this.color != Color.UNDEFINED) {
            return(other.getScore()-this.getScore());
        } else if (this.color == Color.UNDEFINED && other.getColor() != Color.UNDEFINED) {
            return(-1);
        } else if (other.getColor() == Color.UNDEFINED && this.color != Color.UNDEFINED) {
            return(1);
        } else if (this.color == other.getColor() && this.color == Color.UNDEFINED) {
            return(other.getScore()-this.getScore());
        } else if (this.color != other.getColor()) {
            return(this.color.colorName().compareTo(other.getColor().colorName()));
        } else {
            return(0);
        }
    }
    
}
