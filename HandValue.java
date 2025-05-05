/**
 * Class: HandValue
 * Author: Dean DiCarlo
 * @version 1.0
 * Course: CSE 201
 *
 * Purpose: Encapsulates the numeric category and tie-breaker values of a
 *          5-card poker hand, and provides comparison and utility methods
 *          for ranking and human-readable representation.
 */
import java.util.List;

public class HandValue implements Comparable<HandValue> {
    private int category;            // Hand category, 0=High Card ... 8=Straight Flush
    private List<Integer> tiebreakers; // Tie-breaker values for hands of equal category

    /**
     * Constructs a HandValue with the specified category and tie-breakers.
     *
     * @param category    numeric hand category (0=High Card ... 8=Straight Flush)
     * @param tiebreakers list of integer values to break ties within the same category
     */
    public HandValue(int category, List<Integer> tiebreakers) {
        this.category = category;
        this.tiebreakers = tiebreakers;
    }

    /**
     * Compares this HandValue with another for ordering.
     * First compares categories; if equal, compares tie-breaker lists element-wise.
     *
     * @param other the other HandValue to compare against
     * @return negative if this is weaker, zero if equal, positive if stronger
     */
    @Override
    public int compareTo(HandValue other) {
        if (this.category != other.category) {
            return this.category - other.category;
        }
        int size = Math.min(this.tiebreakers.size(), other.tiebreakers.size());
        for (int i = 0; i < size; i++) {
            int diff = this.tiebreakers.get(i) - other.tiebreakers.get(i);
            if (diff != 0) {
                return diff;
            }
        }
        return 0;
    }

    /**
     * @return the numeric category of the hand (0=High Card, 1=One Pair, 2=Two Pair,
     *         3=Three of a Kind, 4=Straight, 5=Flush, 6=Full House,
     *         7=Four of a Kind, 8=Straight Flush)
     */
    public int getCategory() {
        return category;
    }

    /**
     * Returns the human-readable name of the hand category.
     *
     * @return a String label corresponding to this hand category
     */
    public String categoryName() {
        switch (category) {
            case 8: return "Straight Flush";
            case 7: return "Four of a Kind";
            case 6: return "Full House";
            case 5: return "Flush";
            case 4: return "Straight";
            case 3: return "Three of a Kind";
            case 2: return "Two Pair";
            case 1: return "One Pair";
            default: return "High Card";
        }
    }

    /**
     * @return a string representation including category and tie-breakers
     */
    @Override
    public String toString() {
        return "Category: " + category + ", Tiebreakers: " + tiebreakers;
    }
}