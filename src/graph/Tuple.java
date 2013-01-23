package graph;
public class Tuple implements Cloneable{

	public double coulombFactor = 1e-7;
	public double hookeFactor = 1;

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

	public Tuple coulombRepulsion(Vertex pThis, Vertex pOther,double distance) {
		double s = 1.0 / Math.pow(distance,3);
		return new Tuple((pOther.getPosition().dx - pThis.getPosition().dx) / s * coulombFactor, (pOther.getPosition().dy - pThis.getPosition().dy) / s * coulombFactor);


	public Tuple hookeAttraction(Vertex pThis, Vertex pOther) {
		return new Tuple((pOther.getPosition().dx - pThis.getPosition().dx) * (-hookeFactor), (pOther.getPosition().dy - pThis.getPosition().dy) * (-hookeFactor));
	}
	public Tuple clone(){
		return new Tuple(this.dx,this.dy);
	}

}
