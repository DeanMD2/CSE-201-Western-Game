/**
* Class: Townhall
* @author kasey cole
* @version 1.0
* Course : CSE 201 fall 2025
* Written: April 2, 2025
*
* Purpose: – This class represents the Townhall room in our game
* 
*/
public class Townhall {
    private Player player;

    /**
     * Constructs a Townhall instance associated with a player.
     * 
     * @param player The player interacting with the townhall.
     */
    public Townhall(Player player) {
        this.player = player;
    }
    
    /**
     * Displays an introduction message for the Townhall.
     */ 
    public void IntroText() {
        System.out.println("\n=== TOWNHALL ===");
        System.out.println("Welcome to the Townhall, ");
     
    }
    
     /**
     * Checks the player's current score and money.
     * If the player has at least $2000, they win the game.
     * Otherwise, it displays how much more money is needed to win.
     */
    public void checkWin() {
    	System.out.println("Current Score:" + player.getScore());
    	   
         System.out.println("Current Money: $" + player.getMoney());
         // I just set the win condition to purchase the ranch to 2000 dollars
         if (player.getScore() >= 2000 ) {
        	  System.out.println("Congradulations! you have earned enough money to"
        	  		+ " purchase your ranch!");	     	  
          } else {
        		System.out.println("You dont have enough money to purchase the ranch."
                     + " You need $"  + (2000 - player.getMoney()) + " more." ); 
        		 	  
          }              
    }
}

