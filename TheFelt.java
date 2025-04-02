import java.util.ArrayList;
import java.util.List;

public class TheFelt {
	
    private List<String> communityCards;

    public TheFelt() {
        communityCards = new ArrayList<>();
    }

    public void addCard(String card) {
        communityCards.add(card);
    }

    public void printBoard() {
        System.out.print("Community Cards: ");
        for (String card : communityCards) {
            System.out.print(card + " ");
        }
        System.out.println();
    }
}