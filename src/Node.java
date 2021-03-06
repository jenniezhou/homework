import java.util.ArrayList;
import java.util.List;

public class Node {
	public char[][] board;
	public int heuristicValue = 0;
	public List<Node> children;
	public int depth;
	public int xPos;
	public int yPos;
	public int fromX;
	public int fromY;
	public Node parent;
	public boolean max;
	public List<Node> best;
	
	public Node(char[][] c, Node p, boolean m, int d) {
		children = new ArrayList<Node>();
		best = new ArrayList<Node>();
		
		board = new char[6][3];
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = c[i][j];
			}
		}

		parent = p;
		max = m; 
		depth = d;
	}
}
