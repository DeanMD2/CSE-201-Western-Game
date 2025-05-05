
import java.util.List;

/**
 * Class: EvaluatedHand
 * Author: Dean DiCarlo
 * @version 1.0
 * Course: CSE 201 Spring 2025
 *
 * Purpose: Encapsulates the result of evaluating a poker hand,
 *          including its HandValue (e.g., flush, straight) and the list
 *          of cards that form the best 5-card hand, and enables comparison
 *          between two evaluated hands.
 */
public class EvaluatedHand implements Comparable<EvaluatedHand> {

    private final HandValue handValue;
    private final List<Card> bestHand;

    /**
     * Constructs an EvaluatedHand with the given hand value and best card combination.
     *
     * @param handValue the classification and rank ordering of the hand
     * @param bestHand  the list of cards that make up the best 5‑card hand
     */
    public EvaluatedHand(HandValue handValue, List<Card> bestHand) {
        this.handValue = handValue;
        this.bestHand = bestHand;
    }

    /**
     * @return the HandValue representing the strength and type of this hand
     */
    public HandValue getHandValue() {
        return handValue;
    }

    /**
     * @return an unmodifiable list of the cards composing the best hand
     */
    public List<Card> getBestHand() {
        return bestHand;
    }

    /**
     * Compare this EvaluatedHand to another based solely on their HandValue.
     *
     * @param other the other EvaluatedHand to compare against
     * @return negative if this is weaker, zero if equal strength, positive if stronger
     */
    @Override
    public int compareTo(EvaluatedHand other) {
        return this.handValue.compareTo(other.getHandValue());
    }

    /**
     * @return a user-friendly string summarizing the evaluation,
     *         e.g. "Best Hand: [10♦, J♣, Q♠, K♥, A♦]"
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Best Hand: ");
        for (Card card : bestHand) {
            sb.append(card.toString()).append(" ");
        }
        return sb.toString().trim();
    }
}
