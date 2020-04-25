/** @file Edge.java
@author Daniel Di Cesare
@brief Class to represent edges of the Dijkstra graph
@date February 28,2020
*/
package final_proj.DijkstrasPaths;

import final_proj.DijkstrasPaths.Vertex;

//Edge for Dijkstra graph
public class Edge  {
    private final String id;
    private final Vertex source;
    private final Vertex destination;
    private final int weight;

    //Stores source and destination within the edge.
    public Edge(String id, Vertex source, Vertex destination, int weight) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }
    public Vertex getDestination() {
        return destination;
    }

    public Vertex getSource() {
        return source;
    }
    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return source + " --- " + destination;
    }
}
