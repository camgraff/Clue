package clueGame;

public class Card {
	
	private String cardName;
	private clueGame.CardType cardType; 
	
	public Card() {}
	
	
	public Card(String cardName, CardType cardType) {
		super();
		this.cardName = cardName;
		this.cardType = cardType;
	}


	public boolean equals(Card other) {
		if (cardName == other.getName() && cardType == other.getType()) {
			return true;
		}
		return false;
	}
	
	public CardType getType() {
		return cardType;
	}
	
	public String getName() {
		return cardName;
	}
}
