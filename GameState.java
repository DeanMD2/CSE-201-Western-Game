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
	private Scanner input;

	/** Fee to buy in (or rebuy) and sit down at the game. */
	private static final int BUY_IN_FEE = 200;
	/** Ante amount for each hand before community cards are dealt. */
	private static final int ANTE = 10;
	/**
	 * The full playing stack available to work with. This is not reduced by the
	 * buy-in fee; it represents the chips available for betting each hand.
	 */
	private static final int STACK_SIZE = 200;
	/**
	 * For betting purposes after the ante, a raise is an extra $30 (i.e. the
	 * options per hand become: pay a $10 ante, then call, check, raise by $30, or
	 * fold).
	 */
	private static final int RAISE_AMOUNT = 30;

	/**
	 * Creates a game using the given player names. The first name is the human
	 * player. When a player is created, we simulate that they have paid the $200
	 * buy‑in fee (which does not reduce their playing stack).
	 *
	 * @param playerNames list of player names.
	 * @param input 
	 */
	public GameState(List<String> playerNames, Scanner input) {
		this.input = input;
		deck = new Deck();
		board = new TheFelt();
		players = new ArrayList<>();
		// Create players and simulate that each has paid the $200 buy‑in fee.
		// Their working stack (chips available for betting) is set to STACK_SIZE.
		for (int i = 0; i < playerNames.size(); i++) {
			String name = playerNames.get(i);
			if (i == 0) {
				// The human player
				players.add(new HumanPlayer(name, STACK_SIZE, STACK_SIZE));
			} else if (name.equals("Wild Bill")) {
				players.add(new WildBill(name, STACK_SIZE, STACK_SIZE));
			} else {
				players.add(new ComputerPlayer(name, STACK_SIZE, STACK_SIZE));
			}
			System.out.println(name + " has bought in for " + BUY_IN_FEE + " gold.");
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
	 * Starts the game loop.
	 */
	public void startGame() {
		String command = "";

		while (true) {
			// Reset pot and prepare for a new hand.
			pot = 0;
			deck.shuffle();
			board.clear();
			for (Player player : players) {
				player.clearHand();
				player.resetCurrentBet();
				player.setFolded(false);
				// For non‐human players, the playing stack is reset each hand.
				// For the human player, assume their stack persists across hands until a rebuy.
				if (!(player instanceof HumanPlayer)) {
					player.setStack(STACK_SIZE);
				}
			}

			// Collect the $10 ante from each player from their working stack.
			for (Player player : players) {
				if (player.getStack() >= ANTE) {
					// Deduct the ante from the player's stack.
					// (Assumes that deductMoney works on the playing stack.)
					player.spendGold(ANTE);
					player.placeBet(ANTE);
					pot += ANTE;
				} else {
					System.out.println(player.getName() + " lacks funds for the ante and is out.");
					player.setFolded(true);
				}
			}
			System.out.println("Ante of $" + ANTE + " collected. Pot: $" + pot);

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

			// Show the human player's hand.
			for (Player player : players) {
				if (player instanceof HumanPlayer) {
					player.showHand();
				}
			}
			// pause(2000);

			// Betting rounds and dealing community cards.
			bettingRound("Pre-flop");
			System.out.println("Dealing the Flop...");
			board.addCard(deck.dealCard());
			board.addCard(deck.dealCard());
			board.addCard(deck.dealCard());
			board.printBoard();
			displayHumanPlayerHand();

			// pause(2000);
			bettingRound("Flop");

			System.out.println("Dealing the Turn...");
			board.addCard(deck.dealCard());
			board.printBoard();
			displayHumanPlayerHand();

			// pause(2000);
			bettingRound("Turn");

			System.out.println("Dealing the River...");
			board.addCard(deck.dealCard());
			board.printBoard();
			displayHumanPlayerHand();

			// pause(2000);
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
				// Award the pot to the winner.
				winner.addGold(pot);
			} else {
				System.out.println("No winner this round.");
			}

			System.out.println("Pot of " + pot + " gold awarded. Your current stack: " + getHumanPlayer().getStack() + " gold.");
			System.out.println("Type 'play' to continue, 'rebuy' to re-buy in, or 'exit' to quit:");
			command = input.next().toLowerCase();
			if (command.equals("exit")) {
				HumanPlayer human = getHumanPlayer();
				System.out.println("You have left the table. Final cashout: " + human.getStack() + " gold!");
				human.cashOut();
				break;
			} else if (command.equals("rebuy")) {
			    HumanPlayer human = getHumanPlayer();
			    human.rebuy(BUY_IN_FEE);
			    System.out.println("Re-bought in for $" + BUY_IN_FEE + ". New stack: $" + (human.getStack() + BUY_IN_FEE));
			    human.setStack(human.getStack() + BUY_IN_FEE);
			}

		}
	}

	/**
	 * Pauses execution for a given time. Testing stages and suspense
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

		// Reset each player's current bet for the new betting round.
		for (Player player : players) {
			if (!player.isFolded()) {
				player.resetCurrentBet();
			}
		}

		int currentBet = 0;
		// Find the highest bet in this new round (will start at 0).
		for (Player player : players) {
			if (!player.isFolded() && player.getCurrentBet() > currentBet) {
				currentBet = player.getCurrentBet();
			}
		}

		for (Player player : players) {
			if (player.isFolded())
				continue;

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
						player.spendGold(allInAmount);
						player.placeBet(allInAmount);
						pot += allInAmount;
						System.out.println(player.getName() + " goes all in with $" + allInAmount + ".");
					} else {
						if (player.spendGold(amountToCall)) {
							player.placeBet(amountToCall);
							pot += amountToCall;
							System.out.println(player.getName() + " calls with $" + amountToCall + ".");
						} else {
							int allInAmount = player.getStack();
							player.spendGold(allInAmount);
							player.placeBet(allInAmount);
							pot += allInAmount;
							System.out.println(player.getName() + " goes all in with $" + allInAmount + ".");
						}
					}
				}
				break;
			case RAISE:
				int raiseAmount = 30; // Set the raise amount to $30 as desired.
				int requiredToCall = currentBet - player.getCurrentBet();
				int totalBet = requiredToCall + raiseAmount;
				if (player.getStack() < totalBet) {
					int allInAmount = player.getStack();
					player.spendGold(allInAmount);
					player.placeBet(allInAmount);
					pot += allInAmount;
					System.out.println(player.getName() + " goes all in with $" + allInAmount + ".");
				} else {
					if (player.spendGold(totalBet)) {
						player.placeBet(totalBet);
						currentBet = player.getCurrentBet();
						pot += totalBet;
						System.out.println(player.getName() + " raises by $" + raiseAmount + " (total bet: $"
								+ player.getCurrentBet() + ").");
					} else {
						if (requiredToCall <= 0) {
							System.out.println(player.getName() + " checks instead.");
						} else {
							if (player.getStack() < requiredToCall) {
								int allInAmount = player.getStack();
								player.spendGold(allInAmount);
								player.placeBet(allInAmount);
								pot += allInAmount;
								System.out.println(player.getName() + " goes all in with $" + allInAmount + ".");
							} else {
								if (player.spendGold(requiredToCall)) {
									player.placeBet(requiredToCall);
									pot += requiredToCall;
									System.out
											.println(player.getName() + " calls with $" + requiredToCall + " instead.");
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

	// Add this helper method within your GameState class:
	private void displayHumanPlayerHand() {
		HumanPlayer human = getHumanPlayer();
		if (human != null) {
			human.showHand();
		}
	}
}
