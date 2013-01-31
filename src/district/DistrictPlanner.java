package district;
import graph.Tuple;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;

import algorithms.SimulatedAnnealing;


import datastructures.Charges;
import districtobjects.Bungalow;
import districtobjects.Cottage;
import districtobjects.Mansion;
import districtobjects.Residence;
import districtobjects.WaterBody;

/**
 * De wijkplanner is de basis file voor de Heuristieken opdracht Amstelhaege.
 * Het algoritme voor het oplossen van deze opdracht kan/hoort in deze class
 * geschreven te worden te beginnen in de functie planWijk.
 * 
 * @author bweel
 * 
 */
public class DistrictPlanner {
	private Random random;
	private GroundplanFrame frame;
	private DistrictGenerator generator;
	private SimulatedAnnealing algorithm;
	
	private static final int SCALE = 1;

	public DistrictPlanner() {
		random = new Random(1);
		frame = new GroundplanFrame();
		int houses=20;
		Groundplan plan= new Groundplan(houses);
		generator = new DistrictGenerator(plan,houses);
		plan = planWijk(houses,10000);
		//printStartDistricts();
		printSolution(plan);
		
	}
	
	/** Used for testing*/
	public void printStartDistricts()
	{
		printSolution(generator.generateDistrict3());
	}

	/**
	 * Startpunt voor het oplossen van de opdracht
	 */
	public Groundplan planWijk(int houses, int iter) {
		int infeasiblesolutions=0;
		Groundplan optimalSolution=null;
		double bestsolution=0;
		Charges charges = new Charges(1,2,5,0,0);
		Groundplan currentSolution=null;
		
		algorithm = new SimulatedAnnealing(generator.generateDistrict3());
		optimalSolution=algorithm.getGroundplan();
			//Calc initial solution:
			Tuple.hookefactor=1;		
		do 
		{
				
				currentSolution = runSimulatedAnnealingChangingCharge(houses,
						infeasiblesolutions, charges, currentSolution,iter,algorithm.getGroundplan());
				if(currentSolution.getPlanCummulativeDistance()>optimalSolution.getPlanCummulativeDistance())
				{
					try {
						optimalSolution=currentSolution.clone();
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					bestsolution=optimalSolution.getPlanCummulativeDistance();
					System.out.println("Best value: "+bestsolution);
				}
			}while(!optimalSolution.isValid());
		return optimalSolution;
	}

	private Groundplan runSimulatedAnnealingChangingCharge(int houses,
			int infeasiblesolutions, Charges charges,
			Groundplan currentSolution,int iter,Groundplan plan) {
		
		Groundplan solution;
		
			//run x iterations of simulated annealing algorithm
		algorithm = new SimulatedAnnealing(plan);			
		solution= algorithm.getOptimalSolution(iter,charges,frame);
			
		printSolution(solution);
		return solution;
	}

	private void printSolution(Groundplan solution) {
		frame.setPlan(solution);
		System.out.println("Totale vrijstand: "+solution.getPlanCummulativeDistance()+" Feasible:"+solution.isValid()+" Value:"+solution.getPlanValue());
	}

	private void increaseCharges(Charges charges) {
		double randomnumber = random.nextDouble();
		if(randomnumber<=0.33)
			charges.cottagecharge+=0.5;
		else if(randomnumber<=0.66)
			charges.bungalowcharge+=0.5;
		else
			charges.mansioncharge+=0.5;		
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new DistrictPlanner();
	}
}
