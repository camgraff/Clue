//@authors: Cameron Graff, James Mach
package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.text.JTextComponent;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import clueGame.*;
import javafx.scene.layout.Border;

public class ClueGame extends JFrame {		
	private JPanel controlPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JPanel diePanel = new JPanel();
	private JPanel guessPanel = new JPanel();
	private JPanel resultPanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private JPanel cardPanel = new JPanel();
	private JPanel boardPanel = new JPanel();
	private JDialog detectiveNotes;
	private JMenuBar menu = new JMenuBar();
	private JTextComponent playerNameField = new JTextField(20);
	JTextComponent rollTextField = new JTextField();
	private Player currentPlayer;

	private Board board = Board.getInstance();
	private int currentPlayerIndex = 0;

	public void createDiePanel() {
		JLabel rollLabel = new JLabel("Roll");

		rollTextField.setPreferredSize(new Dimension(60, 20));
		rollTextField.setEditable(false);
		diePanel.add(rollLabel);
		diePanel.add(rollTextField);
		diePanel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
	}

	public void createGuessPanel() {
		JLabel guessLabel = new JLabel("Guess");
		JTextComponent guessTextField = new JTextField();
		guessTextField.setPreferredSize(new Dimension(250, 20));
		guessTextField.setEditable(false);
		guessPanel.add(guessLabel);
		guessPanel.add(guessTextField);
		guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
	}

	public void createResultPanel() {
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
		class nextPlayerButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				board.hasMoved = false;
			}
		}

		nextPlayer.addActionListener(new nextPlayerButtonListener());

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

	//creates gui
	public void createLayout() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(815,765);
		topPanel.setLayout(new BorderLayout());

		createMenuBar();
		setJMenuBar(menu);

		createBoardPanel();
		createControlPanel();	
		createCardDisplay();

		topPanel.add(boardPanel);
		topPanel.add(controlPanel, BorderLayout.SOUTH);
		add(topPanel);

		createDetectiveNotes();

		setVisible(true);

		//splash screen
		JOptionPane.showMessageDialog(this, "You are " + board.getPlayer(0).getName() + ", press Next Player to begin play","Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);

	}

	//board panel contains board and human player's card display
	public void createBoardPanel() {
		board.setConfigFiles("rooms.csv", "legend.txt");
		board.initialize();
		board.setSize(100,100);

		boardPanel.setLayout(new BorderLayout());
		boardPanel.add(board, BorderLayout.CENTER);
		boardPanel.add(cardPanel, BorderLayout.LINE_END);
		boardPanel.setPreferredSize(boardPanel.getPreferredSize());
	}

	//control panel contains all buttons
	public void createControlPanel() {
		createButtonPanel();
		createBottomPanel();
		controlPanel.setLayout(new GridLayout(2, 1));		
		controlPanel.add(buttonPanel);
		controlPanel.add(bottomPanel);
	}

	//create detective notes to keep track of seen cards/guesses
	public void createDetectiveNotes() {
		JPanel peoplePanel = new JPanel();
		JPanel roomsPanel = new JPanel();
		JPanel weaponsPanel = new JPanel();
		JPanel personGuessPanel = new JPanel();
		JPanel roomGuessPanel = new JPanel();
		JPanel weaponGuessPanel = new JPanel();

		JComboBox<String> guessPerson = new JComboBox<String>();
		JComboBox<String> guessRoom = new JComboBox<String>();
		JComboBox<String> guessWeapon = new JComboBox<String>();

		detectiveNotes = new JDialog(this, "Detective Notes");
		detectiveNotes.setTitle("Detective Notes");
		detectiveNotes.setSize(600,500);
		detectiveNotes.setLayout(new GridLayout(3, 2));

		peoplePanel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		roomsPanel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		weaponsPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		personGuessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		roomGuessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		weaponGuessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));

		for(Card c : board.getAllCards()) {
			switch(c.getType()) {
			case PERSON:
				peoplePanel.add(new JCheckBox(c.getName()));
				guessPerson.addItem(c.getName());
				break;

			case ROOM:
				roomsPanel.add(new JCheckBox(c.getName()));
				guessRoom.addItem(c.getName());
				break;

			case WEAPON:
				weaponsPanel.add(new JCheckBox(c.getName()));
				guessWeapon.addItem(c.getName());
				break;
			}
		}

		personGuessPanel.add(guessPerson);
		roomGuessPanel.add(guessRoom);
		weaponGuessPanel.add(guessWeapon);

		detectiveNotes.add(peoplePanel);
		detectiveNotes.add(personGuessPanel);
		detectiveNotes.add(roomsPanel);
		detectiveNotes.add(roomGuessPanel);
		detectiveNotes.add(weaponsPanel);
		detectiveNotes.add(weaponGuessPanel);
	}


	//menu bar is used to access detective notes and exit
	private void createMenuBar() {
		JMenu fileMenu = new JMenu("File");

		//create detectives notes menu item
		JMenuItem detNotesButton = new JMenuItem("Detective notes");
		class detNotesButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				detectiveNotes.setVisible(true);
			}
		}
		detNotesButton.addActionListener(new detNotesButtonListener());

		//create exit menu item
		JMenuItem exit = new JMenuItem("Exit");
		class exitButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		exit.addActionListener(new exitButtonListener());
		fileMenu.add(detNotesButton);
		fileMenu.add(exit);
		menu.add(fileMenu);
	}

	//display player's current hand
	public void createCardDisplay() {
		JPanel peopleCardPanel = new JPanel();
		JPanel roomCardPanel = new JPanel();
		JPanel weaponCardPanel = new JPanel();
		JTextComponent peopleTextField = new JTextArea(10,10);
		JTextComponent roomTextField = new JTextArea(10,10);
		JTextComponent weaponTextField = new JTextArea(10,10);

		cardPanel.setLayout(new GridLayout(3, 1));
		cardPanel.setPreferredSize(new Dimension(200,100));
		cardPanel.setBorder(new TitledBorder(new EtchedBorder(), "My Cards"));
		peopleCardPanel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		roomCardPanel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		weaponCardPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		peopleTextField.setEditable(false);
		roomTextField.setEditable(false);
		weaponTextField.setEditable(false);
		peopleTextField.setBorder(new LineBorder(Color.BLACK));
		roomTextField.setBorder(new LineBorder(Color.BLACK));
		weaponTextField.setBorder(new LineBorder(Color.BLACK));

		for (Card crd : board.getPlayer(1).getHand()) {
			switch (crd.getType()) {
			case PERSON:
				peopleTextField.setText(peopleTextField.getText() + "\n" + crd.getName());
				break;
			case ROOM:
				roomTextField.setText(roomTextField.getText() + "\n" + crd.getName());
				break;
			case WEAPON: 
				weaponTextField.setText(weaponTextField.getText() + "\n" + crd.getName());
			}
		}

		peopleCardPanel.add(peopleTextField);
		roomCardPanel.add(roomTextField);
		weaponCardPanel.add(weaponTextField);
		cardPanel.add(peopleCardPanel);
		cardPanel.add(roomCardPanel);
		cardPanel.add(weaponCardPanel);
	}

	public void doNextPlayerTurn() {

		if (!board.hasMoved) {		
			Random rand = new Random();	
			int dieRoll = rand.nextInt(6) + 1;
			rollTextField.setText(Integer.toString(dieRoll));
			currentPlayer = board.getPlayer(currentPlayerIndex);	
			playerNameField.setText(currentPlayer.getName());
			
			board.makeMove(currentPlayer, dieRoll);		
			
			if (currentPlayer.isHuman()) {
				doHumanPlayerTurn();
			}


			currentPlayerIndex = (currentPlayerIndex + 1) % 6;
			board.repaint();

		}

		/*Logic for do next player turn.
		 * 
		 * Next player pressed
		 * 
		 * 
		 * 
		 * if(current humanPlayer turn finished?){
		 * 		update currentplayer
		 * 		roll dice
		 * 		calc targets
		 *		update game board control panel
		 *		
		 *		if(currentplayer human?){
		 *			display targets
		 *			flag unfinished
		 *			end
		 *		} else{
		 *			do accusation
		 *			do move for computer
		 *			if(computer can make accusation){
		 *				make accusation/computer wins
		 *			}
		 *		}
		 *
		 *
		 * } else(no){
		 * 		error message/ let player finish turn.
		 * }
		 */

	}

	public void doHumanPlayerTurn() {

		class BoardListener implements MouseListener {
			//  Empty definitions for unused event methods.
			public void mousePressed (MouseEvent event) {}
			public void mouseReleased (MouseEvent event) {}
			public void mouseEntered (MouseEvent event) {}
			public void mouseExited (MouseEvent event) {}
			public void mouseClicked (MouseEvent event) {
				for (BoardCell bcell : board.getTargets()) {
					if (bcell.containsClick(event.getX(), event.getY())) {
						board.humanTargetCell = bcell;
					}
				}
			}	
		}	
		board.addMouseListener(new BoardListener());
		while (board.humanTargetCell == null) {


		}
		currentPlayer.setRow(board.humanTargetCell.getRow());
		currentPlayer.setColumn(board.humanTargetCell.getColumn());
		currentPlayer.setCurrentCell(board.humanTargetCell);
		board.hasMoved = true;
		board.humanTargetCell = null;
		for (BoardCell bcell : board.getTargets()) {
			bcell.setIsHumanTarget(false);
		}
		repaint();
	}

	public void playGame() {
		doNextPlayerTurn();
	}

	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.createLayout();
		while(true) {
			game.playGame();
		}

	}
}