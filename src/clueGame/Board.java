//@authors: Cameron Graff and James Mach

package clueGame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class Board extends JPanel {

	private int numRows;
	private int numColumns;
	public static final int MAX_BOARD_SIZE= 75;
	private BoardCell[][] board;
	private Map< Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private static Board theInstance = new Board();
	private Set<BoardCell> visited;
	private Player[] players;
	private ArrayList<Card> deck;
	private static Solution solution;
	
	public ArrayList<Card> allCards;


	private Board() {}

	public static Board getInstance() {
		return theInstance;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i=0; i<numRows; i++) {
			for (int j=0; j<numColumns; j++) {
				board[i][j].draw((Graphics2D )g);

			}
		}
		
		for (Player plr : players) {
			plr.draw((Graphics2D) g);
		}
	}

	//loads data files into board
	public void initialize() {
		deck = new ArrayList<Card>();
		try {			
			loadConfigFiles();
			calcAdjacencies();
		} catch (FileNotFoundException | BadConfigFormatException e) {
			e.printStackTrace();
		}
		allCards = new ArrayList<Card>(deck);
		dealCards();
	}

	//sets names of config files used to loard board and room configurations
	public void setConfigFiles(String board, String room) {
		roomConfigFile = room;
		boardConfigFile = board;
	}

	//loads all configuration files
	public void loadConfigFiles() throws FileNotFoundException, BadConfigFormatException {
		loadRoomConfig();
		loadBoardConfig();
		loadPlayerConfig();
		loadWeaponConfig();
	}

	//deals card to solution and all players
	public void dealCards() {
		//shuffle deck
		Random rand = new Random();
		for (int i = 0; i < 20; i++) 
		{ 
			int r = rand.nextInt(deck.size() - i); 
			//swapping the elements 
			Card temp = deck.get(r); 
			deck.set(r, deck.get(i)); 
			deck.set(i, temp);

		}
		//might need to change these, dont know how to get rid of error on line 111 without initializing to null.
		Card firstPerson = null;
		Card firstRoom = null;
		Card firstWeapon = null;
		//find first person card
		for(int i = 0; i < deck.size(); i++) {
			if(deck.get(i).getType() == CardType.PERSON) {
				firstPerson = deck.get(i);
				deck.remove(i);
				break;
			}
		}
		//find first room card
		for(int i = 0; i < deck.size(); i++) {
			if(deck.get(i).getType() == CardType.ROOM) {
				firstRoom = deck.get(i);
				deck.remove(i);
				break;
			}
		}

		for(int i = 0; i < deck.size(); i++) {
			if(deck.get(i).getType() == CardType.WEAPON) {
				firstWeapon = deck.get(i);
				deck.remove(i);
				break;
			}
		}

		solution = new Solution(firstPerson, firstRoom, firstWeapon);
		int i =0;
		while(!deck.isEmpty()) {
			players[i%(players.length)].recieveCard(deck.get(0));
			deck.remove(0);
			i++;
		}
	}

	//loads weapons into deck
	public void loadWeaponConfig() throws FileNotFoundException {
		FileReader in = new FileReader("weaponConfig.txt");
		Scanner weaponScan = new Scanner(in);
		while(weaponScan.hasNextLine()) {
			deck.add(new Card(weaponScan.nextLine(), CardType.WEAPON));
		}
	}

	//loads players into the board and deck
	public void loadPlayerConfig() throws FileNotFoundException {
		players = new Player[6];
		FileReader in = new FileReader("playerConfig.txt");
		Scanner playerScan = new Scanner(in);
		playerScan.useDelimiter(",");
		for (int i = 0; i < 6; i++) {
			String type, name, row, column, color;
			type = playerScan.next().trim();
			name = playerScan.next().trim();
			row = playerScan.next().trim();
			column = playerScan.next().trim();
			color = playerScan.next().trim();

			if (type.equals("Human")) {
				players[i] = new HumanPlayer(name, Integer.parseInt(row), Integer.parseInt(column) , convertColor(color));
			} else {
				players[i] = new ComputerPlayer(name, Integer.valueOf(row), Integer.valueOf(column) , convertColor(color));
			}
			deck.add(new Card(name, CardType.PERSON));
		}
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
					board[row][col].setIsNameCell();;
				default:
					break;
				}
			}
		}
	}

	//sets up legend with initial, name of each room. Adds rooms to deck
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
			String roomType = in.next();
			if (roomType.equals("Card")) {
				deck.add(new Card(roomName, CardType.ROOM));
			}
			if (!roomType.equals("Card") && !roomType.equals("Other")) {
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

	public void selectAnswer() {

	}

	public Card handleSuggestion(Solution suggestion, Player accuser) {
		int indexOfAccuser = getIndexOfPlayer(players, accuser);
		if(!canBeDisproved(suggestion, accuser))
			return null;
		for(int i = indexOfAccuser+1; i<players.length+indexOfAccuser; i++) {
			if(players[i%players.length].equals(accuser)) {
				continue;
			} else if(players[i%players.length].disproveSuggestion(suggestion)!=null) {
				return players[i%players.length].disproveSuggestion(suggestion);
			}
		
		}
		return null;
	}
	public boolean checkAccusation(Solution accusation) {
		return solution.equals(accusation);
	}

	public Color convertColor(String strColor) {
		Color color;
		try {
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color)field.get(null);
		} catch (Exception e) {
			color = null; // Not defined
		}
		return color;
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
	
	public BoardCell[][] getBoard() {
		return board;
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		return adjMatrix.get(board[row][col]);
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public Player[] getPlayers() {
		return players;
	}

	public Player getPlayer(int p) {
		if (p == 1) return (HumanPlayer) players[p-1];
		else return (ComputerPlayer) players[p-1];
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}
	
	public void setSolution(Solution newSolution) {
		solution = newSolution;
	}
	
	public ArrayList<Card> getAllCards() {

		return allCards;
	}
	
	public int getIndexOfPlayer(Player[] players, Player p) {
		for(int i=0; i<players.length; i++) {
			if(p.equals(players[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean canBeDisproved(Solution suggestion,Player accuser) {
		for(Player p : players) {
			if(!p.equals(accuser)) {
				if(p.disproveSuggestion(suggestion)!=null)
					return true;
			}
		}
		return false;
	}
}