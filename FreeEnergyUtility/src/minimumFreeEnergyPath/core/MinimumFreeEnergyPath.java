package minimumFreeEnergyPath.core;

import java.io.FileNotFoundException;
import java.util.List;

import minimumFreeEnergyPath.weightedVertexGraph.WeightedVertexGraph;

import org.jgrapht.graph.DefaultWeightedEdge;


/** Finds the most likely folding path between a point on the free energy 
 * surface and the point of minimum free energy
 * 
 * ==========================================
 * This project uses JGraphT : a free Java graph-theory library
 *
 * JGraphT Project Info:  http://jgrapht.sourceforge.net/
 * JGraphT Project Creator:  Barak Naveh (http://sourceforge.net/users/barak_naveh)
 *
 * JGraphT (C) Copyright 2003-2008, by Barak Naveh and Contributors.
 *
 * JGraphT and the accompanying materials are dual-licensed under
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
 * 
 * @author Scott Gigante
 * @since 07-Jan-2015
 */
public class MinimumFreeEnergyPath {
	
	/** Suffix to be appended to output filename */
	private static final String OUTPUT_SUFFIX = "_path.dat";

	/** Main method run from command line. Requires tabulated free energy 
	 * surface and x and y coordinates of the starting position
	 * @param args Command line arguments. 
	 * Usage: java MinimumFreeEnergyPath filename xcoord ycoord
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			//printUsageMessage();
			args = new String[3];
			args[0] = "dat/free_energy_select_above_delete_yes_5_not-normalized.dat";
			args[1] = "33";
			args[2] = "0";
		}
		try {
			String fileName = args[0];
			double xStart = Double.parseDouble(args[1]);
			double yStart = Double.parseDouble(args[2]);
			WeightedVertexGraph g = FileUtility.readToGraph(fileName, xStart, yStart); // O(n)
			
			List<DefaultWeightedEdge> path = MostProbableFoldingPath.findPathBetween(g, g.getStartVertex(), g.getEndVertex()); // O(n)
			
			FileUtility.writeFromPath(fileName + OUTPUT_SUFFIX, g, path); // O(n)
		} catch (NumberFormatException e) {
			System.out.println("Coordinates must be numeric.");
			e.printStackTrace(System.out);
			printUsageMessage();
		} catch (FileNotFoundException e) {
			System.out.println("File " + args[0] + " not found.");
			System.exit(0);
		}
	}
	
	/** Prints a message indicating the program was run incorrectly and quits */
	private static void printUsageMessage() {
		System.out.println("Usage: java MinimumFreeEnergyPath <filename> <xcoord> <ycoord>");
		System.exit(0);
	}
}

//End MinimumFreeEnergyPath.java