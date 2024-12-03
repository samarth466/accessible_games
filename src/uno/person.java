package uno;

// Class header
public abstract class Person {
    
    // Initialize instance variables
    private Hand hand;
    private String name;
    
    public Person(String n) {
        name = n;
        hand = new Hand();
    }
    
    // Initialize person's hand with 7 cards
    public void initHand(Deck deck) {
        for (int i=0; i<7; i++) {
            hand.takeCardFromDeck(deck);
        }
        hand.sort();
    }
    
    // Getters
    
    // Gets the person's hand
    public Hand getHand() {
        return(hand);
    }
    
    // gets the person's name
    public String getName() {
        return(name);
    }
    
    public abstract Color chooseCard(Card topCard, Deck deck, Deck discardPile, Color currentColor, boolean light, Game game);
    
    // Returns true if hand is empty
    public boolean isEmptyHand() {
        return(hand.isOutOfCards());
    }
    
    public String toString() {
        return(name);
    }
    
}
