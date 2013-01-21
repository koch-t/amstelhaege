package springembedding;

import graph.Graph;

//Class that manages the spring embedding algorithm
public class SpringEmbeddingManager {
	
	//private vars
	private Graph graph;
	
	//Constructor Springembedding manager
	public SpringEmbeddingManager(Graph g)
	{
		graph =  g.copy();
	}
	
	//Runs the SpringEmbedding Algorithm
	public void runAlgorithm(double min_kinetic_energy)
	{
		double total_kinetic_energy=0;
		
		init();
		do
		{
			total_kinetic_energy=0;
			for(Node n: Graph)
			{
				calcNodeSituation(n);
				total_kinetic_energy=calcTotalKineticEnergy(total_kinetic_energy,n);				
			}
		}	while(total_kinetic_energy>min_kinetic_energy);
	}
	
	//Returns a copy of the graph
	private void init() {
		for(Node n:graph)
		{
			n.velocity.init();
			
		}
	}

	public Graph getGraph()
	{
		return graph.copy()
	}
}
