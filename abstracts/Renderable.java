package abstracts;

import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * Standardizes rendering
**/
public interface Renderable {
	/**
	 * Call from within a {@link JComponent}<code>'s</code> <code>paint(Graphics g)</code> or <code>paintComponent(Graphics g)</code> method.
	 * @param canvas The {@link JComponent} that called this method.
	 * @param g The {@link Graphics} object passed into the <code>paint()</code> method.
	**/
	public void render(JComponent canvas, Graphics g);
}