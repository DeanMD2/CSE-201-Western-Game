public class Townhall {
    private Player player;

    public Townhall(Player player) {
        this.player = player;
    }
  
    public void IntroText() {
        System.out.println("\n=== TOWNHALL ===");
        System.out.println("Welcome to the Townhall, ");
     
    }
    
    
    public void checkWin() {
    	System.out.println("Current Score:" + player.getScore());
    	   
         System.out.println("Current Money: $" + player.getMoney());
         // I just set the win condition to purchase the ranch to 2000 dollars
         if (player.getScore() >= 2000 ) {
        	  System.out.println("Congradulations! you have earned enough money to "
        	  		+ " purchase your ranch!");	     	  
          } else {
        		System.out.println("You dont have enough money to purchase the ranch. You need " 
                + (2000 - player.getMoney()) + " more dollars " ); 
        		 	  
          }              
    }
}

