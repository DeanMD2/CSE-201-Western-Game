import java.util.Timer;
import java.util.TimerTask;

/**
 * Class: saloon
 * @author Matthew Valachek
 * @version 1.0
 * Course: CSE 201 E Spring 2025
 * Written: 4/4/2025
 */
public class saloon {
	
	/**
	 * Display some basic introduction text when the user enters the room.
	 */
	public void introText() {
		System.out.println("intro text goes here");
	}
	
	/**
	 * Basic method to pass the drink to the user/player to fulfill
	 */
	public void passDrink() {
		System.out.println("Drink passed.");
	}
	
	
	/**
	 * Picks at random a drink off of the saloon menu (as represented as a fixed
	 * String array). The randomization is handled by using a random number within
	 * the length of the array.
	 * @return the string of the drink ordered.
	 */
	public String orderRandomizer() {
		// Props to Dean DiCarlo and Google Gemini for giving me ideas on drink names...
		String [] menu = {"Old Fashioned","White Russian","Shirley Temple",
				"Bloody Mary","Sarsaparilla","Stone Fence"};
		int selector = (int) (Math.random() * menu.length);
		return menu[selector];
	}
	
	/**
	 * Method to clear the order off when the user completes/fulfills the order.
	 */
	public void fillOrder() {
		//something here to indicate filling order
	}
	/**
	 * Timer to help with managing each of the orders being fulfilled. This will need
	 * to be further refined to associate with each order instead of being a generic
	 * instance as it is currently.
	 * @TODO make this method specific to each order as opposed to a generic timer.
	 */
    public void timer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int secondsRemaining = 30; 

            // Overrides the timer's run method with desired functionality
            @Override
            public void run() {
                if (secondsRemaining > 0) {
                    System.out.println("Time remaining: " + secondsRemaining + " seconds");
                    secondsRemaining--;
                } else {
                    System.out.println("Time's up!");
                    timer.cancel(); // Stop the timer
                }
            }
        };

        // Schedule the task to run every 1 second (1000 milliseconds)
        timer.scheduleAtFixedRate(task, 0, 1000);
	}
	
	
}
