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
		return v1.getPlaceable().getDistance(v2.getPlaceable());
	}
	
	public ArrayList<Vertex> setNearestNeighbours(){
		Vertex wall = new Vertex(true);
		for (Vertex v1 : vertices){
			Vertex nearest = wall;
			double distance = distanceToWall(v1);
			for (Vertex v2 : vertices){
				if (v1 == v2)
					continue;
				double curdist = distanceBetween(v1, v2);
				if (curdist < distance){
					nearest = v2;
					distance = curdist;
				}
			}
			v1.setToVertex(nearest,distance);
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
}
