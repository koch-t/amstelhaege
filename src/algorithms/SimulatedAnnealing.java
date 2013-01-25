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
	private Graph currentplan;
	private Graph nextplan;
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
		currentplan = new Graph(plan.clone());
		nextplan = new Graph(plan.clone());
		
		double startValueOfT;
	}
	
	public Groundplan getGroundplan()
	{
		return optimalplan.getGroundplan();
	}

	//Runs the algorithms
	public Groundplan getOptimalSolution(int maxiter,Charges charges,GroundplanFrame frame){
		
		double currentvalue,nextvalue;
		setCharges(charges);
		
		currentvalue=currentplan.getGroundplan().getPlanValue();
		for(int i=0;i<=maxiter;i++)
		{
			springembedding.springEmbed(nextplan);
			
			//bereken startCurrentValue en startNextValue (deze zijn nodig voor berekenen T)
			
			nextvalue=nextplan.getGroundplan().getPlanValue()-getPenalty();
			
			//Print every solution of spring embedding algorithm
			//printSolution(frame);
			
			if(i==0) setT(currentvalue,nextvalue);
			if(currentvalue<nextvalue){
				cloneGroundPlans(nextvalue);
			}
			else
			{
				if(determineAcception(currentvalue, nextvalue))
					cloneGroundPlans(nextvalue);
			}
		}
		return optimalplan.getGroundplan();
	}

	private void printSolution(GroundplanFrame frame) {
		frame.setPlan(nextplan.getGroundplan());
		System.out.println("Value: "+nextplan.getGroundplan().getPlanValue()+" feasible: "+nextplan.getGroundplan().isValid());
	}
		
	//Sets the charges of the vertices
	private void setCharges(Charges charges) {
		for(Vertex v:nextplan.getVertices())
		{
			if(v.getPlaceable() instanceof Cottage)
				v.setMass(charges.cottagecharge);
			else if(v.getPlaceable() instanceof Bungalow)
				v.setMass(charges.bungalowcharge);
			else if(v.getPlaceable() instanceof Mansion)
				v.setMass(charges.mansioncharge);
		}
	}

	private double getPenalty() {
		double penalty=0;
		
		if(nextplan.getGroundplan().isValid())
			return penalty;
		for(Vertex v:nextplan.getVertices())
		{
			if(!nextplan.getGroundplan().isCorrectlyPlaced(v.getPlaceable()))
				penalty+=1000000;
		}
		return penalty;
	}

	private void setT(double currentvalue, double nextvalue) {
		startTValues = new Pair<Double,Double>(currentvalue,nextvalue);
	}

	private void cloneGroundPlans(double nextValue) {
		if(nextValue>optimalplan.getGroundplan().getPlanValue())
			optimalplan=nextplan.clone();
		currentplan=nextplan.clone();
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
