package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.Main;
import main.Settings;
import obj.life.Autotroph;

public class RenderPane extends javax.swing.JPanel {
	private static final long serialVersionUID = 1L;
	
	public RenderPane() {
		setPreferredSize(new java.awt.Dimension(Settings.canvasSize, Settings.canvasSize));
		setBackground(Color.BLACK);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Main.world.render(this, g);
		for (int i = 0; i < Main.critters.size(); i++) {
			int onTopOf = 0;
			
			Main.critters.get(i).render(this, g);
			
			for (int j = 0; j < Main.critters.size(); j++) {
				try {
					if (Main.critters.get(i) != Main.critters.get(j) &&
						Main.critters.get(i).x == Main.critters.get(j).x &&
						Main.critters.get(i).y == Main.critters.get(j).y) {
					onTopOf++;
					}
				} catch (IndexOutOfBoundsException e) {
					System.err.println("Oops");
				} catch (NullPointerException e) {
					System.err.println("Oops");
				}
			}
			if (onTopOf > 0) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("Helvetica", Font.PLAIN, 13));
				g.drawString(Integer.toString(onTopOf + 1), (Main.critters.get(i).x * Settings.cellSize) + 2, (Main.critters.get(i).y * Settings.cellSize) - 4);
			}
		}
		for (Autotroph autotroph : Main.autotrophs) {
			autotroph.render(this, g);
		}
		
		if (Settings.showNum) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Helvetica", Font.PLAIN, 13));
			g.drawString("0", 2, Settings.cellSize - 4);
			for (int x = 1; x < Main.world.xSize; x++) {
				g.drawString(Integer.toString(x), x * Settings.cellSize, Settings.cellSize - 4);
			}
			for (int y = 1; y < Main.world.xSize; y++) {
				g.drawString(Integer.toString(y), 2, ((y + 1) * Settings.cellSize) - 4);
			}
		}
	}
}