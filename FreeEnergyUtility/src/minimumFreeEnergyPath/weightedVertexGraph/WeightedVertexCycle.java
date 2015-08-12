package minimumFreeEnergyPath.weightedVertexGraph;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DefaultWeightedEdge;

/** Class representing a cycle in the Molecular Shortest Path algorithm
 * @author Scott Gigante
 *
 */
public class WeightedVertexCycle {
	
	/** List of vertices belonging to the cycle */
	private ArrayList<WeightedVertex> vertices;
	
	// Getters and setters
	public List<WeightedVertex> getVertices() {
        return vertices;
	}
	
	/** Create a new cycle object
	 * @param graph The graph in which the cycle lies
	 * @param start Any one of the vertices in the cycle
	 */
	public WeightedVertexCycle(WeightedVertexGraph graph, WeightedVertex start) {
		vertices = new ArrayList<WeightedVertex>();
		
		// add all connected vertices
		WeightedVertex next = start;
		do {
			WeightedVertexCycle cycle = next.getCycle();
        	if (cycle != null && cycle != this) {
        		// already part of another cycle, merge them
        		this.merge(cycle);
        		break;
        	}
			
			this.add(next);
            DefaultWeightedEdge minEdge = graph.getMinEdge(next);
        	
        	next = graph.getEdgeTarget(minEdge);
        	
        	
		} while (!this.contains(next));
	}
	
	/** Add a vertex to the cycle
	 * @param v The vertex to be added
	 */
	private void add(WeightedVertex v) {
		vertices.add(v);
		v.setCycle(this);
	}
	
	/** Check if a particular vertex belongs to this cycle
	 * @param v The vertex to be checked
	 * @return True if the vertex is contained, false otherwise
	 */
	public boolean contains(WeightedVertex v) {
		return vertices.contains(v);
	}
	
	/** Merge this cycle with another one
	 * This cycle is made redundant
	 * @param cycle The cycle to be merged
	 */
	private void merge(WeightedVertexCycle cycle) {
		for (WeightedVertex v : vertices) {
			cycle.add(v);
		}
		vertices.clear();
	}
	
	/** Merge this cycle with the cycle belonging to another vertex
	 * This cycle is made redundant
	 * If no cycle exists, the vertex is added to this cycle
	 * @param vertex The vertex whose cycle is to be merged
	 */
	public void merge(WeightedVertex vertex) {
		WeightedVertexCycle cycle = vertex.getCycle();
		if (cycle != null && !this.contains(vertex)) {
			merge(cycle);
		} else {
			add(vertex);
		}
	}
}
