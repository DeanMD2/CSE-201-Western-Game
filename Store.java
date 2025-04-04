import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



public class Store {

    private Map<String, Integer> itemsForSale;

    public Store() {
        itemsForSale = new HashMap<>();
        itemsForSale.put("water", 1);
    }

    public void welcomeMenu() {
        System.out.println("");
        System.out.println("Welcome to the greatest store in this town");
        System.out.println("We have run out of stock at the moment.");
        System.out.println("//////////////////////////////////////");
    }

    public void enterStore(Player player) {
        Scanner sc = new Scanner(System.in);
        boolean shopping = true;
        
        while (shopping) {
            welcomeMenu();
            System.out.println("you have $" + player.getMoney());
            System.out.print("Type the goods' name or type exit to exit.");
            String input = sc.nextLine();
            // below are if statments for checking the situations
            if (input.equalsIgnoreCase("exit")) {
                shopping = false;
            } else if (itemsForSale.containsKey(input)) {
                int cost = itemsForSale.get(input);
                if (player.getMoney() >= cost) {
                    player.setMoney(player.getMoney() - cost);
                    System.out.println("You get" + input + "using" + cost);
                } else {
                    System.out.println("Not enough to buy it");
                }
            } else {
                System.out.println("That item is out of stock at the moment");
                int b = (int)(Math.random() * 5) + 1;
                if (b < 4){
                    int a = (int)(Math.random() * 5) + 1;
                    System.out.println("We are sorry for the inconvenience and have decided to give you " + a + " dollars.");
                    player.setMoney(player.getMoney() + a);
                }
            }
        }

        System.out.println("Feel free to coming by!");
        sc.close();
    }
}
