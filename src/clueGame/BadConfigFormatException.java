//@author: Cameron Graff
//author: James Mach

package clueGame;

public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
		System.out.println("Error: There is a problem with the configuration files");
		return;
	}

	public BadConfigFormatException(String s) {
		switch (s) {
		case "badCols" : 
			System.out.println("Error: Rows do not all have the same number of columns");
			return;
		case "badRoom" :
			System.out.println("Error: Room is not present in legend");
			return;
		case "badFormat" : 
			System.out.println("Error: Room config file does not have correct format");
			return;
		}
	}
}
