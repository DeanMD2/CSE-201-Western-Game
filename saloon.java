import java.util.Timer;
import java.util.TimerTask;

public class saloon {
	public void introText() {
		System.out.println("intro text goes here");
	}
	public void passDrink() {
		System.out.println("Drink passed.");
	}
	public void passFood() {
		System.out.println("Food passed.");
	}
	public void orderRandomizer() {
		// something here to randomize orders
	}
	public void fillOrder() {
		//something here to indicate filling order
	}
	
    public void timer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int secondsRemaining = 30; 

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
