import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Evaluates poker hands.
 */
public class HandEvaluator {

    /**
     * Evaluates the best 5-card hand from 7 cards.
     *
     * @param sevenCards a list of 7 cards.
     * @return the best evaluated hand.
     */
    public static EvaluatedHand evaluateBestHand(List<Card> sevenCards) {
        List<List<Card>> combinations = new ArrayList<>();
        combine(sevenCards, 0, new ArrayList<>(), combinations);

        EvaluatedHand bestEvaluatedHand = null;
        for (List<Card> hand : combinations) {
            HandValue hv = evaluate5CardHand(hand);
            EvaluatedHand current = new EvaluatedHand(hv, hand);
            if (bestEvaluatedHand == null || current.compareTo(bestEvaluatedHand) > 0) {
                bestEvaluatedHand = current;
            }
        }
        return bestEvaluatedHand;
    }

    /**
     * Recursively generates all 5-card combinations.
     *
     * @param cards the full card list.
     * @param start starting index.
     * @param current current combination.
     * @param combinations all combinations.
     */
    private static void combine(List<Card> cards, int start, List<Card> current, List<List<Card>> combinations) {
        if (current.size() == 5) {
            combinations.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i < cards.size(); i++) {
            current.add(cards.get(i));
            combine(cards, i + 1, current, combinations);
            current.remove(current.size() - 1);
        }
    }

    /**
     * Evaluates a 5-card hand.
     *
     * @param hand a list of 5 cards.
     * @return a HandValue representing the hand's strength.
     */
    public static HandValue evaluate5CardHand(List<Card> hand) {
        // Sort hand descending by rank.
        Collections.sort(hand, (c1, c2) -> c2.getRank().getValue() - c1.getRank().getValue());

        // Get rank values.
        List<Integer> rankValues = new ArrayList<>();
        for (Card card : hand) {
            rankValues.add(card.getRank().getValue());
        }

        // Check flush.
        boolean flush = true;
        Card.Suit suit = hand.get(0).getSuit();
        for (Card card : hand) {
            if (card.getSuit() != suit) {
                flush = false;
                break;
            }
        }

        // Check straight.
        boolean straight = false;
        int highStraightCard = 0;
        if (isConsecutive(rankValues)) {
            straight = true;
            highStraightCard = rankValues.get(0);
        } else if (rankValues.get(0) == 14) { // Ace-low straight
            List<Integer> altRanks = new ArrayList<>();
            for (int r : rankValues) {
                altRanks.add(r == 14 ? 1 : r);
            }
            Collections.sort(altRanks, Collections.reverseOrder());
            if (isConsecutive(altRanks)) {
                straight = true;
                highStraightCard = altRanks.get(0);
            }
        }

        // Frequency count.
        Map<Integer, Integer> freq = new HashMap<>();
        for (int r : rankValues) {
            freq.put(r, freq.getOrDefault(r, 0) + 1);
        }

        // Sorted frequency entries.
        List<Map.Entry<Integer, Integer>> freqList = new ArrayList<>(freq.entrySet());
        Collections.sort(freqList, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> e1, Map.Entry<Integer, Integer> e2) {
                if (e1.getValue().equals(e2.getValue())) {
                    return e2.getKey() - e1.getKey();
                } else {
                    return e2.getValue() - e1.getValue();
                }
            }
        });

        int category;
        List<Integer> tiebreakers = new ArrayList<>();

        // Determine hand category.
        if (straight && flush) {
            category = 8; // Straight flush
            tiebreakers.add(highStraightCard);
        } else if (freqList.get(0).getValue() == 4) {
            category = 7; // Four of a kind
            tiebreakers.add(freqList.get(0).getKey());
            for (int r : rankValues) {
                if (r != freqList.get(0).getKey()) {
                    tiebreakers.add(r);
                    break;
                }
            }
        } else if (freqList.get(0).getValue() == 3 && freqList.size() > 1 && freqList.get(1).getValue() >= 2) {
            category = 6; // Full house
            tiebreakers.add(freqList.get(0).getKey());
            tiebreakers.add(freqList.get(1).getKey());
        } else if (flush) {
            category = 5; // Flush
            tiebreakers.addAll(rankValues);
        } else if (straight) {
            category = 4; // Straight
            tiebreakers.add(highStraightCard);
        } else if (freqList.get(0).getValue() == 3) {
            category = 3; // Three of a kind
            tiebreakers.add(freqList.get(0).getKey());
            for (int r : rankValues) {
                if (r != freqList.get(0).getKey()) {
                    tiebreakers.add(r);
                }
            }
        } else if (freqList.get(0).getValue() == 2 && freqList.size() >= 2 && freqList.get(1).getValue() == 2) {
            category = 2; // Two pair
            tiebreakers.add(freqList.get(0).getKey());
            tiebreakers.add(freqList.get(1).getKey());
            for (int r : rankValues) {
                if (r != freqList.get(0).getKey() && r != freqList.get(1).getKey()) {
                    tiebreakers.add(r);
                    break;
                }
            }
        } else if (freqList.get(0).getValue() == 2) {
            category = 1; // One pair
            tiebreakers.add(freqList.get(0).getKey());
            for (int r : rankValues) {
                if (r != freqList.get(0).getKey()) {
                    tiebreakers.add(r);
                }
            }
        } else {
            category = 0; // High card
            tiebreakers.addAll(rankValues);
        }

        return new HandValue(category, tiebreakers);
    }

    /**
     * Checks if the list of numbers is consecutive.
     *
     * @param nums list of integers.
     * @return true if consecutive.
     */
    private static boolean isConsecutive(List<Integer> nums) {
        if (nums.size() != 5) return false;
        for (int i = 0; i < nums.size() - 1; i++) {
            if (nums.get(i) - nums.get(i + 1) != 1) {
                return false;
            }
        }
        return true;
    }
}
