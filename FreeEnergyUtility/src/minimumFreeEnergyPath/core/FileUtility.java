package minimumFreeEnergyPath.core;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import minimumFreeEnergyPath.weightedVertexGraph.WeightedVertex;
import minimumFreeEnergyPath.weightedVertexGraph.WeightedVertexGraph;

import org.jgrapht.graph.DefaultWeightedEdge;

/** Utility class for reading and writing WeightedVertexGraph and GraphPath objects to file
 * 
 * @author Scott Gigante
 *
 */
public final class FileUtility {
	
	/** Utility class not to be instantiated */
	private FileUtility() {
	}
	
	/** Parses double surrounded by non-numeric text 
	 * @param s The string to be parsed 
	 */
	private static Double parseDouble(String s) {
		return Double.parseDouble(s.replaceAll("[^\\d.]", ""));
	}
	
	/** Reads output file from FreeEnergyError.r to a WeightedVertexGraph
	 * @param filename Name of R output file
	 * @param xStart Starting node of graph x coord
	 * @param yStart Starting node of graph y coord
	 * @return The WeightedVertexGraph
	 * @throws FileNotFoundException
	 */
	protected static WeightedVertexGraph readToGraph(String filename, double xStart, double yStart) throws FileNotFoundException {
		
		// read in input file
		Scanner scanner = new Scanner(new FileReader(filename));
		
		// create header array
		List<String> xStringHeader = new ArrayList<String>(Arrays.asList(scanner.nextLine().split("\\t")));
		xStringHeader.remove(0);
		ArrayList<Double> xHeader = new ArrayList<Double>();
		ArrayList<Double> yHeader = new ArrayList<Double>();
		for (String s : xStringHeader) {
			xHeader.add(parseDouble(s));
		}
		
		// read all values from table into vertex array - O(n)
		VertexArray vertexArray = new VertexArray();
		while (scanner.hasNextLine()) {
			String[] line = scanner.nextLine().split("\\t");
			double y = parseDouble(line[0]);
			yHeader.add(y);
			
			for (int i=1;i<line.length;i++) {
				try {
					vertexArray.add(new WeightedVertex(xHeader.get(i-1),y,parseDouble(line[i])));
				} catch (NumberFormatException e) {
					// Inf found, ignore it
				}
			}
		}
		
		vertexArray.initialise(xHeader, yHeader);
		
		// create graph and add vertices - O(n)
		WeightedVertexGraph g = new WeightedVertexGraph();
		for (WeightedVertex v : vertexArray) {
			g.addVertex(v);
		}
		
		// add edges to graph - O(8n)
		for (WeightedVertex v1 : vertexArray) {
			for (WeightedVertex v2 : vertexArray.getAdjacentVertices(v1)) {
				if (v1 != v2 && vertexArray.isAdjacent(v1, v2)) {
					g.addEdge(v1, v2);
				} else {
					System.out.println("fuck");
				}
			}
		}
		
		// set starting and ending points of graph
		g.setStartVertex(vertexArray.getAtCoords(xStart, yStart));
		g.setEndVertex(vertexArray.getAtWeight(0));
				
		// clean up
		scanner.close();
		return g;
	}
	
	/** Writes a path to file - O(n)
	 * @param filename The file to be written to
	 * @param g The graph on which the path lies
	 * @param path The path to be parsed and written in List form
	 */
	protected static void writeFromPath(String filename, WeightedVertexGraph g, List<DefaultWeightedEdge> path) {
		PrintWriter writer;
		try {
			// open file
			writer = new PrintWriter(filename, "UTF-8");
			
			// print the source of each edge in order
			for (DefaultWeightedEdge e : path) {
				WeightedVertex v = g.getEdgeSource(e);
				writer.println(v.toString());
			}
			// print the last edge
			writer.println(g.getEndVertex().toString());
			
			// clean up
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
