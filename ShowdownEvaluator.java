/**
 * Class: ShowdownEvaluator
 * Author: Dean DiCarlo
 * @version 1.0
 * Course: CSE 201
 *
 * Purpose: Determines the winner at showdown by evaluating each non-folded
 *          player's combined hand of hole cards and community cards, and
 *          comparing their best 5-card hands.
 */
import java.util.ArrayList;
import java.util.List;

public class ShowdownEvaluator {

    /**
     * Evaluates all active players' 7-card hands, prints each player's best hand,
     * and returns the player with the highest ranking hand.
     *
     * @param players   list of all PokerPlayer objects (some may be folded)
     * @param community list of community Card objects on the board
     * @return the PokerPlayer whose hand outranks all others, or null if none
     */
    public PokerPlayer evaluateWinner(List<PokerPlayer> players, List<Card> community) {
        EvaluatedHand best = null;
        PokerPlayer winner = null;

        for (PokerPlayer p : players) {
            if (!p.isFolded()) {
                // Combine hole cards and community cards
                List<Card> seven = new ArrayList<>(p.getHand());
                seven.addAll(community);

                // Evaluate the best 5-card hand from seven cards
                EvaluatedHand eh = HandEvaluator.evaluateBestHand(seven);
                System.out.println(p.getName() + "'s best hand: " + eh);

                // Compare to current best
                if (best == null || eh.compareTo(best) > 0) {
                    best = eh;
                    winner = p;
                }
            }
        }
        return winner;
    }
}