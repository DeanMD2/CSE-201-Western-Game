import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<String> hand;
    
    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public void receiveCard(String card) {
        hand.add(card);
    }
    
    public void showHand() {
        System.out.println(name + "'s hand: " + hand);
    }
    
    public List<String> getHand() {
        return hand;
    }
}