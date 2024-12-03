package uno;

// Import Java modules
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Class header
public class Computer extends Person {
    
    // Constructor
    public Computer(String name) {
        super(name);
    }
    
    public ArrayList<Card> checkPlayableCards(Card top_card, Color current_color) {
        ArrayList<Card> playableCards = new ArrayList<Card>();
        for (Card card: super.getHand().getHand()) {
            if (card.getCardValue().cardName() == "Wild" || card.getCardValue().cardName() == "Wild Draw Two" || card.getCardValue().cardName() == "Wild Draw Color") {
                playableCards.add(card);
            } else if (top_card.getColor() == Color.UNDEFINED) {
                if (card.getColor() == current_color) {
                    playableCards.add(card);
                }
            } else if (card.getColor() == top_card.getColor()) {
                playableCards.add(card);
            } else if (card.getCardValue().cardName() == top_card.getCardValue().cardName()) {
                playableCards.add(card);
            }
        }
        return(playableCards);
    }

    // Picks a card for the computer
    @Override
    public Color chooseCard(Card topCard, Deck deck, Deck discardPile, Color currentColor, boolean light, Game game) {
        ArrayList<Card> playableCards = checkPlayableCards(topCard, currentColor);
        if (playableCards.isEmpty()) {
            super.getHand().takeCardFromDeck(deck);
            System.out.println(super.getName() + " drew a card. " + super.getName() + " has " + super.getHand().getHand().size() + " cards.");
            return(Color.UNDEFINED);
        } else {
            int randomIndex = (int)(Math.random()*(playableCards.size()-1));
            Card cardToPlay = playableCards.get(randomIndex);
            super.getHand().removeCard(cardToPlay);
            discardPile.add(cardToPlay);
            if (cardToPlay.getColor() == Color.UNDEFINED) {
                return(chooseColor());
            } else {
                return(cardToPlay.getColor());
            }
        }
    }
    
    public Color chooseColor() {
        HashMap<Color,Integer> frequencyMap = new HashMap<Color,Integer>(); // Stores the colors of the cards in the computer's hand and how many of the computer's cards are of that color
        for (Card card: super.getHand().getHand()) {
            frequencyMap.put(card.getColor(),frequencyMap.getOrDefault(card.getColor(),0)+1); // Increments the frequency of any given color each time it is encountered in computer's hand
        }
        Color mostFrequentColor = null;
        int maxCount = 0;
        for (Map.Entry<Color,Integer> entry: frequencyMap.entrySet()) {
            if (entry.getValue()>maxCount) {
                maxCount = entry.getValue();
                mostFrequentColor = entry.getKey();
            }
        }
        return(mostFrequentColor);
    }
    
}
