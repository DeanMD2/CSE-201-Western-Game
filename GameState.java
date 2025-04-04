import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameState {
    private Deck deck;
    private TheFelt board;
    private List<Player> players;

    public GameState(List<String> playerNames) {
        deck = new Deck();
        board = new TheFelt();
        players = new ArrayList<>();
        for (String name : playerNames) {
            players.add(new Player(name, 0, 0));
        }
    }

    public void startGame() {
        deck.shuffle();

        // Deal two cards to each player (hole cards)
        int cardsPerPlayer = 2;
        for (int i = 0; i < cardsPerPlayer; i++) {
            for (Player player : players) {
                Card card = deck.dealCard();
                if (card != null) {
                    player.receiveCard(card);
                }
            }
        }

        // Debug: Show each player's hand
        for (Player player : players) {
            player.showHand();
        }

        // Deal community cards
        System.out.println("To the flop!");
        board.addCard(deck.dealCard());
        board.addCard(deck.dealCard());
        board.addCard(deck.dealCard());
        board.printBoard();
        System.out.println();

        System.out.println("And the turn!");
        board.addCard(deck.dealCard());
        board.printBoard();
        System.out.println();

        System.out.println("Finally, THE RIVER!");
        board.addCard(deck.dealCard());
        board.printBoard();
        System.out.println();

        // Determine the winner using hand evaluation.
        determineWinner();
    }

    private void determineWinner() {
        EvaluatedHand bestOverall = null;
        Player winner = null;
        List<Card> communityCards = board.getCommunityCards();

        for (Player player : players) {
            List<Card> sevenCards = new ArrayList<>();
            sevenCards.addAll(player.getHand());
            sevenCards.addAll(communityCards);

            EvaluatedHand evaluatedHand = HandEvaluator.evaluateBestHand(sevenCards);
            System.out.println(player.getName() + "'s best hand: " + evaluatedHand);

            if (bestOverall == null || evaluatedHand.compareTo(bestOverall) > 0) {
                bestOverall = evaluatedHand;
                winner = player;
            }
        }

        if (winner != null) {
            System.out.println("The winner is " + winner.getName() + " with " + bestOverall);
        } else {
            System.out.println("No winner determined.");
        }
    }
    

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> playerNames = new ArrayList<>();
        System.out.println("Enter your player name: ");
        playerNames.add(sc.next());
        playerNames.add("Doc Holliday");
        playerNames.add("Calamity Jane");
        playerNames.add("Wild Bill");
        sc.close();

        GameState game = new GameState(playerNames);
        game.startGame();
    }
}
