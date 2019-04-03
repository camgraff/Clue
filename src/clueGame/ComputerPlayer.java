package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	private BoardCell justVisited;
	private Set<Card> seenCards = new HashSet<Card>();

	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
	}

	public ComputerPlayer() {
		super();
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		BoardCell[] targetArray = new BoardCell[targets.size()];
		targetArray = targets.toArray(targetArray);		
		int random = new Random().nextInt(targets.size());
		//if no rooms in targets, choose random target 
		for (BoardCell bcell : targetArray) {
			if (bcell.isRoom()) {
				if (justVisited != bcell) {
					if (bcell.isRoom())
						justVisited = bcell;
					return bcell;
				} 
			}
		}
		if (targetArray[random].isRoom())
			justVisited = targetArray[random];
		return targetArray[random];
	}

	public Solution makeAccusation(String person, String room, String weapon) {
		return new Solution(new Card(person, CardType.PERSON), new Card(room, CardType.ROOM), new Card(weapon, CardType.WEAPON));
	}

	public Solution createSuggestion() {
		return new Solution(new Card(), new Card(), new Card());
	}
	
	public void addSeenCards(Card crd) {
		//seenCards.add(crd);
	}

}
