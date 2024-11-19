package unoGameProject;

// Import Java modules
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

// Class header
public class Card implements Comparable {
    
    // Initialize instance variables
    private Color color;
    private NumberOrAction cardValue;
    private CardType cardType;
    
    // Constructor
    public Card(Color c, NumberOrAction v) {
        color = c;
        cardValue = v;
        assignCardType();
    }
    
    // Constructor
    public Card(Color c, NumberOrAction v, CardType t) {
        color = c;
        cardValue = v;
        cardType = t;
    }
    
    // Constructor
    public Card(Card card) {
        color = card.getColor();
        cardValue = card.getCardValue();
        cardType = card.getCardType();
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
                filename = String.format("%s_%s.png", color.colorName().substring(0,2), cardValue.cardName().contains(" ")?cardValue.cardName().split(" ")[0].charAt(0)+cardValue.cardName().split(" ")[1].charAt(0):cardValue.cardName().substring(0,2));
            }
        } else {
            if (cardValue == NumberOrAction.WILD) {
                filename = String.format("%s_WLD.png", (cardType==CardType.LIGHT)?"L":"D");
            } else if (cardValue == NumberOrAction.WILD_DRAW_COLOR) {
                filename = "W_D_C.png";
            } else if (cardValue == NumberOrAction.WILD_DRAW_TWO) {
                filename = "W_D_2";
            }
        }
        return(Paths.get(basePath,filename).toString());
    }

    public AccessibleBufferedImage loadImage() {
        AccessibleBufferedImage image = null;
        try (InputStream is = Card.class.getClassLoader().getResourceAsStream(parseImagePath("images"))) {
            if (is != null) {
                image = ImageIO.read(is);
                return(image);
            } else {
                return(null);
            }
        } catch (IOException e) {
            return(image);
        }
    }

    public int compareTo(Card other) {
        if (color == other.getColor() && color != Color.UNDEFINED) {
            return(other.getScore()-getScore());
        } else if (color == Color.UNDEFINED && other.getColor() != Color.UNDEFINED) {
            return(-1);
        } else if (other.getColor() == Color.UNDEFINED && color != Color.UNDEFINED) {
            return(1);
        } else if (color == other.color && color == Color.UNDEFINED) {
            return(other.getScore()-getScore());
        } else if (color != other.getColor()) {
            return(color.colorName().compareTo(other.getColor().colorName()));
        }
    }
    
}
