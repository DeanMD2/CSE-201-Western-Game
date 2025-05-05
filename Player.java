/**
 * Class: Player
 * @version 1.0
 * Course: CSE 201
 *
 * Purpose: Defines the base attributes and behaviors for a game participant,
 *          including name, overall money (gold), and current betting stack,
 *          with methods to adjust and query these values.
 */
public class Player {
    private String name;   // Player's display name
    private int gold;      // Total money available to the player
    private int stack;     // Chips currently at the table for betting

    /**
     * Constructs a Player with the given name, gold, and stack values.
     *
     * @param name  the display name of the player
     * @param gold  the total money the player possesses
     * @param stack the amount of chips the player brings to the table
     */
    public Player(String name, int gold, int stack) {
        this.name = name;
        this.gold = gold;
        this.stack = stack;
    }

    /**
     * @return the player's display name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the player's total money (gold)
     */
    public int getGold() {
        return gold;
    }

    /**
     * Sets the player's total money (gold).
     *
     * @param gold the new total money value
     */
    public void setGold(int gold) {
        this.gold = gold;
    }

    /**
     * @return the player's current betting stack
     */
    public int getStack() {
        return stack;
    }

    /**
     * Sets the player's current betting stack.
     *
     * @param stack the new stack value
     */
    public void setStack(int stack) {
        this.stack = stack;
    }

    /**
     * Adds the specified amount to both the player's gold and stack.
     *
     * @param amount the amount to add
     */
    public void addGold(int amount) {
        this.gold += amount;
        this.stack += amount;
    }

    /**
     * Deducts the specified amount from the player's gold and stack if sufficient funds exist.
     *
     * @param amount the amount to deduct
     * @return true if deduction succeeded, false if insufficient funds
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