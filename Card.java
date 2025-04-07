/**
 * Represents a playing card with a rank and suit.
 */
public class Card {
    /**
     * Suits with symbols.
     */
    public enum Suit {
        HEARTS("♥"), DIAMONDS("♦"), SPADES("♠"), CLUBS("♣");

        private final String symbol;

        Suit(String symbol) {
            this.symbol = symbol;
        }

        /**
         * @return the suit symbol.
         */
        public String getSymbol() {
            return symbol;
        }
    }

    /**
     * Ranks with display and value.
     */
    public enum Rank {
        TWO("2", 2), THREE("3", 3), FOUR("4", 4), FIVE("5", 5), SIX("6", 6),
        SEVEN("7", 7), EIGHT("8", 8), NINE("9", 9), TEN("10", 10),
        JACK("J", 11), QUEEN("Q", 12), KING("K", 13), ACE("A", 14);

        private String display;
        private final int value;

        Rank(String display, int value) {
            this.display = display;
            this.value = value;
        }

        /**
         * @return the display string.
         */
        public String getDisplay() {
            return display;
        }

        /**
         * @return the numeric value.
         */
        public int getValue() {
            return value;
        }
    }

    private final Suit suit;
    private final Rank rank;

    /**
     * Creates a card with the specified rank and suit.
     *
     * @param rank the card's rank.
     * @param suit the card's suit.
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * @return the card's rank.
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * @return the card's suit.
     */
    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank.getDisplay() + suit.getSymbol();
    }

    /**
     * Compares cards by rank value.
     *
     * @param other the card to compare.
     * @return negative, zero, or positive if this card is less, equal, or greater.
     */
    public int compareTo(Card other) {
        return Integer.compare(this.rank.getValue(), other.rank.getValue());
    }
}
