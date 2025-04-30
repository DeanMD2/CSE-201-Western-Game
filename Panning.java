import java.util.Random;
import java.util.Scanner;

/**
 * Class: Panning
 * Author: 
 * @version 1.0
 * Course: CSE 201 Spring 2025
 * Written: April 25, 2025
 *
 * Purpose: Text-based panning minigame where player can search for gold.
 */
public class Panning {
    private ThePlayer1 player;
    private final Random random;
    private final String[] locations = {
		"Dust Devil Ditch",      
		"Silver Creek",
		"Dead Man’s Gulch",        
		"Miner’s Hollow",
		"Fortune Falls"
    };

    /**
     * Constructor
     * @param player The player object.
     */
    public Panning(ThePlayer1 player) {
        this.player = player;
        this.random = new Random();
    }

    /**
     * Starts the panning minigame.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWelcome to the Panning Grounds!");
        System.out.println("Choose a location to pan:");

        for (int i = 0; i < locations.length; i++) {
            System.out.println((i + 1) + ". " + locations[i]);
        }

        System.out.print("Enter your choice (1-" + locations.length + "): ");
        int choice = scanner.nextInt();

        if (choice < 1 || choice > locations.length) {
            System.out.println("That spot’s no good. Try again next time.");
            return;
        }

        int outcome = random.nextInt(3); // 0 = nothing, 1 = fool’s gold, 2 = real gold
        if (outcome == 0) {
            System.out.println("\nYou found nothing but dirt.");
        } else if (outcome == 1) {
            System.out.println("\nYou found Fool’s Gold!");
        } else {
            System.out.println("\nYeehaw! You struck real gold! (+50 gold)");
            player.addGold(50);
        }

        System.out.println("\nCurrent Gold: " + player.getGold());
    }
}
