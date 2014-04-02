import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Breakthrough {
	
	private final int numRows = 6;
	private final int numCols = 3;
	
	public static void main(String[] args) {
		Breakthrough array = new Breakthrough();
		
		char[][] temp = array.readFile("src/input.txt");
		
		Node root = new Node(temp, null, true);
		
//		alphaBetaPrune(root, -Integer.MIN_VALUE, Integer.MAX_VALUE, true);
		
		maxValue(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
		
		System.err.println(root.heuristicValue);
	}
	
	public static int alphaBetaPrune(Node n, int a, int b, boolean max) {
		if (cutOffTest(n)) {
			return n.heuristicValue;
		}
		if (max) {
			generateSuccessors(n.max, n);
			for (Node c : n.children) {
				a = Math.max(a, alphaBetaPrune(c, a, b, !max));
				if (b <= a) {
					break;
				}
			}
			return a;
		}
		else {
			generateSuccessors(n.max, n);
			for (Node c : n.children) {
				b = Math.min(b, alphaBetaPrune(c, a, b, !max));
				if (b <= a) {
					break;
				}
			}
			return b;
		}
	}
	
	// PLAYER A'S TURN
	public static int maxValue(Node n, int a, int b) {
		if (cutOffTest(n)) {
			return n.heuristicValue;
		}
		
		generateSuccessors(n.max, n);
		
		for (Node c : n.children) {
			n.heuristicValue = Math.max(n.heuristicValue, minValue(c, a, b));
			a = Math.max(n.heuristicValue, a);
			if (a >= b) {
				return a;
			}
		}
		return a;
	}
	
	// PLAYER B'S TURN
	public static int minValue(Node n, int a, int b) {
		if (cutOffTest(n)) {
			return n.heuristicValue;
		}
		
		generateSuccessors(n.max, n);

		for (Node c : n.children) {
			n.heuristicValue = Math.min(n.heuristicValue, maxValue(c, a, b));
			b = Math.min(n.heuristicValue, b);
			if (b <= a) {
				return b;
			}
		}
		return b;
	}
	
	public static boolean cutOffTest(Node n) {
		// If white has reached black's side of the board
		for (int i = 0; i < 3; i++) {
			if (n.board[i][0] == 'W') {
				n.heuristicValue = 1;
				return true;
			}
		}
		
		// If black has reached white's side of the board
		for (int i = 0; i < 3; i++) {
			if (n.board[i][5] == 'B') {
				n.heuristicValue = -1;
				return true;
			}
		}	
		
		// If the number of pieces captured is six, then return true
		if (n.numCaptured == 6) {
			if (n.max) {
				n.heuristicValue = 1;
				return true;
			}
			else {
				n.heuristicValue = -1;
				return true;
			}
		}
		return false;
	}
	
	public static void generateSuccessors(boolean max, Node b) {		
		/*
		 *  If the player is white, then it can only move forward. Find all places in the board where there 
		 *  is a 'W' and create a new board state with W all next possible places. 
		 */
		if (max) {
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 3; j++) {
						if (b.board[j][i] == 'W') {
							if (i > 0) {
								if (b.board[j][i-1] == 'X') {
									// Create new node with b as the parent and add to b's children
									Node forward = new Node(b.board, b, false);
									forward.board[j][i] = 'X';
									forward.board[j][i-1] = 'W';
	//								printBoard(forward);
									b.children.add(forward);
								}
								if (j == 1) {// 0) {
									if (b.board[j-1][i-1] == 'X' || b.board[j-1][i-1] == 'B') {
										// Create new node with b as the parent and add to b's children
										Node forwardLeft = new Node(b.board, b, false);
										forwardLeft.board[j][i] = 'X';
										forwardLeft.board[j-1][i-1] = 'W';
	//									printBoard(forwardLeft);
										b.children.add(forwardLeft);
									}
//								}
//								if (j < 2) {
									if (b.board[j+1][i-1] == 'X' || b.board[j+1][i-1] == 'B') {
										// Create new node with b as the parent and add to b's children
										Node forwardRight = new Node(b.board, b, false);
										forwardRight.board[j][i] = 'X';
										forwardRight.board[j+1][i-1] = 'W';
	//									printBoard(forwardRight);
										b.children.add(forwardRight);
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
						if (b.board[j][i] == 'B') {
							if (i < 4) {
								if (b.board[j][i+1] == 'X') {
									// Create new node with b as the parent and add to b's children
									Node forward = new Node(b.board, b, true);
									forward.board[j][i] = 'X';
									forward.board[j][i+1] = 'B';
	//								printBoard(forward);
									b.children.add(forward);
								}
								if (j == 1) { // > 0) {
									if (b.board[j-1][i+1] == 'X' || b.board[j-1][i+1] == 'W') {
										// Create new node with b as the parent and add to b's children
										Node forwardLeft = new Node(b.board, b, true);
										forwardLeft.board[j][i] = 'X';
										forwardLeft.board[j-1][i+1] = 'B';
	//									printBoard(forwardLeft);
										b.children.add(forwardLeft);
									}
//								}
//								if (j < 2) {
									if (b.board[j+1][i+1] == 'X' || b.board[j+1][i+1] == 'W') {
										// Create new node with b as the parent and add to b's children
										Node forwardRight = new Node(b.board, b, true);
										forwardRight.board[j][i] = 'X';
										forwardRight.board[j+1][i+1] = 'B';
	//									printBoard(forwardRight);
										b.children.add(forwardRight);
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
				System.out.print(n.board[j][i]);
			}
			System.out.println();
		}
		System.out.println("---------------------");
	}
	
	public char[][] readFile(String name) {
		char[][] board = new char[numCols][numRows];
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(name));
			
			String line;
            int col = 0, row = 0;
            
            try {
				while((line = reader.readLine()) != null && row < numRows) {
				    for(col = 0; col < line.length() && col < numCols; col++) {
				        board[col][row] = line.charAt(col);
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
