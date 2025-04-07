/**
 * Base class for a player's attributes.
 */
public class ThePlayer1 {
    private String name;
    private int health;
    private int money;  // overall money
    private int score;
    private int stack;  // current betting stack

    /**
     * Creates a player with the given name, money, score, and stack.
     *
     * @param name  player's name.
     * @param money player's money.
     * @param score player's score.
     * @param stack player's stack.
     */
    public ThePlayer1(String name, int money, int score, int stack) {
        this.name = name;
        this.money = money;
        this.score = score;
        this.stack = stack;
        this.health = 10;
    }
    
    /**
     * Returns the player's name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the player's score.
     *
     * @return the score.
     */
    public int getScore() {
        return score;
    }
    
    /**
     * Returns the player's money.
     *
     * @return the money.
     */
    public int getMoney() {
        return money;
    }
    
    /**
     * Sets the player's money.
     *
     * @param money the new money value.
     */
    public void setMoney(int money) {
        this.money = money;
    }
    
    /**
     * Returns the player's stack.
     *
     * @return the stack.
     */
    public int getStack() {
        return stack;
    }
    
    /**
     * Sets the player's stack.
     *
     * @param stack the new stack value.
     */
    public void setStack(int stack) {
        this.stack = stack;
    }
    
    /**
     * Adds an amount to both the player's money and stack.
     *
     * @param amount the amount to add.
     */
    public void addMoney(int amount) {
        this.money += amount;
        this.stack += amount;
    }
    
    /**
     * Deducts an amount from the player's money and stack if available.
     *
     * @param amount the amount to deduct.
     * @return true if successful, false otherwise.
     */
    public boolean deductMoney(int amount) {
        if (money >= amount && stack >= amount) {
            money -= amount;
            stack -= amount;
            return true;
        }
        return false;
    }
}
