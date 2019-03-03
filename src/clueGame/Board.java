package clueGame;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.zip.InflaterInputStream;
public class Board {

	private int numRows = 1;
	private int numColumns = 1;
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
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		try {
			loadBoardConfig();
			loadRoomConfig();
		} catch (Exception FileIOException) {
			
		}
	}

	public void setConfigFiles(String room, String board) {
		roomConfigFile = room;
		boardConfigFile = board;
	}
	
	//sets up board with row, column, initial of each room
	public void loadRoomConfig() throws IOException, BadConfigFormatException {
		InputStream in = new FileInputStream(roomConfigFile);
		in.mark(0);
		Scanner sc = new Scanner(in);
		for (char c : sc.nextLine().toCharArray()) {
			if (c == ',') {
				numColumns++;
			}
		}
		while(sc.hasNextLine()) {
			numRows++;
			sc.nextLine();
		}
		in = new FileInputStream(roomConfigFile);		
		Reader reader = new InputStreamReader(in);
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				char c = (char) reader.read();
				if (c == ',' || c == '\n' || c =='\r') {
					c = (char) reader.read();
				}
				board[row][col] = new BoardCell(row, col, c);
				c = (char) reader.read();
				if (c == 'R') {
					board[row][col].setDoorDirection(DoorDirection.RIGHT);
				}  else if (c == 'L'){
					board[row][col].setDoorDirection(DoorDirection.LEFT);
				} else if (c == 'U'){
					board[row][col].setDoorDirection(DoorDirection.UP);
				} else if (c == 'D'){
					board[row][col].setDoorDirection(DoorDirection.DOWN);
				} else if (c == 'N') {
					continue;
				} else {
					reader.close();
					throw new BadConfigFormatException(""+c);				
				}

			}

		}
	}

	//sets up legend with initial, name of each room
	public void loadBoardConfig() throws IOException, BadConfigFormatException {
		legend = new HashMap<Character, String>();
		FileReader reader = new FileReader(boardConfigFile);
		Scanner in = new Scanner(reader);
		while(in.hasNextLine()) {
			char key = in.next().charAt(0);
			String value = in.next();
			while (value.charAt(value.length()-1) != ',') {
				value = value.concat(" " + in.next());
			}
			value = value.substring(0, value.length()-1);
			legend.put(key, value);
			in.next();
		}
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

	public int getNumColumns() {
		return numColumns;
	}

	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}
}
