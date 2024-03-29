package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

public class gameSetupTests {

	private static Board board;

	@Before
	public void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("Rooms.csv", "legend.txt");		
		// Initialize will load all config files 
		board.initialize();
	}

	//Make sure 1st, 3rd, and last players were loaded correctly
	//Tests name, color, human or computer, and start location
	@Test
	public void testLoadPlayers() {
		assertEquals(board.getPlayer(0).getName(), "Miss Scarlett");
		assertEquals(board.getPlayer(0).getColor(), Color.RED);
		assertEquals(board.getPlayer(0).isHuman(), true);
		assertEquals(board.getPlayer(0).getRow(), 9);
		assertEquals(board.getPlayer(0).getColumn(), 0);

		assertEquals(board.getPlayer(2).getName(), "Mrs. Peacock");
		assertEquals(board.getPlayer(2).getColor(), Color.BLUE);
		assertEquals(board.getPlayer(2).isHuman(), false);
		assertEquals(board.getPlayer(2).getRow(), 0);
		assertEquals(board.getPlayer(2).getColumn(), 12);

		assertEquals(board.getPlayer(5).getName(), "Mrs. White");
		assertEquals(board.getPlayer(5).getColor(), Color.WHITE);
		assertEquals(board.getPlayer(5).isHuman(), false);
		assertEquals(board.getPlayer(5).getRow(), 17);
		assertEquals(board.getPlayer(5).getColumn(), 2);
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
		board.dealCards();
		//Ensure all cards have been dealt (i.e. deck is empty)
		assertEquals(0, board.getDeck().size());
		//Ensure all players have roughly the same number of cards
		int leastCards = board.getPlayer(5).getHand().size();
		for (Player p : board.getPlayers()) {
			assertTrue (p.getHand().size() >= leastCards && p.getHand().size() <= leastCards + 1);
		}
		//Ensure no two players have the same card
		for (int i = 0; i <= 5; i++) {
			for (int j = i+1; j <=5; j++) {
				for (Card m : board.getPlayer(i).getHand()) {
					for (Card n : board.getPlayer(j).getHand()) {
						assertTrue(!m.equals(n));
					}
				}
			}
		}
	}
}



