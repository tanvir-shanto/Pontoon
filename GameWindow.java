import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class GameWindow extends JFrame implements ActionListener{
	
	private Random random;
	
	private int total, colorPicker;  // colorPicker is used to decide the background color of a button upon it's selected
	private String selectPlayer;
	
	private JPanel topPanel;
	private JPanel bottomPanel;   // Where the game takes place
	
	
	
	// The labels
	private JLabel score, gameMessage;
	
	// The buttons
	private JButton startButton;  // Starts a new game
	private JButton button[] = new JButton[25];  
	
	public GameWindow() {
		
		random = new Random();
		
		topPanel = new JPanel();
		bottomPanel = new JPanel();
		
		// Prepare the items for the top panel
		startButton = new JButton("New Game");
		startButton.addActionListener(this);
		
		score = new JLabel("Current: -");
		gameMessage = new JLabel("Keep the total below 22. Click 'New Game' to begin.");
		
		// Top Panel
		topPanel.setLayout(new BorderLayout(3,3));
		topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Create space around the top panel
		
		topPanel.add(startButton, BorderLayout.WEST);
		topPanel.add(score, BorderLayout.EAST);
		topPanel.add(gameMessage, BorderLayout.CENTER);
		
		// Bottom Panel
		bottomPanel.setLayout(new GridLayout(5,5 , 4, 4));
		
		// Create the buttons for the bottom panel
		for(int i = 0; i<button.length; i++) {
			button[i] = new JButton("-");
			button[i].setBackground(Color.WHITE);
			button[i].setPreferredSize(new Dimension(95, 95));
			button[i].setFont(new Font("Arial", Font.PLAIN, 30));
			button[i].setEnabled(false);   // Initially the button's can't be selected
			
			button[i].addActionListener(this);
			
			bottomPanel.add(button[i]);
		}
		
		// Add the panels to the frame 
		getContentPane().setLayout(new BorderLayout(5, 5));
		getContentPane().add(topPanel, BorderLayout.NORTH);  // Add the top panel to the frame
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);  // Add the bottom panel
		
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		
		
		
	}
	
	// Prepare the grid so that the player can start playing
	public void loadGame() {
		
		// Reset the background color for the buttons
		for(int i=0; i<button.length; i++) {
			button[i].setBackground(Color.WHITE);
		}
		
		// Reset the score
		total = 0;
		score.setText("Current: " + total);
		
		
		// Reset The button color picker
		colorPicker = 0;
		
		// Define which player will start the game
		int decider = random.nextInt(2);
		
		if(decider == 0) {
			gameMessage.setText("Player 1's turn...");
			selectPlayer = "Player1";
		}else if(decider == 1) {
			gameMessage.setText("Player 2's turn...");
			selectPlayer = "Player2";
		}
		
		// Assign a random value (1 to 5) to each button
		for(int i = 0; i<button.length; i++) {
			int number = 1 + random.nextInt(5);
			button[i].setText("" + number);
			
			button[i].setEnabled(true);    // All the buttons can be selected now
		}
		
	}
	
	// Disables all the buttons in the bottom panel
	public void disableAllButtons() {
		for(int i=0; i<button.length; i++) {
			button[i].setEnabled(false);
		}
	}

	
	public void actionPerformed(ActionEvent e) {
		
		// This block of code will execute upon clicking "New Game"
		if (e.getSource() == startButton) {
			loadGame();
		}
		
		
		
		// If the user clicks any of the buttons in the bottom panel
		for(int i = 0; i<button.length; i++) {
			
			if(e.getSource() == button[i]) {
				
				int buttonValue = 0;
				
				try {
					buttonValue = Integer.parseInt(button[i].getText());
					total += buttonValue;   // Add the number on the button to the total
				}catch(Exception ex) {
					System.out.println("Not a valid number");
				}
				
				
				score.setText("Current: " + total);   // Display the updated value
				
				
				// Change the background of the button
				if(colorPicker % 2 == 0) {
					button[i].setBackground(Color.BLUE);				
					colorPicker++;   // Update the colorPicker
				}else {
					button[i].setBackground(Color.ORANGE);	
					colorPicker++;
				}
				
				
				// Check who has won
				if(total > 21) {
					if(selectPlayer.equals("Player1")) {
						gameMessage.setText("Player 2 Wins!");		
					}else if(selectPlayer.equals("Player2")){
						gameMessage.setText("Player 1 Wins!");
					}
					
					// Disable all the buttons
					disableAllButtons();
					
					break;
				}
				
				// Disable this button that has been selected
				button[i].setEnabled(false);
				
				
				
				// Switch the player
				if(selectPlayer.equals("Player1")) {
					selectPlayer = "Player2";
					gameMessage.setText("Player 2's turn...");
					
				}else if(selectPlayer.equals("Player2")){
					selectPlayer = "Player1";
					gameMessage.setText("Player 1's turn...");
				}
				

			}
		}
		
	}

}
