package districtobjects;

/**
 * Een Maison is 11x10.5 meter (breed x diep) 
 * heeft een waarde van E.610.000,-
 * De woning heeft rondom zes meter vrijstand nodig, iedere volle meter meer levert een prijsverbetering op van 6%. 	
 * @author bweel
 *
 */
public class Mansion extends Residence {
	public Mansion(){
		super(11.0, 10.5, 610000, 6, 0.06);
	}

	public Mansion(double x, double y){
		super(11.0, 10.5, 610000, 6, 0.06);
		setX(x);
		setY(y);
	}
	
	@Override
	public String getType() {
		return "Mansion";
	}
}
