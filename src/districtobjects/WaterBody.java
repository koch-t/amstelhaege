package districtobjects;

public class WaterBody extends Placeable {

	public WaterBody(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	@Override
	public Placeable clone() {
		return new WaterBody(getX(),getY(),getWidth(),getHeight());
	}
	
	
	
}
