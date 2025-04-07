import java.util.Scanner;

/**
 * Represents a human player.
 */
public class HumanPlayer extends Player {

    /**
     * Creates a HumanPlayer with the given attributes.
     *
     * @param name  player's name.
     * @param money player's money.
     * @param score player's score.
     * @param stack player's betting stack.
     */
    public HumanPlayer(String name, int money, int score, int stack) {
        super(name, money, score, stack);
    }
    
    /**
     * Prompts the human player for an action.
     *
     * @param gameState the current game state.
     * @return the chosen action.
     */
    @Override
    public GameState.Action makeAction(GameState gameState) {
        return promptAction();
    }
    
    /**
     * Prompts and returns the human player's action.
     *
     * @return the chosen action.
     */
    public GameState.Action promptAction() {
        Scanner sc = new Scanner(System.in);
        System.out.println(getName() + ", enter your action (FOLD, CHECK/CALL, RAISE): ");
        String input = sc.next().toUpperCase();
        switch(input) {
            case "FOLD":
                return GameState.Action.FOLD;
            case "RAISE":
                return GameState.Action.RAISE;
            default:
                return GameState.Action.CALL;
        }
    }
}
