//@authors: Cameron Graff and James Mach

package clueGame;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
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
	private Set<BoardCell> visited;

	private Board() {}

	public static Board getInstance() {
		return theInstance;
	}

	//loads data files into board
	public void initialize() {
		try {			
			loadRoomConfig();
			loadBoardConfig();
			calcAdjacencies();
		} catch (FileNotFoundException | BadConfigFormatException e) {
			e.printStackTrace();
		}
	}

	//sets names of config files used to loard board and room configurations
	public void setConfigFiles(String board, String room) {
		roomConfigFile = room;
		boardConfigFile = board;
	}

	//sets up board with row, column, initial of each room
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		numRows = 1;
		numColumns = 1;
		//calculates number of rows and columns
		FileReader in = new FileReader(boardConfigFile);
		Scanner countRowCols = new Scanner(in);
		for (char c : countRowCols.nextLine().toCharArray()) {
			if (c == ',') {
				numColumns++;
			}
		}
		while(countRowCols.hasNextLine()) {
			numRows++;
			countRowCols.nextLine();
		}

		//makes sure all rows have the same number of columns, otherwise throws exception
		in = new FileReader(boardConfigFile);
		Scanner sameNumCols = new Scanner(in);
		while(sameNumCols.hasNextLine()) {
			int cols = 1;
			for (char c : sameNumCols.nextLine().toCharArray()) {
				if (c == ',') {
					cols++;
				}
			}
			if (cols != numColumns) {
				throw new BadConfigFormatException("badCols");
			}
		}

		//loads cells into board
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		in = new FileReader(boardConfigFile);
		Scanner reader = new Scanner(in);
		for (int row = 0; row < numRows; row++) {
			String nextRow = reader.nextLine();
			int i = 0;
			for (int col = 0; col < numColumns; col++) {
				char c = nextRow.charAt(i);
				i++;
				if (c == ',') {
					c = nextRow.charAt(i);
					i++;
				}
				if (!legend.containsKey(c)) {
					throw new BadConfigFormatException("badRoom");
				}
				board[row][col] = new BoardCell(row, col, c);
				if (i == nextRow.length()) {
					break;
				}
				c = nextRow.charAt(i);
				i++;
				//set door direction
				switch(c) {
					case ',' :
						continue;
					case 'R' :
						board[row][col].setDoorDirection(DoorDirection.RIGHT);
						break;
					case 'L' :
						board[row][col].setDoorDirection(DoorDirection.LEFT);
						break;
					case 'U' :
						board[row][col].setDoorDirection(DoorDirection.UP);
						break;
					case 'D' :
						board[row][col].setDoorDirection(DoorDirection.DOWN);
						break;
					case 'N' :
						continue;
					default:
						break;
				}
			}
		}
	}

	//sets up legend with initial, name of each room
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		legend = new HashMap<Character, String>();
		FileReader reader = new FileReader(roomConfigFile);
		Scanner in = new Scanner(reader);
		while(in.hasNextLine()) {
			String roomName = in.next();
			if (roomName.length() != 2) {
				throw new BadConfigFormatException("badFormat");
			}
			char key = roomName.charAt(0);
			roomName = in.next();
			while (roomName.charAt(roomName.length()-1) != ',') {
				roomName = roomName.concat(" " + in.next());
			}
			roomName = roomName.substring(0, roomName.length()-1);
			legend.put(key, roomName);
			roomName = in.next();
			if (!roomName.equals("Card") && !roomName.equals("Other")) {
				throw new BadConfigFormatException("badFormat");
			}
		}
	}
	
	//calculates the adjacency list for each cell in the board
	public void calcAdjacencies() {
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		for(int r =0; r <numRows; r++) {
			for(int c =0; c< numColumns; c++) {
				Set<BoardCell> adjCells = new HashSet<BoardCell>();
				if(board[r][c].isDoorway()) {
					switch(board[r][c].getDoorDirection()) {
						case LEFT :
							adjCells.add(board[r][c-1]);
							break;
						case RIGHT : 
							adjCells.add(board[r][c+1]);
							break;
						case UP :
							adjCells.add(board[r-1][c]);
							break;
						case DOWN :
							adjCells.add(board[r+1][c]);
							break;
					}
				} else if (board[r][c].isWalkway()){

					if(r!=0) {
						if (board[r-1][c].isWalkway() || ((board[r-1][c].isDoorway()) && board[r-1][c].getDoorDirection() == DoorDirection.DOWN)) {
							adjCells.add(board[r-1][c]);
						}
					}
					if(r!=numRows-1) {
						if (board[r+1][c].isWalkway() || (board[r+1][c].isDoorway() && board[r+1][c].getDoorDirection() == DoorDirection.UP)) {
							adjCells.add(board[r+1][c]);
						}
					}
					if(c!=0) {
						if (board[r][c-1].isWalkway() || (board[r][c-1].isDoorway() && board[r][c-1].getDoorDirection() == DoorDirection.RIGHT)) {
							adjCells.add(board[r][c-1]);
						}
					}
					if(c!=numColumns-1) {
						if (board[r][c+1].isWalkway() || (board[r][c+1].isDoorway() && board[r][c+1].getDoorDirection() == DoorDirection.LEFT)) {
							adjCells.add(board[r][c+1]);
						}
					}
				}

				adjMatrix.put(board[r][c], adjCells); 
			}
		}
	}
	
	//secondary function used in calcTargets. Finds targets cells that are numSteps away from a cell
	private void findAllTargets(BoardCell thisCell, int numSteps) {
		for (BoardCell cell : adjMatrix.get(thisCell)) {
			if (visited.contains(cell)) {
				continue;
			}
			visited.add(cell);
			if ((numSteps == 1) || cell.isDoorway()) {
				targets.add(cell);
			} else {
				findAllTargets(cell, numSteps - 1);
			}
			visited.remove(cell);
		}
	}
	
	//uses adjacency lists to calculate targets that are numSteps away from a cell on the board
	public void calcTargets(int row, int col, int pathLength) {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(board[row][col]);
		findAllTargets(board[row][col], pathLength);
	}

	public Map<Character, String> getLegend() {
		return legend;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		return adjMatrix.get(board[row][col]);
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}
}