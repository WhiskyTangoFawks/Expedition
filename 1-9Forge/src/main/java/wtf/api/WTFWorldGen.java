package wtf.api;

import java.util.ArrayList;

public class WTFWorldGen {

	//The arraylist of all registered generators
	private static final ArrayList<PopulationGenerator> generators = new ArrayList<PopulationGenerator>();
	
	
	public static boolean addGen(PopulationGenerator generator){
		return generators.add(generator);
	}
	
	public static boolean addGen(PopulationGenerator generator, int spot){
		generators.add(spot, generator);
		return generators.get(spot)==generator;
	}
	
	public static ArrayList<PopulationGenerator> getGenerators(){
		return generators;
	}
	
	
}
