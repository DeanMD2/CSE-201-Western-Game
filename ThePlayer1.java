/**
 * Base class for a player's attributes.
 */
public class ThePlayer1 {
    private String name;
    private int gold;  // overall money
    private int stack;  // current betting stack

    /**
     * Creates a player with the given name, money, score, and stack.
     *
     * @param name  player's name.
     * @param money player's money.
     * @param score player's score.
     * @param stack player's stack.
     */
    public ThePlayer1(String name, int gold, int stack) {
        this.name = name;
        this.gold = gold;
        this.stack = stack;
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
     * Returns the player's money.
     *
     * @return the money.
     */
    public int getGold() {
        return gold;
    }
    
    /**
     * Sets the player's money.
     *
     * @param money the new money value.
     */
    public void setGold(int gold) {
        this.gold = gold;
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
    public void addGold(int amount) {
        this.gold += amount;
        this.stack += amount;
    }
    
    /**
     * Deducts an amount from the player's money and stack if available.
     *
     * @param amount the amount to deduct.
     * @return true if successful, false otherwise.
     */
    public boolean spendGold(int amount) {
        if (gold >= amount && stack >= amount) {
            gold -= amount;
            stack -= amount;
            return true;
        }
        return false;
    }
}
