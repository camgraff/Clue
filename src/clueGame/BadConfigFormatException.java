package clueGame;

public class BadConfigFormatException extends Exception {
	
	public BadConfigFormatException() {
		System.out.println("Error: There is a problem with the configuration files");
		return;
	}
	
	public BadConfigFormatException(String s) {
		if (s == "badCols") {
			System.out.println("Error: Rows do not all have the same number of columns");
			return;
		} else if (s == "badRoom") {
			System.out.println("Error: Room is not present in legend");
			return;
		} else if (s == "badFormat") {
			System.out.println("Error: Room config file does not have correct format");
			return;
		}
	}
}
