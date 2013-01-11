package districtobjects;

public final class Ground extends Placeable {
	public Ground(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	@Override
	public Placeable clone() {
		return new Ground(getX(),getY(),getWidth(),getHeight());
	}
	

}
