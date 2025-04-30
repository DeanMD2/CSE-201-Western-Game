import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class: Saloon
 * @author Matthew Valachek
 * @version 1.1
 * Course: CSE 201 E Spring 2025
 * Written: 4/4/2025
 */
public class Saloon {
    private Queue<String> orderQueue = new LinkedList<>();
    private Timer timer;
    private ThePlayer1 player;
    private boolean orderCompleted = false;

    /**
     * Constructor
     * @param player The player object.
     */
    public Saloon(ThePlayer1 player) {
        this.player = player;
    }

    /**
     * Display an introduction when entering the saloon
     */
    public void introText() {
        System.out.println("Welcome to the saloon! Get ready to mix up some drinks.");
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
                         "Bloody Mary", "Sarsaparilla", "Stone Fence"};
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
                    System.out.println("Time remaining: " + secondsRemaining + " seconds");
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
        fillOrder(drink);
    }

    /**
     * Handles user input to fulfill the order within the time limit.
     * @param currentDrink the drink order to be fulfilled by the user.
     */
    public void fillOrder(String currentDrink) {
        Scanner userIn = new Scanner(System.in);
        while (!orderCompleted) {
            System.out.print("Enter drink name: ");
            String userText = userIn.nextLine();
            if (userText.equalsIgnoreCase(currentDrink)) {
                orderQueue.poll();
                player.addGold(50);
                orderCompleted = true;
                timer.cancel();
                System.out.println("Correct! You earned 50 gold.");
            }
        }
        userIn.close();
    }

    /** Handles the game flow by passing the player 6 drink orders */
    /**
     * Driver method to help make the game smooth.
     */
    public void play() {
        Scanner userIn = new Scanner(System.in);
        
        introText();
        System.out.println();
    
        for (int i = 0; i < 6; i++) {
            System.out.println("\n--- Order " + (i + 1) + " ---");
            passDrink();
        
            String currentDrink = orderQueue.peek();
            orderCompleted = false;

            // Start timer for current drink
            startTimer(currentDrink);
        
            while (!orderCompleted) {
                System.out.print("Enter drink name: ");
                String userText = userIn.nextLine();
                if (userText.equalsIgnoreCase(currentDrink)) {
                    orderQueue.poll();
                    player.addGold(3);
                    orderCompleted = true;
                    timer.cancel();
                    System.out.println("Correct! You earned 3 gold.");
                }
            }
        }
    System.out.println();
    System.out.println("Game Over! Final Gold: " + player.getGold());
    userIn.close();
}
}