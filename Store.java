import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Class: Store
 * @author Hanwen Xing
 * @version 1.7
 * Course: CSE201 Spring 2025
 * Written: April 24, 2025
 *
 * Purpose: This class represents a store where players can purchase items.
 * It handles store display, purchasing logic, and occasional bonus money giveaways.
 */
public class Store {

    /** Maps item names to their prices */
    private Map<String, Integer> itemsForSale;

    /**
     * Constructor initializes store with default items.
     */
    public Store() {
        itemsForSale = new HashMap<>();
        itemsForSale.put("water", 1);
        itemsForSale.put("bread", 2);
        itemsForSale.put("whiskey", 5);
        itemsForSale.put("arrow", 3);
        itemsForSale.put("torch", 4);
        itemsForSale.put("dagger", 10);
    }


    /**
     * Displays the welcome menu when a player enters the store.
     */
    public void welcomeMenu() {
        System.out.println();
        System.out.println("Welcome to the greatest store in this town");
        System.out.println("We have run out of stock at the moment.");
        System.out.println("//////////////////////////////////////");
        System.out.println();
    }

    /**
     * Interacts with the player and handles purchasing logic.
     *
     * @param player the Player object who enters the store
     */
    public void enterStore(Player player) {
        Scanner sc = new Scanner(System.in);
        boolean shopping = true;

        while (shopping) {
            welcomeMenu();
            System.out.println("You have $" + player.getMoney());
            System.out.print("Type the goods' name or type 'exit' to leave: ");

            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                shopping = false;
            } else if (itemsForSale.containsKey(input)) {
                int cost = itemsForSale.get(input);

                if (player.getMoney() >= cost) {
                    player.setMoney(player.getMoney() - cost);
                    System.out.println("You get " + input + " using $" + cost);
                } else {
                    System.out.println("Not enough money to buy it.");
                }
            } else {
                System.out.println("That item is out of stock at the moment.");
                int bonusChance = (int) (Math.random() * 5) + 1;

                if (bonusChance < 2) {
                    int bonus = (int) (Math.random() * 5) + 1;  // a lucky chance to get money in store
                    System.out.println("We are sorry for the inconvenience and have decided to give you $" + bonus + ".");
                    player.setMoney(player.getMoney() + bonus);
                }
            }
        }

        System.out.println("Feel free to come by again!");
        //sc.close();  // not close scanner in shop
    }
}
