/**
 * Class: BettingRound
 * Author: Dean DiCarlo
 * @version 1.0
 * Course: CSE 201
 * Written: May 4, 2025
 *
 * Purpose: Manages a single betting round in a poker session, handling player actions,
 *          resetting bets, processing folds, checks, calls, and raises, and updating the pot.
 */
import java.util.List;

public class BettingRound {
    private final int raiseAmount;

    /**
     * Constructs a BettingRound with the specified raise amount.
     *
     * @param raiseAmount the fixed amount by which players raise
     */
    public BettingRound(int raiseAmount) {
        this.raiseAmount = raiseAmount;
    }

    /**
     * Executes a betting round, prompting each active player to act and updating the pot.
     *
     * @param title   a descriptive title for the betting round (e.g., "Pre-flop", "Flop")
     * @param session the current PokerSession containing players and game state
     * @param pot     the current pot size before this round
     * @return the updated pot size after all actions
     */
    public int run(String title, PokerSession session, int pot) {
        System.out.println("\n--- " + title + " betting round ---");

        // Reset each active player's current bet
        for (PokerPlayer p : session.getPlayers()) {
            if (!p.isFolded()) {
                p.resetCurrentBet();
            }
        }

        // Determine the highest existing bet
        int highest = 0;
        for (PokerPlayer p : session.getPlayers()) {
            if (!p.isFolded()) {
                highest = Math.max(highest, p.getCurrentBet());
            }
        }

        // Process each player's action
        for (PokerPlayer p : session.getPlayers()) {
            if (p.isFolded()) {
                continue;
            }
            PokerSession.Action act = p.makeAction(session);
            switch (act) {
                case FOLD:
                    p.setFolded(true);
                    System.out.println(p.getName() + " folds.");
                    break;
                case CHECK:
                    int toCall = highest - p.getCurrentBet();
                    if (toCall <= 0) {
                        System.out.println(p.getName() + " checks.");
                    } else {
                        int pay = Math.min(toCall, p.getStack());
                        p.setStack(p.getStack() - pay);
                        p.placeBet(pay);
                        pot += pay;
                        System.out.println(p.getName() + " calls $" + pay + ".");
                    }
                    break;
                case RAISE:
                    int total = (highest - p.getCurrentBet()) + raiseAmount;
                    int payR  = Math.min(total, p.getStack());
                    p.setStack(p.getStack() - payR);
                    p.placeBet(payR);
                    highest = p.getCurrentBet();
                    pot += payR;
                    System.out.println(p.getName() + " raises by $" + raiseAmount + ".");
                    break;
            }
        }

        return pot;
    }
}