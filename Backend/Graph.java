package Eclipse;

import java.util.List;
import Eclipse.Vertex;
import Eclipse.Edge;

public class Graph {
	private final List<Edge> edges;
	private final List<Vertex> vertexs;
	
	public Graph(List<Vertex> vertexs, List<Edge> edges) {
		this.vertexs = vertexs;
		this.edges = edges;
	}
	
	public List<Edge> getEdges()  { return edges; }
	public List<Vertex> getVetexs() { return vertexs; }
	
}
