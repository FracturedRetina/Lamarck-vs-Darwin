package log;

import abstracts.Steppable;

public interface Log extends Steppable {
	/**Exports this {@link Log} to an HTML file**/
	public void export();
}