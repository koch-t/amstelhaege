package district;

import java.util.Random;

import districtobjects.Bungalow;
import districtobjects.Cottage;
import districtobjects.Mansion;
import districtobjects.WaterBody;

public class DistrictGenerator {
	private Groundplan plan;
	private int houses;
	private Random random;
	
	public DistrictGenerator(Groundplan plan,int houses)
	{
		this.plan = plan;
		this.houses=houses;
		random = new Random(1);
	}
	
	//Random generation of Groundplan
	public Groundplan generateRandomMap()
	{
		plan = new Groundplan(houses);
		for (int i = 0; i < Groundplan.MINIMUM_COTTAGE_PERCENTAGE * 20; i++) {
			plan.addResidence(new Cottage(random.nextDouble()
					* Groundplan.WIDTH, random.nextDouble() * Groundplan.HEIGHT));
		}

		for (int i = 0; i < Groundplan.MINIMUM_BUNGALOW_PERCENTAGE * 20; i++) {
			plan.addResidence(new Bungalow(random.nextDouble()
					* Groundplan.WIDTH, random.nextDouble() * Groundplan.HEIGHT));
		}

		for (int i = 0; i < Groundplan.MINIMUM_MANSION_PERCENTAGE * 20; i++) {
			plan.addResidence(new Mansion(random.nextDouble()
					* Groundplan.WIDTH, random.nextDouble() * Groundplan.HEIGHT));
		}

		double size = Groundplan.WIDTH * Groundplan.HEIGHT * Groundplan.MINIMUM_WATER_PERCENTAGE;
		double length = Math.sqrt(size);
		plan.addWaterBody(new WaterBody(random.nextDouble() * (Groundplan.WIDTH - length),
				random.nextDouble() * (Groundplan.HEIGHT - length), length, length));
		return plan;
	}
	
	//Generates a map with water in the corner
	public Groundplan generateRandomMapFixedWater()
	{
		plan = new Groundplan(houses);
		for (int i = 0; i < Groundplan.MINIMUM_COTTAGE_PERCENTAGE * houses; i++) {
			plan.addResidence(new Cottage(random.nextDouble()
					* Groundplan.WIDTH, random.nextDouble() * Groundplan.HEIGHT));
		}

		for (int i = 0; i < Groundplan.MINIMUM_BUNGALOW_PERCENTAGE *houses ; i++) {
				plan.addResidence(new Bungalow(random.nextDouble()
						* Groundplan.WIDTH, random.nextDouble() * Groundplan.HEIGHT));
			}

			for (int i = 0; i < Groundplan.MINIMUM_MANSION_PERCENTAGE * houses; i++) {
				plan.addResidence(new Mansion(random.nextDouble()
						* Groundplan.WIDTH, random.nextDouble() * Groundplan.HEIGHT));
			}
			
			createWaterInCorners(plan);		
			
			//System.out.println("Value of the plan: "+plan.getPlanValue());
			return plan;
	}
	
	//Generates a random map without water
	public Groundplan generateRandomNoWater()
	{
		plan = new Groundplan(houses);
		for (int i = 0; i < Groundplan.MINIMUM_COTTAGE_PERCENTAGE * houses; i++) {
			plan.addResidence(new Cottage(random.nextDouble()
					* Groundplan.WIDTH, random.nextDouble() * Groundplan.HEIGHT));
		}

		for (int i = 0; i < Groundplan.MINIMUM_BUNGALOW_PERCENTAGE *houses ; i++) {
				plan.addResidence(new Bungalow(random.nextDouble()
						* Groundplan.WIDTH, random.nextDouble() * Groundplan.HEIGHT));
			}

			for (int i = 0; i < Groundplan.MINIMUM_MANSION_PERCENTAGE * houses; i++) {
				plan.addResidence(new Mansion(random.nextDouble()
						* Groundplan.WIDTH, random.nextDouble() * Groundplan.HEIGHT));
			}
			return plan;
	}
	
	//All residences have the same startposition
	public Groundplan generateFlat()
	{
		plan = new Groundplan(houses);
		for(int i=0;i<Groundplan.MINIMUM_COTTAGE_PERCENTAGE*houses;i++)
			plan.addResidence(new Cottage(80,60));
		for(int i=0;i<Groundplan.MINIMUM_BUNGALOW_PERCENTAGE*houses;i++)
			plan.addResidence(new Bungalow(80,60));
		for(int i=0;i<Groundplan.MINIMUM_MANSION_PERCENTAGE*houses;i++)
			plan.addResidence(new Mansion(80,60));
		createWaterInCorners(plan);
		return plan;
		
	}
	
	public Groundplan generateDistrict3()
	{
		plan = new Groundplan(houses);
		int j=0;
		int k=0;
		createWaterInCorners(plan);
		for(int i=0;i<Groundplan.MINIMUM_COTTAGE_PERCENTAGE*houses;i++)
		{
			plan.addResidence(new Cottage(2+10*k,2+10*j));
			k++;
			if(i!=0 && i%6==0)
			{
				j++;
				k=0;
			}
		}

		j=3;
		k=0;
		for(int i=0;i<Groundplan.MINIMUM_BUNGALOW_PERCENTAGE*houses;i++)
		{

			plan.addResidence(new Bungalow(Groundplan.WIDTH-3-13*k,j));
			k++;
			if(i==4 || i==9) {
				j+=10.5;
				k=0;
			}
		}
		
		j=6;
		k=0;
		for(int i=0;i<Groundplan.MINIMUM_MANSION_PERCENTAGE*houses;i++)
		{
			plan.addResidence(new Mansion(Groundplan.WIDTH-6-17*k,Groundplan.HEIGHT-j));
			k++;
			if(i==3 || i==6){
				j+=16.5;
				k=0;
			}
		}
		return plan;
		
	}
	
	//Clustering residences
	public Groundplan generateDistrict1()
	{
		plan = new Groundplan(houses);
		int j=0;
		int k=0;
		for(int i=0;i<Groundplan.MINIMUM_COTTAGE_PERCENTAGE*houses;i++)
		{
			plan.addResidence(new Cottage(2+10*k,2+10*j));
			k++;
			if(i!=0 && i%6==0)
			{
				j++;
				k=0;
			}
		}

		j=3;
		k=0;
		for(int i=0;i<Groundplan.MINIMUM_BUNGALOW_PERCENTAGE*houses;i++)
		{

			plan.addResidence(new Bungalow(Groundplan.WIDTH-3-13*k,j));
			k++;
			if(i==4 || i==9) {
				j+=10.5;
				k=0;
			}
		}
		
		j=6;
		k=0;
		for(int i=0;i<Groundplan.MINIMUM_MANSION_PERCENTAGE*houses;i++)
		{
			plan.addResidence(new Mansion(Groundplan.WIDTH-6-17*k,Groundplan.HEIGHT-j));
			k++;
			if(i==3 || i==6){
				j+=16.5;
				k=0;
			}
		}
		double length1=Math.sqrt((Groundplan.WIDTH*Groundplan.HEIGHT)*Groundplan.MINIMUM_WATER_PERCENTAGE)+5;
		double length2 = ((Groundplan.WIDTH*Groundplan.HEIGHT)*Groundplan.MINIMUM_WATER_PERCENTAGE)/length1;
		plan.addWaterBody(new WaterBody(0,Groundplan.HEIGHT-length2,
				length1,length2));
		return plan;
	}
	
	public Groundplan generateDistrict2()
	{
		plan = new Groundplan(houses);
		int j=0;
		int k=0;
		for(int i=1;i<=Groundplan.MINIMUM_COTTAGE_PERCENTAGE*houses;i++)
		{
			plan.addResidence(new Cottage(2+10*k,2+10*j));
			k++;
			if(i!=0 && i%6==0)
			{
				j++;
				k=0;
			}
		}

		j=3;
		k=0;
		for(int i=0;i<Groundplan.MINIMUM_BUNGALOW_PERCENTAGE*houses;i++)
		{

			plan.addResidence(new Bungalow(Groundplan.WIDTH-3-13*k,j));
			k++;
			if(i==4 || i==9) {
				j+=10.5;
				k=0;
			}
		}
		
		j=6;
		k=0;
		for(int i=0;i<Groundplan.MINIMUM_MANSION_PERCENTAGE*houses;i++)
		{
			plan.addResidence(new Mansion(55+k*17,Groundplan.HEIGHT-j));
			k++;
			if(i==3 || i==6){
				j+=11;
				k=0;
			}
		}
		plan.addWaterBody(new WaterBody(70,0,30,40));
		plan.addWaterBody(new WaterBody(0,70,33,40));
		plan.addWaterBody(new WaterBody(Groundplan.WIDTH-40,90,40,33));
		return plan;
	}
		
	//Creates water in corner
	private void createWaterInCorners(Groundplan plan) {
			double size = (Groundplan.WIDTH * Groundplan.HEIGHT * Groundplan.MINIMUM_WATER_PERCENTAGE)/4;
			double height =Math.sqrt(size/4);
			double width=size/height;
				plan.addWaterBody(new WaterBody(0,0, width, height));
				plan.addWaterBody(new WaterBody(Groundplan.WIDTH-width,0, width, height));
				plan.addWaterBody(new WaterBody(0,Groundplan.HEIGHT-height, width, height));
				plan.addWaterBody(new WaterBody(Groundplan.WIDTH-width,Groundplan.HEIGHT-height, width, height));
	}
	
}
