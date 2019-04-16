package clueGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Player {
	private static final int SIZE = 25;
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> hand = new ArrayList<Card>();
	BoardCell currentCell;

	public Player() {}
	
	public Player(String playerName, int row, int column, Color color) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.fillOval(column*SIZE+3, row*SIZE+3, SIZE-5, SIZE-5);
		g.setStroke(new BasicStroke(1));
		g.setColor(color.BLACK);
		g.drawOval(column*SIZE+3, row*SIZE+3, SIZE-5, SIZE-5);
	}


	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> disproveCards = new ArrayList<Card>();
		for (Card crd : hand) {
			if (suggestion.contains(crd))
				disproveCards.add(crd);
		}
		if (disproveCards.size() == 0) 
			return null;
		int random = new Random().nextInt(disproveCards.size());
		return disproveCards.get(random);
	}
	
	public void recieveCard(Card c) {
		hand.add(c);
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
	
	public ArrayList<Card> getHand() {
		return hand;
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
	
	public boolean equals(Player other) {
		if(playerName != other.getName())
			return false;
		if(row != other.getRow())
			return false;
		if(column != other.getColumn())
			return false;
		if(color != other.getColor())
			return false;
		return true;
	}
	
	public void setCurrentCell(BoardCell b) {
		currentCell = b;
	}
	
		
}
