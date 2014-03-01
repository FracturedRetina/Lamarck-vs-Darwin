package abstracts;

/**
 * Superclass for all objects that have x and y positions
**/
public abstract class Position {
	public int x;
	public int y;
	
	public Position() {}
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return super.toString() + " is at " + x + ", " + y;
	}
}