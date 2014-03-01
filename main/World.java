package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JComponent;

public class World {
	public enum Terrain {
		DIRT(139, 69, 19),
		OBSTACLE(167, 167, 167);
		
		public Color color;
		
		Terrain(int red, int green, int blue) {
			color = new Color(red, green, blue);
		}
	};
	public final int xSize;
	public final int ySize;
	public Terrain[][] terrain;
	
	public World(int width, int height) {
		Random gen = new Random();
		xSize = width;
		ySize = height;
		terrain = new Terrain[width][height];
		
		//Generate terrain
		//Seed terrain
		for (int y = 0; y < ySize; y++) {
			for (int x = 0; x < xSize; x++) {
				if (gen.nextInt(40) == 0) {
					terrain[x][y] = Terrain.OBSTACLE;
				} else {
					terrain[x][y] = Terrain.DIRT;
				}
			}
		}
		//Generate formations
		for (int y = 1; y < ySize - 1; y++) {
			for (int x = 1; x < xSize - 1; x++) {
				if (gen.nextInt(3) == 0 && terrain [x - 1][y	] == Terrain.OBSTACLE)
					terrain[x][y] = Terrain.OBSTACLE;
				if (gen.nextInt(3) == 0 && terrain [x + 1][y	] == Terrain.OBSTACLE)
					terrain[x][y] = Terrain.OBSTACLE;
				if (gen.nextInt(3) == 0 && terrain [x - 1][y + 1] == Terrain.OBSTACLE)
					terrain[x][y] = Terrain.OBSTACLE;
				if (gen.nextInt(3) == 0 && terrain [x + 1][y - 1] == Terrain.OBSTACLE)
					terrain[x][y] = Terrain.OBSTACLE;
				if (gen.nextInt(3) == 0 && terrain [x	 ][y + 1] == Terrain.OBSTACLE)
					terrain[x][y] = Terrain.OBSTACLE;
				if (gen.nextInt(3) == 0 && terrain [x	 ][y - 1] == Terrain.OBSTACLE)
					terrain[x][y] = Terrain.OBSTACLE;
			}
		}
	}
	
	public void render(JComponent canvas, Graphics g) {
		for (int y = 0; y < ySize; y++) {
			for (int x = 0; x < xSize; x++) {
				g.setColor(terrain[x][y].color);
				g.fillRect(x * Settings.cellSize, y * Settings.cellSize, Settings.cellSize, Settings.cellSize);
			}
		}
	}
}