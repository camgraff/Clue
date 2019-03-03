//@authors: Cameron Graff
//@author: James Mach
package clueGame;
import clueGame.DoorDirection;
public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDirection;
	
	public BoardCell(int row, int column, char initial) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
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
	
	
	
	
}