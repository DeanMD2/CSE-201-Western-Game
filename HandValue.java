import java.util.List;

/**
 * Represents a hand's value for comparison.
 */
public class HandValue implements Comparable<HandValue> {
    private int category; // Hand category.
    private List<Integer> tiebreakers; // Tie-breaker values.

    /**
     * Constructs a HandValue.
     *
     * @param category hand category.
     * @param tiebreakers tie-breaker values.
     */
    public HandValue(int category, List<Integer> tiebreakers) {
        this.category = category;
        this.tiebreakers = tiebreakers;
    }

    /**
     * Compares hand values.
     *
     * @param other the other HandValue.
     * @return negative, zero, or positive.
     */
    @Override
    public int compareTo(HandValue other) {
        if (this.category != other.category) {
            return this.category - other.category;
        }
        for (int i = 0; i < Math.min(this.tiebreakers.size(), other.tiebreakers.size()); i++) {
            if (!this.tiebreakers.get(i).equals(other.tiebreakers.get(i))) {
                return this.tiebreakers.get(i) - other.tiebreakers.get(i);
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Category: " + category + ", Tiebreakers: " + tiebreakers;
    }
}
