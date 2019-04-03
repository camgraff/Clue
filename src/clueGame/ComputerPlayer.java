package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	private Set<Character>visited = new HashSet<Character>();
	
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		BoardCell[] targetArray = new BoardCell[targets.size()];
		targetArray = targets.toArray(targetArray);		
		int random = new Random().nextInt(targets.size());
		//if no rooms in targets, choose random target 
		for (BoardCell bcell : targetArray) {
			if (bcell.isRoom()) {
				if (!visited.contains(bcell.getInitial())) {
					visited.add(bcell.getInitial());
					return bcell;
				} 
			}
		}
		visited.add(targetArray[random].getInitial());
		return targetArray[random];
	}
	
	public void makeAccusation() {
		
	}
	
	public void createSuggestion() {
		
	}
	
}
