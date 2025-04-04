public class StoreTest {
    public static void main(String[] args) {
        // Create a test player
        Player testPlayer = new Player("Cowboy Joe", 300, 0);

        // Launch the store
        Store store = new Store();
        store.enterStore(testPlayer);

        // After exiting the store, print player's final status
        System.out.println("\n=== Final Player Stats ===");
        System.out.println("Name: " + testPlayer.getName());
        System.out.println("Money Left: $" + testPlayer.getMoney());
        System.out.println("Score: " + testPlayer.getScore());
    }
}
