package log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import main.Main;
import obj.life.Critter;

public class LogPlanB implements Log {
	public final List<Integer> population = new ArrayList<Integer>();
	public final int maturityAge = (new Critter()).maturityAge;
	public final int numChildren = (new Critter()).numOfChildren;
	
	@Override
	public void step() {
		population.add(Main.critters.size());
	}
	
	@Override
	public void export() {
		(new File("logs")).mkdirs();
		//Determine file name is taken
		File file = new File("logs/log.html");
		if (file.exists()) {
			int i = 1;
			do {
				file = new File("logs/log" + i + ".html");
				i++;
			} while (file.exists());
		}
		
		String content = "<html lang=\"en\"> <head> <style type=\"text/css\"> p { display:inline } </style> </head> <body> <b>Maturity Age: </b><p>&mate-age&</p> <br /> <b>Nmber of Offspring: </b><p>&offspring&</p> <br /> <table border=\"1\"> <tr> <td><b>Population</b></td> </tr> </table> </body></html>";
		content.replaceAll(String.valueOf(maturityAge), "&mate-age&");
		content.replaceAll("&offspring&", String.valueOf(numChildren));
//		for (Integer pop : population) {
//			content = content.replaceAll("</td>$", String.valueOf("<td>" + pop + "</td>"));
//		}
		
		try {
			PrintWriter out = new PrintWriter(file.getPath());
			
			out.print(content);
			out.close();
		} catch (FileNotFoundException e) {
			net.krakendev.util.ExceptionHandler.excPopup(e);
		}
	}
}