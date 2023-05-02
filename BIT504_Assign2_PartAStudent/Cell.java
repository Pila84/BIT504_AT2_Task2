import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Cell {
    
	Player content;					//content of this cell (empty, cross, nought)	
	int row, col;					//row and column of this cell
	
	/** Constructor to initialise this cell with the specified row and col */
	public Cell(int row, int col) {
		
		// TODO: Initialise the variables row, col 
		this.row = row;
	    this.col = col;
				
		//TODO: call the method that sets the cell content to EMPTY
	    clear();
		 
	}
	

	/** Paint itself on the graphics canvas, given the Graphics context g */ 
	public void paint(Graphics g) {
		//Graphics2D allows setting of pen's stroke size(line width)
		Graphics2D graphic2D = (Graphics2D) g;
		graphic2D.setStroke(new BasicStroke(GameMain.SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		
		//draw the symbol in the position
		int x1 = col * GameMain.CELL_SIZE + GameMain.CELL_PADDING;	// calculate the x-coordinate of the top-left corner of the symbol
		int y1 = row * GameMain.CELL_SIZE + GameMain.CELL_PADDING;	// calculate the y-coordinate of the top-left corner of the symbol
		
		if (content == Player.Cross) {	// if the cell contains a Cross
			graphic2D.setColor(Color.RED);	// set color to red
			
		int x2 = (col + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;	// calculate the x-coordinate of the bottom-right corner of the symbol
		int y2 = (row + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;	// calculate the y-coordinate of the bottom-right corner of the symbol
		graphic2D.drawLine(x1, y1, x2, y2);		// draw a line from the top-left corner to the bottom-right corner
		graphic2D.drawLine(x2, y1, x1, y2);		// draw a line from the top-right corner to the bottom-left corner
			
			}else if (content == Player.Nought) {	// if the cell contains a Nought
				graphic2D.setColor(Color.BLUE);		// set color to blue
				graphic2D.drawOval(x1, y1, GameMain.SYMBOL_SIZE, GameMain.SYMBOL_SIZE);		// draw an oval with the top-left corner at (x1, y1) and the specified size
		}
	}
	
	/** Set this cell's content to EMPTY */
	public void clear() {
		
		// TODO: Set the value of content to Empty (Remember this is an enum)
		content = Player.Empty;
		
	}
		
}
