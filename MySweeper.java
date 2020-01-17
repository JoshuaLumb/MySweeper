package MySweeper;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MySweeper {

	private static int n = 10; //The size of the board, default 20
	private static int difficulty; //Difficulty determined by buttons
	private static Integer[][] v; //2d array
	private static Integer[] sizeChoices = {10,11,12,13,14,15,16,17,18,19,20};
	private static JPanel p;
	private static JPanel p2;
	private static JFrame f;
	
	public static void initializeV() {
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				v[i][j] = (int) Math.floor(Math.random()*30);
				if (v[i][j] > difficulty) {
					v[i][j] = 0;
				}
				else {
					if (v[i][j] != 0)
						v[i][j] = -1;
				}
			}
		}
	}
	
	//A diagnostic method for printing v to the console
	public static void printV() {
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				System.out.print(v[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
	
	//A method to set the values at each position in the array to reflect the number of adjacent mines
	private static void calculateBoardValues() {
		for (int x=0; x<n; x++) {
			for (int y=0; y<n; y++) {
				if (v[x][y]!=-1) {
					v[x][y] = 0;
					for (int i=(x-1); i<=(x+1); i++) {
						for (int j=(y-1); j<=(y+1); j++) {
							if (i>=0 && i<n && j>=0 && j<n) {	
								if (v[i][j] == -1) {
									v[x][y]++;
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static void startGame() {
		v = new Integer[n][n];
		f.setSize(new Dimension(n*50, n*50));
		initializeV();
		f.remove(p2); //Change screens
		
		//Set up game board
		p = new JPanel(new GridLayout(n, n));
		for (int i=0; i<(n*n); i++) {
			p.add(new MySweeperCell(i/n, i%n));
		}
		f.add(p);
		f.validate();
		calculateBoardValues();
		printV(); //Test method
	}
	
	public static void resetGame() {
		//Get user input for difficulty and size of board.
		f.remove(p);
		f.add(p2);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(new Dimension(400, 100));
		f.setVisible(true);	
		f.validate();
	}
	
	//A method for handling what to do when the player clicks a button
	public static void onClick(int x, int y, boolean isFirstClick) {
		if (x>=0 && x<n && y>=0 && y<n && v[x][y]==0 && (((MySweeperCell)p.getComponent(x*n + y)).getText().compareTo(((Integer)v[x][y]).toString()))!=0) {
			((MySweeperCell) p.getComponent(x*n + y)).setText(((Integer)v[x][y]).toString());
			if (didYouWin()) {
				resetGame();
			}
			for (int i=(x-1); i<=(x+1); i++) {
				for (int j=(y-1); j<=(y+1); j++) {
					onClick(i, j, false);
				}
			}
		}
		else if (x>=0 && x<n && y>=0 && y<n && v[x][y]==-1 && isFirstClick) {
			JOptionPane.showMessageDialog(null, "You hit a mine :(");
			resetGame();
		}
		else if(x>=0 && x<n && y>=0 && y<n && v[x][y] != -1 && v[x][y]>0) {
			((MySweeperCell) p.getComponent(x*n + y)).setText(v[x][y].toString());
			if (didYouWin()) {
				resetGame();
			}
		}
	}
	
	private static boolean didYouWin() {
		for(int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				if (v[i][j] != -1 && ((MySweeperCell)p.getComponent(i*n + j)).getText().compareTo(((Integer)v[i][j]).toString())!=0) {
					return false;
				}
			}
		}
		JOptionPane.showMessageDialog(null, "You won!");
		return true;
	}
	
	public static void main(String[] args) {
		
		f = new JFrame("MySweeper!");
		p = new JPanel();
		
/*********************Components of P2***************************/
		p2 = new JPanel();
		JButton easyButton = new JButton("NORMAL");
		JButton mediumButton = new JButton("HARD");
		JButton hardButton = new JButton("NIGHTMARE");
		JComboBox<Integer> sizeChooser = new JComboBox<Integer>(sizeChoices);
		
		easyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				difficulty =3;
				startGame();
			}
		});
		mediumButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				difficulty = 7;
				startGame();
			}
		});
		hardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				difficulty = 12;
				startGame();
			}
		});
		sizeChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				n = (int)sizeChooser.getSelectedItem();
			}
		});
/****************************************************************/
				
		//Set up p2
		p2.add(sizeChooser);
		p2.add(easyButton);
		p2.add(mediumButton);
		p2.add(hardButton);
		
		//Make sure F closes on close
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		resetGame();
		
	}

}
