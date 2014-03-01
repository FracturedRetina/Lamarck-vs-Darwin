package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.BevelBorder;

import abstracts.Renderable;

import main.Main;
import main.Settings;
import main.World;

public class Legend extends javax.swing.JPanel {
	private static final long serialVersionUID = 1L;
	
	public Legend() {
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		this.setPreferredSize(new Dimension(150, 150));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.GREEN);
		g.fillOval(4, 4, Settings.cellSize, Settings.cellSize);
		
		g.setColor(Color.ORANGE);
		g.fillOval(4, 8 + (Settings.cellSize), Settings.cellSize, Settings.cellSize);
		
		if (Main.step % 2 == 0) {
			g.setColor(new Color(0, 0.5f, 0));
		} else {
			g.setColor(new Color(0, 0.75f, 0));
		}
		g.fillOval(4, 12 + (2 * Settings.cellSize), Settings.cellSize, Settings.cellSize);
		
		g.setColor(new Color(1, 15, 32));
		g.fillRect(4, 16 + (Settings.cellSize * 3), Settings.cellSize, Settings.cellSize);
		
		g.setColor(World.Terrain.OBSTACLE.color);
		g.fillRect(4, 20 + (Settings.cellSize * 4), Settings.cellSize, Settings.cellSize);
		
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Helvetica", Font.PLAIN, 13));
		g.drawString("Plant (AKA Autotroph)",	8 + Settings.cellSize, 2 + Settings.cellSize);
		g.drawString("Dead Plant",				8 + Settings.cellSize, 6 + (2 * Settings.cellSize));
		g.drawString("Plant (Being Eaten)",		8 + Settings.cellSize, 10 + (3 * Settings.cellSize));
		g.drawString("Critter",					8 + Settings.cellSize, 14 + (4 * Settings.cellSize));
		g.drawString("Obstacle",				8 + Settings.cellSize, 18 + (5 * Settings.cellSize));
	}
	
	/**TODO**/
	private class Item implements Renderable {
		public final String name;
		public final int y;
		
		public Item(int y, String name) {
			this.y = y;
			this.name = name;
		}
		
		@Override
		public void render(JComponent canvas, Graphics g) {
			
		}
	}
}