package minimumFreeEnergyPath.weightedVertexGraph;

/** A vertex object with specified weight to be added to a WeightedVertexGraph
 * @author Scott Gigante
 *
 */
public class WeightedVertex {
	
	/** x coordinate associated with the vertex */
	private final double x;
	/** y coordinate associated with the vertex */
	private final double y;
	/** weight associated with the vertex */
	private final double weight;
	/** The cycle this vertex belongs to, if any */
	private WeightedVertexCycle cycle;
	
	// Getters and setters
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getWeight() {
		return weight;
	}

	public WeightedVertexCycle getCycle() {
		return cycle;
	}

	public void setCycle(WeightedVertexCycle cycle) {
		this.cycle = cycle;
	}

	/** Constructor creates a new WeightedVertex object
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param weight The vertex's weight
	 */
	public WeightedVertex(double x, double y, double weight) {
		super();
		this.x = x;
		this.y = y;
		this.weight = weight;
	}
	
	/* (non-Javadoc)
	 * Method used for printing a vertex to a TSV file
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getX() + "\t" + getY() + "\t" + getWeight();
	}
	
}
