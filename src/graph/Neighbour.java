package graph;

public class Neighbour implements Comparable<Neighbour>{
	public double distance;
	public Vertex neighbour;
	@Override
	public int compareTo(Neighbour other) {
		if (this == other){
			return 0;
		}
		return (int) ((distance - other.distance)*100000);
	}
}