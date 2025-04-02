import java.util.ArrayList;
import java.util.List;


public class Player extends ThePlayer1 {
    
	private List<String> pokerHand;
	private String name;
	private int money;
	private int score;
    
    public Player(String name, int money, int score) {
    	super(name, money, score);
    	this.name = name;
    	this.money = money;
    	this.score = score;
		this.pokerHand = new ArrayList<>();
	}
  
  //poker methods here:
    public void receiveCard(String card) {
        pokerHand.add(card);
    }
    
    public void showHand() {
        System.out.println(name + "'s hand: " + pokerHand);
    }
    
    public List<String> getHand() {
        return pokerHand;
    }
    
    public void clearHand() {
    	pokerHand.clear();
    }
}