package tests;

import static org.junit.Assert.*;

import org.junit.Before;
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
		board.setConfigFiles("rooms.csv", "legend.txt");		
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
		//Ensure deck loads names correctly
		assertEquals("Office", board.getDeck().get(0).getName());
		assertEquals("Miss Scarlett", board.getDeck().get(9).getName());
		assertEquals("Candlestick", board.getDeck().get(15).getName());

	}
	
	//Make sure cards are dealt correctly
	@Test
	public void testDealCards() {
		//Ensure all cards have been dealt (i.e. deck is empty)
		assertEquals(0, board.getDeck().size());
		//Ensure all players have roughly the same number of cards
		
	}

}
