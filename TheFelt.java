import java.util.ArrayList;
import java.util.List;

/**
 * Manages the community cards on the felt.
 */
public class TheFelt {
    
    private List<Card> communityCards;

    /**
     * Creates a new felt with an empty list of community cards.
     */
    public TheFelt() {
        communityCards = new ArrayList<>();
    }

    /**
     * Adds a card to the community cards.
     *
     * @param card the card to add.
     */
    public void addCard(Card card) {
        communityCards.add(card);
    }

    /**
     * Prints the community cards.
     */
    public void printBoard() {
        System.out.print("Community Cards: ");
        for (Card card : communityCards) {
            System.out.print(card.toString() + " ");
        }
        System.out.println();
    }

    /**
     * Returns the list of community cards.
     *
     * @return the community cards.
     */
    public List<Card> getCommunityCards() {
        return communityCards;
    }
    
    /**
     * Clears all community cards.
     */
    public void clear() {
        communityCards.clear();
    }
}
