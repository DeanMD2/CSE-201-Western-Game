import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int health;
    private int money;
    private int score;
    private List<String> pokerHand;
    
    public void move() {
    	
    }
    
    public void setScore(int score) {
    	this.score = score;
    }
    
    public int getScore() {
    	return score;
    }
    
    public void setHealth(int health) {
    	this.health = health;
    }
    
    public int getHealth() {
    	return health;
    }
    
    public void setMoney(int money) {
    	this.money = money;
    }
    
    public int getMoney() {
    	return money;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public Player(String name) {
    	this.name = name;
        pokerHand = new ArrayList<>();
        health = 10;
    }
    
    
    
    //poker methods below:
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