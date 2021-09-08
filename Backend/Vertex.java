package Eclipse;

public class Vertex {
    final private String id;
    final private String street;
    final private int weight;
    
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
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

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
