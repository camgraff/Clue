package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.ComputerPlayer;

public class gameActionTests {

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

	//Makes sure targets are selected correctly
	@Test
	public void testSelectTargets() {
		//if no rooms in target list, should select randomly
		board.calcTargets(9, 0, 2);
		int cell1, cell2;
		cell1 = cell2 = 0;
		for (int i=0; i<100; i++) {
			if (board.selectTarget((ComputerPlayer) board.getPlayer(2)).getRow() == 8) {
				cell1++;
			} else cell2++;
		}
		assertTrue(cell1 > 30 && cell2 > 30); //only 2 targets so each should get hit more than 30 times
		board.calcTargets(9, 5, 1);
		int cell3, cell4;
		cell1 = cell2 = cell3 = cell4 = 0;
		for (int i=0; i<100; i++) {
			if (board.selectTarget((ComputerPlayer) board.getPlayer(2)).getRow() == 8) {
				cell1++;
			} else if (board.selectTarget((ComputerPlayer)board.getPlayer(2)).getRow() == 10)  {
				cell2++;
			} else if (board.selectTarget((ComputerPlayer)board.getPlayer(2)).getColumn() == 4) {
				cell3++;
			} else cell4++;
		}
		assertTrue(cell1 > 10 && cell2 > 10 && cell3 > 10 && cell4 > 10); 

		//if room is in list, but not just visited, should visit room
		board.calcTargets(8, 3, 3);
		assertEquals (7, board.selectTarget((ComputerPlayer)board.getPlayer(2)).getRow());
		assertEquals (2, board.selectTarget((ComputerPlayer)board.getPlayer(2)).getColumn());
		board.calcTargets(17, 3, 5);
		assertEquals (14, board.selectTarget((ComputerPlayer)board.getPlayer(2)).getRow());
		assertEquals (3, board.selectTarget((ComputerPlayer)board.getPlayer(2)).getColumn());

		//if room just visited is in list, should select randomly
		board.calcTargets(21, 8, 3);
		board.selectTarget((ComputerPlayer)board.getPlayer(2));
		board.calcTargets(21, 8, 1);
		cell1 = cell2 = cell3 = 0;
		for (int i = 0; i < 100; i++) {
			if (board.selectTarget((ComputerPlayer)board.getPlayer(2)).getRow() == 21) cell1++;
			else if (board.selectTarget((ComputerPlayer)board.getPlayer(2)).getRow() == 20) cell2++;
			else if (board.selectTarget((ComputerPlayer)board.getPlayer(2)).getRow() == 22) cell3++;
		}
		assertTrue(cell1 > 20 && cell2 > 20 && cell3 > 20);
	}



}
