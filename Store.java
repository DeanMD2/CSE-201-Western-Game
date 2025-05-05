import java.util.Scanner;

/**
 * Class: Store
 * Author: 
 * @version 1.0
 * Course: CSE 201 Spring 2025
 * Written: April 25, 2025
 *
 * Purpose: Allows the player to buy items with gold.
 */
public class Store {
    private Player player;
    private Scanner input;

    /**
     * Constructor
     * @param player The player object.
     */
    public Store(Player player, Scanner input) {
        this.player = player;
        this.input = input;
    }

    /**
     * Displays store items and processes purchases.
     */
    public void enterStore() {

        System.out.println("\nWelcome to the General Store!");
        System.out.println("Available Items:");
        System.out.println("1. Pickaxe (20 gold)");
        System.out.println("2. Canteen (10 gold)");
        System.out.println("3. Donkey (50 gold)");
        System.out.print("Choose an item to buy or 0 to leave: ");

        int choice = input.nextInt();
        int cost = 0;
        String item = "";

        switch (choice) {
            case 1:
                item = "Pickaxe";
                cost = 20;
                break;
            case 2:
                item = "Canteen";
                cost = 10;
                break;
            case 3:
                item = "Donkey";
                cost = 50;
                break;
            case 0:
                System.out.println("You head out of the store.");
                return;
            default:
                System.out.println("That item doesn't exist.");
                return;
        }

        if (player.spendGold(cost)) {
            System.out.println("You bought a " + item + "!");
        } else {
            System.out.println("Not enough gold!");
        }

        System.out.println("Current Gold: " + player.getGold());
    }
}
