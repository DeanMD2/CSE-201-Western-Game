// Card.java

/**
 * Class: Card
 * Author: Dean DiCarlo
 * @version 1.0
 * Course: CSE 201 Spring 2025
 *
 * Purpose: Represents a single playing card with a suit and a rank.
 * Provides comparison for sorting, and a human‑readable string format.
 */
public class Card implements Comparable<Card> {

    /**
     * Enumeration of the four suits in a standard deck.
     */
    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES
    }

    /**
     * Enumeration of the thirteen ranks in a standard deck, each with
     * an associated display value and numeric strength.
     */
    public enum Rank {
        TWO   ("2",  2),
        THREE ("3",  3),
        FOUR  ("4",  4),
        FIVE  ("5",  5),
        SIX   ("6",  6),
        SEVEN ("7",  7),
        EIGHT ("8",  8),
        NINE  ("9",  9),
        TEN   ("10", 10),
        JACK  ("J",  11),
        QUEEN ("Q",  12),
        KING  ("K",  13),
        ACE   ("A",  14);

        private final String symbol;
        private final int value;

        /**
         * Construct a Rank enum constant.
         *
         * @param symbol the human‑readable symbol for this rank
         * @param value  the numeric strength of this rank
         */
        Rank(String symbol, int value) {
            this.symbol = symbol;
            this.value = value;
        }

        /**
         * @return the symbol used to display this rank
         */
        public String getSymbol() {
            return symbol;
        }

        /**
         * @return the numeric value of this rank
         */
        public int getValue() {
            return value;
        }
    }

    private final Rank rank;
    private final Suit suit;

    /**
     * Constructs a new Card with the specified rank and suit.
     *
     * @param rank the rank of this card
     * @param suit the suit of this card
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * @return the rank of this card
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * @return the suit of this card
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Compare this card to another based on rank value, then suit order.
     * Enables sorting of cards.
     *
     * @param other the card to compare against
     * @return a negative integer, zero, or positive integer as this card
     *         is less than, equal to, or greater than the other card
     */
    @Override
    public int compareTo(Card other) {
        int diff = Integer.compare(this.rank.getValue(), other.rank.getValue());
        if (diff != 0) {
            return diff;
        }
        return this.suit.compareTo(other.suit);
    }

    /**
     * @return a string representation of the card, e.g. "K♠" or "10♥"
     */
    @Override
    public String toString() {
        String suitSymbol;
        switch (suit) {
            case CLUBS:    suitSymbol = "♣"; break;
            case DIAMONDS: suitSymbol = "♦"; break;
            case HEARTS:   suitSymbol = "♥"; break;
            case SPADES:   suitSymbol = "♠"; break;
            default:       suitSymbol = "?";  break;
        }
        return rank.getSymbol() + suitSymbol;
    }

    /**
     * Two cards are equal if they have the same rank and suit.
     *
     * @param obj the object to compare
     * @return true if obj is a Card with identical rank and suit
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Card)) return false;
        Card that = (Card) obj;
        return this.rank == that.rank && this.suit == that.suit;
    }

    /**
     * @return a hash code consistent with equals(), combining rank and suit
     */
    @Override
    public int hashCode() {
        return rank.hashCode() * 31 + suit.hashCode();
    }
}
