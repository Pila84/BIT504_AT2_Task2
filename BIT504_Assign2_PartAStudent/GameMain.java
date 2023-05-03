import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class GameMain extends JPanel implements MouseListener{
	//Constants for game 
	// number of ROWS by COLS cell constants 
	public static final int ROWS = 3;     
	public static final int COLS = 3;  
	public static final String TITLE = "Tic Tac Toe";

	//constants for dimensions used for drawing
	//cell width and height
	public static final int CELL_SIZE = 100;
	//drawing canvas
	public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
	public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
	//Noughts and Crosses are displayed inside a cell, with padding from border
	public static final int CELL_PADDING = CELL_SIZE / 6;    
	public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;    
	public static final int SYMBOL_STROKE_WIDTH = 8;
	
	/*declare game object variables*/	
	private Board board;				// the game board 
	private GameState currentState; 	//TODO: create the enumeration for the variable below (GameState currentState)
	private Player currentPlayer; 		// the current player		
	private JLabel statusBar;     		// for displaying game status message  
	

	/** Constructor to setup the UI and game components on the panel */
	public GameMain() {   
		
		// TODO: This JPanel fires a MouseEvent on MouseClicked so add required event listener to 'this'.          
		// add MouseListener to GameMain component
        addMouseListener(this);						//object that implements the MouseListener interface
	    
		// Setup the status bar (JLabel) to display status message       
		statusBar = new JLabel("         ");       
		statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));   	//set font    
		statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));	//add empty borders to the status bar       
		statusBar.setOpaque(true);       									//make the status bar opaque so background color is visible
		statusBar.setBackground(Color.LIGHT_GRAY);  						//set the background color
		
		//layout of the panel is in border layout
		setLayout(new BorderLayout());       
		add(statusBar, BorderLayout.SOUTH);									//add bar to the south (bottom) of the panel
		// account for statusBar height in overall height
		setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));	//set preferred size of panel
		
		
		// TODO: Create a new instance of the game "Board"class. HINT check the variables above for the correct name
		board = new Board();
		
		//TODO: call the method to initialise the game board
		 initGame(); // initialize the game board 

	}
	
	public static void main(String[] args) {
		    // Run GUI code in Event Dispatch thread for thread safety.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {		//delays the GUI creation task until the initial thread's task have been completed
			 
	         
			public void run() {
				//create a main window to contain the panel
				JFrame frame = new JFrame(TITLE);
				
				//TODO: create the new GameMain panel and add it to the frame
				frame.getContentPane().add(new GameMain());		//adds the GameMain panel to the content pane of the JFrame	
				
				
				//TODO: set the default close operation of the frame to exit_on_close
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				frame.pack();             				//resizes the frame 
				frame.setLocationRelativeTo(null);		//centers the frame on the screen
				frame.setVisible(true);					//makes the frame visible 	
	         }
		 });
	}
	/** Custom painting codes on this JPanel */
	public void paintComponent(Graphics g) {
		//fill background and set colour to white
		super.paintComponent(g);
		setBackground(Color.WHITE);
		//ask the game board to paint itself
		board.paint(g);
		
		//set status bar message (message varies depending on condition)
		if (currentState == GameState.Playing) {          
			statusBar.setForeground(Color.BLACK);          
			if (currentPlayer == Player.Cross) {   
			
				//TODO: use the status bar to display the message "X"'s Turn
				statusBar.setText("X's Turn");
				
			} else {    
				
				//TODO: use the status bar to display the message "O"'s Turn
				statusBar.setText("O's Turn");
				
			}       
			} else if (currentState == GameState.Draw) {          
				statusBar.setForeground(Color.RED);          
				statusBar.setText("It's a Draw! Click to play again.");       
			} else if (currentState == GameState.Cross_won) {          
				statusBar.setForeground(Color.RED);          
				statusBar.setText("'X' Won! Click to play again.");       
			} else if (currentState == GameState.Nought_won) {          
				statusBar.setForeground(Color.RED);          
				statusBar.setText("'O' Won! Click to play again.");       
			}
		}
		
	
	  /** Initialise the game-board contents and the current status of GameState and Player) */
		public void initGame() {
			//loop through every row and column of the game board
			for (int row = 0; row < ROWS; ++row) {          
				for (int col = 0; col < COLS; ++col) {  
					//sets the content of the cells to empty
					board.cells[row][col].content = Player.Empty;           
				}
			}
			//sets currentState of the game to Playing
			 currentState = GameState.Playing;
			//sets currentPlayer to Cross
			 currentPlayer = Player.Cross;
		}
		
		
		/**After each turn check to see if the current player hasWon by putting their symbol in that position, 
		 * If they have the GameState is set to won for that player
		 * If no winner then isDraw is called to see if deadlock, if not GameState stays as PLAYING
		 *   
		 */
		public void updateGame(Player thePlayer, int row, int col) {
			//check for win after play
			if(board.hasWon(thePlayer, row, col)) {
				
				// TODO: check which player has won and update the currentstate to the appropriate gamestate for the winner
				if (thePlayer == Player.Cross) {
			        currentState = GameState.Cross_won;
			    } else {
			        currentState = GameState.Nought_won;
			    }
				
			} else if (board.isDraw ()) {					
				// TODO: set the currentstate to the draw gamestate
					 currentState = GameState.Draw;
			}
			else {
			//otherwise no change to current state of playing
			currentState = GameState.Playing;
		}
		}
		
				
	
		/** Event handler for the mouse click on the JPanel. If selected cell is valid and Empty then current player is added to cell content.
		 *  UpdateGame is called which will call the methods to check for winner or Draw. if none then GameState remains playing.
		 *  If win or Draw then call is made to method that resets the game board.  Finally a call is made to refresh the canvas so that new symbol appears*/
	
	public void mouseClicked(MouseEvent e) {  
	    // get the coordinates of where the click event happened            
		int mouseX = e.getX();             
		int mouseY = e.getY();             
		// Get the row and column clicked             
		int rowSelected = mouseY / CELL_SIZE;             
		int colSelected = mouseX / CELL_SIZE;               			
		if (currentState == GameState.Playing) {                
			if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS && board.cells[rowSelected][colSelected].content == Player.Empty) {
				// move  
				board.cells[rowSelected][colSelected].content = currentPlayer; 
				// update currentState                  
				updateGame(currentPlayer, rowSelected, colSelected); 
				// Switch player
				if (currentPlayer == Player.Cross) {
					currentPlayer =  Player.Nought;
				}
				else {
					currentPlayer = Player.Cross;
				}
			}             
		} else {        
			// game over and restart              
			initGame();            
		}   
		
		//TODO: redraw the graphics on the UI          
		repaint();				// schedule a call to the paint(Graphics g) method of the JPanel
	}
		
	
	@Override
	public void mousePressed(MouseEvent e) {
		//  Auto-generated, event not used
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		//  Auto-generated, event not used
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// Auto-generated,event not used
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// Auto-generated, event not used
		
	}

}
