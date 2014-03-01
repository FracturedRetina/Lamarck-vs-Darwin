package main;

import static enums.Move.DOWN;
import static enums.Move.LEFT;
import static enums.Move.RIGHT;
import static enums.Move.UP;
import static main.World.Terrain.OBSTACLE;

import java.util.Random;

import obj.life.Autotroph;
import obj.life.Critter;
import abstracts.Controller;
import abstracts.Position;
import enums.Move;

/**File containing all classes that implement {@link Controller}**/
public class Controllers {
	private static Random gen;
	
	/**Moves critter in a random direction and eats anything within range**/
	public static class Wanderer implements Controller {
		@Override
		public void move(Critter critter) {
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
	};
	
	/**Moves critter in a random direction, but makes sure it isn't running into a wall, and eats anything within range**/
	public static class Nomad implements Controller {
		@Override
		public void move(Critter critter) {
			boolean[] tested = new boolean[4];
			boolean hasMoved = false;
			
//			for (Autotroph autotroph : Main.autotrophs) {
//				if (Util.adjacentTo(critter, autotroph)) {
//					int dirToMove = -1;
//					
//					do {
//						int dirToTest = gen.nextInt(4);
//						
//						switch (dirToTest) {
//							case 0:
//								if (Util.adjacentTo(new Position(critter.x - 1, critter.y), autotroph)) {
//									dirToMove = 0;
//								}
//								break;
//							case 1:
//								if (Util.adjacentTo(new Position(critter.x + 1, critter.y), autotroph)) {
//									dirToMove = 1;
//								}
//								break;
//							case 2:
//								if (Util.adjacentTo(new Position(critter.x, critter.y - 1), autotroph)) {
//									dirToMove = 2;
//								}
//								break;
//							case 3:
//								if (Util.adjacentTo(new Position(critter.x, critter.y + 1), autotroph)) {
//									dirToMove = 3;
//								}
//								break;
//						}
//					} while (dirToMove == -1);
//					
//					switch (dirToMove) {
//						case 0: critter.moveUp();
//							break;
//						case 1: critter.moveDown();
//							break;
//						case 2: critter.moveLeft();
//							break;
//						case 3: critter.moveRight();
//							break;
//					}
//					
//					
//					autotroph.interact(critter);
//					return;
//				}
//			}
			
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
	};
	
	/**Moves critter towards the nearest {@link Autotroph} and eats it when within range. If only path is running into a wall, the critter doesn't move to conserve energy.**/
	public static class RightDirection implements Controller {
		protected Position target = null;
		
		@Override
		public void move(Critter critter) {
//			borderCells = 4 * radius;
			
			//If critter does not have a target
			if (target == null || target instanceof Autotroph == false) {
				//Expand the radius of search until an Autotroph is found
				for (int radius = 1; radius < Main.world.xSize / 2; radius++) {
					//Check the top left side of the rectangle for Autotrophs
					for (int i = 0; i < radius; i++) {
						//Code in placed block for future implementation of a critter's Diet
						{
							//Check if any Autotroph is at the current spot on the circumference
							for (Autotroph autotroph : Main.autotrophs) {
								if (autotroph.getFood() > 0 && autotroph.x == critter.x - radius + i && autotroph.y == critter.y - i) {
									target = autotroph;
									critter.move(bestMove(critter, target));
									autotroph.interact(critter);
									return;
								}
							}
						}
					}
					
					for (int i = radius; i > 0; i--) {
						{
							for (Autotroph autotroph : Main.autotrophs) {
								if (autotroph.getFood() > 0 && autotroph.x == critter.x + radius - i && autotroph.y == critter.y - i) {
									target = autotroph;
									critter.move(bestMove(critter, target));
									autotroph.interact(critter);
									return;
								}
							}
						}
					}
					
					for (int i = 0; i < radius; i++) {
						{
							for (Autotroph autotroph : Main.autotrophs) {
								if (autotroph.getFood() > 0 && autotroph.x == critter.x + radius - i && autotroph.y == critter.y + i) {
									target = autotroph;
									critter.move(bestMove(critter, target));
									autotroph.interact(critter);
									return;
								}
							}
						}
					}
					
					for (int i = radius; i > 0; i--) {
						{
							for (Autotroph autotroph : Main.autotrophs) {
								if (autotroph.getFood() > 0 && autotroph.x == critter.x - radius + i && autotroph.y == critter.y + i) {
									target = autotroph;
									critter.move(bestMove(critter, target));
									autotroph.interact(critter);
									return;
								}
							}
						}
					}
				}
			} else {
				critter.move(bestMove(critter, target));
				if (target instanceof Autotroph) {
					((Autotroph) target).interact(critter);
					if (((Autotroph) target).getFood() <= 0) {
						target = null;
					}
				} else if (target instanceof Critter && critter.gender != ((Critter) target).gender) {
					critter.mate((Critter) target);
				}
			}
		}
		
		/**Returns this {@link Controller}'s current target as a {@link Position} object**/
		public Position getTarget() {
			return target;
		}
		
		/**
		 * @param critter {@link Critter} to move
		 * @param destination The {@link Position} to move the critter to
		 * @return Best move, as a {@link Move}, to get the critter to its target, not considering future obstacles that it may run into
		 */
		public Move bestMove(Position critter, Position destination) {
			Boolean up = null;
			Boolean left = null;
			
			if (Util.adjacentTo(critter, destination)) {
				return null;
			}
			
			if (critter.y > destination.y) {
				up = true;
			} else if (critter.y < destination.y) {
				up = false;
			}
			
			if (critter.x > destination.x) {
				left = true;
			} else if (critter.x < destination.x) {
				left = false;
			}
			
			if (up == null) {
				//If already at destination, return
				if (left == null) {
					return null;
				}
				try {
					//If destination is to the left and no obstacle is to the left
					if (left == true && Main.world.terrain[critter.x - 1][critter.y] != OBSTACLE) {
						return LEFT;
					}
				} catch(Exception e) {}
				try {
					//If destination is to the right and no obstacle is to the right
					if (left == false && Main.world.terrain[critter.x + 1][critter.y] != OBSTACLE) {
						return RIGHT;
					}
				} catch(Exception e) {}
			}
			if (left == null) {
				if (up == null) {
					return null;
				}
				try {
					if (up == true && Main.world.terrain[critter.x - 1][critter.y] != OBSTACLE) {
						return UP;
					}
				} catch(Exception e) {}
				try {
					if (up == false && Main.world.terrain[critter.x + 1][critter.y] != OBSTACLE) {
						return DOWN;
					}
				} catch(Exception e) {}
			}
			
			
			try {
				if (up == true && Main.world.terrain[critter.x][critter.y - 1] != OBSTACLE) {
					return UP;
				}
			} catch(Exception e) {}
			try {
				if (up == false && Main.world.terrain[critter.x][critter.y + 1] != OBSTACLE) {
					return DOWN;
				}
			} catch(Exception e) {}
			
			try {
				if (left == true && Main.world.terrain[critter.x - 1][critter.y] != OBSTACLE) {
					return LEFT;
				}
			} catch(Exception e) {}
			try{
				if (left == false && Main.world.terrain[critter.x + 1][critter.y] != OBSTACLE) {
					return RIGHT;
				}
			} catch(Exception e) {}
			
			
			
			return null;
		}
	}
	
	public static class Romeo extends RightDirection implements Controller {
		@Override
		public void move(Critter critter) {
			if (critter.lookingForMate()) {
				//If critter does not have a target
				if (target == null || target instanceof Critter == false) {
					//Expand the radius of search until an Autotroph is found
					for (int radius = 1; radius < Main.world.xSize / 2; radius++) {
						//Check the top left side of the rectangle for Autotrophs
						for (int i = 0; i < radius; i++) {
							//Code in placed block for future implementation of a critter's Diet
							{
								//Check if any Autotroph is at the current spot on the circumference
								for (Critter mate : Main.critters) {
									if (mate.gender != critter.gender && mate.x == critter.x - radius + i && mate.y == critter.y - i) {
										target = mate;
										critter.move(bestMove(critter, target));
										mate.mate(critter);
										return;
									}
								}
							}
						}
						
						for (int i = radius; i > 0; i--) {
							{
								for (Critter mate : Main.critters) {
									if (mate.gender != critter.gender && mate.x == critter.x + radius - i && mate.y == critter.y - i) {
										target =mate;
										critter.move(bestMove(critter, target));
										mate.mate(critter);
										return;
									}
								}
							}
						}
						
						for (int i = 0; i < radius; i++) {
							{
								for (Critter mate : Main.critters) {
									if (mate.gender != critter.gender && mate.x == critter.x + radius - i && mate.y == critter.y + i) {
										target = mate;
										critter.move(bestMove(critter, target));
										mate.mate(critter);
										return;
									}
								}
							}
						}
						
						for (int i = radius; i > 0; i--) {
							{
								for (Critter mate : Main.critters) {
									if (mate.gender != critter.gender && mate.x == critter.x - radius + i && mate.y == critter.y + i) {
										target = mate;
										critter.move(bestMove(critter, target));
										mate.mate(critter);
										return;
									}
								}
							}
						}
					}
				} else {
					critter.move(bestMove(critter, target));
					if (target instanceof Autotroph) {
						((Autotroph) target).interact(critter);
						if (((Autotroph) target).getFood() <= 0) {
							target = null;
						}
					} else if (target instanceof Critter) {
						critter.mate((Critter) target);
					}
				}
			} else {
				super.move(critter);
			}
		}
	}
	
	public static class Pathfinder implements Controller {
		@Override
		public void move(Critter critter) {
			int radius = 1;
//			border cells = 4 * radius;
			
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
		
		private Move simpleRoute(Critter critter, Position destination) {
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
		
		@SuppressWarnings("unused")
		private Move[] bestRoute(Critter critter, Position destination, int radius) {
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
//					moveQue[0] = Move.LEFT;
				} else if (left == false) {
//					moveQue[0] = Move.RIGHT;
				}
			}
			
			
			return moveQue;
		}
	};
}