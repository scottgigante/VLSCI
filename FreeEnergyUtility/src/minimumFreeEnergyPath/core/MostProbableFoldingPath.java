/* ==========================================
 * JGraphT : a free Java graph-theory library
 * ==========================================
 *
 * Project Info:  http://jgrapht.sourceforge.net/
 * Project Creator:  Barak Naveh (http://sourceforge.net/users/barak_naveh)
 *
 * (C) Copyright 2003-2008, by Barak Naveh and Contributors.
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
/* -------------------------
 * MolecularShortestPath.java
 * 
 * Modified from: DijkstraShortestPath.java
 * -------------------------
 * (C) Copyright 2003-2008, by John V. Sichi and Contributors.
 *
 * Original Author:  John V. Sichi
 * Contributor(s):   Christian Hammer, Scott Gigante
 *
 * $Id$
 *
 * Changes
 * -------
 * 02-Sep-2003 : Initial revision (JVS);
 * 29-May-2005 : Make non-static and add radius support (JVS);
 * 07-Jun-2005 : Made generic (CH);
 * 07-Jan-2015 : Modified to Molecular Shortest Path
 */
package minimumFreeEnergyPath.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import minimumFreeEnergyPath.weightedVertexGraph.WeightedVertex;
import minimumFreeEnergyPath.weightedVertexGraph.WeightedVertexCycle;
import minimumFreeEnergyPath.weightedVertexGraph.WeightedVertexGraph;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.GraphPathImpl;



/**
 * An implementation of Most Probable Folding Path 
 * Follows lowest edge weight until target node is reached, ignoring all dead ends and cycles
 *
 * @author John V. Sichi, modified by Scott Gigante
 * @since Sep 2, 2003
 */
public final class MostProbableFoldingPath
{
    

    private GraphPath<WeightedVertex, DefaultWeightedEdge> path;


    /**
     * Creates and executes a new MostProbableFoldingPath algorithm instance. An
     * instance is only good for a single search; after construction, it can be
     * accessed to retrieve information about the path found.
     *
     * @param graph the graph to be searched
     * @param startVertex the vertex at which the path should start
     * @param endVertex the vertex at which the path should end
     * Double.POSITIVE_INFINITY for unbounded search
     */
    public MostProbableFoldingPath(
        WeightedVertexGraph graph,
        WeightedVertex startVertex,
        WeightedVertex endVertex)
    {
        if (!graph.containsVertex(endVertex)) {
            throw new IllegalArgumentException(
                "graph must contain the end vertex");
        }

        List<DefaultWeightedEdge> edgeList = createEdgeList(graph, startVertex, endVertex);
        
        removeDeadEnds(graph, edgeList);
        
        path =
                new GraphPathImpl<WeightedVertex, DefaultWeightedEdge>(
                    graph,
                    startVertex,
                    endVertex,
                    edgeList,
                    0);
    }

    

    /**
     * Return the edges making up the path found.
     *
     * @return List of Edges, or null if no path exists
     */
    public List<DefaultWeightedEdge> getPathEdgeList()
    {
        if (path == null) {
            return null;
        } else {
            return path.getEdgeList();
        }
    }

    /**
     * Return the path found.
     *
     * @return path representation, or null if no path exists
     */
    public GraphPath<WeightedVertex, DefaultWeightedEdge> getPath()
    {
        return path;
    }

    /**
     * Convenience method to find the shortest path via a single static method
     * call. If you need a more advanced search (e.g. limited by radius, or
     * computation of the path length), use the constructor instead.
     *
     * @param graph the graph to be searched
     * @param startVertex the vertex at which the path should start
     * @param endVertex the vertex at which the path should end
     *
     * @return List of Edges, or null if no path exists
     */
    public static List<DefaultWeightedEdge> findPathBetween(
        WeightedVertexGraph graph,
        WeightedVertex startVertex,
        WeightedVertex endVertex)
    {
        MostProbableFoldingPath alg =
            new MostProbableFoldingPath(
                graph,
                startVertex,
                endVertex);

        return alg.getPathEdgeList();
    }

    /**
     * The core of the algorithm
     * touches each vertex once in visiting and potentially touches one other 
     * vertex for each visited vertex in creating cycles - O(2n)
     * 
     * @param graph The graph within which to search
     * @param startVertex The vertex at which to start
     * @param endVertex The vertex at which to end
     * @return A list of edges indicating the path to be taken
     */
    private List<DefaultWeightedEdge> createEdgeList(
        WeightedVertexGraph graph,
        WeightedVertex startVertex,
        WeightedVertex endVertex)
    {
    	
    	// iterate through graph
        List<DefaultWeightedEdge> edgeList = new ArrayList<DefaultWeightedEdge>();
        List<WeightedVertex> visited = new ArrayList<WeightedVertex>();
        WeightedVertex currentVertex = startVertex;
        
        while (currentVertex != endVertex) {
        	if (!visited.contains(currentVertex)) {
        		visited.add(currentVertex);
        	}
        	
        	DefaultWeightedEdge minEdge = graph.getMinEdge(currentVertex);
        	DefaultWeightedEdge minCycleEdge = graph.getMinEdgeInCycle(currentVertex);

        	if (minEdge == null) {
        		// reached a dead end, go back
        		DefaultWeightedEdge previousEdge = edgeList.get(edgeList.size()-1);
        		minCycleEdge = graph.getEdge(currentVertex, graph.getEdgeSource(previousEdge));
        	}
        	
        	WeightedVertex minCycleEdgeTarget = graph.getEdgeTarget(minCycleEdge);
        	while (visited.contains(minCycleEdgeTarget)) {
        		// we've found a cycle!
        		WeightedVertexCycle currentCycle = currentVertex.getCycle();
        		WeightedVertexCycle targetCycle = minCycleEdgeTarget.getCycle();
        		if (currentCycle != null) {
        			currentCycle.merge(minCycleEdgeTarget);
        		} else if (targetCycle != null) {
        			targetCycle.merge(currentVertex);
        		} else {
        			currentCycle = new WeightedVertexCycle(graph, currentVertex);
        		}
        		minCycleEdge = graph.getMinEdgeInCycle(currentVertex);
        		minCycleEdgeTarget = graph.getEdgeTarget(minCycleEdge);
        	}
        	
        	// proceed to the next node
        	edgeList.add(minCycleEdge);
        	currentVertex = graph.getEdgeTarget(minCycleEdge);
        }

    	return edgeList;
        
    }

    /** Removes any dead-end paths from a list of edges in order to remove noise
     * Runs through edge array only once, max number of edges = number of vertices - O(n)
     * @param edgeList The list of edges to be parsed
     */
    public void removeDeadEnds(WeightedVertexGraph graph, List<DefaultWeightedEdge> edgeList) {
	    Collections.reverse(edgeList);
    	Iterator<DefaultWeightedEdge> i = edgeList.iterator();
	    if (i.hasNext()) {
	    	DefaultWeightedEdge currentEdge = i.next();
	    	while (i.hasNext()) {
	    		DefaultWeightedEdge previousEdge = currentEdge;
	    		currentEdge = i.next();
	    		if (graph.getEdgeTarget(currentEdge) != graph.getEdgeSource(previousEdge)) {
	    			// dead end! remove currentEdge
	    			i.remove();
	    			currentEdge = previousEdge;
	    		}
	    	}
	    }
	    Collections.reverse(edgeList);
    }
}

// End MostProbableFoldingPath.java