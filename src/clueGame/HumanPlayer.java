package clueGame;

import java.awt.Color;

public class HumanPlayer extends Player {

	public HumanPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
	}
	
	public HumanPlayer() {
		super();
	}
	
	@Override
	public boolean isHuman() {
		return true;
	}

}
