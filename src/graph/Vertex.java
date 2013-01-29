package graph;

import java.util.ArrayList;
import java.util.Collections;

import districtobjects.Placeable;

/**
 * Vertex is a graph-vertex defining the nearest neighbour. 
 * If a vertex is fixed this means that object can't move, for example a wall
 * TODO 
 * @author thomas
 */

public class Vertex implements Cloneable{
    private Placeable p;
    private Tuple velocity;
    private Tuple position;
    private double mass;
    private double distance;
    private ArrayList<Neighbour> neighbours = new ArrayList<Neighbour>();
    public boolean iscloned;
    public boolean isfixed;

      
    public Vertex(Placeable p){
    	this(p,false);
    }
    
    public Vertex(Placeable p, boolean isfixed){
    	this.p = p;
    	this.isfixed = isfixed;
    	velocity = new Tuple(0,0);
    }
    
    public Vertex(boolean isfixed){
    	this(null,true);
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
			return position;
		return new Tuple(p.getX(),p.getY());
	}
	
	public void setPosition(Tuple position){
	if(isfixed)
		this.position = position;
	else
	{
		p.setX(position.dx);
		p.setY(position.dy);
	}
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

	public ArrayList<Neighbour> getNeighbours() {
		return neighbours;
	}

	public void setNeighbours(ArrayList<Neighbour> neighbours) {
		this.neighbours = neighbours;
	}
	
	public ArrayList<Vertex>getKNearestNeighbours(int k){
		Collections.sort(this.neighbours);
		ArrayList<Vertex> neighbours = new ArrayList<Vertex>(k);
		for (int i = 0; i < k && i < this.neighbours.size(); i++){
			Neighbour n = this.neighbours.get(i);
			neighbours.add(n.neighbour);
		}
		return neighbours;
	}
}