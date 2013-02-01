package algorithms;

import datastructures.Charges;
import datastructures.Pair;
import district.*;
import districtobjects.Bungalow;
import districtobjects.Cottage;
import districtobjects.Mansion;
import districtobjects.Residence;
import graph.Graph;
import graph.Tuple;
import graph.Vertex;

import java.util.Random;

//Runs the spring embedding algorithm in combination with simulated annealing
public class SimulatedAnnealing {
	
	//Private vars:
	private Graph optimalplan;
	private Pair<Double,Double> startTValues;
	private SpringEmbedding springembedding;
	private boolean isNextTrialAccepted;
	private int iterator;
	private Random random;
	
	//Constructor:
	public SimulatedAnnealing(Groundplan plan) {
		isNextTrialAccepted = false;
		random = new Random();
		optimalplan = new Graph(plan);
		springembedding = new SpringEmbedding();
		
		double startValueOfT;
	}
	
	public Groundplan getGroundplan()
	{
		return optimalplan.getGroundplan();
	}

	//Runs the algorithms
	public Groundplan getOptimalSolution(int maxiter,Charges charges,GroundplanFrame frame){
		
		double currentvalue,nextvalue,optimalvalue=Double.NEGATIVE_INFINITY;
		Graph nextplan=null;
		Graph currentplan=null;
		try {
			currentplan = new Graph(optimalplan.getGroundplan().clone());
			nextplan = new Graph(optimalplan.getGroundplan().clone());
		} catch (CloneNotSupportedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setCharges(charges,nextplan);
		
			nextvalue=0;
			for(int i=0;i<=maxiter;i++)
			{
				try{
				springembedding.springEmbed(nextplan);
				currentvalue=currentplan.getGroundplan().getPlanValue()-getPenalty(currentplan);
				//bereken startCurrentValue en startNextValue (deze zijn nodig voor berekenen T)
				nextvalue=nextplan.getGroundplan().getPlanValue()-getPenalty(nextplan);
				
				if(i==0) setT(currentvalue,nextvalue);
				
				if(currentvalue<nextvalue){
					if(optimalvalue<nextvalue)
					{
						optimalplan = new Graph(nextplan.getGroundplan().clone());
						optimalvalue=nextvalue;
					}
					currentplan = new Graph(nextplan.getGroundplan().clone());
				}
				else
				{
					if(determineAcception(currentvalue, nextvalue))
						currentplan = new Graph(nextplan.getGroundplan().clone());
				}
				//printSolution(frame,optimalplan);
				printSolution(frame,nextplan);
			}
				catch(Exception e){
					//printSolution(frame);
				}
		}
		printSolution(frame,optimalplan);
		return optimalplan.getGroundplan();
	}

	private void printSolution(GroundplanFrame frame,Graph plan) {
		frame.setPlan(plan.getGroundplan());
		System.out.println("Value: "+plan.getGroundplan().getPlanValue()+" feasible: "+optimalplan.getGroundplan().isValid());
	}
		
	//Sets the charges of the vertices
	private void setCharges(Charges charges,Graph nextplan) {
		for(Vertex v: nextplan.getVertices())
		{
			if(v.getPlaceable() instanceof Cottage)
				v.setMass(charges.cottagecharge);
			else if(v.getPlaceable() instanceof Bungalow)
				v.setMass(charges.bungalowcharge);
			else if(v.getPlaceable() instanceof Mansion)
				v.setMass(charges.mansioncharge);
		}
	}

	private double getPenalty(Graph nextplan) {
		double penalty=0;
		
		if(nextplan.getGroundplan().isValid())
			return penalty;
		for(Vertex v:nextplan.getVertices())
		{
			if(!nextplan.getGroundplan().isCorrectlyPlaced(v.getPlaceable())){}
				penalty+=100000; 
		}
		return penalty;
	}

	private void setT(double currentvalue, double nextvalue) {
		startTValues = new Pair<Double,Double>(currentvalue,nextvalue);
	}

	private boolean determineAcception(double currentValue, double nextValue) {
		double x = (nextValue - currentValue) / getValueOfT();
		double acceptanceP = Math.pow(Math.E, -x);
		if(random.nextDouble()<=acceptanceP){
			return true;
		}
		return false;
	}


	private double getValueOfT() {
		return -((double)startTValues.getRight() - (double)startTValues.getLeft())
				/Math.log(0.8-iterator*0.00008); //10.000 iteraties
	}
	

}
