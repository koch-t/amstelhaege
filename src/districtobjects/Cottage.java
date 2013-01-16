package districtobjects;

/**
 * Een eengezinswoning is 8x8 meter (breed x diep) 
 * Heeft een waarde van E.285.000,-
 * De woning heeft rondom twee meter vrijstand nodig iedere meter meer levert een prijsverbetering op van 3%
 * 
 * @author bweel
 *
 */
public class Cottage extends Residence {
	Cottage(){
		super(8.0, 8.0, 285000, 2, 0.03);
	}
	
	public Cottage(double x, double y){
		super(8.0, 8.0, 285000, 2, 0.03);
		setX(x);
		setY(y);
	}

	@Override
	public String getType() {
		return "Cottage";
	}
}
