package clueGame;

import java.awt.Color;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	
	
	public Player(String playerName, int row, int column, Color color) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
	}


	Card disproveSuggestion(Solution suggestion) {
		return null;
	}
	
	public String getName() {
		return playerName;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}


	public void setRow(int row) {
		this.row = row;
	}


	public void setColumn(int column) {
		this.column = column;
	}


	public void setColor(Color color) {
		this.color = color;
	}


	public boolean isHuman() {
		return false;
	}
		
}
