package graph;

import java.util.ArrayList;

import district.Groundplan;
import districtobjects.Ground;
import districtobjects.Residence;
import districtobjects.WaterBody;

public class Graph{
	
	private Groundplan plan;
	private ArrayList<Vertex> vertices;

	public Graph(Groundplan plan){
		this.plan = plan;
		this.vertices = buildGraph(plan);
		setNearestNeighbours();
	}
	
	public double distanceToWall(Vertex v){
		if (v.getPosition() == null){
			return 0;
		}
		double distance = 0.0;
		Ground ground  = plan.getGround();
		distance = Math.max(distance,v.getPlaceable().rightEdge() - ground.rightEdge());
		distance = Math.max(distance,v.getPlaceable().leftEdge() - ground.leftEdge());
		distance = Math.max(distance,v.getPlaceable().topEdge() - ground.topEdge());
		distance = Math.max(distance,v.getPlaceable().bottomEdge() - ground.bottomEdge());
		return distance;
	}
	
	public double distanceBetween(Vertex v1, Vertex v2){
		if(v1.isfixed)
			return distanceToWall(v2);
		if(v2.isfixed)
			return distanceToWall(v1);
		
		return v1.getPlaceable().getDistance(v2.getPlaceable());
	}
	
	public Tuple wallPosition(Vertex v) throws Exception
	{
		if(distanceToWall(v)+v.getPlaceable().getX()==120)
			return new Tuple(120,v.getPlaceable().getY());
		else if(v.getPlaceable().getX()-distanceToWall(v)==0)
			return new Tuple(0,v.getPlaceable().getY());
		else if(v.getPlaceable().getY()+distanceToWall(v)==160)
			return new Tuple(v.getPlaceable().getX(),160);
		else if(v.getPlaceable().getY()-distanceToWall(v)==0)
			return new Tuple(v.getPlaceable().getX(),160);
		throw new Exception("Combination of vertex position and distance to wall does not hit the wall");
	}
	
	public ArrayList<Vertex> setNearestNeighbours(){
		Vertex wall = new Vertex(true);
		for (Vertex v1 : vertices){
			ArrayList<Neighbour> neighbours = new ArrayList<Neighbour>(vertices.size());
			Neighbour n = new Neighbour();
			n.distance = distanceToWall(v1);
			n.neighbour = wall;
			neighbours.add(n);
			for (Vertex v2 : vertices){
				if (v1 == v2)
					continue;
				n = new Neighbour();
				n.distance =  distanceBetween(v1, v2);
				n.neighbour = v2;
				neighbours.add(n);
			}
			v1.setNeighbours(neighbours);
		}
		return vertices;
	}
	
	public ArrayList<Vertex> buildGraph(Groundplan plan){
		ArrayList<Vertex> vertices = new ArrayList<Vertex>(100);
		for (Residence r: plan.getResidences()){
			vertices.add(new Vertex(r));
		}
		for (WaterBody w: plan.getWaterBodies()){
			vertices.add(new Vertex(w));
		}
		return vertices;
	}

	public ArrayList<Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<Vertex> vertices) {
		this.vertices = vertices;
	}
	public Graph clone()
	{
		Graph clone = new Graph(plan.clone());
		return clone;
	}

	/*
	private void changeVertexCloneState(Graph clone, boolean b) {
		for(Vertex v:vertices)
			v.iscloned=false;
		for(Vertex v:clone.vertices)
			v.iscloned=false;
	}
*/
	public Groundplan getGroundplan()
	{
		return plan;
	}
	/*
	private void cloneV(ArrayList<Vertex> clonelist, Vertex v) {
		Vertex cloneofv;
		
		cloneofv=v.clone();
		while(!clonelist.contains(cloneofv))
		{
			clonelist.add(cloneofv);
			if(cloneofv.getToVertex()!=null)
				cloneofv=cloneofv.getToVertex();
		}
		if(cloneofv.isfixed)
			clonelist.remove(cloneofv);
			
	}*/
}

