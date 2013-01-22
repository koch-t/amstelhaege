package districtobjects;

public final class Ground extends Placeable {
	public Ground(double x, double y, double width, double height) {
		super(x, y, width, height);
	}
	
	public Ground clone() throws CloneNotSupportedException
	{
		return new Ground(getX(),getY(),getWidth(),getHeight());
	}
}
