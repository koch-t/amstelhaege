package graph;

import districtobjects.Placeable;

/**
 * Vertex is a graph-vertex defining the nearest neighbour. 
 * If a vertex is fixed this means that object can't move, for example a wall
 * TODO 
 * @author thomas
 */

public class Vertex implements Cloneable{
    private Placeable p;
    private boolean isfixed;
    private Tuple velocity;
    private double mass;
    private Vertex toVertex;
    private double distance;
    
    @Override
    public Vertex clone(){
    	Vertex clone = new Vertex(this.p,isfixed);
    	clone.velocity = (Tuple) velocity.clone();
    	return clone;
    }
    
    public Vertex(Placeable p){
    	this(p,false);
    }
    
    public Vertex(Placeable p, boolean isfixed){
    	this.p = p;
    	this.isfixed = isfixed;
    }
    
    public Vertex(boolean isfixed){
    	this(null,true);
    }
    
    /**
     * @return the edge defining the nearest neighbour.
     */       
    public Vertex getToVertex(){
    	return toVertex;
    }
 
    /**
     * Set the nearest neighbour relation (directed)
     */
    public void setToVertex(Vertex toVertex, double distance){
    	this.toVertex = toVertex;
    }
    
    /**
     * @return Veolicty of the vertex
     */

	public Tuple getVelocity() {
		return velocity;
	}
	
    /**
     * set the veolicity of the vertex
     */

	public void setVelocity(Tuple velocity) {
		this.velocity = velocity;
	}
	
	/**
	 * 
	 * @return Postion object with the position of the object.
	 * null when there is no position for example a wall.
	 * TODO Or set position of wall? When calculating distance important to consider width and length
	 */
	
	public Tuple getPosition(){
		if (isfixed)
			return null;
		return new Tuple(p.getX(),p.getY());
	}
	
	public Placeable getPlaceable(){
		return this.p;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
}