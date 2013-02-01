package district;

import java.util.Random;

import districtobjects.Bungalow;
import districtobjects.Cottage;
import districtobjects.Mansion;
import districtobjects.Residence;
import districtobjects.WaterBody;

public class DistrictGenerator {
	private Groundplan plan;
	private int houses;
	private Random random;
	
	public DistrictGenerator(Groundplan plan,int houses)
	{
		this.plan = plan;
		this.houses=houses;
		random = new Random(10);
	}
	
	//Random generation of Groundplan
	public Groundplan generateRandomMap()
	{
		plan = new Groundplan(houses);
		for (int i = 0; i < Groundplan.MINIMUM_COTTAGE_PERCENTAGE * 20 -2; i++) {
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
	
	public Groundplan generateDistrict4()
	{
		int nocottagestopleft=0;
		int nocottagesbottomright=0;
		if(Groundplan.MINIMUM_COTTAGE_PERCENTAGE*houses%2!=0)
		{
			nocottagestopleft=(int) Math.floor(Groundplan.MINIMUM_COTTAGE_PERCENTAGE*houses);
			nocottagesbottomright=(int) Math.ceil(Groundplan.MINIMUM_COTTAGE_PERCENTAGE*houses);
		}
		else
		{
			nocottagestopleft=(int) ((Groundplan.MINIMUM_COTTAGE_PERCENTAGE*houses)/2);
			nocottagesbottomright=(int) ((Groundplan.MINIMUM_COTTAGE_PERCENTAGE*houses)/2);
		}
		plan = new Groundplan(houses);
		int j=0;
		int k=0;
		for(int i=0;i<nocottagestopleft;i++)
		{
			plan.addResidence(new Cottage(2+10*k,2+10*j+14));
			k++;
			if(i!=0 && i%6==0)
			{
				j++;
				k=0;
			}
		}
		j=0;
		k=0;
		for(int i=0;i<nocottagesbottomright;i++)
		{
			plan.addResidence(new Cottage(Groundplan.WIDTH-10-10*k,Groundplan.HEIGHT-2-10*j-22));
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

			plan.addResidence(new Bungalow(Groundplan.WIDTH-20-13*k,j+13));
			k++;
			if(i==4 || i==9) {
				j+=11;
				k=0;
			}
		}
		
		j=6;
		k=0;
		for(int i=0;i<Groundplan.MINIMUM_MANSION_PERCENTAGE*houses;i++)
		{
			plan.addResidence(new Mansion(6+17*k,Groundplan.HEIGHT-j-20));
			k++;
			if(i==3 || i==6){
				j+=17;
				k=0;
			}
		}
		createWaterInCorners(plan);
		return plan;
	}
	public Groundplan generateDistrict3()
	{
		plan = new Groundplan(houses);
		int j=0;
		int k=0;
		for(int i=0;i<Groundplan.MINIMUM_COTTAGE_PERCENTAGE*houses;i++)
		{
			plan.addResidence(new Cottage(2+10*k,2+10*j+14));
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

			plan.addResidence(new Bungalow(Groundplan.WIDTH-20-13*k,j+13));
			k++;
			if(i==4 || i==9) {
				j+=11;
				k=0;
			}
		}
		
		j=6;
		k=0;
		for(int i=0;i<Groundplan.MINIMUM_MANSION_PERCENTAGE*houses;i++)
		{
			plan.addResidence(new Mansion(Groundplan.WIDTH-25-17*k,Groundplan.HEIGHT-j-20));
			k++;
			if(i==3 || i==6){
				j+=17;
				k=0;
			}
		}
		createWaterInCorners(plan);
		return plan;
	}
	
	//Generates a map with water in the corner
	public Groundplan generateRandomMapFixedWater()
	{
		plan = new Groundplan(houses);
		int j=0;
		int k=0;
		for(int i=0;i<Groundplan.MINIMUM_COTTAGE_PERCENTAGE*houses;i++)
		{
			plan.addResidence(new Cottage(2+10*k,2+10*j+14));
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

			plan.addResidence(new Bungalow(Groundplan.WIDTH-20-13*k,j+13));
			k++;
			if(i==4 || i==9) {
				j+=11;
				k=0;
			}
		}
		
		j=6;
		k=0;
		for(int i=0;i<Groundplan.MINIMUM_MANSION_PERCENTAGE*houses;i++)
		{
			plan.addResidence(new Mansion(Groundplan.WIDTH-25-17*k,Groundplan.HEIGHT-j-20));
			k++;
			if(i==3 || i==6){
				j+=17;
				k=0;
			}
		}
			//System.out.println("Value of the plan: "+plan.getPlanValue());
			return plan;
	}
	
	//Generates a random map without water
	
	public Groundplan generateClusteredMapNoWater()
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
		return plan;
	}
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
		for(int i=1;i<Groundplan.MINIMUM_COTTAGE_PERCENTAGE*houses;i++)
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
		plan.addWaterBody(new WaterBody(60,0,35,40));
		plan.addWaterBody(new WaterBody(0,60,30.5,40));
		plan.addWaterBody(new WaterBody(Groundplan.WIDTH-40,60,40,30.5));
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
	
	public Groundplan addWaterRandomNoWater(Groundplan plan)
	{
		double size = (Groundplan.WIDTH * Groundplan.HEIGHT * Groundplan.MINIMUM_WATER_PERCENTAGE)/4;
		double height =Math.sqrt(size/4);
		double width=size/height;
			plan.addWaterBody(new WaterBody(15,50, width, height));
			plan.addWaterBody(new WaterBody(Groundplan.WIDTH-width,15, width, height));
			plan.addWaterBody(new WaterBody(15,Groundplan.HEIGHT-height, width, height));
			plan.addWaterBody(new WaterBody(Groundplan.WIDTH-width,Groundplan.HEIGHT-height-15, width, height));
			return plan;
	}
	
}
