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
	
	public Tuple subtract(Tuple a, Tuple b){
		Tuple tuple = new Tuple();
		tuple.dx = a.dx - b.dx;
		tuple.dy = a.dy - b.dy;
		return tuple;
	}	
	
	/*
	 * Coulomb's law, force of attraction/repulsion
     * F = Q₁Q₂/(4πε₀r²)
     * where ε₀ = 8.8542e-12 F/m = permittivity of space (Farad/meter)
     * Q₁ and Q₂ are the charges in coulombs
     * r is separation in meters
     * 1 electron charge = 1.602e-19 C
	 */
	public static Tuple coulomb_repulsion(Vertex v1, Vertex v2){
		Tuple result = new Tuple();
		double q1 = v1.getPlaceable() == null ? 0 : v1.getPlaceable().getHeight() * v1.getPlaceable().getWidth() * 1.602e-19;
		double q2 = v1.getPlaceable() == null ? 0 : v1.getPlaceable().getHeight() * v1.getPlaceable().getWidth() * 1.602e-19;
        double distance = 0;
        double permittivityspace = 8.854E-12;
		result.dx = (q1*q2) / (4*Math.PI*permittivityspace*Math.sqrt(distance));
		result.dy = (q1*q2) / (4*Math.PI*permittivityspace*Math.sqrt(distance));
		return result;
	}
	
	public static Tuple hooke_attraction(Vertex v1, Vertex v2){ // On node01 on node2
		return new Tuple();
	}
	
	public Tuple clone(){
		return new Tuple(this.dx,this.dy);
	}
}