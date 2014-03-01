package log;

import java.io.File;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import main.Main;
import main.Settings;

import obj.life.Critter;

public class LogPlanA implements Log {
	/**Population of {@link Critter}<code>s</code> recorded at the start of each generation**/
	public final List<Integer> population = new ArrayList<Integer>();
	/**Data about each of the {@link Critter}<code>s'</code> traits recorded at death**/
	public final List<CensusEntry> census = new ArrayList<CensusEntry>();
	/**The age at which {@link Critter}<code>s</code> begin looking for mates**/
	public final int maturityAge = (new Critter()).maturityAge;
	/**The number of offspring produced by each pair of {@link Critter}<code>s</code>**/
	public final int numChildren = (new Critter()).numOfChildren;
	
	@Override
	public void step() {
		population.add((int) Main.critters.size());
	}
	
	@Override
	public void export() {
		//Make sure there is a directory that a log can be created in. Prevents FileNotFound Exceptions.
		(new File("logs")).mkdirs();
		//Determine file name is taken
		File file = new File("logs/log.html");
		if (file.exists()) {
			int i = 1;
			do {
				file = new File("logs/log" + (new DecimalFormat("000000")).format(i) + ".html");
				i++;
			} while (file.exists());
		}
		
		//HTML to export
		String html = "<html lang=\"en\">" +
				"<head><style type=\"text/css\">" +
					"p {display:inline}" +
				"</style></head>" +
				"<body>" +
				"<b>Timestamp: </b><p>" + (new Date()) + "</p><br />" +
				"<b>Simulation Duration(Num of generations): </b><p>" + Main.step + "</p><br />" +
				"<b>Critter Type: </b><p>" + Settings.critterType.getSimpleName() + "</p><br />" +
				"<b>Maturity Age: </b><p>" + maturityAge +"</p><br />" +
				"<b>Number of Offspring: </b><p>" + numChildren + "</p><br /><br />" +
				//Create the trait data table
				"<table id=\"traitData\" border=\"1\"><tr><td><b>Critter</b></td><td><b>Tribe</b></td><td><b># of Traits Evolved</b></td><td><b>Extent of CLAWS</b></td><td><b>Extent of HEIGHT</b></td><td><b>Extent of SKIN</b></td><td><b>Extent of DIGESTIVE_SYSTEM</b></td></tr>";
		for (CensusEntry cenEn : census) {
			html += cenEn.toHTMLrow();
		}
		html += "</table><table id=\"popData\" border=\"1\"><tr><td><b>Step</b><td><b>Population</b></td></tr>";
		
		//Create the population table
		for (int i = 1; i <= population.size(); i++) {
			html += "<tr><td>" + i + "</td><td>" + population.get(i - 1) + "</td></tr>";
		}
		html += "</table>" + "</body></html>";
		
		
		//Writes this log to the file
		try {
			PrintWriter out = new PrintWriter(file.getPath());
			
			out.print(html);
			out.close();

			JOptionPane.showMessageDialog(null, "Log successfully exported to \"" + file.getPath() + "\"");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "There was an error exporting the log");
		}
	}
}