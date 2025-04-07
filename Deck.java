import java.util.Random;

/**
 * Represents a standard deck of 52 cards.
 */
public class Deck {
    private static final int DECK_SIZE = 52;
    private Card[] theDeck;
    private int currCardIndex;

    /**
     * Constructs a deck with 52 cards.
     */
    public Deck() {
        Card.Suit[] suits = Card.Suit.values();
        Card.Rank[] ranks = Card.Rank.values();
        theDeck = new Card[DECK_SIZE];
        int index = 0;
        for (Card.Suit suit : suits) {
            for (Card.Rank rank : ranks) {
                theDeck[index++] = new Card(rank, suit);
            }
        }
        currCardIndex = 0;
    }

    /**
     * Shuffles the deck.
     */
    public void shuffle() {
        Random rand = new Random();
        for (int i = theDeck.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            Card temp = theDeck[i];
            theDeck[i] = theDeck[j];
            theDeck[j] = temp;
        }
        currCardIndex = 0;
    }

    /**
     * Deals the next card.
     *
     * @return the next card or null if none left.
     */
    public Card dealCard() {
        if (currCardIndex < DECK_SIZE) {
            return theDeck[currCardIndex++];
        }
        return null;
    }

    /**
     * Prints the deck.
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
