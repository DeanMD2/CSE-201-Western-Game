import java.util.ArrayList;
import java.util.List;

public class TheFelt {

    private List<Card> communityCards;

    public TheFelt() {
        communityCards = new ArrayList<>();
    }

    public void addCard(Card card) {
        communityCards.add(card);
    }
    
    public List<Card> getCommunityCards() {
        return communityCards;
    }

    public void printBoard() {
        System.out.print("Community Cards: ");
        for (Card card : communityCards) {
            System.out.print(card + " ");
        }
        System.out.println();
    }
}
