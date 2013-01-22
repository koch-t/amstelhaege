package simulatedAnnealing;

import district.*;
import java.util.Random;

import springembedding.SpringEmbeddingManager;


public class SimulatedAnnealing {
	Groundplan currentplan;
	Groundplan optimalplan;
	Groundplan nextplan;
	SpringEmbeddingManager manager;
	boolean isNextTrialAccepted;
	int iterator;
	Random random;
	int maxiter;
	
	public SimulatedAnnealing(Groundplan plan,int maxIterations) {
		isNextTrialAccepted = false;
		random = new Random();
		optimalplan = plan;
		currentplan = plan.clone();
		nextplan = plan.clone();
		manager = new SpringEmbeddingManager();
		maxiter=maxIterations;
		double startValueOfT;
	}


	private void run(){
		
		double currentValue,nextValue;
		double startCurrentValue, startNextValue;
		
		for(int i=0;i<=maxiter;i++)
		{
			//manager runs 1 iteration
			//bereken startCurrentValue en startNextValue (deze zijn nodig voor berekenen T)
			currentValue=currentplan.getPlanValue();
			nextValue=nextplan.getPlanValue();
			if(currentValue<nextValue){
				cloneGroundPlans(nextValue);
			}
			else
			{
				if(determineAcception(currentValue, nextValue))
					cloneGroundPlans(nextValue);
			}
			iterator++;
		}
	}
		
	
	
	private void cloneGroundPlans(double nextValue) {
		if(nextValue>optimalplan.getPlanValue())
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
		return -(startNextValue - startCurrentValue)/Math.log(0.8-iterator*0.00008); //10.000 iteraties
	}


	public static void main(String[] args) {
		new SimulatedAnnealing().run();

	}
	

}
