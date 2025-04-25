import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class: BoomTown
 * @author 
 * @version 1.0
 * Course: CSE 201 Spring 2025
 * Written: April 25, 2025
 *
 * Purpose: Driver class for the BoomTown game. Manages navigation between minigames and tracks gold.
 */
public class BoomTown {
	
	/**
	 * Main method
	 * @param args
	 */
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);

        System.out.println("Welcome to BoomTown!");
        System.out.print("What's your name, prospector?: ");
        String playerName = inputScanner.nextLine();

        // Initialize player with poker-compatible attributes
        HumanPlayer player = new HumanPlayer(playerName, 200, 0, 1000);

        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n------------------------");
            System.out.println("Where would you like to go?");
            System.out.println("1. Panning for Gold");
            System.out.println("2. General Store");
            System.out.println("3. Town Hall");
            System.out.println("4. Saloon Challenge (Tapping)");
            System.out.println("5. Saloon Poker Game");
            System.out.println("6. Exit Game");
            System.out.print("Enter your choice (1-6): ");

            String choice = inputScanner.nextLine();

            switch (choice) {
                case "1": {
                    Panning panningGame = new Panning(player);
                    panningGame.start();
                    break;
                }
                case "2": {
                    Store store = new Store(player);
                    store.enterStore();
                    break;
                }
                case "3": {
                    Townhall townhall = new Townhall(player);
                    
                    townhall.enterTownhall();
                    break;
                }
                case "4": {
                    Saloon saloon = new Saloon(player);
                    saloon.playGame();
                    break;
                }
                case "5": {
                    // Initialize poker game with default opponents
                    List<String> playerNames = new ArrayList<>();
                    playerNames.add(player.getName());
                    playerNames.add("Doc Holliday");
                    playerNames.add("Calamity Jane");
                    playerNames.add("Wild Bill");
                    GameState pokerGame = new GameState(playerNames);
                    pokerGame.startGame();
                    break;
                }
                case "6": {
                    System.out.println("Thanks for visiting BoomTown. Goodbye!");
                    isRunning = false;
                    break;
                }
                default: {
                    System.out.println("Invalid choice. Please enter a number from 1 to 6.");
                    break;
                }
            }

            // Check win condition
            if (player.getGold() >= 2000) {
                System.out.println("\nCongratulations, " + player.getName() + "!");
                System.out.println("You’ve struck it rich with " + player.getGold() + " gold!");
                System.out.println("Thanks for playing BoomTown.");
                break;
            }
        }

        inputScanner.close();
    }
}