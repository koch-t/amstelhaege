package district;
import java.util.ArrayList;

import districtobjects.Ground;
import districtobjects.Placeable;
import districtobjects.Residence;
import districtobjects.WaterBody;

/**
 * Eisen aan de wijk
 * <ol>
 * <li>De wijk heeft betrekking op een stuk land van 120x160 meter.</li>
 * <li>Omdat Amstelhaege in een polder ligt, moet het land voor minimaal 20% uit oppervlaktewater bestaan.</li>
 * <li>Het oppervlaktewater mag om afvoertechnische redenen in maximaal vier lichamen worden onderverdeeld. De vorm van een lichaam is een rechthoek, met een zijderatio van niet groter dan 1:4.</li>
 * <li>Het aantal woningen in de wijk bestaat voor 60% uit eengezinswoningen, 25% uit luxe bungalows en 15% uit maisons.</li>
 * </ol>
 *  
 * @author bweel
 *
 */
public class Groundplan {
	public static final double	WIDTH 						= 160;
	public static final double	HEIGHT 						= 120;
	public static final double	MINIMUM_WATER_PERCENTAGE 	= 0.2;
	public static final int		MAXIMUM_WATER_BODIES 		= 4;
	public static final double	MINIMUM_COTTAGE_PERCENTAGE 	= 0.60;
	public static final double	MINIMUM_BUNGALOW_PERCENTAGE = 0.25;
	public static final double	MINIMUM_MANSION_PERCENTAGE	= 0.15;

	private ArrayList<Residence> residences;
	private ArrayList<WaterBody> waterBodies;
	private Ground ground;
	private int nCottages, nBungalows, nMansions, nHouses;

	public Groundplan(int nrHouses){
		residences = new ArrayList<Residence>();
		waterBodies = new ArrayList<WaterBody>();
		ground = new Ground(0, 0, WIDTH, HEIGHT);
		nCottages = nBungalows = nMansions = 0;
		nHouses = nrHouses;
	}

	@SuppressWarnings("unchecked")
	public Groundplan clone()
	{
		Groundplan cloneofplan = new Groundplan(nHouses);
		cloneofplan.nCottages=nCottages;
		cloneofplan.nBungalows=nBungalows;
		cloneofplan.nMansions=nMansions;
		cloneofplan.residences =(ArrayList<Residence>) residences.clone();
		cloneofplan.waterBodies = (ArrayList<WaterBody>)waterBodies.clone();
		return cloneofplan;
	}

	public void addResidence(Residence residence){
		if(residence.getType().equals("Cottage")){
			nCottages++;
		}else if(residence.getType().equals("Bungalow")){
			nBungalows++;
		}else if(residence.getType().equals("Mansion")){
			nMansions++;
		}
		residences.add(residence);
	}

	public void addWaterBody(WaterBody waterBody){
		waterBodies.add(waterBody);
	}

	/**
	 * Checks whether the ground plan is valid.
	 * Watch out! Expensive operation!
	 * 
	 * @return true if all the placables in the ground plan are correctly places
	 * and all the constraints are satisfied.
	 */
	public boolean isValid(){
		// Check constraints
		if( waterBodies.size() > MAXIMUM_WATER_BODIES
				|| ((double) nCottages / nHouses) < MINIMUM_COTTAGE_PERCENTAGE
				|| ((double) nBungalows / nHouses) < MINIMUM_BUNGALOW_PERCENTAGE
				|| ((double) nMansions / nHouses) < MINIMUM_MANSION_PERCENTAGE){
			System.out.println("Something below mininum percentage");
			return false;
		}else{
			double waterSurfaceArea = 0;
			// Check water body placement
			for(WaterBody waterBody : waterBodies){
				if(!isCorrectlyPlaced(waterBody)){
					//System.out.println("Invalid water body placement");
					return false;
				}else{
					waterSurfaceArea += waterBody.getWidth() * waterBody.getHeight();
				}
			}
			// Check water percentage
			double groundSurfaceArea = WIDTH * HEIGHT;
			if((double) waterSurfaceArea / groundSurfaceArea < MINIMUM_WATER_PERCENTAGE){
				//System.out.println("Not enough water");
				return false;
			}

			// Check residence placement
			for(Residence residence : residences){
				if(!isCorrectlyPlaced(residence)){
					//System.out.println("Residence placement faulty");
					return false;
				}
			}
		}
		return true;
	}

	public boolean isCorrectlyPlaced(Placeable placeable){
		// Check for rectangle overlap. Visualization helper: http://silentmatt.com/rectangle-intersection/
		if(placeable.rightEdge() > ground.rightEdge()
				|| placeable.leftEdge() < ground.leftEdge()
				|| placeable.topEdge() < ground.topEdge()
				|| placeable.bottomEdge() > ground.bottomEdge()){
			// Residence is not fully intersecting the ground
			//System.out.println("Placeable outside ground");
			return false;
		}

		// Check distance to edges
		if(placeable instanceof Residence){
			if(placeable.leftEdge() < ((Residence) placeable).getMinimumDistance()
					|| placeable.rightEdge() > ground.rightEdge() - ((Residence) placeable).getMinimumDistance()
					|| placeable.topEdge() < ((Residence) placeable).getMinimumDistance()
					|| placeable.bottomEdge() > ground.bottomEdge() - ((Residence) placeable).getMinimumDistance()){
				//System.out.println("Placeable to close");
				return false;
			}
		}

		for(WaterBody waterBody : waterBodies){
			if(placeable != waterBody
					&& placeable.leftEdge() < waterBody.rightEdge()
					&& placeable.rightEdge() > waterBody.leftEdge()
					&& placeable.topEdge() < waterBody.bottomEdge()
					&& placeable.bottomEdge() > waterBody.topEdge()){
				// intersecting
				//System.out.println("Intersecting with water");
				return false;
			}
		}

		for(Residence other : residences){
			if(placeable != other
					&& placeable.leftEdge() < other.rightEdge()
					&& placeable.rightEdge() > other.leftEdge()
					&& placeable.topEdge() < other.bottomEdge()
					&& placeable.bottomEdge() > other.topEdge()){
				// intersecting
				//System.out.println("Intersecting with other placeables");
				return false;
			}else if(placeable instanceof Residence
					&& placeable != other
					&& getDistance((Residence) placeable, other) < ((Residence) placeable).getMinimumDistance()){
				// check distance
				//System.out.println("Residence below minimum distance");
				return false;
			}
		}
		return true;
	}
	
	public boolean intersectsWithWater(Placeable placeable)
	{
		for(WaterBody waterBody : waterBodies){
			if(placeable != waterBody
					&& placeable.leftEdge() < waterBody.rightEdge()
					&& placeable.rightEdge() > waterBody.leftEdge()
					&& placeable.topEdge() < waterBody.bottomEdge()
					&& placeable.bottomEdge() > waterBody.topEdge()){
				// intersecting
				//System.out.println("Intersecting with water");
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Residence> getResidences(){
		return (ArrayList<Residence>) residences.clone();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<WaterBody> getWaterBodies(){
		return (ArrayList<WaterBody>) waterBodies.clone();
	}

	public double getResidenceValue(Residence residence){
		double value = residence.getValue();
		double distance = getValueDistance(residence);
		double valueIncrease = residence.getAddedValuePercentage() * value;

		value += (Math.max((int)distance - residence.getMinimumDistance(),0)) * valueIncrease;

		return value;
	}

	public double getPlanValue(){
		double value = 0;
		for(Residence residence : residences){			
			value += getResidenceValue(residence);
		}
		return value;
	}

	public double getPlanCummulativeDistance(){
		double value = 0;
		for(Residence residence : residences){
			if(isCorrectlyPlaced(residence)){
				value += getValueDistance(residence);
			}
			value += getValueDistance(residence);
		}
		return value;
	}

	public double getValueDistance(Residence residence){
		double minimum = residence.leftEdge();

		if(residence.topEdge() < minimum){
			minimum = residence.topEdge();
		}
		if(ground.rightEdge() - residence.rightEdge() < minimum){
			minimum = ground.rightEdge() - residence.rightEdge();
		}
		if(ground.bottomEdge() - residence.bottomEdge() < minimum){
			minimum = ground.bottomEdge() - residence.bottomEdge();
		}

		for(Residence other : residences){
			if(residence != other){
				double d = getDistance(residence, other);
				if(d < minimum){
					minimum = d;
				}
			}
		}

		return minimum;
	}

	public double getDistance(Residence residence, Residence other){
		double distance = 0;
		if(residence != other){
			if(residence.leftEdge() <= other.rightEdge()
					&& residence.rightEdge() >= other.leftEdge()
					&& residence.topEdge() <= other.bottomEdge()
					&& residence.bottomEdge() >= other.topEdge()){
				// Houses are intersecting, distance is 0.
				return 0;
			}else if(residence.leftEdge() < other.rightEdge()
					&& residence.rightEdge() > other.leftEdge()
					&& residence.topEdge() > other.bottomEdge()){
				// Residence is completely below
				distance = residence.topEdge() - other.bottomEdge();
			}else if(residence.leftEdge() < other.rightEdge()
					&& residence.rightEdge() > other.leftEdge()
					&& residence.bottomEdge() < other.topEdge()){
				// Residence is completely above
				distance = other.topEdge() - residence.bottomEdge();
			}else if(residence.bottomEdge() > other.topEdge()
					&& residence.topEdge() < other.bottomEdge()
					&& residence.rightEdge() < other.leftEdge()){
				// Residence is completely to the left
				distance = other.leftEdge() - residence.rightEdge();
			}else if(residence.bottomEdge() > other.topEdge()
					&& residence.topEdge() < other.bottomEdge()
					&& residence.leftEdge() > other.rightEdge()){
				// Residence is completely to the right
				distance = residence.leftEdge() - other.rightEdge();
			}else if(residence.rightEdge() > other.leftEdge()
					&& residence.topEdge() < other.bottomEdge()){
				// Residence is to top right
				distance = Math.sqrt(Math.pow(residence.leftEdge() - other.rightEdge(),2) + Math.pow(other.topEdge() - residence.bottomEdge(),2));
			}else if(residence.leftEdge() < other.rightEdge()
					&& residence.topEdge() < other.bottomEdge()){
				// Residence is to top left
				distance = Math.sqrt(Math.pow(other.leftEdge() - residence.rightEdge(),2) + Math.pow(other.topEdge() - residence.bottomEdge(),2));
			}else if(residence.rightEdge() > other.leftEdge()
					&& residence.bottomEdge() > other.topEdge()){
				// Residence is to the bottom right
				distance = Math.sqrt(Math.pow(residence.leftEdge() - other.rightEdge(),2) + Math.pow(residence.topEdge() - other.bottomEdge(),2));
			}else if(residence.leftEdge() < other.rightEdge()
					&& residence.bottomEdge() > other.topEdge()){
				// Residence is to the bottom left
				distance = Math.sqrt(Math.pow(other.leftEdge() - residence.rightEdge(),2) + Math.pow(residence.topEdge() - other.bottomEdge(),2));
			}
		}else{
			distance = Double.MAX_VALUE;
		}
		return distance;
	}

	public Ground getGround(){
		return ground;
	}
}
