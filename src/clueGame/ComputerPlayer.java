package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player {
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		//choose random target 
		BoardCell[] targetsArray = (BoardCell[]) targets.toArray();
		int randomIndex = (int)(Math.random()*targetsArray.length);
		return targetsArray[randomIndex];
	}
	
	public void makeAccusation() {
		
	}
	
	public void createSuggestion() {
		
	}
	
}
