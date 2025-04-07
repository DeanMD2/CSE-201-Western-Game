import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a poker player with a hand and bet tracking.
 */
public class Player extends ThePlayer1 {

    private List<Card> pokerHand;
    private int currentBet; // Current round bet.
    private boolean folded; // True if player has folded.

    /**
     * Creates a player with the given name, money, score, and stack.
     *
     * @param name  player's name.
     * @param money player's total money.
     * @param score player's score.
     * @param stack player's betting stack.
     */
    public Player(String name, int money, int score, int stack) {
        super(name, money, score, stack);
        pokerHand = new ArrayList<>();
        currentBet = 0;
        folded = false;
    }

    /**
     * Adds a card to the player's hand.
     *
     * @param card the card to add.
     */
    public void receiveCard(Card card) {
        pokerHand.add(card);
    }

    /**
     * Prints the player's hand.
     */
    public void showHand() {
        System.out.println(getName() + "'s hand: " + pokerHand);
    }

    /**
     * Returns the player's hand.
     *
     * @return list of cards.
     */
    public List<Card> getHand() {
        return pokerHand;
    }

    /**
     * Clears the player's hand.
     */
    public void clearHand() {
        pokerHand.clear();
    }

    /**
     * Returns the current bet.
     *
     * @return current bet amount.
     */
    public int getCurrentBet() {
        return currentBet;
    }

    /**
     * Resets the current bet.
     */
    public void resetCurrentBet() {
        currentBet = 0;
    }

    /**
     * Increases the current bet.
     *
     * @param amount amount to add.
     */
    public void placeBet(int amount) {
        currentBet += amount;
    }

    /**
     * Checks if the player is folded.
     *
     * @return true if folded.
     */
    public boolean isFolded() {
        return folded;
    }

    /**
     * Sets the folded status.
     *
     * @param folded true if folded.
     */
    public void setFolded(boolean folded) {
        this.folded = folded;
    }

    /**
     * Returns the player's action during a bet.
     *
     * @param gameState current game state.
     * @return the chosen action.
     */
    public GameState.Action makeAction(GameState gameState) {
        return GameState.Action.CALL;
    }

    /**
     * Prompts the human player for an action.
     *
     * @return the chosen action.
     */
    public GameState.Action promptAction() {
        Scanner sc = new Scanner(System.in);
        System.out.println(getName() + ", enter your action (FOLD, CHECK/CALL, RAISE): ");
        String input = sc.next().toUpperCase();
        if (input.equals("FOLD")) {
            return GameState.Action.FOLD;
        } else if (input.equals("RAISE")) {
            return GameState.Action.RAISE;
        }
        return GameState.Action.CALL;
    }
}
