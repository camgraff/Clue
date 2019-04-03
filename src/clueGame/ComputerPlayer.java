package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
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

	public Solution createSuggestion(ArrayList<Card> allCards, BoardCell[][] board, Map< Character, String> legend) {
		ArrayList<Card> possiblePersons = new ArrayList<Card>();
		ArrayList<Card> possibleWeapons = new ArrayList<Card>();
		for (Card crd : allCards) {
			if (!seenCards.contains(crd) && crd.getType()==CardType.PERSON) 
				possiblePersons.add(crd);
			if (!seenCards.contains(crd) && crd.getType()==CardType.WEAPON) 
				possibleWeapons.add(crd);
		}
		int randomPerson = new Random().nextInt(possiblePersons.size());
		int randomWeapon = new Random().nextInt(possibleWeapons.size());
		return new Solution(possiblePersons.get(randomPerson), new Card(legend.get(board[getRow()][getColumn()].getInitial()), CardType.ROOM), possibleWeapons.get(randomWeapon));
	}
	
	public void addSeenCards(Card crd) {
		seenCards.add(crd);
	}

}
