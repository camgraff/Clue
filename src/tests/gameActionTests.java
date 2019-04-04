package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class gameActionTests {

	private static Board board;
	ComputerPlayer player = new ComputerPlayer();
	BoardCell currentTarget;

	@Before
	public void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("Rooms.csv", "legend.txt");		
		// Initialize will load all config files 
		board.initialize();
		BoardCell currentTarget;
	}

	//Makes sure targets are selected correctly
	@Test
	public void testTargetRandomSelection() {
		//if no rooms in target list, should select randomly
		board.calcTargets(9, 0, 2);
		int cell1, cell2, cell3, cell4;
		cell1 = cell2 = cell3 = 0;
		for (int i=0; i<100; i++) {
			currentTarget = player.pickLocation(board.getTargets());
			if (currentTarget.getRow() == 8) 
				cell1++;
			else if (currentTarget.getRow() == 9)
				cell2++;
			else if (currentTarget.getRow() == 10)
				cell3++;
		}
		assertTrue(cell1 > 15 && cell2 > 15 && cell3 > 15); //only 3 targets so each should get hit more than 15 times

		board.calcTargets(9, 5, 1);
		cell1 = cell2 = cell3 = cell4 = 0;
		for (int i=0; i<100; i++) {
			currentTarget = player.pickLocation(board.getTargets());
			if (currentTarget.getRow() == 8) {
				cell1++;
			} else if (currentTarget.getRow() == 10)  {
				cell2++;
			} else if (currentTarget.getColumn() == 4) {
				cell3++;
			} else if (currentTarget.getColumn() == 6)
				cell4++;
		}
		assertTrue(cell1 > 10 && cell2 > 10 && cell3 > 10 && cell4 > 10); 
	}
	@Test
	//if room is in list, but not just visited, should visit room
	public void testTargetRoomNotVisited() {
		board.calcTargets(8, 3, 3);
		currentTarget = player.pickLocation(board.getTargets());
		assertEquals (7, currentTarget.getRow());
		assertEquals (2, currentTarget.getColumn());

		board.calcTargets(17, 3, 5);
		currentTarget = player.pickLocation(board.getTargets());
		assertEquals (14, currentTarget.getRow());
		assertEquals (3, currentTarget.getColumn());
	}

	@Test 		
	//if room just visited is in list, should select randomly
	public void testTargetRoomJustVisited() {
		int cell1, cell2, cell3;
		cell1 = cell2 = cell3 = 0;
		board.calcTargets(21, 8, 3);
		player.pickLocation(board.getTargets());	//make player visit room
		board.calcTargets(21, 8, 1);
		cell1 = cell2 = cell3 = 0;
		for (int i = 0; i < 100; i++) {
			currentTarget = player.pickLocation(board.getTargets());
			if (currentTarget.getRow() == 21) cell1++;
			else if (currentTarget.getRow() == 20) cell2++;
			else if (currentTarget.getRow() == 22) cell3++;
		}
		assertTrue(cell1 > 20 && cell2 > 20 && cell3 > 20);
	}

	@Test
	//check that accusations are checkd correctly
	public void testAccusation() {
		//set the solution
		Solution playerGuess;
		Card person = new Card("Miss Scarlet", CardType.PERSON);
		Card room = new Card("Swimming Pool", CardType.ROOM);
		Card weapon = new Card("Revolver", CardType.WEAPON);
		Solution solution = new Solution(person, room, weapon);
		board.setSolution(solution);

		//should return true when accusation is correct
		playerGuess = player.makeAccusation("Miss Scarlet", "Swimming Pool", "Revolver");
		assertTrue(board.checkAccusation(playerGuess));

		//should return false if any one of the cards are incorrect
		playerGuess = player.makeAccusation("Mr. Green", "Swimming Pool", "Revolver");
		assertFalse(board.checkAccusation(playerGuess));
		playerGuess = player.makeAccusation("Miss Scarlet", "Office", "Revolver");
		assertFalse(board.checkAccusation(playerGuess));
		playerGuess = player.makeAccusation("Miss Scarlet", "Swimming Pool", "Baseball Bat");
		assertFalse(board.checkAccusation(playerGuess));
	}

	@Test
	//Room should match players current location
	//if only one weapon or person not seen, should be selected
	public void testCreateSuggestionOneNotSeen() {
		player.setRow(22);
		player.setColumn(20);
		assertEquals("Studio", player.createSuggestion(board.getAllCards(), board.getBoard(), board.getLegend()).getRoom().getName());

		for (Card crd : board.getDeck()) {
			if (crd.getType() == CardType.WEAPON && !crd.getName().equals("Lead Pipe"))
				player.addSeenCards(crd);
			if (crd.getType() == CardType.PERSON && !crd.getName().equals("Colonel Mustard"))
				player.addSeenCards(crd);
		}
		assertEquals("Lead Pipe", player.createSuggestion(board.getAllCards(), board.getBoard(), board.getLegend()).getWeapon().getName());
		assertEquals("Colonel Mustard", player.createSuggestion(board.getAllCards(), board.getBoard(), board.getLegend()).getPerson().getName());
	}
	@Test
	//if multiple persons or weapons not seen, should randomly select
	public void testCreateSuggestionMultNotSeen() {
		for (Card crd : board.getDeck()) {
			if (crd.getType() == CardType.WEAPON && !crd.getName().equals("Lead Pipe") && !crd.getName().equals("Knife") && !crd.getName().equals("Baseball Bat") )
				player.addSeenCards(crd);
			if (crd.getType() == CardType.PERSON && !crd.getName().equals("Colonel Mustard") && !crd.getName().equals("Mr. Green") && !crd.getName().equals("Mrs. White"))
				player.addSeenCards(crd);
		}

		int pipe = 0, knife = 0, bat = 0, mustard = 0, green = 0, white = 0;;
		for (int i=0; i<100; i++) {
			String weaponGuess = player.createSuggestion(board.getAllCards(), board.getBoard(), board.getLegend()).getWeapon().getName();
			String personGuess = player.createSuggestion(board.getAllCards(), board.getBoard(), board.getLegend()).getPerson().getName();
			if (weaponGuess.equals("Lead Pipe"))
				pipe++;
			else if (weaponGuess.equals("Knife"))
				knife++;
			else if (weaponGuess.equals("Baseball Bat"))
				bat++;

			if (personGuess.equals("Colonel Mustard"))
				mustard++;
			else if (personGuess.equals("Mr. Green"))
				green++;
			else if (personGuess.equals("Mrs. White"))
				white++;
		}
		assertTrue(pipe > 15 && knife > 15 && bat > 15 && mustard>15 && green>15 && white>15);
	}
	
	@Test
	public void TestDisproveSuggestionOneMatch() {
		//give player cards
		Player plr = new Player();
		plr.recieveCard(new Card("Miss Scarlett", CardType.PERSON));
		plr.recieveCard(new Card("Bowling Alley", CardType.ROOM));
		plr.recieveCard(new Card("Hammer", CardType.WEAPON));	
		
		//if player only has 1 matching card, should be returned
		Solution suggestion = new Solution(new Card("Miss Scarlett", CardType.PERSON), new Card("Office", CardType.ROOM), new Card("Revolver", CardType.WEAPON));
		assertTrue(plr.disproveSuggestion(suggestion).getName().equals("Miss Scarlett"));
		
		//if player has more than 1 matching card, should choose randomly
		suggestion = new Solution(new Card("Miss Scarlett", CardType.PERSON), new Card("Bowling Alley", CardType.ROOM), new Card("Hammer", CardType.WEAPON));
		int person = 0, room = 0, weapon = 0;
		for (int i=0; i<100; i++) {
			String disprove = plr.disproveSuggestion(suggestion).getName();
			if (disprove.equals("Miss Scarlett"))
				person++;
			else if (disprove.equals("Bowling Alley"))
				room++;
			else if (disprove.equals("Hammer"))
				weapon++;
		}
		assertTrue(person>15 && room>15 && weapon>15);
		
		//if player has no matching cards should return null
		suggestion = new Solution(new Card("Mr. Green", CardType.PERSON), new Card("Office", CardType.ROOM), new Card("Revolver", CardType.WEAPON));
		assertEquals(null, player.disproveSuggestion(suggestion));
	}
	@Test
	public void TestHandleSuggestion() {
		//setup
		Player[] players = board.getPlayers(); //load 6 players into array, 1st one in human
		players[0].recieveCard(new Card("Thomas", CardType.PERSON));
		players[1].recieveCard(new Card("Miss Scarlett", CardType.PERSON));
		for ( int i = 2; i<players.length; i++) {
			players[i].recieveCard(new Card("nothing special", CardType.PERSON));
		}
		// all players have 1 card. 5 computer players have same card
		
		Solution notDisprovable = new Solution(new Card("Mr. Green", CardType.PERSON), new Card("Office", CardType.ROOM), new Card("Revolver", CardType.WEAPON));
		Solution diprovableBycPlr = new Solution(new Card("Miss Scarlett", CardType.PERSON), new Card("Office", CardType.ROOM), new Card("Revolver", CardType.WEAPON));
		Solution diprovableByhPlr = new Solution(new Card("Thomas", CardType.PERSON), new Card("Office", CardType.ROOM), new Card("Revolver", CardType.WEAPON));
		
		//test notDisprovable 
		assertEquals(null,board.handleSuggestion(notDisprovable, players[0]));
		assertEquals(null,board.handleSuggestion(notDisprovable, players[1]));
		assertEquals(null,board.handleSuggestion(notDisprovable, players[2]));
		assertEquals(null,board.handleSuggestion(notDisprovable, players[3]));
		assertEquals(null,board.handleSuggestion(notDisprovable, players[4]));
		assertEquals(null,board.handleSuggestion(notDisprovable, players[5]));
		
		//test diprovableBycPlr
		assertTrue(new Card("Miss Scarlett", CardType.PERSON).equals(board.handleSuggestion(diprovableBycPlr, players[0])));
		assertEquals(null,board.handleSuggestion(diprovableBycPlr, players[1]));
		assertTrue(new Card("Miss Scarlett", CardType.PERSON).equals(board.handleSuggestion(diprovableBycPlr, players[2])));
		assertTrue(new Card("Miss Scarlett", CardType.PERSON).equals(board.handleSuggestion(diprovableBycPlr, players[3])));
		assertTrue(new Card("Miss Scarlett", CardType.PERSON).equals(board.handleSuggestion(diprovableBycPlr, players[4])));
		assertTrue(new Card("Miss Scarlett", CardType.PERSON).equals(board.handleSuggestion(diprovableBycPlr, players[5])));
		
		//test diprovableByhPlr
		assertEquals(null,board.handleSuggestion(diprovableByhPlr, players[0]));
		assertTrue(new Card("Thomas", CardType.PERSON).equals(board.handleSuggestion(diprovableByhPlr, players[1])));
		assertTrue(new Card("Thomas", CardType.PERSON).equals(board.handleSuggestion(diprovableByhPlr, players[2])));
		assertTrue(new Card("Thomas", CardType.PERSON).equals(board.handleSuggestion(diprovableByhPlr, players[3])));
		assertTrue(new Card("Thomas", CardType.PERSON).equals(board.handleSuggestion(diprovableByhPlr, players[4])));
		assertTrue(new Card("Thomas", CardType.PERSON).equals(board.handleSuggestion(diprovableByhPlr, players[5])));
		
		
		
		
		
	}
}





