import java.util.ArrayList;
import java.util.List;

public class ThePlayer1 {

	
	private String name;
    private int health;
    private int money;
    private int score;
    
    
    public ThePlayer1(String name, int money, int score) {
    	this.name = name;
        health = 10;
        this.money = money;
        this.score = score;
    }
    
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
    
    public void addMoney(int amount) {
        this.money += amount;
    }
    
    public boolean deductMoney(int amount) {
        if (money >= amount) {
            money -= amount;
            return true;
        }
        return false;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
}
