package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.Color;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;

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
	//Tests name, color, human or computer, and start location
	@Test
	public void testLoadPlayers() {
		assertEquals(board.getPlayer(1).getName(), "Miss Scarlett");
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
	
	//Make sure deck of cards was loaded correctly
	
	@Test
	public void testLoadCards() {
		//Ensure deck has all of the cards
		assertEquals(21, board.getDeck().size());
		//Ensure deck has correct number of each type of card
		int rooms = 0, weapons = 0, persons = 0;
		for (Card c : board.getDeck() ) {
			if (c.getType() == CardType.ROOM) rooms++;
			else if (c.getType() == CardType.WEAPON) weapons++;
			else if (c.getType() == CardType.PERSON) persons++;
		}
		assertEquals(9, rooms);
		assertEquals(6, weapons);
		assertEquals(6, persons);
		
		Card office = new Card("Office", CardType.ROOM);
		assertTrue(board.getDeck().contains(office));
		
		

	}

}
