package abstracts;

/**
 * Allows objects to perform certain methods every iteration of the main loop
**/
public interface Steppable {
	/**
	 * Performs the methods and calculations of this object that should be executed every iteration of the main loop.
	**/
	public void step();
}