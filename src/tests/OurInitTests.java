package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class OurInitTests {
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 23;
	public static final int NUM_COLUMNS = 24;
	
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("Rooms.csv", "legend.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}

	@Test
	public void testRooms() {
		Map<Character, String> legend = board.getLegend();
		assertEquals(LEGEND_SIZE, legend.size());
		
		assertEquals("Office", legend.get('O'));
		assertEquals("Ballroom", legend.get('B'));
		assertEquals("Library", legend.get('L'));
		assertEquals("Dining Room", legend.get('D'));
		assertEquals("Walkway", legend.get('W'));
		assertEquals("Bowling Alley", legend.get('A'));
		assertEquals("Server Room", legend.get('V'));
		assertEquals("Studio", legend.get('S'));
	}
	
	@Test
	public void testBoardDimensions() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	
	@Test
	public void FourDoorDirections() {
		BoardCell room = board.getCellAt(7, 2);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		
		room = board.getCellAt(13, 3);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		
		room = board.getCellAt(3, 14);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		
		room = board.getCellAt(11, 21);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		

		room = board.getCellAt(14, 7);
		assertFalse(room.isDoorway());	
		
		BoardCell cell = board.getCellAt(14, 13);
		assertFalse(cell.isDoorway());		

	}
	
	@Test
	public void testNumberOfDoorways() 
	{
		int numDoors = 0;
		for (int row=0; row<board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(16, numDoors);
	}
	
	@Test
	public void testRoomInitials() {
		// Test first cell in room
		assertEquals('O', board.getCellAt(0, 0).getInitial());
		assertEquals('L', board.getCellAt(4, 8).getInitial());
		assertEquals('B', board.getCellAt(3, 17).getInitial());
		// Test last cell in room
		assertEquals('S', board.getCellAt(21, 22).getInitial());
		assertEquals('V', board.getCellAt(1, 22).getInitial());
		// Test a walkway
		assertEquals('W', board.getCellAt(0, 5).getInitial());
		// Test the closet
		assertEquals('X', board.getCellAt(12,9).getInitial());
	}

}
