package algorithms;

import graph.Graph;
import graph.Tuple;
import graph.Vertex;

import java.util.ArrayList;

public class SpringEmbedding {

	public final double DAMPING = 0.5;
	public final double TIMESTEP = 1;

	public void springEmbed(Graph g){
		
		ArrayList<Vertex>vertices = g.getVertices();
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
				if(v.isfixed)
					getWallPosition(g, v, v_other);
				netto_sum.sum(Tuple.coulombRepulsion(v,v_other, Graph.distanceBetween(v, v_other)));
			}
			/*
			 *   for each spring connected to this node
			 *   net-force := net-force + Hooke_attraction( this_node, spring )
			 *   next spring
			 */
			if(!v.isfixed)
				netto_sum.sum((Tuple.hookeAttraction(v,v.getToVertex())));
			//v.velocity := (v.velocity + timestep * net-force) * damping
			v.getVelocity().dx = (v.getVelocity().dx * TIMESTEP * netto_sum.dx) * DAMPING;
			v.getVelocity().dy = (v.getVelocity().dy * TIMESTEP * netto_sum.dy) * DAMPING;
			//v.position := v.position + timestep * this_node.velocity
			v.getPosition().dx += v.getPosition().dx + (TIMESTEP * v.getVelocity().dx);
			v.getPosition().dy += v.getPosition().dy + (TIMESTEP * v.getVelocity().dy);
			//total_kinetic_energy := total_kinetic_energy + this_node.mass * (this_node.velocity)^2
			total_kinetic_energy.dx =  total_kinetic_energy.dx + (v.getMass() * Math.sqrt(v.getVelocity().dx));
			total_kinetic_energy.dy =  total_kinetic_energy.dy+ (v.getMass() * Math.sqrt(v.getVelocity().dy));
		}
	}

	private void getWallPosition(Graph g, Vertex v, Vertex v_other) {
		try {
			v.setPosition(g.wallPosition(v_other));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
