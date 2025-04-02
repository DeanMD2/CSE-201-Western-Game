import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameState {
	private Deck deck;
	private TheFelt board;
	private List<Player> players;
	
	public GameState(List<String> playerNames) {
		deck = new Deck();
		board = new TheFelt();
		players = new ArrayList<>();
		
		for (String name : playerNames) {
			players.add(new Player(name));
		}
	}
	
	public void startGame() {
		deck.Shuffle();
		
		int cardsPerPlayer = 2;
		for (int i = 0; i < cardsPerPlayer; i++) {
			for (Player player : players) {
				String card = deck.dealCard();
				if (card != null) {
					player.receiveCard(card);
				}
			}
		}
		
		
		
		
		//debug print out to check functionality
		for (Player player : players) {
			player.showHand();
		}
		
		//Need to implement conditions for the "pot to be right"
		System.out.println("To the flop!");
		board.addCard(deck.dealCard());
		board.addCard(deck.dealCard());
		board.addCard(deck.dealCard());
		board.printBoard();
		System.out.println();
		
		//More conditions to get here
		System.out.println("And the turn!");
		board.addCard(deck.dealCard());
		board.printBoard();
		System.out.println();
		
		//Conditions to get here
		System.out.println("Finally THE RIVER!");
		board.addCard(deck.dealCard());
		board.printBoard();
		
		
		//Need to implement the ways to determine a winner;
	}
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<String> playerNames = new ArrayList<>();
		System.out.println("Enter your player name: ");
		playerNames.add(sc.next());
		playerNames.add("Doc Holliday");
		playerNames.add("Calamity Jane");
		playerNames.add("Wild Bill");
		
		sc.close();
		
		GameState game = new GameState(playerNames);
		game.startGame();
		
	}
}
