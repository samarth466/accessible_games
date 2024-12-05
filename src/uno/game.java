package uno;

// Import Java modules
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

// Class header
public class Game extends JPanel {
    
    // Initialize instance variables
    private String playerName;
    private int thresholdScore;
    private ArrayList<Integer> scores;
    private int numPlayers;
    private ArrayList<Person> allPlayers;
    private Deck deck;
    private Deck discardPile;
    
    // Constructor
    public Game(int nP, String pN, int tS) {
        numPlayers = nP;
        playerName = pN;
        thresholdScore = tS;
        scores = new ArrayList<Integer>(numPlayers);
        setInitialGameState();
        System.out.println("Game created with " + numPlayers + " players.");
        //startGame();
    }

    // Sets the background
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(java.awt.Color.decode("#efefef"));
    }
    
    // Initializes instance variables that must be re-initialized at the beginning of each round
    private void setInitialGameState() {
        deck = new Deck(true);
        discardPile = new Deck();
        Player player = new Player(playerName);
        ArrayList<ComputerName> computerNames = new ArrayList<ComputerName>();
        ArrayList<Computer> computers = new ArrayList<Computer>();
        for (int i=0; i<numPlayers-1; i++) {
        int randomIndex = (int)(Math.random()*(ComputerName.values().length-1));
            if (!computerNames.contains(ComputerName.values()[randomIndex])) {
                computerNames.add(ComputerName.values()[randomIndex]);
                computers.add(new Computer(ComputerName.values()[randomIndex].computerName()));
            }
        }
        allPlayers = new ArrayList<Person>(computers);
        allPlayers.add(player);
    }
    
    // Handles the logic for a single game
    public void startGame() {
        while (true) {
            Person winningPlayer = startRound();
            int totalScore = 0;
            for (Person person: allPlayers) {
                if (person != winningPlayer) {
                    System.out.println(person + " gives " + person.getHand().calculatedValue() + " points to the winner.");
                    totalScore += person.getHand().calculatedValue();
                }
            }
            System.out.println(winningPlayer + " wins the round with " + totalScore + " points for a total score of " + scores.get(allPlayers.indexOf(winningPlayer)) + " points.");
            scores.set(allPlayers.indexOf(winningPlayer),scores.get(allPlayers.indexOf(winningPlayer))+totalScore);
            if (scores.get(allPlayers.indexOf(winningPlayer)) >= thresholdScore) {
                System.out.println(winningPlayer + " won the game with " + scores.get(allPlayers.indexOf(winningPlayer)));
                break;
            }
            setInitialGameState();
        }
    }
    
    // Handles the logic for a single round and returns the winning player
    private Person startRound() {
        randomizePlayerOrder();
        System.out.println(allPlayers.get(1) + " goes first.");
        System.out.println("Shuffling the deck.");
        deck.shuffle();
        boolean light = true;
        for (Person person: allPlayers) {
            System.out.println("Dealing " + person + "'s cards");
            person.initHand(deck);
        }
        int playerIndex = 0;
        Color currentColor;
        boolean checkedCard = false;
        while (true) {
            Card card = deck.takeCard();
            if (card.getCardValue().cardName().contains("Wild")) {
                deck.add(card);
            } else {
                discardPile.add(card);
                currentColor = card.getColor();
                System.out.println("The top card is: \n" + card);
                break;
            }
        }
        while (true) {
            playerIndex++;
            if (playerIndex < 0) {
                playerIndex = allPlayers.size()-1;
            } else if (playerIndex >= allPlayers.size()) {
                playerIndex = 0;
            }
            Person person = allPlayers.get(playerIndex);
            Card currentCard = discardPile.getTopCard();
            if (!checkedCard) {
                playerIndex = checkCard(currentCard,playerIndex, person, currentColor,light,allPlayers);
                light = isPlayingLightSide();
                checkedCard = true;
                continue;
            }
            currentColor = person.chooseCard(currentCard, deck, discardPile, currentColor, light, this);
            if (person instanceof Player) {
                ((Player) person).printHand(this);
            }
            currentColor = person.chooseCard(currentCard, deck, discardPile, currentColor, light, this);
            if (currentCard != currentCard) {
                System.out.println(person + " played " + discardPile.getCards().get(discardPile.size()-1));
                if (discardPile.getCards().get(discardPile.size()-1).getCardValue() == NumberOrAction.WILD || discardPile.getCards().get(discardPile.size()-1).getCardValue() == NumberOrAction.WILD_DRAW_TWO || discardPile.getCards().get(discardPile.size()-1).getCardValue() == NumberOrAction.WILD_DRAW_COLOR.WILD_DRAW_COLOR) {
                    System.out.println("The wild color is " + currentColor);
                }
            }
            checkedCard = false;
            if (deck.isOutOfCards()) {
                System.out.println("Out of cards! Reshuffling deck.");
                Card card = discardPile.takeCard();
                deck = new Deck(discardPile.getCards());
                discardPile = new Deck();
                deck.shuffle();
            }
            if (person.isEmptyHand()) {
                System.out.println(person + " is out of cards. Person won the round.");
                return(person);
            }
        }
    }
    
    // Randomizes the playing order
    private void randomizePlayerOrder() {
        ArrayList<Person> newOrder = new ArrayList<Person>();
        while (!allPlayers.isEmpty()) {
            int randomIndex = (int)(Math.random()*(allPlayers.size()-1));
            newOrder.add(allPlayers.get(randomIndex));
            allPlayers.remove(randomIndex);
        }
        allPlayers = newOrder;
    }
    
    private int checkCard(Card currentCard, int playerIndex, Person currentPerson, Color currentColor, boolean light, ArrayList<Person> allPlayers) {
        System.out.println("Checking the card!");
        if (currentCard.getCardValue() == NumberOrAction.REVERSE) {
            playerIndex -= 2;
        } else if (currentCard.getCardValue() == NumberOrAction.DRAW_ONE) {
            currentPerson.getHand().takeCardFromDeck(deck);
            playerIndex++;
        } else if (currentCard.getCardValue() == NumberOrAction.WILD_DRAW_TWO) {
            for (int i=0; i<2; i++) {
                currentPerson.getHand().takeCardFromDeck(deck);
            }
            playerIndex++;
        } else if (currentCard.getCardValue() == NumberOrAction.DRAW_FIVE) {
            for (int i = 0; i<5; i++) {
                currentPerson.getHand().takeCardFromDeck(deck);
            }
            playerIndex++;
        } else if (currentCard.getCardValue() == NumberOrAction.WILD_DRAW_COLOR) {
            currentPerson.getHand().takeCardFromDeckUntilColor(deck,currentColor);
            playerIndex++;
        } else if (currentCard.getCardValue() == NumberOrAction.SKIP) {
            playerIndex++;
        } else if (currentCard.getCardValue() == NumberOrAction.SKIP_EVERYONE) {
            playerIndex--;
        } else if (currentCard.getCardValue() == NumberOrAction.FLIP) {
            deck.flipCards(light);
            discardPile.flipCards(light);
            for (Person person: allPlayers) {
                person.getHand().flip(light,deck);
            }
        }
        return(playerIndex);
    }
    
    private boolean isPlayingLightSide() {
        String side = deck.getSide();
        return(side == CardType.LIGHT.cardTypeName());
    }
    
}
