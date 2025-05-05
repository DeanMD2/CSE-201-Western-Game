import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class: HandEvaluator
 * Author: Dean DiCarlo
 * @version 1.0
 * Course: CSE 201
 *
 * Purpose: Provides methods to evaluate poker hands by generating all
 *          5-card combinations from seven cards and determining the
 *          highest-ranking hand based on standard poker rules.
 */
public class HandEvaluator {

    /**
     * Evaluates the best 5-card hand from a list of seven cards.
     *
     * @param sevenCards a list of seven Card objects to evaluate
     * @return the EvaluatedHand representing the highest-ranking 5-card hand
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
     * Recursively generates all possible 5-card combinations from the provided list.
     *
     * @param cards        the full list of Card objects
     * @param start        the index at which to begin selecting cards
     * @param current      the current combination being built
     * @param combinations the list that accumulates all 5-card combinations
     */
    private static void combine(List<Card> cards,
                                int start,
                                List<Card> current,
                                List<List<Card>> combinations) {
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
     * Evaluates a 5-card poker hand and returns its HandValue rating.
     * Implements flush, straight (including Ace-low), and frequency-based categories.
     *
     * @param hand a list of exactly five Card objects
     * @return a HandValue representing the hand's category and tiebreaker values
     */
    public static HandValue evaluate5CardHand(List<Card> hand) {
        // Sort hand descending by rank
        Collections.sort(hand, (c1, c2) -> c2.getRank().getValue() - c1.getRank().getValue());

        // Extract rank values
        List<Integer> rankValues = new ArrayList<>();
        for (Card card : hand) {
            rankValues.add(card.getRank().getValue());
        }

        // Check for flush
        boolean flush = true;
        Card.Suit suit = hand.get(0).getSuit();
        for (Card card : hand) {
            if (card.getSuit() != suit) {
                flush = false;
                break;
            }
        }

        // Check for straight and Ace-low straight
        boolean straight = false;
        int highStraightCard = 0;
        if (isConsecutive(rankValues)) {
            straight = true;
            highStraightCard = rankValues.get(0);
        } else if (rankValues.get(0) == 14) {
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

        // Count frequencies of each rank
        Map<Integer, Integer> freq = new HashMap<>();
        for (int r : rankValues) {
            freq.put(r, freq.getOrDefault(r, 0) + 1);
        }

        // Sort rank-frequency pairs by frequency and then by rank value
        List<Map.Entry<Integer, Integer>> freqList =
            new ArrayList<>(freq.entrySet());
        Collections.sort(freqList, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> e1,
                               Map.Entry<Integer, Integer> e2) {
                if (e1.getValue().equals(e2.getValue())) {
                    return e2.getKey() - e1.getKey();
                } else {
                    return e2.getValue() - e1.getValue();
                }
            }
        });

        int category;
        List<Integer> tiebreakers = new ArrayList<>();

        // Determine hand category and tiebreakers
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
        } else if (freqList.get(0).getValue() == 3 && freqList.size() > 1 &&
                   freqList.get(1).getValue() >= 2) {
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
        } else if (freqList.get(0).getValue() == 2 && freqList.size() >= 2 &&
                   freqList.get(1).getValue() == 2) {
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
     * Checks whether a list of five integers forms a consecutive sequence.
     *
     * @param nums list of integer values sorted in descending order
     * @return true if the five values are sequential, false otherwise
     */
    private static boolean isConsecutive(List<Integer> nums) {
        if (nums.size() != 5) {
            return false;
        }
        for (int i = 0; i < nums.size() - 1; i++) {
            if (nums.get(i) - nums.get(i + 1) != 1) {
                return false;
            }
        }
        return true;
    }
}