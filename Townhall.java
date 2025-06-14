/**
 * Class: Townhall
 * Author: Kasey Cole 
 * @version 1.3
 * Course: CSE 201 Spring 2025
 * Written: April 2, 2025
 *
 * Purpose: Shows the player’s current gold and win status.
 */
public class Townhall {
    private Player player;

    /**
     * Constructor
     * @param player The player object.
     */
    public Townhall(Player player) {
        this.player = player;
    }

    /**
     * Displays player status and introduces the player to the game.
     */
    public void enterTownhall() {
        System.out.println("\nYou stroll into the Town Hall And see what the man at the counter has to say... ");
        System.out.println("Hey there " + player.getName() + "! Welcome to BoomTown's Town Hall,  There is plenty to do around here.\n" 
        		            + "You can try your hand at poker if your feeling lucky, or pan for gold at the nearby creek!\n"
        		            + "Rumor has it that the bartender at the saloon is looking for a extra hand as well.\n "
        		            + "If you want to live around here, you're gonna have to get to work!");
        
        System.out.println("Prospector: " + player.getName());
        System.out.println("Current Gold: " + player.getGold());

        if (player.getGold() >= 2000) {
            System.out.println("You’ve earned a place in the BoomTown Hall of Fame!");
        } else {
            System.out.println("Keep at it! 2000 gold and we can help you put up your own homestead in Boomtown!");
        }
    }
    
}
