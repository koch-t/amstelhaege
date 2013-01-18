package districtobjects;

public abstract class Placeable {
	private double x;
	private double y;
	private double width;
	private double height;
	private boolean flipped = false;

	public Placeable(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setWidth(double width) {
		this.width = width;
	}

	public double getWidth() {
		return width;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}
	
	public double getHeight() {
		return height;
	}
	
	public double leftEdge(){
		return x;
	}
	
	public double rightEdge(){
		return x + width;
	}
	
	public double topEdge(){
		return y;
	}
	
	public double bottomEdge(){
		return y + height;
	}

	/**
	 * 
	 * @return false if it has normal orientation
	 *        true if it is flipped
	 */
	public boolean getOrientation() {
		return flipped;
	}

	/**
	 *  flips orientation
	 */
	public void flip() {
		if(flipped){
			flipped = false;
		}else{
			flipped = true;
		}
		
		double tmp = width;
		width = height;
		height = tmp;
	}
	
	public double getDistance(Placeable other){
		double distance = 0;
		if(this != other){
			if(this.leftEdge() <= other.rightEdge()
				&& this.rightEdge() >= other.leftEdge()
				&& this.topEdge() <= other.bottomEdge()
				&& this.bottomEdge() >= other.topEdge()){
				// Houses are intersecting, distance is 0.
				return 0;
			}else if(this.leftEdge() < other.rightEdge()
					&& this.rightEdge() > other.leftEdge()
					&& this.topEdge() > other.bottomEdge()){
				// Residence is completely below
				distance = this.topEdge() - other.bottomEdge();
			}else if(this.leftEdge() < other.rightEdge()
					&& this.rightEdge() > other.leftEdge()
					&& this.bottomEdge() < other.topEdge()){
				// Residence is completely above
				distance = other.topEdge() - this.bottomEdge();
			}else if(this.bottomEdge() > other.topEdge()
					&& this.topEdge() < other.bottomEdge()
					&& this.rightEdge() < other.leftEdge()){
				// Residence is completely to the left
				distance = other.leftEdge() - this.rightEdge();
			}else if(this.bottomEdge() > other.topEdge()
					&& this.topEdge() < other.bottomEdge()
					&& this.leftEdge() > other.rightEdge()){
				// Residence is completely to the right
				distance = this.leftEdge() - other.rightEdge();
			}else if(this.rightEdge() > other.leftEdge()
					&& this.topEdge() < other.bottomEdge()){
				// Residence is to top right
				distance = Math.sqrt(Math.pow(this.leftEdge() - other.rightEdge(),2) + Math.pow(other.topEdge() - this.bottomEdge(),2));
			}else if(this.leftEdge() < other.rightEdge()
					&& this.topEdge() < other.bottomEdge()){
				// Residence is to top left
				distance = Math.sqrt(Math.pow(other.leftEdge() - this.rightEdge(),2) + Math.pow(other.topEdge() - this.bottomEdge(),2));
			}else if(this.rightEdge() > other.leftEdge()
					&& this.bottomEdge() > other.topEdge()){
				// Residence is to the bottom right
				distance = Math.sqrt(Math.pow(this.leftEdge() - other.rightEdge(),2) + Math.pow(this.topEdge() - other.bottomEdge(),2));
			}else if(this.leftEdge() < other.rightEdge()
					&& this.bottomEdge() > other.topEdge()){
				// Residence is to the bottom left
				distance = Math.sqrt(Math.pow(other.leftEdge() - this.rightEdge(),2) + Math.pow(this.topEdge() - other.bottomEdge(),2));
			}
		}else{
			distance = Double.MAX_VALUE;
		}
		return distance;
	}
}
