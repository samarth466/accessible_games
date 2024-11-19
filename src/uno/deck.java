package unoGameProject;

// Import Java modules
import java.util.Collections;
import java.util.Random;

// Import Java classes
import java.util.ArrayList;

// Class header
public class Deck {
	
	// Initialize instance variables
	private ArrayList<Card> deck;
	private static ArrayList<Card> lightCards;
	private static ArrayList<Card> darkCards;
	
	// Constructor
	public Deck() {
		getLightCards();
		getDarkCards();
		deck = new ArrayList<Card>();
	}
	
	// Constructor
	public Deck(boolean makeDeck) {
		getLightCards();
		getDarkCards();
		deck = new ArrayList<Card>();
		if (makeDeck) {
			deck = new ArrayList<Card>(lightCards);
		}
	}
	
	// Constructor
	public Deck(ArrayList<Card> cards) {
		getLightCards();
		getDarkCards();
		deck = new ArrayList<Card>(cards);
	}
	
	public String getSide() {
		return deck.get(0).getCardType().cardTypeName();
	}
	
	private static void getLightCards() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(Color.BLUE,NumberOrAction.ONE));
		cards.add(new Card(Color.BLUE,NumberOrAction.ONE));
		cards.add(new Card(Color.BLUE,NumberOrAction.TWO));
		cards.add(new Card(Color.BLUE,NumberOrAction.TWO));
		cards.add(new Card(Color.BLUE,NumberOrAction.THREE));
		cards.add(new Card(Color.BLUE,NumberOrAction.THREE));
		cards.add(new Card(Color.BLUE,NumberOrAction.FOUR));
		cards.add(new Card(Color.BLUE,NumberOrAction.FIVE));
		cards.add(new Card(Color.BLUE,NumberOrAction.FIVE));
		cards.add(new Card(Color.BLUE,NumberOrAction.SIX));
		cards.add(new Card(Color.BLUE,NumberOrAction.SIX));
		cards.add(new Card(Color.BLUE,NumberOrAction.SEVEN));
		cards.add(new Card(Color.BLUE,NumberOrAction.SEVEN));
		cards.add(new Card(Color.BLUE,NumberOrAction.EIGHT));
		cards.add(new Card(Color.BLUE,NumberOrAction.EIGHT));
		cards.add(new Card(Color.BLUE,NumberOrAction.NINE));
		cards.add(new Card(Color.BLUE,NumberOrAction.NINE));
		cards.add(new Card(Color.BLUE,NumberOrAction.DRAW_ONE));
		cards.add(new Card(Color.BLUE,NumberOrAction.DRAW_ONE));
		cards.add(new Card(Color.BLUE,NumberOrAction.FLIP));
		cards.add(new Card(Color.BLUE,NumberOrAction.FLIP));
		cards.add(new Card(Color.BLUE,NumberOrAction.REVERSE));
		cards.add(new Card(Color.BLUE,NumberOrAction.REVERSE));
		cards.add(new Card(Color.BLUE,NumberOrAction.SKIP));
		cards.add(new Card(Color.BLUE,NumberOrAction.SKIP));
		cards.add(new Card(Color.GREEN,NumberOrAction.ONE));
		cards.add(new Card(Color.GREEN,NumberOrAction.ONE));
		cards.add(new Card(Color.GREEN,NumberOrAction.TWO));
		cards.add(new Card(Color.GREEN,NumberOrAction.TWO));
		cards.add(new Card(Color.GREEN,NumberOrAction.THREE));
		cards.add(new Card(Color.GREEN,NumberOrAction.THREE));
		cards.add(new Card(Color.GREEN,NumberOrAction.FOUR));
		cards.add(new Card(Color.GREEN,NumberOrAction.FIVE));
		cards.add(new Card(Color.GREEN,NumberOrAction.FIVE));
		cards.add(new Card(Color.GREEN,NumberOrAction.SIX));
		cards.add(new Card(Color.GREEN,NumberOrAction.SIX));
		cards.add(new Card(Color.GREEN,NumberOrAction.SEVEN));
		cards.add(new Card(Color.GREEN,NumberOrAction.SEVEN));
		cards.add(new Card(Color.GREEN,NumberOrAction.EIGHT));
		cards.add(new Card(Color.GREEN,NumberOrAction.EIGHT));
		cards.add(new Card(Color.GREEN,NumberOrAction.NINE));
		cards.add(new Card(Color.GREEN,NumberOrAction.NINE));
		cards.add(new Card(Color.GREEN,NumberOrAction.DRAW_ONE));
		cards.add(new Card(Color.GREEN,NumberOrAction.DRAW_ONE));
		cards.add(new Card(Color.GREEN,NumberOrAction.FLIP));
		cards.add(new Card(Color.GREEN,NumberOrAction.FLIP));
		cards.add(new Card(Color.GREEN,NumberOrAction.REVERSE));
		cards.add(new Card(Color.GREEN,NumberOrAction.REVERSE));
		cards.add(new Card(Color.GREEN,NumberOrAction.SKIP));
		cards.add(new Card(Color.GREEN,NumberOrAction.SKIP));
		cards.add(new Card(Color.RED,NumberOrAction.ONE));
		cards.add(new Card(Color.RED,NumberOrAction.ONE));
		cards.add(new Card(Color.RED,NumberOrAction.TWO));
		cards.add(new Card(Color.RED,NumberOrAction.TWO));
		cards.add(new Card(Color.RED,NumberOrAction.THREE));
		cards.add(new Card(Color.RED,NumberOrAction.THREE));
		cards.add(new Card(Color.RED,NumberOrAction.FOUR));
		cards.add(new Card(Color.RED,NumberOrAction.FIVE));
		cards.add(new Card(Color.RED,NumberOrAction.FIVE));
		cards.add(new Card(Color.RED,NumberOrAction.SIX));
		cards.add(new Card(Color.RED,NumberOrAction.SIX));
		cards.add(new Card(Color.RED,NumberOrAction.SEVEN));
		cards.add(new Card(Color.RED,NumberOrAction.SEVEN));
		cards.add(new Card(Color.RED,NumberOrAction.EIGHT));
		cards.add(new Card(Color.RED,NumberOrAction.EIGHT));
		cards.add(new Card(Color.RED,NumberOrAction.NINE));
		cards.add(new Card(Color.RED,NumberOrAction.NINE));
		cards.add(new Card(Color.RED,NumberOrAction.DRAW_ONE));
		cards.add(new Card(Color.RED,NumberOrAction.DRAW_ONE));
		cards.add(new Card(Color.RED,NumberOrAction.FLIP));
		cards.add(new Card(Color.RED,NumberOrAction.FLIP));
		cards.add(new Card(Color.RED,NumberOrAction.REVERSE));
		cards.add(new Card(Color.RED,NumberOrAction.REVERSE));
		cards.add(new Card(Color.RED,NumberOrAction.SKIP));
		cards.add(new Card(Color.RED,NumberOrAction.SKIP));
		cards.add(new Card(Color.YELLOW,NumberOrAction.ONE));
		cards.add(new Card(Color.YELLOW,NumberOrAction.ONE));
		cards.add(new Card(Color.YELLOW,NumberOrAction.TWO));
		cards.add(new Card(Color.YELLOW,NumberOrAction.TWO));
		cards.add(new Card(Color.YELLOW,NumberOrAction.THREE));
		cards.add(new Card(Color.YELLOW,NumberOrAction.THREE));
		cards.add(new Card(Color.YELLOW,NumberOrAction.FOUR));
		cards.add(new Card(Color.YELLOW,NumberOrAction.FIVE));
		cards.add(new Card(Color.YELLOW,NumberOrAction.FIVE));
		cards.add(new Card(Color.YELLOW,NumberOrAction.SIX));
		cards.add(new Card(Color.YELLOW,NumberOrAction.SIX));
		cards.add(new Card(Color.YELLOW,NumberOrAction.SEVEN));
		cards.add(new Card(Color.YELLOW,NumberOrAction.SEVEN));
		cards.add(new Card(Color.YELLOW,NumberOrAction.EIGHT));
		cards.add(new Card(Color.YELLOW,NumberOrAction.EIGHT));
		cards.add(new Card(Color.YELLOW,NumberOrAction.NINE));
		cards.add(new Card(Color.YELLOW,NumberOrAction.NINE));
		cards.add(new Card(Color.YELLOW,NumberOrAction.DRAW_ONE));
		cards.add(new Card(Color.YELLOW,NumberOrAction.DRAW_ONE));
		cards.add(new Card(Color.YELLOW,NumberOrAction.FLIP));
		cards.add(new Card(Color.YELLOW,NumberOrAction.FLIP));
		cards.add(new Card(Color.YELLOW,NumberOrAction.REVERSE));
		cards.add(new Card(Color.YELLOW,NumberOrAction.REVERSE));
		cards.add(new Card(Color.YELLOW,NumberOrAction.SKIP));
		cards.add(new Card(Color.YELLOW,NumberOrAction.SKIP));
		cards.add(new Card(Color.UNDEFINED,NumberOrAction.WILD,CardType.LIGHT));
		cards.add(new Card(Color.UNDEFINED,NumberOrAction.WILD,CardType.LIGHT));
		cards.add(new Card(Color.UNDEFINED,NumberOrAction.WILD,CardType.LIGHT));
		cards.add(new Card(Color.UNDEFINED,NumberOrAction.WILD,CardType.LIGHT));
		cards.add(new Card(Color.UNDEFINED,NumberOrAction.WILD_DRAW_TWO));
		cards.add(new Card(Color.UNDEFINED,NumberOrAction.WILD_DRAW_TWO));
		cards.add(new Card(Color.UNDEFINED,NumberOrAction.WILD_DRAW_TWO));
		cards.add(new Card(Color.UNDEFINED,NumberOrAction.WILD_DRAW_TWO));
		lightCards = new ArrayList<Card>(cards);
	}
	
	private static void getDarkCards() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(Color.PURPLE,NumberOrAction.SKIP_EVERYONE));
		cards.add(new Card(Color.PURPLE,NumberOrAction.SKIP_EVERYONE));
		cards.add(new Card(Color.PINK,NumberOrAction.SIX));
		cards.add(new Card(Color.PURPLE,NumberOrAction.EIGHT));
		cards.add(new Card(Color.TEAL,NumberOrAction.TWO));
		cards.add(new Card(Color.PURPLE,NumberOrAction.ONE));
		cards.add(new Card(Color.TEAL,NumberOrAction.DRAW_FIVE));
		cards.add(new Card(Color.PINK,NumberOrAction.NINE));
		cards.add(new Card(Color.ORANGE,NumberOrAction.REVERSE));
		cards.add(new Card(Color.PURPLE,NumberOrAction.REVERSE));
		cards.add(new Card(Color.TEAL,NumberOrAction.SKIP_EVERYONE));
		cards.add(new Card(Color.ORANGE,NumberOrAction.SKIP_EVERYONE));
		cards.add(new Card(Color.ORANGE,NumberOrAction.THREE));
		cards.add(new Card(Color.TEAL,NumberOrAction.FOUR));
		cards.add(new Card(Color.TEAL,NumberOrAction.REVERSE));
		cards.add(new Card(Color.PURPLE,NumberOrAction.FLIP));
		cards.add(new Card(Color.ORANGE,NumberOrAction.FIVE));
		cards.add(new Card(Color.PINK,NumberOrAction.SIX));
		cards.add(new Card(Color.TEAL,NumberOrAction.SIX));
		cards.add(new Card(Color.PURPLE,NumberOrAction.SIX));
		cards.add(new Card(Color.PURPLE,NumberOrAction.SEVEN));
		cards.add(new Card(Color.UNDEFINED,NumberOrAction.WILD,CardType.DARK));
		cards.add(new Card(Color.ORANGE,NumberOrAction.FOUR));
		cards.add(new Card(Color.TEAL,NumberOrAction.ONE));
		cards.add(new Card(Color.PINK,NumberOrAction.NINE));
		cards.add(new Card(Color.ORANGE,NumberOrAction.FIVE));
		cards.add(new Card(Color.ORANGE,NumberOrAction.FLIP));
		cards.add(new Card(Color.TEAL,NumberOrAction.SKIP_EVERYONE));
		cards.add(new Card(Color.TEAL,NumberOrAction.DRAW_FIVE));
		cards.add(new Card(Color.PINK,NumberOrAction.FLIP));
		cards.add(new Card(Color.PURPLE,NumberOrAction.TWO));
		cards.add(new Card(Color.PINK,NumberOrAction.EIGHT));
		cards.add(new Card(Color.TEAL,NumberOrAction.NINE));
		cards.add(new Card(Color.TEAL,NumberOrAction.FOUR));
		cards.add(new Card(Color.ORANGE,NumberOrAction.SEVEN));
		cards.add(new Card(Color.UNDEFINED,NumberOrAction.WILD_DRAW_COLOR));
		cards.add(new Card(Color.PINK,NumberOrAction.FIVE));
		cards.add(new Card(Color.TEAL,NumberOrAction.TWO));
		cards.add(new Card(Color.ORANGE,NumberOrAction.SIX));
		cards.add(new Card(Color.TEAL,NumberOrAction.NINE));
		cards.add(new Card(Color.PINK,NumberOrAction.REVERSE));
		cards.add(new Card(Color.ORANGE,NumberOrAction.DRAW_FIVE));
		cards.add(new Card(Color.PINK,NumberOrAction.REVERSE));
		cards.add(new Card(Color.TEAL,NumberOrAction.SIX));
		cards.add(new Card(Color.ORANGE,NumberOrAction.SIX));
		cards.add(new Card(Color.UNDEFINED,NumberOrAction.WILD_DRAW_COLOR));
		cards.add(new Card(Color.TEAL,NumberOrAction.THREE));
		cards.add(new Card(Color.ORANGE,NumberOrAction.ONE));
		cards.add(new Card(Color.PINK,NumberOrAction.SEVEN));
		cards.add(new Card(Color.ORANGE,NumberOrAction.NINE));
		cards.add(new Card(Color.PURPLE,NumberOrAction.SEVEN));
		cards.add(new Card(Color.PURPLE,NumberOrAction.TWO));
		cards.add(new Card(Color.PINK,NumberOrAction.THREE));
		cards.add(new Card(Color.PURPLE,NumberOrAction.DRAW_FIVE));
		cards.add(new Card(Color.ORANGE,NumberOrAction.REVERSE));
		cards.add(new Card(Color.UNDEFINED,NumberOrAction.WILD_DRAW_COLOR));
		cards.add(new Card(Color.PINK,NumberOrAction.SEVEN));
		cards.add(new Card(Color.ORANGE,NumberOrAction.FLIP));
		cards.add(new Card(Color.PURPLE,NumberOrAction.DRAW_FIVE));
		cards.add(new Card(Color.TEAL,NumberOrAction.FIVE));
		cards.add(new Card(Color.PINK,NumberOrAction.TWO));
		cards.add(new Card(Color.ORANGE,NumberOrAction.NINE));
		cards.add(new Card(Color.PURPLE,NumberOrAction.FIVE));
		cards.add(new Card(Color.PINK,NumberOrAction.SKIP_EVERYONE));
		cards.add(new Card(Color.ORANGE,NumberOrAction.ONE));
		cards.add(new Card(Color.PURPLE,NumberOrAction.REVERSE));
		cards.add(new Card(Color.TEAL,NumberOrAction.SEVEN));
		cards.add(new Card(Color.TEAL,NumberOrAction.REVERSE));
		cards.add(new Card(Color.PURPLE,NumberOrAction.FIVE));
		cards.add(new Card(Color.PINK,NumberOrAction.THREE));
		cards.add(new Card(Color.PINK,NumberOrAction.FOUR));
		cards.add(new Card(Color.PURPLE,NumberOrAction.THREE));
		cards.add(new Card(Color.PINK,NumberOrAction.EIGHT));
		cards.add(new Card(Color.TEAL,NumberOrAction.SEVEN));
		cards.add(new Card(Color.PURPLE,NumberOrAction.THREE));
		cards.add(new Card(Color.ORANGE,NumberOrAction.DRAW_FIVE));
		cards.add(new Card(Color.UNDEFINED,NumberOrAction.WILD,CardType.DARK));
		cards.add(new Card(Color.PINK,NumberOrAction.SKIP_EVERYONE));
		cards.add(new Card(Color.UNDEFINED,NumberOrAction.WILD,CardType.DARK));
		cards.add(new Card(Color.TEAL,NumberOrAction.ONE));
		cards.add(new Card(Color.TEAL,NumberOrAction.EIGHT));
		cards.add(new Card(Color.PINK,NumberOrAction.DRAW_FIVE));
		cards.add(new Card(Color.PURPLE,NumberOrAction.ONE));
		cards.add(new Card(Color.PURPLE,NumberOrAction.FLIP));
		cards.add(new Card(Color.PINK,NumberOrAction.DRAW_FIVE));
		cards.add(new Card(Color.PURPLE,NumberOrAction.NINE));
		cards.add(new Card(Color.TEAL,NumberOrAction.EIGHT));
		cards.add(new Card(Color.UNDEFINED,NumberOrAction.WILD_DRAW_COLOR));
		cards.add(new Card(Color.ORANGE,NumberOrAction.SKIP_EVERYONE));
		cards.add(new Card(Color.ORANGE,NumberOrAction.TWO));
		cards.add(new Card(Color.PURPLE,NumberOrAction.SIX));
		cards.add(new Card(Color.ORANGE,NumberOrAction.TWO));
		cards.add(new Card(Color.PINK,NumberOrAction.ONE));
		cards.add(new Card(Color.PURPLE,NumberOrAction.FOUR));
		cards.add(new Card(Color.TEAL,NumberOrAction.FIVE));
		cards.add(new Card(Color.PINK,NumberOrAction.ONE));
		cards.add(new Card(Color.PURPLE,NumberOrAction.EIGHT));
		cards.add(new Card(Color.PINK,NumberOrAction.FOUR));
		cards.add(new Card(Color.ORANGE,NumberOrAction.EIGHT));
		cards.add(new Card(Color.TEAL,NumberOrAction.FLIP));
		cards.add(new Card(Color.UNDEFINED,NumberOrAction.WILD,CardType.DARK));
		cards.add(new Card(Color.ORANGE,NumberOrAction.THREE));
		cards.add(new Card(Color.TEAL,NumberOrAction.FLIP));
		cards.add(new Card(Color.PURPLE,NumberOrAction.SEVEN));
		cards.add(new Card(Color.PINK,NumberOrAction.FLIP));
		cards.add(new Card(Color.PINK,NumberOrAction.FIVE));
		cards.add(new Card(Color.TEAL,NumberOrAction.THREE));
		cards.add(new Card(Color.PURPLE,NumberOrAction.NINE));
		cards.add(new Card(Color.ORANGE,NumberOrAction.FOUR));
		cards.add(new Card(Color.PINK,NumberOrAction.TWO));
		cards.add(new Card(Color.ORANGE,NumberOrAction.SEVEN));
		darkCards = new ArrayList<Card>(cards);
	}
	
	// Flip the deck
	public void flipCards(boolean light) {
		ArrayList<Card> flippedCards = new ArrayList<Card>();
		for (Card card: deck) {
			if (light) {
				flippedCards.add(darkCards.get(lightCards.indexOf(card)));
			} else {
				flippedCards.add(lightCards.get(darkCards.indexOf(card)));
			}
		}
		deck = new ArrayList<Card>(flippedCards);
	}
	
	// Returns a list cards that contain cards on the other side of the cards in cards
	public ArrayList<Card> flipCards(boolean light, ArrayList<Card> cards) {
		ArrayList<Card> flippedCards = new ArrayList<Card>();
		for (Card card: cards) {
			if (light) {
				flippedCards.add(darkCards.get(lightCards.indexOf(card)));
			} else {
				flippedCards.add(lightCards.get(darkCards.indexOf(card)));
			}
		}
		return(flippedCards);
	}
	
	// Return the cards in the deck
	public ArrayList<Card> getCards() {
		return(deck);
	}
	
	public void add(Card card) {
		deck.add(card);
	}
	
	// Shuffles the deck
	public void shuffle() {
		Collections.shuffle(deck,new Random());
	}
	
	// Takes a Card from the deck and returns it
	public Card takeCard() {
		Card cardToTake = new Card(deck.get(0));
		deck.remove(0);
		return(cardToTake);
	}
	
	// Returns the number of cards in deck
	public int size() {
		return(deck.size());
	}
	
	// Returns true if deck is empty
	public boolean isOutOfCards() {
		return(deck.isEmpty());
	}
	
	public String toString() {
		String result = "";
		for (Card card: deck) {
			result += card+"\n";
		}
		return(result);
	}
	
}
