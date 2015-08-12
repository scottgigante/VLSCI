package minimumFreeEnergyPath.core;

import java.util.ArrayList;
import java.util.List;

import minimumFreeEnergyPath.weightedVertexGraph.WeightedVertex;

/** Helper class used in creation of a WeightedVertexGraph
 * Uses a grid to reduce search time
 * @author Scott Gigante
 * @since Jan 2015
 */
public class VertexArray extends ArrayList<WeightedVertex> {
	private static final long serialVersionUID = 1L;
	
	/** List of column names */
	private ArrayList<Double> xHeader;
	/** List of row names */
	private ArrayList<Double> yHeader;
	
	private WeightedVertex[][] vertexGrid;
	
	// Getters and setters
	private void setXHeader(ArrayList<Double> xHeader) {
		this.xHeader = xHeader;
	}
	
	private void setYHeader(ArrayList<Double> yHeader) {
		this.yHeader = yHeader;
	}
	
	/** Put all vertices into a grid
	 * @param xHeader The names of columns
	 * @param yHeader The names of rows
	 */
	protected void initialise(ArrayList<Double> xHeader, ArrayList<Double> yHeader) {
		setXHeader(xHeader);
		setYHeader(yHeader);
		vertexGrid = new WeightedVertex[xHeader.size()][yHeader.size()];
		for (WeightedVertex v : this) {
			vertexGrid[xHeader.indexOf(v.getX())][yHeader.indexOf(v.getY())] = v;
		}
	}
	
	/** Finds the vertex at given coordinates, if it exists - O(1)
	 * @param x The x coordinate to be retrieved
	 * @param y The y coordinate to be retrieved
	 * @return The vertex at (x,y), or null if there isn't one
	 */
	protected WeightedVertex getAtCoords(double x, double y) {
		return vertexGrid[xHeader.indexOf(x)][yHeader.indexOf(y)];
	}
	
	/** Finds the vertex at given weight, if it exists - O(n)
	 * @param weight The weight to be retrieved
	 * @return The vertex with given weight, or null if there isn't one
	 */
	protected WeightedVertex getAtWeight(double weight) {
		for (WeightedVertex v : this) {
			if (v.getWeight() == weight) {
				return v;
			}
		}
		return null;
	}
	
	/** Checks if two vertices are adjacent, assuming diagonal adjacency is allowed
	 * @param v1 The first vertex to be checked
	 * @param v2 The second vertex to be checked
	 * @return True if they are adjacent, false otherwise
	 */
	protected boolean isAdjacent(WeightedVertex v1, WeightedVertex v2) {
		return Math.abs(xHeader.indexOf(v1.getX()) - xHeader.indexOf(v2.getX())) <= 1 &&  Math.abs(yHeader.indexOf(v1.getY()) - yHeader.indexOf(v2.getY())) <= 1;
	}
	
	/** Retrieves an array of vertices adjacent to a given vertex - O(8)
	 * Diagonally adjacent vertices accepted
	 * @param vertex The central vertex
	 * @return A list of vertices
	 */
	protected List<WeightedVertex> getAdjacentVertices(WeightedVertex vertex) {
		ArrayList<WeightedVertex> adjacentList = new ArrayList<WeightedVertex>();
		// cardinal directions first
		addAdjacent(adjacentList,vertex,-1,0);
		addAdjacent(adjacentList,vertex,1,0);
		addAdjacent(adjacentList,vertex,0,1);
		addAdjacent(adjacentList,vertex,0,-1);
		// diagonal directions
		addAdjacent(adjacentList,vertex,-1,-1);
		addAdjacent(adjacentList,vertex,1,1);
		addAdjacent(adjacentList,vertex,-1,1);
		addAdjacent(adjacentList,vertex,1,-1);
		return adjacentList;
	}
	
	private void addAdjacent(List<WeightedVertex> list, WeightedVertex vertex, int xDir, int yDir) {
		try {
			WeightedVertex adj = getAtCoords(xHeader.get(xHeader.indexOf(vertex.getX())+xDir),yHeader.get(yHeader.indexOf(vertex.getY())+yDir));
			if (adj != null) {
				list.add(adj);
			}
		} catch (IndexOutOfBoundsException e) {
			// we're on the edge of the grid, no problems here
		}
	}
}
