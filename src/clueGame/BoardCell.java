//@authors: Cameron Graff
//@author: James Mach
package clueGame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;

import clueGame.DoorDirection;
public class BoardCell {
	private static final int SIZE = 25;
	private int row, column;
	private char initial;
	private DoorDirection doorDirection;
	private boolean isNameCell = false;
	private boolean isHumanTarget = false;

	public BoardCell(int row, int column, char initial) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
	}

	public void draw(Graphics2D g) {
		if (isWalkway()) {	
			g.setStroke(new BasicStroke(1));			
			g.setColor(Color.YELLOW);
			if (isHumanTarget) 
				g.setColor(Color.CYAN);
			g.fillRect(column*SIZE, row*SIZE, SIZE, SIZE);
			g.setColor(Color.BLACK);
			g.drawRect(column*SIZE, row*SIZE, SIZE, SIZE);
		} else {			
			g.setColor(Color.LIGHT_GRAY);	
			if (isHumanTarget) 
				g.setColor(Color.CYAN);
			g.fillRect(column*SIZE, row*SIZE, SIZE, SIZE);
		}


		if (isDoorway()) {
			g.setColor(Color.BLUE);
			g.setStroke(new BasicStroke(5));

			switch (doorDirection) {
			case UP:
				g.drawLine(column*SIZE+2, row*SIZE+2, (column+1)*(SIZE)+2, row*(SIZE)+2);
				break;
			case RIGHT:
				g.drawLine((column+1)*SIZE-2, row*SIZE+2, (column+1)*(SIZE)-2, (row+1)*(SIZE)-2);
				break;
			case LEFT:
				g.drawLine((column)*SIZE+2, (row)*SIZE+2, (column)*(SIZE)+2, (row+1)*(SIZE)-2);
				break;
			case DOWN:
				g.drawLine(column*SIZE+2, (row+1)*(SIZE)-2, (column+1)*(SIZE)-2, (row+1)*(SIZE)-2);
				break;
			}
		}


		if (isNameCell) {
			g.setColor(Color.BLUE);
			g.drawString(Board.getInstance().getLegend().get(initial), column*SIZE, row*SIZE-2);
		}
	}


	public boolean containsClick(int mouseX, int mouseY) {
		Rectangle rect = new Rectangle(column*SIZE, row*SIZE, SIZE, SIZE);
		if (rect.contains(new Point(mouseX, mouseY)))
			return true;
		return false;
	}


	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public boolean isWalkway() {
		if (initial == 'W') {
			return true;
		} else return false;
	}

	public boolean isRoom() {
		if (initial != 'W') {
			return true;
		} else return false;
	}

	public boolean isDoorway() {
		if (doorDirection == null) {
			return false;
		} else {
			return true;
		}

	}


	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		return initial;
	}

	public void setDoorDirection(DoorDirection d) {
		doorDirection = d;
	}

	public void setIsNameCell() {
		isNameCell = true;
	}

	public void setIsHumanTarget(boolean bool) {
		isHumanTarget = bool;
	}



}