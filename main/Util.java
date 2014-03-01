package main;

import static enums.Obstruction.*;
import static enums.Trait.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JComponent;

import main.World.Terrain;

import abstracts.Position;
import enums.Obstruction;
import enums.Trait;

public class Util {
//	public static boolean adjacentTo(int[] position1, int[] position2) {
//		for (int y = -1; y < 1; y++) {
//			for (int x = -1; x < 1; x++) {
//				if (position1[0] == position2[0] + x && position1[1] == position2[1] + y) {
//					return true;
//				}
//				if (position1[0] + x == position2[0] && position1[1] + y == position2[1]) {
//					return true;
//				}
//			}
//		}
//		
//		
//		return false;
//	}
	
	public static boolean adjacentTo(Position pos1, Position pos2) {
		for (int y = -1; y < 1; y++) {
			for (int x = -1; x < 1; x++) {
				if (pos1.x == pos2.x + x && pos1.y == pos2.y + y) {
					return true;
				}
				if (pos1.x + x == pos2.x && pos1.y + y == pos2.y) {
					return true;
				}
			}
		}
		
		
		return false;
	}
	
	public static void drawSquare(int x, int y, Color color, JComponent canvas, Graphics g) {
		g.setColor(color);
		g.fillRect(x * 16, y * 16, 16, 16);
	}
	
	public static boolean traitValid(Trait trait, Obstruction obstruction) {
		for (Obstruction obst : Settings.traitMap.get(trait)) {
			if (obst == obstruction) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean canEat(HashMap<Trait, Integer> traits, HashMap<Obstruction, Integer> obstruction) {
		//TODO Make dynamic
		for (int i = 0; i < 4; i++) {
			if (traitToIntArray(traits)[i] < obstToIntArray(obstruction)[i]) {
				return false;
			}
		}
		
		if (traitToIntArray(traits)[3] < obstToIntArray(obstruction)[5]) {
			return false;
		}
		
		
		return true;
	}
	
	public static int[] obstToIntArray(HashMap<Obstruction, Integer> traits) {	
		return new int[] {
			traits.get(DIGESTABILITY),
			traits.get(Obstruction.HEIGHT),
			traits.get(SHELL),
			traits.get(THORNS),
			traits.get(UNDERGROUND)
		};
	}
	public static int[] traitToIntArray(HashMap<Trait, Integer> traits) {	
		return new int[] {
			traits.get(DIGESTIVE_SYSTEM),
			traits.get(Trait.HEIGHT),
			traits.get(CLAWS),
			traits.get(SKIN)
		};
	}
	
	public static int getLowerBias(final int max) {
		int[] chances = new int[max];
		int randToBias;
		Random gen = new Random();
		
		for (int i = 1; i < max + 1; i++) {
			chances[max - i] = (int) Math.pow(2, i);
		}
		
		randToBias = gen.nextInt((int) Math.round(Math.pow(2, max)));

		int lastNum = 0;
		for (int i = chances.length - 1; i >= 0; i--) {
			if (randToBias >= lastNum && randToBias < chances[i]) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * @return A pseudo-random position, as an <code>int[]</code>, that is not an obstacle
	**/
	public static int[] getValidPosition() {
		int[] position = new int[2];
		Random gen = new Random();
		
		do {
			position[0] = gen.nextInt(Main.world.xSize);
			position[1] = gen.nextInt(Main.world.ySize);
		} while (Main.world.terrain[position[0]][position[1]] == Terrain.OBSTACLE);
		
		return position;
	}
}