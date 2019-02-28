package clueGame;
import java.util.Map;
import java.util.Set;
public class Board {
	
	private int numRows;
	private int numColumns;
	public static final int MAX_BOARD_SIZE= 75;
	private BoardCell[][] board;
	private Map< Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private static Board theInstance = new Board();
		
	private Board() {}
		
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		
	}
	
	public void setConfigFiles(String room, String board) {
		roomConfigFile = room;
		boardConfigFile = board;
	}
	
	public void loadRoomConfig() {
		
	}
	
	
	public void loadBoardConfig() {
		
	}
	
	public void calcAdjacencies() {
		
	}
	
	public void calcTargets(BoardCell cell, int pathLength) {
		
	}

	public Map<Character, String> getLegend() {
		return legend;
	}

	public int getNumRows() {
		return numRows;
	}
	
	public int getNumCols() {
		return numColumns;
	}

	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}
}
