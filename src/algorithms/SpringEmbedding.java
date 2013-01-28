package algorithms;

import graph.Graph;
import graph.Tuple;
import graph.Vertex;

import java.util.ArrayList;
import java.util.Random;

import district.Groundplan;
import districtobjects.Placeable;
import districtobjects.Residence;
import districtobjects.WaterBody;

public class SpringEmbedding {

	public final double DAMPING = 0.005;
	public final double TIMESTEP = 1;
	Random random;
	
	public SpringEmbedding()
	{
		random = new Random(1);
	}

	public void springEmbed(Graph g){
		
		ArrayList<Vertex> vertices = g.getVertices();
		Tuple total_kinetic_energy = new Tuple();
		
		for (Vertex v : vertices){
			Tuple netto_sum = new Tuple();

			/*for each other node
			 * net-force := net-force + Coulomb_repulsion( this_node, other_node )
			 * next node
			 */
			for (Vertex v_other : vertices){
				if (v_other == v)
					continue;
				getWallPosition(g, v, v_other);
				netto_sum.sum(Tuple.coulombRepulsion(v,v_other, g.distanceBetween(v, v_other)));
			}
			/*
			 *   for each spring connected to this node
			 *   net-force := net-force + Hooke_attraction( this_node, spring )
			 *   next spring
			 */
			getWallPosition(g,v.getToVertex(),v);
			netto_sum.sum((Tuple.hookeAttraction(v,v.getToVertex())));
			//v.velocity := (v.velocity + timestep * net-force) * damping
			v.getVelocity().dx = (v.getVelocity().dx + TIMESTEP * netto_sum.dx) * DAMPING;
			v.getVelocity().dy = (v.getVelocity().dy + TIMESTEP * netto_sum.dy) * DAMPING;
			//v.position := v.position + timestep * this_node.velocity
			if(v.getPlaceable()!=null)
			{
				v.getPlaceable().setX(v.getPosition().dx + (TIMESTEP * v.getVelocity().dx));
				v.getPlaceable().setY(v.getPosition().dy + (TIMESTEP * v.getVelocity().dy));
				//calc nearest neighbour
				removeStack(v, vertices);
				bounce(v,g);
			}
			//total_kinetic_energy := total_kinetic_energy + this_node.mass * (this_node.velocity)^2
			total_kinetic_energy.dx =  total_kinetic_energy.dx + (v.getMass() * Math.sqrt(v.getVelocity().dx));
			total_kinetic_energy.dy =  total_kinetic_energy.dy+ (v.getMass() * Math.sqrt(v.getVelocity().dy));
			
			vertices= g.setNearestNeighbours();
		
		}		
	}

	private void removeStack(Vertex v,ArrayList<Vertex> vertices) {
		for(Vertex v2:vertices)
		{
			if(v==v2) continue;
			if(v.getPosition().equals(v2.getPosition()))
				v.setPosition(new Tuple(v.getPosition().dx+1,v.getPosition().dy));
		}
		
	}

	private void bounce(Vertex v,Graph graph) {
		bounceOutOfWater(v, graph);
		bounceFromWall(v);
	}

	private void bounceOutOfWater(Vertex v, Graph graph) {
		while(graph.getGroundplan().intersectsWithWater(v.getPlaceable()))
		{
			//Set v to old position
			v.getPlaceable().setX(v.getPosition().dx - (TIMESTEP * v.getVelocity().dx));
			v.getPlaceable().setY(v.getPosition().dy - (TIMESTEP * v.getVelocity().dy));
		}
	}
	
	private void bounceFromWall(Vertex v) {
		Placeable p = v.getPlaceable();
		if(p instanceof Residence)
		{
			bounceResidenceFromWall(p);
		}
		else
			bounceWaterFromWall(p);
	}

	private void bounceWaterFromWall(Placeable p) {
		if(p.getX()<0)
			p.setX(0);
		if(p.getX()+p.getWidth()>Groundplan.WIDTH)
			p.setX(Groundplan.WIDTH-p.getWidth());
		if(p.getY()<0)
			p.setY(0);
		if(p.getY()+p.getHeight()>Groundplan.HEIGHT)
			p.setY(Groundplan.HEIGHT-p.getHeight());
	}

	private void bounceResidenceFromWall(Placeable p) {
		Residence r=(Residence)p;
		
		if(r.getX()-r.getMinimumDistance()<0)
			r.setX(r.getMinimumDistance());
		if(r.getX()+r.getWidth()+r.getMinimumDistance()>Groundplan.WIDTH)
			r.setX(Groundplan.WIDTH-r.getMinimumDistance()-r.getWidth());
		if(r.getY()-r.getMinimumDistance()<0)
			r.setY(r.getMinimumDistance());
		if(r.getY()+r.getHeight()+r.getMinimumDistance()>Groundplan.HEIGHT)
			r.setY(Groundplan.HEIGHT-r.getMinimumDistance()-r.getHeight());
			
	}

	private void getWallPosition(Graph g, Vertex v, Vertex v_other) {
		try {
			if(v.isfixed)
				v.setPosition(g.wallPosition(v_other));
			if(v_other.isfixed)
				v_other.setPosition(g.wallPosition(v));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
