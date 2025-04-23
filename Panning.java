import java.util.Random;
import java.util.Scanner;

/**
 * Class: Panning
 * @author Jason Hathaway
 * @version 1.0
 * Course : CSE 201 Spring 2025
 * Written: April 23, 2025
 *
 * Purpose: Implements a text-based gold-panning minigame. Players choose from 
 * named spots and receive a random outcome.
 */
public class Panning {
	
    // Available panning spots
    private static final int NUMBER_OF_SPOTS = 5;

    // Spot names
    private static final String[] SPOT_LABELS = {
        "Golden Bend",
        "Silver Creek",
        "Prospect Point",
        "Miner’s Hollow",
        "Fortune Falls"
    };

    // Possible outcomes when panning
    private static final String[] PANNING_OUTCOMES = {
        "Gold",
        "Fool’s Gold",
        "Nothing"
    };

    // Random number generator for outcomes
    private final Random randomGenerator;
    // Scanner for reading user input
    private final Scanner inputScanner;

    /**
     * Constructor that initializes the random generator and input scanner.
     */
    public Panning() {
        this.randomGenerator = new Random();
        this.inputScanner   = new Scanner(System.in);
    }

    /**
     * Entry point: runs the panning game loop until the player quits.
     */
    public void runMinigame() {
        System.out.println("Welcome to the Panning Minigame!");
        boolean continuePlaying = true;

        while (continuePlaying) {
            int chosenSpot = spotPrompt();
            panningResult(chosenSpot);
            continuePlaying = playPrompt();
        }

        System.out.println("Thanks for playing! Goodbye.");
    }

    /**
     * Prompts the user to select a spot from the themed list.
     * @return the valid spot number chosen by the user
     */
    private int spotPrompt() {
        int spotNumber = -1;

        System.out.println("\nChoose a spot to pan:");
        for (int i = 0; i < NUMBER_OF_SPOTS; i++) {
            System.out.printf("  %d: %s%n", i + 1, SPOT_LABELS[i]);
        }

        while (spotNumber < 1 || spotNumber > NUMBER_OF_SPOTS) {
            System.out.printf("Enter choice (1-%d): ", NUMBER_OF_SPOTS);
            if (inputScanner.hasNextInt()) {
                spotNumber = inputScanner.nextInt();
            } else {
                inputScanner.next();
            }
        }

        return spotNumber;
    }

    /**
     * Randomly selects an outcome and displays the result.
     * @param spotNumber the spot the user panned (1-based index)
     */
    private void panningResult(int spotNumber) {
        int randomIndex = randomGenerator.nextInt(PANNING_OUTCOMES.length);
        String foundResource = PANNING_OUTCOMES[randomIndex];
        String spotLabel = SPOT_LABELS[spotNumber - 1];

        System.out.printf(
            "\nYou panned at %s and found %s!%n%n",
            spotLabel,
            foundResource
        );
    }

    /**
     * Asks the player whether to play again.
     * @return true if the player enters 'y' or 'yes' (case-insensitive)
     */
    private boolean playPrompt() {
        System.out.print("Play again? (y/n): ");
        String response = inputScanner.next().trim().toLowerCase();
        return response.equals("y") || response.equals("yes");
    }

    /**
     * Main method appears at end of file per coding standard.
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Panning game = new Panning();
        game.runMinigame();
    }
}
