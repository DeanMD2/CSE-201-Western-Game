// Deck.java
import java.util.Random;

/**
 * Class: Deck
 * Author: Dean DiCarlo
 * @version 1.0
 * Course: CSE 201 Spring 2025
 *
 * Purpose: Represents a standard deck of 52 playing cards, providing methods
 * to construct, shuffle, deal, and display the deck contents.
 */
public class Deck {
    private static final int DECK_SIZE = 52;
    private Card[] theDeck;
    private int currCardIndex;

    /**
     * Constructs a new deck containing one card of each suit and rank,
     * in a standard order before any shuffle.
     */
    public Deck() {
        Card.Suit[] suits = Card.Suit.values();
        Card.Rank[] ranks = Card.Rank.values();
        theDeck = new Card[DECK_SIZE];
        currCardIndex = 0;

        for (Card.Suit s : suits) {
            for (Card.Rank r : ranks) {
                theDeck[currCardIndex++] = new Card(r, s);
            }
        }
        currCardIndex = 0;
    }

    /**
     * Randomly shuffles the deck so that subsequent dealCard() calls
     * return cards in a new, unpredictable order.
     */
    public void shuffle() {
        Random rand = new Random();
        for (int i = 0; i < theDeck.length; i++) {
            int j = rand.nextInt(DECK_SIZE);
            Card temp = theDeck[i];
            theDeck[i] = theDeck[j];
            theDeck[j] = temp;
        }
        currCardIndex = 0;
    }

    /**
     * Deal the next card from the deck.
     *
     * @return the next Card in the shuffled deck
     */
    public Card dealCard() {
        if (currCardIndex >= DECK_SIZE) {
            throw new IllegalStateException("No cards left in the deck");
        }
        return theDeck[currCardIndex++];
    }

    /**
     * Print out all 52 cards in their current order to the console,
     * 13 cards per line.
     */
    public void printDeck() {
        for (int i = 0; i < theDeck.length; i++) {
            System.out.print(theDeck[i] + " ");
            if ((i + 1) % 13 == 0) {
                System.out.println();
            }
        }
    }
}
