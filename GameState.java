import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Manages the poker game loop and round operations.
 */
public class GameState {
    private Deck deck;
    private TheFelt board;
    private List<Player> players;
    private int pot;

    /** Round buy-in amount. */
    private static final int BUY_IN = 50;
    /** Starting stack for each player. */
    private static final int STACK_SIZE = 1000;

    /**
     * Creates a game using the given player names. The first name is the human player.
     *
     * @param playerNames list of player names.
     */
    public GameState(List<String> playerNames) {
        deck = new Deck();
        board = new TheFelt();
        players = new ArrayList<>();
        for (int i = 0; i < playerNames.size(); i++) {
            String name = playerNames.get(i);
            if (i == 0) {
                players.add(new HumanPlayer(name, 200, 0, STACK_SIZE));
            } else if (name.equals("Wild Bill")) {
                players.add(new WildBill(name, 100, 0, STACK_SIZE));
            } else {
                players.add(new ComputerPlayer(name, 100, 0, STACK_SIZE));
            }
        }
    }

    /**
     * Returns the human player.
     *
     * @return the HumanPlayer instance.
     */
    private HumanPlayer getHumanPlayer1() {
        for (Player player : players) {
            if (player instanceof HumanPlayer) {
                return (HumanPlayer) player;
            }
        }
        return null;
    }

    /**
     * Starts the game loop.
     */
    public void startGame() {
        Scanner sc = new Scanner(System.in);
        String command = "";

        while (true) {
            pot = 0;
            deck.shuffle();
            board.clear();
            for (Player player : players) {
                player.clearHand();
                player.resetCurrentBet();
                player.setFolded(false);
            }
            // Collect buy-in.
            for (Player player : players) {
                if (player.deductMoney(BUY_IN)) {
                    player.placeBet(BUY_IN);
                    pot += BUY_IN;
                } else {
                    System.out.println(player.getName() + " lacks funds and is out.");
                    player.setFolded(true);
                }
            }
            System.out.println("Buy-in complete. Pot: $" + pot);

            // Deal hole cards.
            int cardsPerPlayer = 2;
            for (int i = 0; i < cardsPerPlayer; i++) {
                for (Player player : players) {
                    if (!player.isFolded()) {
                        Card card = deck.dealCard();
                        if (card != null) {
                            player.receiveCard(card);
                        }
                    }
                }
            }

            // Show human player's hand.
            System.out.println("Your hand: ");
            for (Player player : players) {
                if (player instanceof HumanPlayer) {
                    player.showHand();
                }
            }
            pause(2000);

            // Betting rounds and dealing community cards.
            bettingRound("Pre-flop");
            System.out.println("Dealing the Flop...");
            board.addCard(deck.dealCard());
            board.addCard(deck.dealCard());
            board.addCard(deck.dealCard());
            board.printBoard();
            pause(2000);
            bettingRound("Flop");

            System.out.println("Dealing the Turn...");
            board.addCard(deck.dealCard());
            board.printBoard();
            pause(2000);
            bettingRound("Turn");

            System.out.println("Dealing the River...");
            board.addCard(deck.dealCard());
            board.printBoard();
            pause(2000);
            bettingRound("River");

            // Showdown.
            EvaluatedHand bestOverall = null;
            Player winner = null;
            List<Card> communityCards = board.getCommunityCards();

            for (Player player : players) {
                if (!player.isFolded()) {
                    List<Card> sevenCards = new ArrayList<>();
                    sevenCards.addAll(player.getHand());
                    sevenCards.addAll(communityCards);

                    EvaluatedHand evalHand = HandEvaluator.evaluateBestHand(sevenCards);
                    System.out.println(player.getName() + "'s best hand: " + evalHand);

                    if (bestOverall == null || evalHand.compareTo(bestOverall) > 0) {
                        bestOverall = evalHand;
                        winner = player;
                    }
                }
            }

            if (winner != null) {
                System.out.println("Winner: " + winner.getName() + " with " + bestOverall);
                winner.addMoney(pot);
            } else {
                System.out.println("No winner this round.");
            }

            System.out.println("Pot of $" + pot + " awarded. Your money: $" + getHumanPlayer1().getMoney());
            System.out.println("Type 'play' to continue, 'rebuy' to add funds, or 'exit' to quit:");
            command = sc.next().toLowerCase();
            if (command.equals("exit")) {
                break;
            } else if (command.equals("rebuy")) {
                getHumanPlayer1().addMoney(200);
                System.out.println("Re-bought in. New balance: $" + getHumanPlayer1().getMoney());
            }
        }
        sc.close();
    }

    /**
     * Pauses execution for a given time.
     *
     * @param ms milliseconds to pause.
     */
    private void pause(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Returns the human player.
     *
     * @return the HumanPlayer instance.
     */
    private HumanPlayer getHumanPlayer() {
        for (Player player : players) {
            if (player instanceof HumanPlayer) {
                return (HumanPlayer) player;
            }
        }
        return null;
    }

    /**
     * Enum for betting actions.
     */
    public enum Action {
        FOLD, CALL, CHECK, RAISE
    }

    /**
     * Executes a betting round.
     *
     * @param roundName name of the betting round.
     */
    private void bettingRound(String roundName) {
        System.out.println("Betting round: " + roundName);
        int currentBet = 0;
        for (Player player : players) {
            if (!player.isFolded() && player.getCurrentBet() > currentBet) {
                currentBet = player.getCurrentBet();
            }
        }
        for (Player player : players) {
            if (player.isFolded()) continue;

            GameState.Action action = player.makeAction(this);
            switch (action) {
                case FOLD:
                    player.setFolded(true);
                    System.out.println(player.getName() + " folds.");
                    break;
                case CALL:
                    int amountToCall = currentBet - player.getCurrentBet();
                    if (amountToCall <= 0) {
                        System.out.println(player.getName() + " checks.");
                    } else {
                        if (player.getStack() < amountToCall) {
                            int allInAmount = player.getStack();
                            player.deductMoney(allInAmount);
                            player.placeBet(allInAmount);
                            pot += allInAmount;
                            System.out.println(player.getName() + " goes all in with $" + allInAmount + ".");
                        } else {
                            if (player.deductMoney(amountToCall)) {
                                player.placeBet(amountToCall);
                                pot += amountToCall;
                                System.out.println(player.getName() + " calls with $" + amountToCall + ".");
                            } else {
                                int allInAmount = player.getStack();
                                player.deductMoney(allInAmount);
                                player.placeBet(allInAmount);
                                pot += allInAmount;
                                System.out.println(player.getName() + " goes all in with $" + allInAmount + ".");
                            }
                        }
                    }
                    break;
                case RAISE:
                    int raiseAmount = 50;
                    int requiredToCall = currentBet - player.getCurrentBet();
                    int totalBet = requiredToCall + raiseAmount;
                    if (player.getStack() < totalBet) {
                        int allInAmount = player.getStack();
                        player.deductMoney(allInAmount);
                        player.placeBet(allInAmount);
                        pot += allInAmount;
                        System.out.println(player.getName() + " goes all in with $" + allInAmount + ".");
                    } else {
                        if (player.deductMoney(totalBet)) {
                            player.placeBet(totalBet);
                            currentBet += raiseAmount;
                            pot += totalBet;
                            System.out.println(player.getName() + " raises by $" + raiseAmount + " (total bet: $" + player.getCurrentBet() + ").");
                        } else {
                            if (requiredToCall <= 0) {
                                System.out.println(player.getName() + " checks instead.");
                            } else {
                                if (player.getStack() < requiredToCall) {
                                    int allInAmount = player.getStack();
                                    player.deductMoney(allInAmount);
                                    player.placeBet(allInAmount);
                                    pot += allInAmount;
                                    System.out.println(player.getName() + " goes all in with $" + allInAmount + ".");
                                } else {
                                    if (player.deductMoney(requiredToCall)) {
                                        player.placeBet(requiredToCall);
                                        pot += requiredToCall;
                                        System.out.println(player.getName() + " calls with $" + requiredToCall + " instead.");
                                    } else {
                                        System.out.println(player.getName() + " cannot call and folds.");
                                        player.setFolded(true);
                                    }
                                }
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Main entry point.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> playerNames = new ArrayList<>();
        System.out.println("Welcome to our western game!");
        System.out.println("Our game starts from this game desk.");
        System.out.println("Win this game to prove you can survive from western world");
        System.out.println("Enter your player name: ");
        String humanName = sc.next();
        playerNames.add(humanName);
        playerNames.add("Doc Holliday");
        playerNames.add("Calamity Jane");
        playerNames.add("Wild Bill");
        GameState game = new GameState(playerNames);
        game.startGame();
    }
}
