import java.util.Random;
import java.security.SecureRandom;

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
	Random random;
	GroundplanFrame frame;
	private static final int SCALE = 1;
	private static final int numberOfResidences = 60;

	public DistrictPlanner() {
		random = new SecureRandom();
		frame = new GroundplanFrame();
		Groundplan plan = planWijk();

		frame.setPlan(plan);
	}

	/**
	 * Startpunt voor het oplossen van de opdracht
	 */
	public Groundplan planWijk() {
		// TODO: Implementeer je algoritme hier.
		long starttime = System.currentTimeMillis();
		Groundplan plan = randomPlan();
		replaceNotFeasibleResidences(plan);
		System.out.println("plan is valid="+plan.isValid());
		long endtime = System.currentTimeMillis();
		System.out.println(endtime-starttime);
		System.out.println("Value of the plan: "+plan.getPlanValue());
		return plan;
	}
	
	//Replace only the not feasible residence
	private void replaceNotFeasibleResidences(Groundplan plan) {
		try{
			for(Residence r:plan.getResidences())
			{
				while(!plan.isCorrectlyPlaced(r))
				{
					plan.deleteResidence(r);
					plan.addResidence(moveResidence(r));
				}
			}
		}
		catch(Exception e)
		{
			System.out.print(e.getMessage());
		}
	}

	//Move residence to a random location in the district
	private Residence moveResidence(Residence r) {
		r.setX(random.nextDouble()* Groundplan.WIDTH);
		r.setY(10+random.nextDouble() * (Groundplan.HEIGHT-10));
		return r;
	}

	private Groundplan randomPlan() {
		Groundplan plan = new Groundplan(numberOfResidences);
		
		for (int i = 0; i < Groundplan.MINIMUM_COTTAGE_PERCENTAGE * numberOfResidences; i++) {
			plan.addResidence(new Cottage(random.nextDouble()
					* (Groundplan.WIDTH),10+ random.nextDouble() * (Groundplan.HEIGHT-10)));
		}

		for (int i = 0; i < Groundplan.MINIMUM_BUNGALOW_PERCENTAGE * numberOfResidences; i++) {
			plan.addResidence(new Bungalow(random.nextDouble()
					* Groundplan.WIDTH, 10+ random.nextDouble() * (Groundplan.HEIGHT-10)));
		}

		for (int i = 0; i < Groundplan.MINIMUM_MANSION_PERCENTAGE * numberOfResidences; i++) {
			plan.addResidence(new Mansion(random.nextDouble()
					* Groundplan.WIDTH, 10+random.nextDouble() * (Groundplan.HEIGHT-10)));
		}

		
		//Create 4 different waterbodies of same size at random place:
		//for(int i=0;i<=3;i++)
		//{
			plan.addWaterBody(new WaterBody(0, 0, 80, 15));
			plan.addWaterBody(new WaterBody(80,0,80,15));
			plan.addWaterBody(new WaterBody(0,105,80,15));
			plan.addWaterBody(new WaterBody(80,105,80,15));
		//}
		
		//System.out.println("Value of the plan: "+plan.getPlanValue());
		
		
		return plan;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new DistrictPlanner();
	}
}
