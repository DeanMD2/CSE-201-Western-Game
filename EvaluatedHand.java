import java.util.List;

public class EvaluatedHand implements Comparable<EvaluatedHand> {
    private HandValue handValue;
    private List<Card> bestHand; // The 5 cards that make up the evaluated hand

    public EvaluatedHand(HandValue handValue, List<Card> bestHand) {
        this.handValue = handValue;
        this.bestHand = bestHand;
    }

    public HandValue getHandValue() {
        return handValue;
    }

    public List<Card> getBestHand() {
        return bestHand;
    }

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
