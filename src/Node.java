import java.util.ArrayList;
import java.util.List;

public class Node {
	public char[][] board;
	public int heuristicValue = 0;
	public List<Node> children;
	public Node parent;
	public boolean max;
	public int numCaptured;
	
	public Node(char[][] c, Node p, boolean m) {
		children = new ArrayList<Node>();
		
		board = new char[3][6];
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 3; j++) {
				board[j][i] = c[j][i];
			}
		}

		parent = p;
		max = m; 
		numCaptured = 0;
	}
}
