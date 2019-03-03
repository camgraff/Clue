package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {
	
	public BadConfigFormatException() {
		super("Error: Invalid Config file");
	}
	
	public BadConfigFormatException(String invalidCell) {
		super("Error: Invalid Cell \""+invalidCell+"\"");
		
		try {
			PrintWriter writer = new PrintWriter("logfile.txt");
			writer.println("Error: Invalid Cell \""+invalidCell+"\"");
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
