/**
 * Class: PokerUI
 * Author: Dean DiCarlo
 * @version 1.0
 * Course: CSE 201
 *
 * Purpose: Handles all console-based input and output for a poker session,
 *          including displaying player hands, table status, and prompting
 *          the human player for actions and post-hand choices.
 */
import java.util.List;
import java.util.Scanner;

public class PokerUI {
    private final Scanner input;

    /**
     * Constructs a PokerUI using the provided Scanner for console I/O.
     *
     * @param input shared Scanner(System.in) for reading user input
     */
    public PokerUI(Scanner input) {
        this.input = input;
    }

    /**
     * Displays the hole cards of the specified player.
     *
     * @param player the human PokerPlayer whose hand to show
     */
    public void showHand(PokerPlayer player) {
        System.out.println(player.getName() + "'s hand: " + player.getHand());
    }

    /**
     * Displays all players' hole cards at showdown, indicating folded players.
     *
     * @param players list of all PokerPlayer objects in the session
     */
    public void showAllHands(List<PokerPlayer> players) {
        System.out.println("\n--- Players’ Hands ---");
        for (PokerPlayer p : players) {
            String status = p.isFolded() ? " (folded)" : "";
            System.out.println(p.getName() + status + ": " + p.getHand());
        }
        System.out.println("-----------------------\n");
    }

    /**
     * Displays each player's current stack and the total pot.
     * Marks the human player's entry with "(you)".
     *
     * @param players list of all PokerPlayer objects in the session
     * @param pot     the current pot size
     */
    public void showTableStatus(List<PokerPlayer> players, int pot) {
        System.out.println("\n--- Table Status ---");
        for (PokerPlayer p : players) {
            String you = p.isHuman() ? " (you)" : "";
            System.out.printf("%-15s : Stack = $%d%n", p.getName() + you, p.getStack());
        }
        System.out.println("Current pot: $" + pot);
        System.out.println("--------------------\n");
    }

    /**
     * Prompts the human player for their action during a betting round.
     * Shows the current pot and amount needed to call.
     *
     * @param player  the human PokerPlayer
     * @param session the current PokerSession context
     * @return the chosen Action (FOLD, CHECK, or RAISE)
     */
    public PokerSession.Action promptPlayerAction(PokerPlayer player, PokerSession session) {
        int highest = 0;
        for (PokerPlayer other : session.getPlayers()) {
            if (!other.isFolded()) {
                highest = Math.max(highest, other.getCurrentBet());
            }
        }
        int toCall = highest - player.getCurrentBet();

        System.out.println(player.getName()
            + ", pot is $" + session.getPot()
            + ", to call $" + toCall
            + ". Enter FOLD, CHECK, or RAISE:");
        String cmd = input.nextLine().trim().toUpperCase();
        switch (cmd) {
            case "FOLD":  return PokerSession.Action.FOLD;
            case "RAISE": return PokerSession.Action.RAISE;
            default:      return PokerSession.Action.CHECK;
        }
    }

    /**
     * Prompts the human player for post-hand options: play again, rebuy, or exit.
     *
     * @param buyInFee the cost to rebuy into the session
     * @return the user's command in lowercase ("play", "rebuy", or "exit")
     */
    public String promptPostHand(int buyInFee) {
        System.out.println("Type 'play' to deal another hand, 'rebuy' to add $"
                           + buyInFee + " to your stack, or 'exit' to cash out:");
        return input.nextLine().trim().toLowerCase();
    }
}