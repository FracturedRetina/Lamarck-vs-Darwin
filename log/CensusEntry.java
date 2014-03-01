package log;

import obj.life.Critter;
import enums.Tribe;
import static enums.Trait.*;

public class CensusEntry {
	public final String name;
	public final Tribe tribe;
	public final int numTraits, claws, height, skin, digest;
	
	public CensusEntry(Critter critter) {
		name = "@" + Integer.toHexString(critter.hashCode());
		tribe = critter.tribe;
		claws = critter.traits.get(CLAWS);
		height = critter.traits.get(HEIGHT);
		skin = critter.traits.get(SKIN);
		digest = critter.traits.get(DIGESTIVE_SYSTEM);
		
		int traitCount = 0;
		
		if (claws > 0) {
			traitCount++;
		}
		if (height > 0) {
			traitCount++;		
		}
		if (skin > 0) {
			traitCount++;
		}
		if (digest > 0) {
			traitCount++;
		}
		
		numTraits = traitCount;
	}
	
	/**A row of an HTML table representing this entry's data**/
	public String toHTMLrow() {
		return this.toString();
	}
	@Override
	public String toString() {
		return "<tr><td>" + name + "</td><td>" + tribe.toString().toLowerCase() + "</td><td>" + numTraits + "</td><td>" + claws + "</td><td>" + height + "</td><td>" + skin + "</td><td>" + digest + "</td></tr>";
	}
}