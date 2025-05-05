import java.util.ArrayList;
import java.util.List;

/**
 * Class: TheFelt
 * Author: Dean DiCarlo
 * @version 1.0
 * Course: CSE 201
 *
 * Purpose: Manages the community (“board”) cards during a poker hand,
 *          allowing addition, display, retrieval, and clearing of cards.
 */
public class TheFelt {
    private List<Card> communityCards;

    /**
     * Constructs an empty felt with no community cards.
     */
    public TheFelt() {
        communityCards = new ArrayList<>();
    }

    /**
     * Adds a card to the community board.
     *
     * @param card the Card to add to the community
     */
    public void addCard(Card card) {
        communityCards.add(card);
    }

    /**
     * Prints the community cards to the console in a single line.
     */
    public void printBoard() {
        System.out.print("Community Cards: ");
        for (Card card : communityCards) {
            System.out.print(card + " ");
        }
        System.out.println();
    }

    /**
     * @return the list of community cards currently on the felt
     */
    public List<Card> getCommunityCards() {
        return communityCards;
    }

    /**
     * Removes all cards from the community board.
     */
    public void clear() {
        communityCards.clear();
    }
}