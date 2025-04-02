import java.util.Collections;
import java.util.Random;

public class Deck {
	
	private static int DECK_SIZE = 52;
	private String[] theDeck;
	private int currCardIndex;
	
	public Deck() {
		
		String[] cards = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
		String[] suits = { "♥", "♠", "♣", "♦"};
		theDeck = new String[DECK_SIZE];
		currCardIndex = 0;
		
		for (int i = 0; i < suits.length; i++) {
			for (int j = 0; j < cards.length; j++) {
				int index = cards.length * i + j;
				theDeck[index] = (cards[j] + suits[i]);
			}
		}
	}
	
	public void Shuffle() {
		Random rand = new Random();
		for (int i = theDeck.length - 1; i > 0; i--) {
			int j = rand.nextInt(i + 1);
			String temp =  theDeck[i];
			theDeck[i] = theDeck[j];
			theDeck[j] = temp;
			
		}
		
		currCardIndex = 0;
		
	}
	
	public String dealCard() {
		if (currCardIndex < DECK_SIZE) {
			return theDeck[currCardIndex++];
		} else {
			return null;
		}
	}
	
	public void Print() {
		for (int i = 0; i < theDeck.length; i++) {	
			System.out.print(theDeck[i] + " ");
			if ((i + 1) % 13 == 0) {
				System.out.println();
			}
		}
	}
		
	
	public String[] getDeck() {
		return theDeck;
	}
	
	
		
	
}
