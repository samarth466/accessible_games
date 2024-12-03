package uno;

// Import Java modules
import java.util.ArrayList;
import java.util.Collections;

// Class header
public class Hand {
    
    private ArrayList<Card> hand;
    
    // Constructor
    public Hand() {
        hand = new ArrayList<Card>();
    }
    
    public void flip(boolean light, Deck deck) {
        hand = deck.flipCards(light,hand);
        sort();
    }
    
    public void takeCardFromDeck(Deck deck) {
        hand.add(deck.takeCard());
        sort();
    }
    
    // Continues to take card from deck until color of card matches currentColor
    public void takeCardFromDeckUntilColor(Deck deck, Color currentColor) {
        while (true) {
            Card card = deck.takeCard();
            hand.add(card);
            if (card.getColor() == currentColor) {
                break;
            }
        }
        sort();
    }
    
    // Calculates the total number of points the hand is worth
    public int calculatedValue() {
        int totalValue = 0;
        for (Card card: hand) {
            totalValue += card.getScore();
        }
        return(totalValue);
    }
    
    // Sorts the cards in hand in ascending order
    public void sort() {
        Collections.sort(hand);
    }
    
    // Returns the hand instance variable
    public ArrayList<Card> getHand() {
        return(hand);
    }
    
    // Removes card from hand and returns it
    public Card removeCard(Card card) {
        return(hand.remove(hand.indexOf(card)));
    }
    
    // Returns true if hand is empty
    public boolean isOutOfCards() {
        return(hand.isEmpty());
    }
    
    // Gets the number of cards in hand
    public int getNumberOfCards() {
        return(hand.size());
    }
    
    // Returns a String representation of a Card
    public String toString() {
        String result = "";
        for (Card card: hand) {
            result += card+"\n";
        }
        return(result);
    }
    
}
