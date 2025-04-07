import java.util.List;

/**
 * Wraps a hand value and its best 5-card combination.
 */
public class EvaluatedHand implements Comparable<EvaluatedHand> {
    private HandValue handValue;
    private List<Card> bestHand; // The 5 cards making the hand.

    /**
     * Constructs an EvaluatedHand.
     *
     * @param handValue evaluated hand value.
     * @param bestHand list of 5 cards.
     */
    public EvaluatedHand(HandValue handValue, List<Card> bestHand) {
        this.handValue = handValue;
        this.bestHand = bestHand;
    }

    /**
     * @return the hand value.
     */
    public HandValue getHandValue() {
        return handValue;
    }

    /**
     * @return the best 5-card hand.
     */
    public List<Card> getBestHand() {
        return bestHand;
    }

    /**
     * Compares evaluated hands.
     *
     * @param other the other evaluated hand.
     * @return negative, zero, or positive.
     */
    @Override
    public int compareTo(EvaluatedHand other) {
        return this.handValue.compareTo(other.getHandValue());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Best Hand: ");
        for (Card card : bestHand) {
            sb.append(card.toString()).append(" ");
        }
        return sb.toString();
    }
}
