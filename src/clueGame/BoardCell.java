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
		
	}
	
	public boolean isRoom() {
		
	}
	
	public boolean isDoorway() {
		
	}

	public DoorDirection getDoorDirection() {
		return DoorDirection.UP;
	}
	
	
	
}