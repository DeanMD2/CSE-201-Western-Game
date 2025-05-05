// Dealer.java
import java.util.List;

/**
 * Class: Dealer
 * Author: Dean DiCarlo
 * @version 1.0
 * Course: CSE 201 Spring 2025
 *
 * Purpose: Manages the deck and the board, handling the dealing of hole cards
 * and community cards for each stage of a poker hand, and provides the current
 * board state for display or evaluation.
 */
public class Dealer {
    private final Deck deck = new Deck();
    private final TheFelt board = new TheFelt();

    /**
     * Shuffle the deck and clear the board for a new hand.
     */
    public void shuffleAndReset() {
        deck.shuffle();
        board.clear();
    }

    /**
     * Deal two hole cards to each non‑folded player.
     *
     * @param players the list of players to receive hole cards
     */
    public void dealHoleCards(List<PokerPlayer> players) {
        for (int round = 0; round < 2; round++) {
            for (PokerPlayer p : players) {
                if (!p.isFolded()) {
                    p.receiveCard(deck.dealCard());
                }
            }
        }
    }

    /**
     * Deal three community cards (the flop) to the board.
     */
    public void dealFlop() {
        deck.dealCard();            // burn
        board.addCard(deck.dealCard());
        board.addCard(deck.dealCard());
        board.addCard(deck.dealCard());
    }

    /**
     * Deal one community card (the turn) to the board.
     */
    public void dealTurn() {
        deck.dealCard();            // burn
        board.addCard(deck.dealCard());
    }

    /**
     * Deal one community card (the river) to the board.
     */
    public void dealRiver() {
        board.addCard(deck.dealCard());
    }

    /**
     * Get the current list of community cards on the board.
     *
     * @return the list of community cards
     */
    public java.util.List<Card> getCommunity() {
        return board.getCommunityCards();
    }

    /**
     * Print the current board (community cards) to the console.
     */
    public void showBoard() {
        board.printBoard();
    }
}
