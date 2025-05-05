import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class: BoomTown
 * @author Jason Hathaway, Matthew Valachek
 * @version 1.1
 * Course: CSE 201 Spring 2025
 * Written: 5/2/2025
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
        Player player = new Player(playerName, 200, 1000);
        new Townhall(player).enterTownhall();

        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n------------------------");
            System.out.println("Where would you like to go?");
            System.out.println("1. Town Hall");
            System.out.println("2. General Store");
            System.out.println("3. Panning for Gold");
            System.out.println("4. Saloon Challenge");
            System.out.println("5. Poker Game");
            System.out.println("6. Player Stats");
            System.out.println("7. Exit Game");
            System.out.print("Enter your choice (1-7): ");

            String choice = inputScanner.nextLine();

            switch (choice) {
                case "1": {
                	new Townhall(player).enterTownhall();
                    break;
                }
                case "2": {
                    new Store(player, inputScanner).enterStore();;
                    break;
                }
                case "3": {
                	new Panning(player, inputScanner).start();
                    break;
                }
                case "4": {
                	new Saloon(player, inputScanner).play();
                    break;
                }
                case "5": {
                    List<PokerPlayer> seats = new ArrayList<>();
                    seats.add(new PokerPlayer(player.getName(), player.getGold(), 200, true));
                    seats.add(new PokerPlayer("Doc Holliday", 2000, 200, false));
                    seats.add(new PokerPlayer("Calamity Jane", 2000, 200, false));
                    seats.add(new PokerPlayer("Wild Bill", 2000, 200, false));

                    PokerSession session = new PokerSession(seats, inputScanner);
                    session.run();

                    PokerPlayer humanSeat = seats.get(0);
                    player.setGold(humanSeat.getGold());
                    break;
                }
                case "6": {
                	System.out.println("\n--- Player Stats ---");
                	System.out.println("Name: " + player.getName());
                	System.out.println("Gold: " + player.getGold());
                	break;
                }
                case "7": {
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