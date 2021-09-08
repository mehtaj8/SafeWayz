/** @file Graph.java
@author Daniel Di Cesare
@brief Class to represent the graph
@date February 28,2020
*/
package final_proj.DijkstrasPaths;

import java.util.List;
import final_proj.DijkstrasPaths.Vertex;
import final_proj.DijkstrasPaths.Edge;

public class Graph {
	private final List<Edge> edges;
	private final List<Vertex> vertexs;
	
	//creating a graph out of the given vertex/edges.
	public Graph(List<Vertex> vertexs, List<Edge> edges) {
		this.vertexs = vertexs;
		this.edges = edges;
	}
	
	public List<Edge> getEdges()  { return edges; }
	public List<Vertex> getVetexs() { return vertexs; }
	
}
