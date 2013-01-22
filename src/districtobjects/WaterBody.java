package districtobjects;

public class WaterBody extends Placeable {

	public WaterBody(double x, double y, double width, double height) {
		super(x, y, width, height);
	}
	
	public WaterBody clone() throws CloneNotSupportedException
	{
		return new WaterBody(getX(),getY(),getWidth(),getHeight());
	}
}
