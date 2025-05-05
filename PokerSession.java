/**
 * Class: PokerSession
 * Author: Dean DiCarlo
 * @version 1.0
 * Course: CSE 201
 *
 * Purpose: Manages a complete poker session loop including buy-in,
 *          card dealing, multiple betting rounds, showdown evaluation,
 *          and post-hand options (rebuy or exit).
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PokerSession {
    // Configuration constants
    public static final int BUY_IN_FEE   = 200;
    public static final int ANTE         = 10;
    public static final int RAISE_AMOUNT = 30;

    /**
     * Possible actions a player can take during a betting round.
     */
    public enum Action {
        FOLD, CHECK, RAISE
    }

    // Core game components
    private final Dealer dealer;
    private final BettingRound betting;
    private final ShowdownEvaluator showdown;
    private final PokerUI ui;
    private final List<PokerPlayer> players;
    private final Scanner input;

    // Mutable session state
    private int pot = 0;

    /**
     * Constructs a PokerSession for the given players and input source.
     *
     * @param players list of PokerPlayer participants (must include one human)
     * @param input   shared Scanner for console input
     */
    public PokerSession(List<PokerPlayer> players, Scanner input) {
        this.players  = new ArrayList<>(players);
        this.input    = input;
        this.dealer   = new Dealer();
        this.betting  = new BettingRound(RAISE_AMOUNT);
        this.showdown = new ShowdownEvaluator();
        this.ui       = new PokerUI(input);
    }

    /**
     * @return the list of players in this session
     */
    public List<PokerPlayer> getPlayers() {
        return players;
    }

    /**
     * @return the current total pot size
     */
    public int getPot() {
        return pot;
    }

    /**
     * @return the Scanner used for reading player input
     */
    public Scanner getInput() {
        return input;
    }

    /**
     * Runs the main game loop: buy-in, deal cards, execute betting rounds,
     * evaluate showdown, and handle post-hand rebuy/exit logic.
     */
    public void run() {
        PokerPlayer human = players.stream()
            .filter(PokerPlayer::isHuman)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No human player"));

        if (!human.buyIn(BUY_IN_FEE)) {
            System.out.println("Not enough gold to buy in. Exiting poker.");
            return;
        }

        boolean keepPlaying = true;
        while (keepPlaying) {
            pot = 0;
            dealer.shuffleAndReset();
            resetPlayers();
            collectAntes();

            dealer.dealHoleCards(players);
            ui.showHand(human);
            pot = betting.run("Pre-flop", this, pot);

            dealer.dealFlop();
            dealer.showBoard();
            ui.showHand(human);
            pot = betting.run("Flop", this, pot);

            dealer.dealTurn();
            dealer.showBoard();
            ui.showHand(human);
            pot = betting.run("Turn", this, pot);

            dealer.dealRiver();
            dealer.showBoard();
            ui.showHand(human);
            pot = betting.run("River", this, pot);

            ui.showAllHands(players);
            PokerPlayer winner = showdown.evaluateWinner(players, dealer.getCommunity());
            if (winner != null) {
                winner.setStack(winner.getStack() + pot);
                List<Card> seven = new ArrayList<>(winner.getHand());
                seven.addAll(dealer.getCommunity());
                EvaluatedHand bestHand = HandEvaluator.evaluateBestHand(seven);
                HandValue hv = bestHand.getHandValue();
                System.out.println(winner.getName()
                    + " wins the pot of $" + pot
                    + " with a " + hv.categoryName() + "!");
            }

            ui.showTableStatus(players, pot);
            String cmd = ui.promptPostHand(BUY_IN_FEE);
            switch (cmd) {
                case "rebuy":
                    if (!human.buyIn(BUY_IN_FEE)) {
                        System.out.println("Not enough gold to rebuy.");
                    }
                    break;
                case "exit":
                    human.cashOutStack();
                    keepPlaying = false;
                    break;
                default:
                    break;
            }
        }

        System.out.println("You cash out with $" + human.getGold() + " total gold.");
    }

    /**
     * Resets each player's hand, current bet, and folded status before a new hand.
     */
    private void resetPlayers() {
        for (PokerPlayer p : players) {
            p.clearHand();
            p.resetCurrentBet();
            p.setFolded(false);
        }
    }

    /**
     * Collects antes from all players, folding any who cannot pay.
     * Increases the pot by the total ante collected.
     */
    private void collectAntes() {
        for (PokerPlayer p : players) {
            if (p.getStack() >= ANTE) {
                p.setStack(p.getStack() - ANTE);
                p.placeBet(ANTE);
                pot += ANTE;
            } else {
                System.out.println(p.getName() + " can't cover the ante and folds.");
                p.setFolded(true);
            }
        }
        System.out.println("Collected ante of $" + ANTE + ". Pot is now $" + pot + ".");
    }
}