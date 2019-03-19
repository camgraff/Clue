package tests;

/*
 * This program tests that adjacencies and targets are calculated correctly.
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class OurAdjTargetTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
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
	
	//Test locations with only walkways as adjacent locations
	// These cells are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testWalkways() {
		Set<BoardCell> testList = board.getAdjList(8, 5);
		assertTrue(testList.contains(board.getCellAt(8, 4)));
		assertTrue(testList.contains(board.getCellAt(8, 6)));
		assertTrue(testList.contains(board.getCellAt(7, 5)));
		assertTrue(testList.contains(board.getCellAt(9, 5)));
		assertEquals(4, testList.size());
		
		testList = board.getAdjList(14, 17);
		assertTrue(testList.contains(board.getCellAt(14, 18)));
		assertTrue(testList.contains(board.getCellAt(14, 16)));
		assertTrue(testList.contains(board.getCellAt(15, 17)));
		assertTrue(testList.contains(board.getCellAt(13, 17)));
		assertEquals(4, testList.size());
	}

	// Test locations within rooms
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		Set<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(4, 17);
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(14, 1);
		assertEquals(0, testList.size());
	}
	
	//Test locations that are at each edge of the board
	//Includes Locations that are beside a room cell that is not a doorway
	//These tests are WHITE on the planning spreadsheet
	@Test
	public void testAdjacencyEdgeOfBoard() {
		//TEST TOP EDGE
		Set<BoardCell> testList = board.getAdjList(0, 5);
		assertTrue(testList.contains(board.getCellAt(0, 4)));
		assertTrue(testList.contains(board.getCellAt(1, 5)));
		assertEquals(2, testList.size());
		//TEST LEFT EDGE
		testList = board.getAdjList(8, 0);
		assertTrue(testList.contains(board.getCellAt(9, 0)));
		assertTrue(testList.contains(board.getCellAt(8, 1)));
		assertEquals(2, testList.size());
		//TEST BOTTOM EDGE
		testList = board.getAdjList(22, 8);
		assertTrue(testList.contains(board.getCellAt(21, 8)));
		assertEquals(1, testList.size());
		//TEST RIGHT EDGE
		testList = board.getAdjList(18, 23);
		assertTrue(testList.contains(board.getCellAt(17, 23)));
		assertTrue(testList.contains(board.getCellAt(18, 22)));
		assertEquals(2, testList.size());
	}

	//Test locations that are doorways. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(13, 3);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(13, 4)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(3, 14);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(3, 13)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(7, 9);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(8, 9)));
		//TEST DOORWAY UP
		testList = board.getAdjList(19, 14);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(18, 14)));
		
	}
	
	// Test locations that are adjacent to a doorway
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction DOWN
		Set<BoardCell> testList = board.getAdjList(8, 2);
		assertTrue(testList.contains(board.getCellAt(7, 2)));
		assertTrue(testList.contains(board.getCellAt(9, 2)));
		assertTrue(testList.contains(board.getCellAt(8, 3)));
		assertTrue(testList.contains(board.getCellAt(8, 1)));
		assertEquals(4, testList.size());
		// Test beside a door direction RIGHT
		testList = board.getAdjList(14, 4);
		assertTrue(testList.contains(board.getCellAt(14, 3)));
		assertTrue(testList.contains(board.getCellAt(14, 5)));
		assertTrue(testList.contains(board.getCellAt(15, 4)));
		assertTrue(testList.contains(board.getCellAt(13, 4)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(22, 19);
		assertTrue(testList.contains(board.getCellAt(22, 20)));
		assertTrue(testList.contains(board.getCellAt(21, 19)));
		assertEquals(2, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(10, 21);
		assertTrue(testList.contains(board.getCellAt(11, 21)));
		assertTrue(testList.contains(board.getCellAt(9, 21)));
		assertTrue(testList.contains(board.getCellAt(10, 20)));
		assertTrue(testList.contains(board.getCellAt(10, 22)));
		assertEquals(4, testList.size());
	}
	
	
	// Tests of targets along walkways, at 1 step away
	// These are LIGHT GREEN on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(19, 4, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 4)));
		assertTrue(targets.contains(board.getCellAt(19, 5)));	
		assertTrue(targets.contains(board.getCellAt(19, 3)));
		
		board.calcTargets(6, 22, 1);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 23)));
		assertTrue(targets.contains(board.getCellAt(6, 21)));	
		assertTrue(targets.contains(board.getCellAt(5, 22)));		
		assertTrue(targets.contains(board.getCellAt(7, 22)));			

	}
	
	// Tests of just walkways, 2 steps
	// These are LIGHT GREEN on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(19, 4, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 4)));
		assertTrue(targets.contains(board.getCellAt(18, 5)));
		assertTrue(targets.contains(board.getCellAt(18, 3)));
		assertTrue(targets.contains(board.getCellAt(19, 2)));

		
		board.calcTargets(6, 22, 2);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 22)));
		assertTrue(targets.contains(board.getCellAt(5, 23)));	
		assertTrue(targets.contains(board.getCellAt(5, 21)));	
		assertTrue(targets.contains(board.getCellAt(6, 20)));
		assertTrue(targets.contains(board.getCellAt(7, 21)));	
		assertTrue(targets.contains(board.getCellAt(7, 23)));
		assertTrue(targets.contains(board.getCellAt(8, 22)));
	}
	
	// Tests of just walkways, 3 steps
	// These are LIGHT GREEN on the planning spreadsheet
	@Test
	public void testTargetsThreeSteps() {
		board.calcTargets(19, 4, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 1)));
		assertTrue(targets.contains(board.getCellAt(19, 3)));
		assertTrue(targets.contains(board.getCellAt(19, 5)));
		assertTrue(targets.contains(board.getCellAt(18, 2)));
		assertTrue(targets.contains(board.getCellAt(18, 4)));
		assertTrue(targets.contains(board.getCellAt(18, 6)));
		assertTrue(targets.contains(board.getCellAt(17, 3)));
		assertTrue(targets.contains(board.getCellAt(17, 5)));
		assertTrue(targets.contains(board.getCellAt(16, 4)));
		
		board.calcTargets(6, 22, 3);
		targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCellAt(9, 22)));
		assertTrue(targets.contains(board.getCellAt(8, 21)));	
		assertTrue(targets.contains(board.getCellAt(8, 23)));	
		assertTrue(targets.contains(board.getCellAt(7, 20)));	
		assertTrue(targets.contains(board.getCellAt(7, 22)));
		assertTrue(targets.contains(board.getCellAt(6, 21)));	
		assertTrue(targets.contains(board.getCellAt(6, 23)));	
		assertTrue(targets.contains(board.getCellAt(5, 20)));	
		assertTrue(targets.contains(board.getCellAt(5, 22)));
		assertTrue(targets.contains(board.getCellAt(4, 21)));	
		assertTrue(targets.contains(board.getCellAt(4, 23)));	
		assertTrue(targets.contains(board.getCellAt(3, 22)));	
	}	
	
	// Tests of just walkways, 4 steps
	// These are LIGHT GREEN on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(19, 4, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 0)));
		assertTrue(targets.contains(board.getCellAt(19, 2)));	
		assertTrue(targets.contains(board.getCellAt(18, 1)));	
		assertTrue(targets.contains(board.getCellAt(18, 3)));	
		assertTrue(targets.contains(board.getCellAt(18, 5)));	
		assertTrue(targets.contains(board.getCellAt(18, 7)));	
		assertTrue(targets.contains(board.getCellAt(17, 2)));	
		assertTrue(targets.contains(board.getCellAt(17, 4)));	
		assertTrue(targets.contains(board.getCellAt(17, 6)));	
		assertTrue(targets.contains(board.getCellAt(16, 5)));	
		assertTrue(targets.contains(board.getCellAt(15, 4)));	

	}	
	
	// Test getting into a room
	// These are LIGHT GREEN on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(4, 12, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 12)));
		assertTrue(targets.contains(board.getCellAt(5, 13)));
		assertTrue(targets.contains(board.getCellAt(4, 14)));
		assertTrue(targets.contains(board.getCellAt(3, 13)));
		assertTrue(targets.contains(board.getCellAt(2, 12)));
	}
	
	// Test getting into room, doesn't require all steps
	// These are LIGHT GREEN on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(18, 21, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 18)));
		assertTrue(targets.contains(board.getCellAt(18, 20)));
		assertTrue(targets.contains(board.getCellAt(18, 22)));
		assertTrue(targets.contains(board.getCellAt(17, 19)));
		assertTrue(targets.contains(board.getCellAt(17, 21)));
		assertTrue(targets.contains(board.getCellAt(17, 23)));
		assertTrue(targets.contains(board.getCellAt(16, 20)));
		assertTrue(targets.contains(board.getCellAt(16, 21)));	
		assertTrue(targets.contains(board.getCellAt(19, 19)));	

		
	}

	// Test getting out of a room
	// These are LIGHT GREEN on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(21, 7, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(21, 8)));
		// Take two steps
		board.calcTargets(21, 7, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 8)));
		assertTrue(targets.contains(board.getCellAt(22, 8)));
	}

}
