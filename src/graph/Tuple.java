package graph;
public class Tuple implements Cloneable{
	public double dx,dy;
	
	public Tuple (double dx, double dy){
		this.dx = dx;
		this.dy = dy;
	}
	public Tuple(){
		this(0.0,0.0);
	}
	public void sum(Tuple other){
		this.dx += other.dx;
		this.dy += other.dy;
	}
	
	public void multiply(double factor){
		this.dx *= factor;
		this.dy *= factor;
	}	
	public static Tuple coulomb_repulsion(Vertex v1, Vertex v2){
		return new Tuple();
	}
	
	public static Tuple hooke_attraction(Vertex v1, Vertex v2){
		return new Tuple();
	}
	
	public Tuple clone()
	{
		return new Tuple(dx,dy);
	}
}