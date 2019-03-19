package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.Color;

import clueGame.Board;

public class gameSetupTests {

	private static Board board;

	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("CTest_ClueLayout.csv", "CTest_ClueLegend.txt");		
		// Initialize will load all config files 
		board.initialize();
	}

	//Make sure 1st, 3rd, and last players were loaded correctly
	@Test
	public void testLoadPlayers() {
		assertEquals(board.getPlayer(1).getName(), "Miss Scarlet");
		assertEquals(board.getPlayer(1).getColor(), Color.RED);
		assertEquals(board.getPlayer(1).isHuman(), true);
		assertEquals(board.getPlayer(1).getRow(), 9);
		assertEquals(board.getPlayer(1).getColumn(), 0);
		
		assertEquals(board.getPlayer(3).getName(), "Mrs. Peacock");
		assertEquals(board.getPlayer(3).getColor(), Color.BLUE);
		assertEquals(board.getPlayer(3).isHuman(), false);
		assertEquals(board.getPlayer(3).getRow(), 0);
		assertEquals(board.getPlayer(3).getColumn(), 12);
		
		assertEquals(board.getPlayer(6).getName(), "Mrs. White");
		assertEquals(board.getPlayer(6).getColor(), Color.WHITE);
		assertEquals(board.getPlayer(6).isHuman(), false);
		assertEquals(board.getPlayer(6).getRow(), 17);
		assertEquals(board.getPlayer(6).getColumn(), 2);
	}

}
