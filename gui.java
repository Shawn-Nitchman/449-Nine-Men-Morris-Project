import java.awt.*;
import javax.swing.*;
public class gui {

	public static void main(String args []) {

		createAndShowGui();
		
	}
	
	private static void createAndShowGui() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (UnsupportedLookAndFeelException e) {
		}
		catch (ClassNotFoundException e) {
		}
		catch(InstantiationException e) {
		}
		catch(IllegalAccessException e) {
		}

		//Sets the size of the frame
		Dimension frameDimension = new Dimension(700, 700);

		//creates a grid of 7x7 for the frame
		GridLayout gameGrid = new GridLayout(7,7);

		//This creates the frame which is the window of the GUI
		//Including the default close operation and the size
		JFrame frame = new JFrame("Nine Men's Morris");
		frame.setLayout(gameGrid);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(frameDimension);


		//Creates a 7x7 array of all the grid spaces and adds them to the frame
		GridSpace[][] gridSpace = new GridSpace[7][7];
		for (int ix = 1; ix < 8; ix++) {
			for (int iy = 1; iy < 8; iy++) {
				gridSpace[ix-1][iy-1] = new GridSpace(ix, iy);
				frame.add(gridSpace[ix-1][iy-1]);
			}
		}

		frame.pack();
		frame.setLocationRelativeTo(null); //centers the app when it opens
		frame.setVisible(true);
	}
}

class GridSpace extends JPanel {
	private boolean isValidSpace;
	private int x;
	private int y;

	//This takes the value of x (should be 1-7) and adds it to 96
	//to get the ascii value of a-g
	private char convertIntToChar(int x) {
		x = x + 96;
		return (char) x;
	}

	//This uses the converted char value to determine whether it is a playable
	//spot on the 7x7 grid. it returns true if it is and false if it isn't.
	private boolean checkValidSpace(int intCoordX, int y){
		char x = convertIntToChar(intCoordX);
		if (x == 'a' || x == 'g') {
			if (y == 7 || y == 4 || y == 1) {
				return true;
			}
			else {
				return false;
			}
		}
		else if (x == 'b' || x == 'f') {
			if (y == 6 || y == 4 || y == 2) {
				return true;
			}
			else {
				return false;
			}
		}
		else if (x == 'c' || x == 'e') {
			if (y == 5 || y == 4 || y == 3) {
				return true;
			}
			else {
				return false;
			}
		}
		else if (x == 'd') {
			if (y == 7 || y == 6 || y == 5 || y == 3 || y == 2 || y == 1) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	//This is the constructor that takes in the integer coordinates
	//Then, it checks if it is valid and stores that in the GridSpace object
	//And if it is valid, appropriately marks it.
	public GridSpace(int x, int y) {
		this.isValidSpace = checkValidSpace(x, y);
		if(isValidSpace == true) {
			add(new JLabel("O"));
		}
		else {
			add(new JLabel(""));
		}
	}

}