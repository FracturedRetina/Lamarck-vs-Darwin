package main;

import java.util.Random;

import enums.Move;

import static main.World.Terrain.*;
import obj.life.Autotroph;
import obj.life.Critter;
import abstracts.Position;

@Deprecated
public class HiveBrain {
	private static Random gen = new Random();

	/**
	 * Move critter in a random direction
	 * @param critter Critter to move
	 */
	public static void wanderer(Critter critter) {
		switch (gen.nextInt(4)) {
			case 0: critter.moveUp();
				break;
			case 1: critter.moveDown();
				break;
			case 2: critter.moveLeft();
				break;
			case 3: critter.moveRight();
				break;
		}
		
		for (Autotroph autotroph : Main.autotrophs) {
			autotroph.interact(critter);
		}
	}
	
	/**
	 * Move critter in a random direction that is not an obstacle
	 * @param critter Critter to move
	 */
	public static void nomad(Critter critter) {
		boolean[] tested = new boolean[4];
		boolean hasMoved = false;
		
//		for (Autotroph autotroph : Main.autotrophs) {
//			if (Util.adjacentTo(critter, autotroph)) {
//				int dirToMove = -1;
//				
//				do {
//					int dirToTest = gen.nextInt(4);
//					
//					switch (dirToTest) {
//						case 0:
//							if (Util.adjacentTo(new Position(critter.x - 1, critter.y), autotroph)) {
//								dirToMove = 0;
//							}
//							break;
//						case 1:
//							if (Util.adjacentTo(new Position(critter.x + 1, critter.y), autotroph)) {
//								dirToMove = 1;
//							}
//							break;
//						case 2:
//							if (Util.adjacentTo(new Position(critter.x, critter.y - 1), autotroph)) {
//								dirToMove = 2;
//							}
//							break;
//						case 3:
//							if (Util.adjacentTo(new Position(critter.x, critter.y + 1), autotroph)) {
//								dirToMove = 3;
//							}
//							break;
//					}
//				} while (dirToMove == -1);
//				
//				switch (dirToMove) {
//					case 0: critter.moveUp();
//						break;
//					case 1: critter.moveDown();
//						break;
//					case 2: critter.moveLeft();
//						break;
//					case 3: critter.moveRight();
//						break;
//				}
//				
//				
//				autotroph.interact(critter);
//				return;
//			}
//		}
		
		do {
			switch (gen.nextInt(4)) {
				case 0:
					//If this this direction already tried, choose a new direction
					if (tested[0]) {
						continue;
					}
					//If cell above isn't obstacle, move up and end process
					if (critter.y != 0 && Main.world.terrain[critter.x][critter.y - 1] != OBSTACLE) {
						critter.moveUp();
						hasMoved = true;
					}
					tested[0] = true;
					break;
				case 1:
					//If this this direction already tried, choose a new direction
					if (tested[1]) {
						continue;
					}
					//If cell below isn't obstacle, move down and end process
					if (critter.y != Main.world.ySize - 1 && Main.world.terrain[critter.x][critter.y + 1] != OBSTACLE) {
						critter.moveDown();
						hasMoved = true;
					}
					tested[1] = true;
					break;
				case 2:
					//If this this direction already tried, choose a new direction
					if (tested[2]) {
						continue;
					}
					//If cell to left isn't obstacle, move left and end process
					if (critter.x != 0 && Main.world.terrain[critter.x - 1][critter.y] != OBSTACLE) {
						critter.moveLeft();
						hasMoved = true;
					}
					tested[2] = true;
					break;
				case 3:
					//If this this direction already tried, choose a new direction
					if (tested[3]) {
						continue;
					}
					//If cell to right isn't obstacle, move right and end process
					if (critter.x != Main.world.xSize - 1 && Main.world.terrain[critter.x + 1][critter.y] != OBSTACLE) {
						critter.moveRight();
						hasMoved = true;
					}
					tested[3] = true;
					break;
			}
		} while (!hasMoved);
		
		for (Autotroph autotroph : Main.autotrophs) {
			autotroph.interact(critter);
		}
	}
	
	public static void rightDirection(Critter critter) {
		int radius = 1;
//		border cells = 4 * radius;
		
		for (; radius < Main.world.xSize / 2; radius++) {
			for (int i = 0; i < radius; i++) {
				for (Autotroph autotroph : Main.autotrophs) {
					if (autotroph.x == critter.x - radius + i && autotroph.y == critter.y - i) {
						//TODO
						critter.move(simpleRoute(critter, autotroph));
						autotroph.interact(critter);
						return;
					}
				}
			}
			
			for (int i = radius; i > 0; i--) {
				for (Autotroph autotroph : Main.autotrophs) {
					if (autotroph.x == critter.x + radius - i && autotroph.y == critter.y - i) {
						//TODO
						critter.move(simpleRoute(critter, autotroph));
						autotroph.interact(critter);
						return;
					}
				}
			}
			
			for (int i = 0; i < radius; i++) {
				for (Autotroph autotroph : Main.autotrophs) {
					if (autotroph.x == critter.x + radius - i && autotroph.y == critter.y + i) {
						//TODO
						critter.move(simpleRoute(critter, autotroph));
						autotroph.interact(critter);
						return;
					}
				}
			}
			
			for (int i = radius; i > 0; i--) {
				for (Autotroph autotroph : Main.autotrophs) {
					if (autotroph.x == critter.x - radius + i && autotroph.y == critter.y + i) {
						//TODO
						critter.move(simpleRoute(critter, autotroph));
						autotroph.interact(critter);
						return;
					}
				}
			}
		}
	}
	
	public static void pathfinder(Critter critter) {
		int radius = 1;
//		border cells = 4 * radius;
		
		for (; radius < Main.world.xSize / 2; radius++) {
			for (int i = 0; i < radius; i++) {
				for (Autotroph autotroph : Main.autotrophs) {
					if (autotroph.x == critter.x - radius + i && autotroph.y == critter.y - i) {
						//TODO
						critter.move(simpleRoute(critter, autotroph));
						autotroph.interact(critter);
						return;
					}
				}
			}
			
			for (int i = radius; i > 0; i--) {
				for (Autotroph autotroph : Main.autotrophs) {
					if (autotroph.x == critter.x + radius - i && autotroph.y == critter.y - i) {
						//TODO
						critter.move(simpleRoute(critter, autotroph));
						autotroph.interact(critter);
						return;
					}
				}
			}
			
			for (int i = 0; i < radius; i++) {
				for (Autotroph autotroph : Main.autotrophs) {
					if (autotroph.x == critter.x + radius - i && autotroph.y == critter.y + i) {
						//TODO
						critter.move(simpleRoute(critter, autotroph));
						autotroph.interact(critter);
						return;
					}
				}
			}
			
			for (int i = radius; i > 0; i--) {
				for (Autotroph autotroph : Main.autotrophs) {
					if (autotroph.x == critter.x - radius + i && autotroph.y == critter.y + i) {
						//TODO
						critter.move(simpleRoute(critter, autotroph));
						autotroph.interact(critter);
						return;
					}
				}
			}
		}
	}
	
	public static Move simpleRoute(Critter critter, Position destination) {
		Move move = null;
		Boolean up = null;
		Boolean left = null;
		
		if (critter.x < destination.x) {
			up = true;
		} else if (critter.x > destination.x) {
			up = false;
		}
		
		if (critter.y < destination.y) {
			left = true;
		} else if (critter.y > destination.y) {
			left = false;
		}
		
		
		if (left != null && left == true) {
			try {
				if (Main.world.terrain[critter.x - 1][critter.y] != OBSTACLE) {
					move = Move.LEFT;
				} else {
					if (gen.nextBoolean()) {
						move = Move.UP;
					} else {
						move = Move.DOWN;
					}
				}
			} catch(Exception e) {}
		} else if (left != null && left == false) {
			try {
				if (Main.world.terrain[critter.x + 1][critter.y] != OBSTACLE) {
					move = Move.RIGHT;
				} else {
					if (gen.nextBoolean()) {
						move = Move.UP;
					} else {
						move = Move.DOWN;
					}
				}
			} catch(Exception e) {}
		}
		
		if (move != null) {
			return move;
		}
		
		if (up != null && up == true) {
			try {
				if (Main.world.terrain[critter.x][critter.y - 1] != OBSTACLE) {
					move = Move.UP;
				} else {
					if (gen.nextBoolean()) {
						move = Move.LEFT;
					} else {
						move = Move.RIGHT;
					}
				}
			} catch(Exception e) {}
		} else if (up != null && up == false) {
			try {
				if (Main.world.terrain[critter.x][critter.y + 1] != OBSTACLE) {
					move = Move.DOWN;
				} else {
					if (gen.nextBoolean()) {
						move = Move.LEFT;
					} else {
						move = Move.RIGHT;
					}
				}
			} catch(Exception e) {}
		}
		
		
		return move;
	}
	
	public static Move[] bestRoute(Critter critter, Position destination, int radius) {
		Move[] moveQue = new Move[radius];
		Boolean up = null;
		Boolean left = null;
		
		//Determine the destination's position relative to the critter
		if (critter.x < destination.x) {
			up = true;
		} else if (critter.x > destination.x) {
			up = false;
		}
		
		if (critter.y < destination.y) {
			left = true;
		} else if (critter.y > destination.y) {
			left = false;
		}
		
		//
		if (up == null) {
			if (left == true) {
				if (Main.world.terrain[critter.x - 1][critter.y] != OBSTACLE) {
					moveQue[0] = Move.LEFT;
				}
//				moveQue[0] = Move.LEFT;
			} else if (left == false) {
//				moveQue[0] = Move.RIGHT;
			}
		}
		
		
		return moveQue;
	}
}