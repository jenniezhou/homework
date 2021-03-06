import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Breakthrough {
	
	private final int numRows = 6;
	private final int numCols = 3;
	
	public static void main(String[] args) {
		Breakthrough array = new Breakthrough();
		
		char[][] temp = array.readFile("input.txt");
		
		Node root = new Node(temp, null, true, 0);
		
		maxValue(root, -Integer.MIN_VALUE, Integer.MAX_VALUE);
		
		Node best = null;

		for (Node n : root.children) {
			if (n.heuristicValue == 1) {
				best = n;
				break;
			}
		}
	
		System.out.println();
		System.out.println("Best move: Player A moves from (" + best.fromX + ", " + best.fromY + ") to (" + best.xPos + ", " + best.yPos + ")");
	}
	
	// PLAYER A'S TURN
	public static int maxValue(Node n, int a, int b) {
		int oldAlpha;
		
		if (cutOffTest(n)) {
			return n.heuristicValue;
		}
		
		generateSuccessors(n);
		
		for (Node c : n.children) {
			System.out.println("Player A moves from (" + c.fromX + ", " + c.fromY + ") to (" + c.xPos + ", " + c.yPos + ")");
			oldAlpha = a;
			a = Math.max(a, minValue(c, a, b));
			n.heuristicValue = a;
			
			// If the old alpha was worse than the new alpha, add it to the list of best children
			if (oldAlpha < a) {
				n.best.add(c);
			}
			if (a >= b) {
				System.err.println("Skipping Player A's moves from: (" + c.fromX + ", " + c.fromY + ") to (" + c.xPos + ", " + c.yPos + ")");
				return a;
			}
		}
		return a;
	}
	
	// PLAYER B'S TURN
	public static int minValue(Node n, int a, int b) {
		int oldBeta;
		
		if (cutOffTest(n)) {
			return n.heuristicValue;
		}
		
		generateSuccessors(n);

		for (Node c : n.children) {
			System.out.println("Player B moves from (" + c.fromX + ", " + c.fromY + ") to (" + c.xPos + ", " + c.yPos + ")");
			oldBeta = b;
			b = Math.min(b, maxValue(c, a, b));
			n.heuristicValue = b;
			
			// If the old beta is worse than the new beta, add this child to the list of best children
			if (oldBeta > b) {
				n.best.add(c);
			}
			if (b <= a) {
				System.err.println("Skipping Player B's moves from: (" + c.fromX + ", " + c.fromY + ") to (" + c.xPos + ", " + c.yPos + ")");
				return b;
			}
		}
		return b;
	}
	
	// Checks any of the following game terminating conditions
	public static boolean cutOffTest(Node n) {
		int whiteCounter = 0;
		int blackCounter = 0;
		
		// If white has reached black's side of the board
		for (int i = 0; i < 3; i++) {
			if (n.board[0][i] == 'W') {
				n.heuristicValue = 1;
				return true;
			}
		}
		
		// If black has reached white's side of the board
		for (int i = 0; i < 3; i++) {
			if (n.board[5][i] == 'B') {
				n.heuristicValue = -1;
				return true;
			}
		}	
		
		// If there are no white pieces or no black pieces left
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 3; j++) {
				if (n.board[i][j] == 'W') {
					++whiteCounter;
				}
				if (n.board[i][j] == 'B') {
					++blackCounter;
				}
			}
		}
		
		if (whiteCounter == 0) {
			n.heuristicValue = -1;
			return true;
		}
		
		if (blackCounter == 0) {
			n.heuristicValue = 1;
			return true;
		}
		
		return false;
	}
	
	public static void generateSuccessors(Node b) {		
		int childDepth = b.depth + 1;
		/*
		 *  If the player is white, then it can only move forward. Find all places in the board where there 
		 *  is a 'W' and create a new board state with W all next possible places. 
		 */
		if (b.max) {
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 3; j++) {
						if (b.board[i][j] == 'W') {
							if (i > 0) {
								if (b.board[i-1][j] == 'X') {
									// Create new node with b as the parent and add to b's children
									Node child = new Node(b.board, b, false, childDepth);
									child.board[i][j] = 'X';
									child.board[i-1][j] = 'W';
									child.fromX = i;
									child.fromY = j;
									child.xPos = i-1;
									child.yPos = j;
//									printBoard(child);
									b.children.add(child);
								}
								if (j == 0) {
									if (b.board[i-1][j+1] == 'X' || b.board[i-1][j+1] == 'B') {
										// Create new node with b as the parent and add to b's children
										Node child = new Node(b.board, b, false, childDepth);
										child.board[i][j] = 'X';
										child.board[i-1][j+1] = 'W';
										child.fromX = i;
										child.fromY = j;
										child.xPos = i-1;
										child.yPos = j+1;
//										printBoard(child);
										b.children.add(child);
									}
								}
								if (j == 1) {// 0) {
									if (b.board[i-1][j-1] == 'X' || b.board[i-1][j-1] == 'B') {
										// Create new node with b as the p1arent and add to b's children
										Node child = new Node(b.board, b, false, childDepth);
										child.board[i][j] = 'X';
										child.board[i-1][j-1] = 'W';
										child.fromX = i;
										child.fromY = j;
										child.xPos = i-1;
										child.yPos = j-1;
//										printBoard(child);
										b.children.add(child);
									}
									if (b.board[i-1][j+1] == 'X' || b.board[i-1][j+1] == 'B') {
										// Create new node with b as the parent and add to b's children
										Node child = new Node(b.board, b, false, childDepth);
										child.board[i][j] = 'X';
										child.board[i-1][j+1] = 'W';
										child.fromX = i;
										child.fromY = j;
										child.xPos = i-1;
										child.yPos = j+1;
//										printBoard(child);
										b.children.add(child);
									}
								}
								if (j == 2) {
									if (b.board[i-1][j-1] == 'X' || b.board[i-1][j-1] == 'B') {
										// Create new node with b as the parent and add to b's children
										Node child = new Node(b.board, b, false, childDepth);
										child.board[i][j] = 'X';
										child.board[i-1][j-1] = 'W';
										child.fromX = i;
										child.fromY = j;
										child.xPos = i-1;
										child.yPos = j-1;
//										printBoard(child);
										b.children.add(child);
									}
								}
							}
						}
					}
				}
			}
		/*
		 *  If the player is black, then it can only move forward. Find all places in the board where there 
		 *  is a 'B' and create a new board state with B all next possible places. 
		 */
			else {
				for (int i = 0; i < 6; i++) {
					for (int j = 0; j < 3; j++) {
						if (b.board[i][j] == 'B') {
							if (i < 5) {
								if (b.board[i+1][j] == 'X') {
									// Create new node with b as the parent and add to b's children
									Node child = new Node(b.board, b, true, childDepth);
									child.board[i][j] = 'X';
									child.board[i+1][j] = 'B';
									child.fromX = i;
									child.fromY = j;
									child.xPos = i+1;
									child.yPos = j;
//									printBoard(child);
									b.children.add(child);
								}
								if (j == 0) {
									if (b.board[i+1][j+1] == 'X' || b.board[i+1][j+1] == 'W') {
										// Create new node with b as the parent and add to b's children
										Node child = new Node(b.board, b, true, childDepth);
										child.board[i][j] = 'X';
										child.board[i+1][j+1] = 'B';
										child.fromX = i;
										child.fromY = j;
										child.xPos = i+1;
										child.yPos = j+1;
//										printBoard(child);
										b.children.add(child);
									}
								}
								if (j == 1) { // > 0) {
									if (b.board[i+1][j-1] == 'X' || b.board[i+1][j-1] == 'W') {
										// Create new node with b as the parent and add to b's children
										Node child = new Node(b.board, b, true, childDepth);
										child.board[i][j] = 'X';
										child.board[i+1][j-1] = 'B';
										child.fromX = i;
										child.fromY = j;
										child.xPos = i+1;
										child.yPos = j-1;
//										printBoard(child);
										b.children.add(child);
									}
									if (b.board[i+1][j+1] == 'X' || b.board[i+1][j+1] == 'W') {
										// Create new node with b as the parent and add to b's children
										Node child = new Node(b.board, b, true, childDepth);
										child.board[i][j] = 'X';
										child.board[i+1][j+1] = 'B';
										child.fromX = i;
										child.fromY = j;
										child.xPos = i+1;
										child.yPos = j+1;
//										printBoard(child);
										b.children.add(child);
									}
								}
								if (j == 2) {
									if (b.board[i+1][j-1] == 'X' || b.board[i+1][j-1] == 'W') {
										// Create new node with b as the parent and add to b's children
										Node child = new Node(b.board, b, true, childDepth);
										child.board[i][j] = 'X';
										child.board[i+1][j-1] = 'B';
										child.fromX = i;
										child.fromY = j;
										child.xPos = i+1;
										child.yPos = j-1;
//										printBoard(child);
										b.children.add(child);
									}
								}
							}
						}
					}
				}
			}
	}
	
	public static void printBoard(Node n) {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(n.board[i][j]);
			}
			System.out.println();
		}
		System.out.println(n.xPos);
		System.out.println(n.yPos);
		System.out.println("---------------------");
	}
	
	public char[][] readFile(String name) {
		char[][] board = new char[numRows][numCols];
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(name));
			
			String line;
            int col = 0, row = 0;
            
            try {
				while((line = reader.readLine()) != null && row < numRows) {
				    for(col = 0; col < line.length() && col < numCols; col++) {
				        board[row][col] = line.charAt(col);
				    }
				    row++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return board;
	}

}
