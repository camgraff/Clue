package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {
	
	public BadConfigFormatException() {
		super("Error: Invalid Config file");
	}
	
	public BadConfigFormatException(String message) {
		super(message);
		
		try {
			PrintWriter writer = new PrintWriter("logfile.txt");
			writer.println(message);
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
