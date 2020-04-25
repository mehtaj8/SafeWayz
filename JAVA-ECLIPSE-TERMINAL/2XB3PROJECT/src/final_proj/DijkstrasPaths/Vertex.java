/** @file Vertex.java
@author Daniel Di Cesare
@brief Class to represent vertices
@date February 28,2020
*/
package final_proj.DijkstrasPaths;

//Vertex class for Dijkstra graph
public class Vertex {
    final private String id;
    final private String street;
    final private int weight;
    
    //Store street name within vertex
    public Vertex(String id, String street, int weight) {
        this.id = id;
        this.street = street;
        this.weight = weight;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return street;
    }

    public int getWeight() {
    	return weight;
    }
    
    //Used for has index
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    //Overriding the built-in equality
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vertex other = (Vertex) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return street;
    }

}
