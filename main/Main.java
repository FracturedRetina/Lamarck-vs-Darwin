package main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import log.CensusEntry;
import log.LogPlanA;
import obj.life.Autotroph;
import obj.life.Critter;
import ui.Window;

public class Main {
	private static Window window = new Window();
	/**						  Dimensions =	  canvas size 	  ÷ 	square size**/
	public static World world = new World(Settings.canvasSize / Settings.cellSize, Settings.canvasSize / Settings.cellSize);
	public static long step = 0;
	/**{@link ArrayList} containing all creatures capable of moving and evolving**/
	public static List<Critter> critters = new ArrayList<Critter>();
	/**{@link ArrayList} containing all environment objects capable of being eaten**/
	public static List<Autotroph> autotrophs = new ArrayList<Autotroph>();
	public static boolean paused = false;
	public static boolean exit = false;
	/**TODO**/
	public static LogPlanA log = new LogPlanA();
	
	/**Initializes the simulation then runs the main loop**/
	public static void main(String[] args) {
		//Populate ArrayLists
		for (int i = 0; i < 17; i++) {
			try {
				critters.add(Settings.critterType.newInstance());
			} catch (Exception e) {
				net.krakendev.util.ExceptionHandler.excPopup(e);
			}
		}
		for (int i = 0; i < 15; i++) {
			autotrophs.add(new Autotroph());
		}
		
		//Wait for display to load
		pause(1000);
		
		//Main loop
		while (!exit) {
			if (!paused) {
				//Update generation label and refresh viewport
				window.refresh();
				log.step();
				
				//Not using iterator to prevent java.util.ConcurrentModificationException
				for (int i = 0; i < critters.size(); i++) {
					critters.get(i).step();
					if (critters.get(i).shouldDestroy()) {
						log.census.add(new CensusEntry(critters.get(i)));
						System.out.println(critters.get(i).getEulogy());
						critters.remove(critters.get(i));
					}
				}
				for (int i = 0; i < autotrophs.size(); i++) {
					autotrophs.get(i).step();
				}
				
				//Add 1 to the step counter
				step++;
				if (step >= Settings.simDuration || (Settings.stopWhenAllDead && critters.size() == 0)) {
					exit();
				}
				//Wait for 100 milliseconds (1/10 second)
				pause(Settings.delay);
			}
		}
	}
	
	/**
	 * Pause the current thread for <code>time</code> milliseconds and print a stack trace if a java.lang.InterruptedException is thrown
	 * <br />
	 * <b>Note:</b> 1 second = 1,000 milliseconds
	 * 
	 * @param time Number of milliseconds to wait
	**/
	public static void pause(long time) {
		if (time <= 0) {
			return;
		}
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void exit(boolean exportLog) {
		exit = true;
		
		if (exportLog) {
			log.export();
		}
		
		
		System.exit(0);
	}

	public static void exit() {
		int confirm = JOptionPane.showOptionDialog(null, "Would you like to export a log?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (confirm == JOptionPane.YES_OPTION) {
			exit(true);
		} else {
			exit(false);
		}
	}
}