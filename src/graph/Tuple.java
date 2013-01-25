package graph;

import districtobjects.WaterBody;

public class Tuple implements Cloneable{

	public static double hookefactor=1;
	public static double coulombfactor= 1e-7;;

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

	public static Tuple coulombRepulsion(Vertex pThis, Vertex pOther,double distance) {
		double s = 1.0 / Math.pow(distance,2);
		double productcharge = pThis.getMass()*pOther.getMass();
		return new Tuple(((pOther.getPosition().dx - pThis.getPosition().dx)*productcharge) / s * coulombfactor, ((pOther.getPosition().dy - pThis.getPosition().dy)*productcharge) / s * coulombfactor);
	}

	public static Tuple hookeAttraction(Vertex pThis, Vertex pOther) {
		if(pThis.getPlaceable() instanceof WaterBody || pOther.getPlaceable() instanceof WaterBody)
			return new Tuple(0,0);
		return new Tuple((pOther.getPosition().dx - pThis.getPosition().dx) * (-hookefactor), (pOther.getPosition().dy - pThis.getPosition().dy) * (-hookefactor));
	}
	public Tuple clone(){
		return new Tuple(this.dx,this.dy);
	}
	
	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Tuple))return false;
	    
	    Tuple otherTuple = (Tuple)other;
	    return this.dx==otherTuple.dx && this.dy==otherTuple.dy;
	}
}
