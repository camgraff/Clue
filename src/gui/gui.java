//@authors: Cameron Graff, James Mach
package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.text.JTextComponent;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

import clueGame.*;
import javafx.scene.layout.Border;

public class gui extends JFrame {		
	private JPanel controlPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JPanel diePanel = new JPanel();
	private JPanel guessPanel = new JPanel();
	private JPanel resultPanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private JPanel peoplePanel = new JPanel();
	private JPanel roomsPanel = new JPanel();
	private JPanel weaponsPanel = new JPanel();
	private JPanel personGuessPanel = new JPanel();
	private JPanel roomGuessPanel = new JPanel();
	private JPanel weaponGuessPanel = new JPanel();
	private JDialog detectiveNotes;
	private JMenuBar menu = new JMenuBar();

	private Board board = Board.getInstance();
	private ArrayList<Card> deck;



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

	public void createLayout() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000,800);
		topPanel.setLayout(new BorderLayout());

		createMenuBar();
		setJMenuBar(menu);

		createBoardPanel();
		createControlPanel();
		topPanel.add(board);
		topPanel.add(controlPanel, BorderLayout.SOUTH);
		add(topPanel);

		createDetectiveNotes();


	}

	public void createBoardPanel() {
		board.setConfigFiles("rooms.csv", "legend.txt");
		board.initialize();
		deck = board.getDeck();
	}

	public void createControlPanel() {
		createButtonPanel();
		createBottomPanel();
		controlPanel.setLayout(new GridLayout(2, 1));		
		controlPanel.add(buttonPanel);
		controlPanel.add(bottomPanel);
	}

	public void createPeoplePanel() {
		peoplePanel.setBorder(new TitledBorder(new EtchedBorder(), "People"));

		ArrayList<JCheckBox> people = new ArrayList<>();
		for(Card c : deck) {
			if(c.getType() == CardType.PERSON) {
				people.add(new JCheckBox(c.getName()));
			}
		}

		for(JCheckBox cb : people) {
			peoplePanel.add(cb);
		}
	}

	public void createRoomsPanel() {	
		roomsPanel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));

		ArrayList<JCheckBox> rooms = new ArrayList<>();
		for(Card c : deck) {
			if(c.getType() == CardType.ROOM) {
				rooms.add(new JCheckBox(c.getName()));
			}
		}

		for(JCheckBox cb : rooms) {
			roomsPanel.add(cb);
		}

	}

	public void createWeaponsPanel() {
		weaponsPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));

		ArrayList<JCheckBox> weapons = new ArrayList<>();
		for(Card c : deck) {
			if(c.getType() == CardType.WEAPON) {
				weapons.add(new JCheckBox(c.getName()));
			}
		}

		for(JCheckBox cb : weapons) {
			weaponsPanel.add(cb);
		}
	}

	public void createDetectiveNotes() {
		detectiveNotes = new JDialog(this, "Detective Notes");
		createPeoplePanel();
		createRoomsPanel();
		createWeaponsPanel();
		createPersonGuessPanel();
		createRoomGuessPanel();
		createWeaponGuessPanel();

		detectiveNotes.setTitle("Detective Notes");
		detectiveNotes.setSize(600,500);
		detectiveNotes.setLayout(new GridLayout(3, 2));
		detectiveNotes.add(peoplePanel);
		detectiveNotes.add(personGuessPanel);
		detectiveNotes.add(roomsPanel);
		detectiveNotes.add(roomGuessPanel);
		detectiveNotes.add(weaponsPanel);
		detectiveNotes.add(weaponGuessPanel);
		//detectiveNotes.setVisible(true);
	}

	public void createPersonGuessPanel() {
		JComboBox<String> guessPeople = new JComboBox<>();
		for(Card c : deck) {
			if(c.getType() == CardType.PERSON) {
				guessPeople.addItem(c.getName());
			}
		}
		personGuessPanel.add(guessPeople);
		personGuessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));

	}

	public void createRoomGuessPanel() {
		JComboBox<String> guessRoom = new JComboBox<>();
		for(Card c : deck) {
			if(c.getType() == CardType.ROOM) {
				guessRoom.addItem(c.getName());
			}
		}
		roomGuessPanel.add(guessRoom);
		roomGuessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
	}

	public void createWeaponGuessPanel() {
		JComboBox<String> guessWeapon = new JComboBox<>();
		for(Card c : deck) {
			if(c.getType() == CardType.WEAPON) {
				guessWeapon.addItem(c.getName());
			}
		}
		weaponGuessPanel.add(guessWeapon);
		weaponGuessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
	}

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




	public static void main(String[] args) {
		gui gui = new gui();
		gui.createLayout();
		gui.setVisible(true);
	}
}