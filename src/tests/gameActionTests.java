package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
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
		int cell1, cell2, cell3;
		cell1 = cell2 = cell3 = 0;
		BoardCell currentTarget;
		for (int i=0; i<100; i++) {
			currentTarget = board.selectTarget((ComputerPlayer) board.getPlayer(2));
			if (currentTarget.getRow() == 8) 
				cell1++;
			else if (currentTarget.getRow() == 9)
				cell2++;
			else if (currentTarget.getRow() == 10)
				cell3++;
		}
		assertTrue(cell1 > 20 && cell2 > 20 && cell3 > 20); //only 3 targets so each should get hit more than 20 times
		board.calcTargets(9, 5, 1);
		int cell4;
		cell1 = cell2 = cell3 = cell4 = 0;
		for (int i=0; i<100; i++) {
			currentTarget = board.selectTarget((ComputerPlayer) board.getPlayer(2));
			if (currentTarget.getRow() == 8) {
				cell1++;
			} else if (currentTarget.getRow() == 10)  {
				cell2++;
			} else if (currentTarget.getColumn() == 4) {
				cell3++;
			} else if (currentTarget.getColumn() == 6)
				cell4++;
		}
		assertTrue(cell1 > 15 && cell2 > 15 && cell3 > 15 && cell4 > 15); 

		//if room is in list, but not just visited, should visit room
		board.calcTargets(8, 3, 3);
		currentTarget = board.selectTarget((ComputerPlayer) board.getPlayer(2));
		assertEquals (7, currentTarget.getRow());
		assertEquals (2, currentTarget.getColumn());
		board.calcTargets(17, 3, 5);
		currentTarget = board.selectTarget((ComputerPlayer) board.getPlayer(2));
		assertEquals (14, currentTarget.getRow());
		assertEquals (3, currentTarget.getColumn());

		//if room just visited is in list, should select randomly
		board.calcTargets(21, 8, 3);
		board.selectTarget((ComputerPlayer)board.getPlayer(2));
		board.calcTargets(21, 8, 1);
		cell1 = cell2 = cell3 = 0;
		for (int i = 0; i < 100; i++) {
			currentTarget = board.selectTarget((ComputerPlayer) board.getPlayer(2));
			if (currentTarget.getRow() == 21) cell1++;
			else if (currentTarget.getRow() == 20) cell2++;
			else if (currentTarget.getRow() == 22) cell3++;
		}
		assertTrue(cell1 > 20 && cell2 > 20 && cell3 > 20);
	}



}
