package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

class OurInitTests {
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("Rooms.csv", "CTest_ClueLegend.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
