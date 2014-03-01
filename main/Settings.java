package main;

import static enums.Obstruction.*;
import static enums.Trait.*;

import java.util.HashMap;

import obj.life.*;
import enums.Obstruction;
import enums.Trait;

/**Class that stores the constants and global variables of the simulation**/
public class Settings {
	/**Terminate the simulation if all creatures are dead**/
	public static final boolean stopWhenAllDead = true;
	/**Show the cell x and ycoordinates along the axes of the display**/
	public static boolean showNum = true;
//	public static boolean fullscreen = false;
	/**
	 * Width of the window</br>
	 * 2 * 2 * 2 * 2 * 2 * 3 * 5
	**/
	public static int winWidth = 1280;
	/**Height of the window**/
	public static int winHeight = 720;
	/**Size of the canvas or display that renders the world**/
	public static int canvasSize = winHeight;
	/**Size, in pixels, of one side of each square cell of the world**/
	public static final int cellSize = 16;
	/**Delay between steps in milliseconds**/
	public static final int delay = 100;
	/**The simulation will automatically terminate after this many steps**/
	public static final long simDuration = 100000;
	/**The type of evolution being tested**/
	public static final Class<? extends Critter> critterType = Lamarckian.class;
	/**Shows which traits are useful for getting around which obstacles**/
	public static final HashMap<Trait, Obstruction[]> traitMap = new HashMap<Trait, Obstruction[]>();
		{
			traitMap.put(CLAWS,
				new Obstruction[] {
					UNDERGROUND, SHELL
				}
			);
			traitMap.put(Trait.HEIGHT,
				new Obstruction[] {
					Obstruction.HEIGHT
				}
			);
			traitMap.put(SKIN,
				new Obstruction[] {
					THORNS
				}
			);
			traitMap.put(DIGESTIVE_SYSTEM,
				new Obstruction[] {
					DIGESTABILITY
				}
			);
		}
}