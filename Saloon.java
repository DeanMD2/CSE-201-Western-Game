import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class: Saloon
 * @author Matthew Valachek
 * @version 1.2
 * Course: CSE 201 E Spring 2025
 * Written: 5/2/2025
 * 
 * This class provides the functionality of the saloon level of the BoomTown game.
 * The class creates a self-contained room with associated methods to pass drinks,
 * fill orders, randomly select drink names for the user to type in to fill the orders,
 * and also adds the ability to have an exit sequence should the user want to leave
 * the level prematurely.
 */
public class Saloon {
    private Queue<String> orderQueue = new LinkedList<>();
    private Timer timer;
    private Player player;
    private boolean orderCompleted = false;
    private Scanner input;
    private boolean forceQuit = false;

    /**
     * Constructor
     * @param player The player object.
     */
    public Saloon(Player player, Scanner input) {
        this.player = player;
        this.input = input;
    }

    /**
     * Display an introduction when entering the saloon
     */
    public void introText() {
        System.out.println("Welcome to the saloon! Get ready to mix up some drinks.");
        System.out.println("Type in the drink as it appears or type 'EXIT' to stop.");
        System.out.println("-------------------------------------------------------");
    }

    /**
     * Pass a drink order to the player
     */
    public void passDrink() {
        String drink = orderRandomizer();
        orderQueue.add(drink);
        System.out.println("A customer ordered a '" + drink + "'. Type it correctly within 10 seconds!");

        startTimer(drink);
    }

    /**
     * Randomly selects a drink from the menu
     * @return a randomly selected drink string
     */
    public String orderRandomizer() {
        String[] menu = {"Old Fashioned", "White Russian", "Shirley Temple",
                         "Bloody Mary", "Sarsaparilla", "Stone Fence", "Snakebite", 
                         "Red Eye", "Gin Sling", "Mint Julep", "Cactus Cooler"};
        return menu[new Random().nextInt(menu.length)];
    }

    /**
     * Starts the timer for the current drink order
     * @param drink the drink string to be associated to the given timer instance.
     */
    private void startTimer(String drink) {
        orderCompleted = false;
        timer = new Timer();
        TimerTask task = new TimerTask() {
            int secondsRemaining = 10;

            @Override
            public void run() {
                if (secondsRemaining > 0) {
                    secondsRemaining--;
                } else {
                    timer.cancel();
                    if (!orderCompleted) {
                        System.out.println("Time's up! You lost 25 gold.");
                        player.addGold(-25);
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    /**
     * Handles user input to fulfill the order within the time limit.
     * @param currentDrink the drink order to be fulfilled by the user.
     */
    public void fillOrder(String currentDrink) {
        
        while (!orderCompleted) {
            System.out.print("Enter drink name: ");
            String userText = input.nextLine();
            
            if (userText.equalsIgnoreCase(currentDrink)) {
                // Add a new drink before removing the current one
                orderQueue.add(orderRandomizer()); 
                orderQueue.poll(); // Remove the completed drink
                
                player.addGold(50);
                orderCompleted = true;
                timer.cancel();
                System.out.println("Correct! You earned 50 gold.");
            }
            if (userText.equalsIgnoreCase("EXIT")) {
            	forceQuit = true;
            	return;
            }
        }
    }

    /**
     * Driver method to help make the game smooth.
     */
    public void play() {
        
        introText();
        System.out.println();

        for (int i = 0; i < 6; i++) {
        	if (!forceQuit) {
        		System.out.println("\n--- Order " + (i + 1) + " ---");

                // Generate & add new drink
                String drink = orderRandomizer();
                orderQueue.add(drink);
                System.out.println("A customer ordered a '" + drink + "'. Type it correctly within 10 seconds!");

                // Start timer for this drink
                startTimer(drink);

                // Prompt user input for this drink separately
                fillOrder(drink);
        	} else {
        		break;
        	}
            
        }

        System.out.println();
        System.out.println("Game Over! Final Gold: " + player.getGold());
    }
}