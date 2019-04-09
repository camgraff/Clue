//@authors: Cameron Graff, James Mach
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

import clueGame.Board;

public class gui extends JFrame {		
	private JPanel controlPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JPanel diePanel = new JPanel();
	private JPanel guessPanel = new JPanel();
	private JPanel resultPanel = new JPanel();
	private JPanel board = Board.getInstance();
	private JPanel topPanel = new JPanel();


	public void createDiePanel() {
		//diePanel.setLayout(new GridLayout(1, 2));
		JLabel rollLabel = new JLabel("Roll");
		JTextComponent rollTextField = new JTextField();
		rollTextField.setPreferredSize(new Dimension(60, 20));
		rollTextField.setEditable(false);
		diePanel.add(rollLabel);
		diePanel.add(rollTextField);
		diePanel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
	}

	public void createGuessPanel() {
		//guessPanel.setLayout(new GridLayout(2, 1));
		JLabel guessLabel = new JLabel("Guess");
		JTextComponent guessTextField = new JTextField();
		guessTextField.setPreferredSize(new Dimension(250, 20));
		guessTextField.setEditable(false);
		guessPanel.add(guessLabel);
		guessPanel.add(guessTextField);
		guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
	}

	public void createResultPanel() {
		//resultPanel.setLayout(new GridLayout(1, 2));
		JLabel resultLabel = new JLabel("Response");
		JTextComponent resultTextField = new JTextField();
		resultTextField.setPreferredSize(new Dimension(200, 20));
		resultTextField.setEditable(false);
		resultPanel.add(resultLabel);
		resultPanel.add(resultTextField);
		resultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));

	}
	
	public void createButtonPanel() {
		JPanel turnPanel = new JPanel();
		JLabel whoseTurn = new JLabel("Whose turn?");		
		JButton nextPlayer = new JButton("Next player");		
		JTextComponent playerNameField = new JTextField(20);
		playerNameField.setEditable(false);
		JButton makeAccusation= new JButton("Make an accusation");
		buttonPanel.setLayout(new GridLayout(1, 3));		
		turnPanel.add(whoseTurn);	
		turnPanel.add(playerNameField);
		buttonPanel.add(turnPanel);
		buttonPanel.add(nextPlayer);
		buttonPanel.add(makeAccusation);
	}
	
	public void createBottomPanel() {
		bottomPanel.setLayout(new GridLayout(1, 3));
		createDiePanel();
		createGuessPanel();
		createResultPanel();
		bottomPanel.add(diePanel);
		bottomPanel.add(guessPanel);
		bottomPanel.add(resultPanel);

	}
	
	private void createLayout() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		controlPanel.setLayout(new GridLayout(2, 1));		
		setSize(800,250);
		
		createButtonPanel();
		createBottomPanel();
		
		
		controlPanel.add(buttonPanel);
		controlPanel.add(bottomPanel);
		topPanel.add(controlPanel);
		topPanel.add(board);
		add(topPanel);
	}




	public static void main(String[] args) {
		gui gui = new gui();
		gui.createLayout();
		gui.setVisible(true);

	}
}