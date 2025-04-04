import java.util.List;

public class HandValue implements Comparable<HandValue> {
    private int category; // Higher means a stronger hand.
    private List<Integer> tiebreakers; // Used to break ties within the same category.

    public HandValue(int category, List<Integer> tiebreakers) {
        this.category = category;
        this.tiebreakers = tiebreakers;
    }

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
