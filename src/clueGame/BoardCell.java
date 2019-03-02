//@authors: Cameron Graff
//@author: James Mach
package clueGame;
import clueGame.DoorDirection;
public class BoardCell {
	private int row;
	private int column;
	private char initial;
	
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	public boolean isWalkway() {
		return true;
	}
	
	public boolean isRoom() {
		return true;

	}
	
	public boolean isDoorway() {
		return true;

	}

	public DoorDirection getDoorDirection() {
		return DoorDirection.UP;
	}

	public char getInitial() {
		return 0;
	}
	
	
	
}