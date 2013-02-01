package district;
import graph.Tuple;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
		final long startTime = System.currentTimeMillis();
		random = new Random(10);
		frame = new GroundplanFrame();
		int houses=20;
		Groundplan plan= new Groundplan(houses);
		generator = new DistrictGenerator(plan,houses);
		//plan = planWijk(houses,10000);
		printStartDistricts();
		//printSolution(plan,startTime,System.currentTimeMillis());
		
	}
	
	/** Used for testing*/
	public void printStartDistricts()
	{
		printSolution(generator.generateDistrict4());
	}

	/**
	 * Startpunt voor het oplossen van de opdracht
	 */
	public Groundplan planWijk(int houses, int iter) {
		int infeasiblesolutions=0;
		Groundplan optimalSolution=null;
		double bestsolution=0;
		Charges charges = new Charges(0.3,5,10,0,0);
		Groundplan currentSolution=null;
		
		algorithm = new SimulatedAnnealing(generator.generateDistrict3());
		optimalSolution=algorithm.getGroundplan();
			//Calc initial solution:
			Tuple.hookefactor=1;					
				currentSolution = runSimulatedAnnealingChangingCharge(houses,
						infeasiblesolutions, charges, currentSolution,iter,algorithm.getGroundplan());
				if(currentSolution.getPlanValue()>optimalSolution.getPlanValue())
				{
					try {
						optimalSolution=currentSolution.clone();
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					bestsolution=optimalSolution.getPlanValue();
					System.out.println("Best value: "+bestsolution);
				}
		return optimalSolution;
	}

	private Groundplan runSimulatedAnnealingChangingCharge(int houses,
			int infeasiblesolutions, Charges charges,
			Groundplan currentSolution,int iter,Groundplan plan) {
		
		Groundplan solution;
		
			//run x iterations of simulated annealing algorithm
		algorithm = new SimulatedAnnealing(plan);			
		solution= algorithm.getOptimalSolution(iter,charges,frame);

		return solution;
	}

	private void printSolution(Groundplan solution, long start, long end) {
		frame.setPlan(solution);
		System.out.println("Totale vrijstand: "+solution.getPlanValue()+" Feasible:"+solution.isValid()+" Value:"+solution.getPlanValue());
		NumberFormat formatter = new DecimalFormat("#0.00000");
		System.out.print("Execution time is " + formatter.format((end - start) / 1000d) + " seconds");
	}
	
	private void printSolution(Groundplan solution)
	{
		frame.setPlan(solution);
		System.out.println("feasible: "+solution.isValid());
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
