
public class Card {
	public enum Suit {
		HEARTS("♥"), DIAMONDS("♦"), SPADES("♠"), CLUBS("♣");
		
		private final String symbol;
		
		Suit(String symbol) {
			this.symbol = symbol;
		}
		
		public String getSymbol() {
			return symbol;
		}
	}
	
	public enum Rank {
		TWO("2", 2), THREE("3", 3), FOUR("4", 4), FIVE("5", 5), SIX("6", 6),
        SEVEN("7", 7), EIGHT("8", 8), NINE("9", 9), TEN("10", 10),
        JACK("J", 11), QUEEN("Q", 12), KING("K", 13), ACE("A", 14);
		
		
		private String display;
		private final int value;
		
		Rank(String display, int value) {
			this.display = display;
			this.value = value;
		}
		
		public String getDisplay() {
			return display;
		}
		
		public int getValue() {
			return value;
		}
	}
	
	private final Suit suit;
	private final Rank rank;
	
	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public Suit getSuit() {
		return suit;
	}
	
	@Override
    public String toString() {
        return rank.getDisplay() + suit.getSymbol();
    }
	
	public int compareTo(Card other) {
		return Integer.compare(this.rank.getValue(), other.rank.getValue());
	}
	
}
