/**
 * Class: PokerPlayer
 * Author: Dean DiCarlo
 * @version 1.0
 * Course: CSE 201
 *
 * Purpose: Represents a poker participant (human or AI) in a poker session,
 *          managing hole cards, bet state, and decision-making logic based
 *          on the current session context.
 */
import java.util.ArrayList;
import java.util.List;

public class PokerPlayer extends Player {
    private final boolean isHuman;
    private final List<Card> hand = new ArrayList<>();
    private int currentBet = 0;
    private boolean folded = false;

    /**
     * Constructs a PokerPlayer with the given identity and behavior type.
     *
     * @param name    the player's display name
     * @param gold    the total starting money
     * @param stack   the initial betting stack
     * @param isHuman true if controlled by a human, false for AI
     */
    public PokerPlayer(String name, int gold, int stack, boolean isHuman) {
        super(name, gold, stack);
        this.isHuman = isHuman;
    }

    // ——— Hand & Bet State ———

    /**
     * Adds a dealt card to the player's hand.
     *
     * @param card the Card to receive
     */
    public void receiveCard(Card card) {
        hand.add(card);
    }

    /**
     * Clears all cards from the player's hand.
     */
    public void clearHand() {
        hand.clear();
    }

    /**
     * @return the current list of hole cards
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * @return the amount currently bet in this round
     */
    public int getCurrentBet() {
        return currentBet;
    }

    /**
     * Resets this player's bet for a new betting round.
     */
    public void resetCurrentBet() {
        currentBet = 0;
    }

    /**
     * Increases the current bet by the given amount.
     *
     * @param amt amount to add to the current bet
     */
    public void placeBet(int amt) {
        currentBet += amt;
    }

    /**
     * @return true if the player has folded this hand
     */
    public boolean isFolded() {
        return folded;
    }

    /**
     * Sets the folded state of the player.
     *
     * @param folded true to mark as folded
     */
    public void setFolded(boolean folded) {
        this.folded = folded;
    }

    /**
     * @return true if this player is human-controlled
     */
    public boolean isHuman() {
        return isHuman;
    }

    // ——— Decision Logic ———

    /**
     * Determines the player's action for the current betting round.
     *
     * @param session the current PokerSession context
     * @return the chosen Action (FOLD, CHECK, or RAISE)
     */
    public PokerSession.Action makeAction(PokerSession session) {
        if (isHuman) {
            return promptAction(session);
        } else {
            return autoRespond(session);
        }
    }

    /**
     * Prompts a human player for their betting choice.
     *
     * @param session the current PokerSession context
     * @return the Action selected by the human player
     */
    private PokerSession.Action promptAction(PokerSession session) {
        int highest = 0;
        for (PokerPlayer o : session.getPlayers()) {
            if (!o.isFolded()) highest = Math.max(highest, o.getCurrentBet());
        }
        int toCall = highest - currentBet;

        System.out.println(getName()
            + ", pot is $" + session.getPot()
            + ", to call $" + toCall
            + ". Enter FOLD, CHECK, or RAISE:");
        String cmd = session.getInput().nextLine().trim().toUpperCase();
        switch (cmd) {
            case "FOLD":  return PokerSession.Action.FOLD;
            case "RAISE": return PokerSession.Action.RAISE;
            default:      return PokerSession.Action.CHECK;
        }
    }

    /**
     * Provides simple AI logic: call if able, otherwise fold.
     *
     * @param session the current PokerSession context
     * @return the AI-chosen Action
     */
    private PokerSession.Action autoRespond(PokerSession session) {
        int highest = 0;
        for (PokerPlayer o : session.getPlayers()) {
            if (!o.isFolded()) highest = Math.max(highest, o.getCurrentBet());
        }
        int owe = highest - currentBet;
        return (getStack() >= owe)
            ? PokerSession.Action.CHECK
            : PokerSession.Action.FOLD;
    }

    // ——— Buy-in / Cash-out ———

    /**
     * Deducts the buy-in amount from gold into the stack if affordable.
     *
     * @param amount the buy-in cost
     * @return true if the buy-in succeeded, false if funds insufficient
     */
    public boolean buyIn(int amount) {
        if (getGold() >= amount) {
            setGold(getGold() - amount);
            setStack(amount);
            return true;
        }
        return false;
    }

    /**
     * Moves remaining stack back into gold (cash out) and empties the stack.
     */
    public void cashOutStack() {
        setGold(getGold() + getStack());
        setStack(0);
    }
}