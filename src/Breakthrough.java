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
		
		maxValue(root, -Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
//	public static int alphaBetaPrune(Node n, int a, int b, boolean max) {
//		if (cutOffTest(n)) {
//			return n.heuristicValue;
//		}
//		if (max) {
//			generateSuccessors(n.max, n);
//			for (Node c : n.children) {
//				n.alpha = Math.max(n.alpha, alphaBetaPrune(c, n.alpha, n.beta, !max));
//				if (n.beta <= n.alpha) {
//					break;
//				}
//			}
//			return n.alpha;
//		}
//		else {
//			generateSuccessors(n.max, n);
//			for (Node c : n.children) {
//				n.beta = Math.min(n.beta, alphaBetaPrune(c, n.alpha, n.beta, !max));
//				if (n.beta <= n.alpha) {
//					break;
//				}
//			}
//			return n.beta;
//		}
//	}
	
	// PLAYER A'S TURN
	public static int maxValue(Node n, int a, int b) {
		if (cutOffTest(n)) {
			return n.heuristicValue;
		}
		
		generateSuccessors(n.max, n);
		
		for (Node c : n.children) {
//			n.heuristicValue = Math.max(n.heuristicValue, minValue(c, a, b));
//			a = Math.max(n.heuristicValue, a);
			a = Math.max(a, minValue(c, a, b));
			if (a >= b) {
				return a;
			}
		}
		return a;
	}
	
	// PLAYER B'S TURN
	public static int minValue(Node n, int a, int b) {
		if (cutOffTest(n)) {
			System.err.println("Min value");
			return n.heuristicValue;
		}
		
		generateSuccessors(n.max, n);

		for (Node c : n.children) {
//			n.heuristicValue = Math.min(n.heuristicValue, maxValue(c, a, b));
//			b = Math.min(n.heuristicValue, b);
			b = Math.min(b, maxValue(c, a, b));
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
		
		int whiteCounter = 0;
		int blackCounter = 0;
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 3; j++) {
				if (n.board[j][i] == 'W') {
					++whiteCounter;
				}
				if (n.board[j][i] == 'B') {
					++blackCounter;
				}
			}
		}
		
		if (whiteCounter == 0) {
			n.heuristicValue = 1;
			return true;
		}
		
		if (blackCounter == 0) {
			n.heuristicValue = -1;
			return true;
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
									Node child = new Node(b.board, b, false);
									child.board[j][i] = 'X';
									child.board[j][i-1] = 'W';
									child.xPos = i-1;
									child.yPos = j;
	//								printBoard(forward);
									b.children.add(child);
								}
								if (j == 0) {
									if (b.board[j+1][i-1] == 'X' || b.board[j+1][i-1] == 'B') {
										// Create new node with b as the parent and add to b's children
										Node child = new Node(b.board, b, false);
										child.board[j][i] = 'X';
										child.board[j+1][i-1] = 'W';
										child.xPos = i-1;
										child.yPos = j+1;
	//									printBoard(forwardRight);
										b.children.add(child);
									}
								}
								if (j == 1) {// 0) {
									if (b.board[j-1][i-1] == 'X' || b.board[j-1][i-1] == 'B') {
										// Create new node with b as the parent and add to b's children
										Node child = new Node(b.board, b, false);
										child.board[j][i] = 'X';
										child.board[j-1][i-1] = 'W';
										child.xPos = i-1;
										child.yPos = j-1;
	//									printBoard(forwardLeft);
										b.children.add(child);
									}
									if (b.board[j+1][i-1] == 'X' || b.board[j+1][i-1] == 'B') {
										// Create new node with b as the parent and add to b's children
										Node child = new Node(b.board, b, false);
										child.board[j][i] = 'X';
										child.board[j+1][i-1] = 'W';
										child.xPos = i-1;
										child.yPos = j+1;
	//									printBoard(forwardRight);
										b.children.add(child);
									}
								}
								if (j == 2) {
									if (b.board[j-1][i-1] == 'X' || b.board[j-1][i-1] == 'B') {
										// Create new node with b as the parent and add to b's children
										Node child = new Node(b.board, b, false);
										child.board[j][i] = 'X';
										child.board[j-1][i-1] = 'W';
										child.xPos = i-1;
										child.yPos = j-1;
	//									printBoard(forwardLeft);
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
						if (b.board[j][i] == 'B') {
							if (i < 4) {
								if (b.board[j][i+1] == 'X') {
									// Create new node with b as the parent and add to b's children
									Node child = new Node(b.board, b, true);
									child.board[j][i] = 'X';
									child.board[j][i+1] = 'B';
									child.xPos = i+1;
									child.yPos = j;
	//								printBoard(forward);
									b.children.add(child);
								}
								if (j == 0) {
									if (b.board[j+1][i+1] == 'X' || b.board[j+1][i+1] == 'W') {
										// Create new node with b as the parent and add to b's children
										Node child = new Node(b.board, b, true);
										child.board[j][i] = 'X';
										child.board[j+1][i+1] = 'B';
										child.xPos = i+1;
										child.yPos = j+1;
	//									printBoard(forwardRight);
										b.children.add(child);
									}
								}
								if (j == 1) { // > 0) {
									if (b.board[j-1][i+1] == 'X' || b.board[j-1][i+1] == 'W') {
										// Create new node with b as the parent and add to b's children
										Node child = new Node(b.board, b, true);
										child.board[j][i] = 'X';
										child.board[j-1][i+1] = 'B';
										child.xPos = i+1;
										child.yPos = j-1;
	//									printBoard(forwardLeft);
										b.children.add(child);
									}
									if (b.board[j+1][i+1] == 'X' || b.board[j+1][i+1] == 'W') {
										// Create new node with b as the parent and add to b's children
										Node child = new Node(b.board, b, true);
										child.board[j][i] = 'X';
										child.board[j+1][i+1] = 'B';
										child.xPos = i+1;
										child.yPos = j+1;
	//									printBoard(forwardRight);
										b.children.add(child);
									}
								}
								if (j == 2) {
									if (b.board[j-1][i+1] == 'X' || b.board[j-1][i+1] == 'W') {
										// Create new node with b as the parent and add to b's children
										Node child = new Node(b.board, b, true);
										child.board[j][i] = 'X';
										child.board[j-1][i+1] = 'B';
										child.xPos = i+1;
										child.yPos = j-1;
	//									printBoard(forwardLeft);
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
