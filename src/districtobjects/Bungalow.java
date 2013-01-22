package districtobjects;

/**
 * Een bungalow is 10x7.5 meter (breed x diep) 
 * heeft een waarde van E.399.000,-.
 * De woning heeft rondom drie meter vrijstand nodig, iedere volle meter meer levert een prijsverbetering op van 4%. 
 * @author bweel
 *
 */
public class Bungalow extends Residence {

	public Bungalow() {
		super(10.0, 7.5, 399000, 3, 0.04);
	}
	
	public Bungalow(double x, double y) {
		super(10.0, 7.5, 399000, 3, 0.04);
		setX(x);
		setY(y);
	}

	@Override
	public String getType() {
		return "Bungalow";
	}
	
	public Bungalow clone() throws CloneNotSupportedException
	{
		return new Bungalow(getX(),getY());
	}

}
