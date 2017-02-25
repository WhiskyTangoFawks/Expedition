package wtf.config;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import wtf.utilities.UBC.UBCCompat;

public abstract class AbstractConfig {

	protected final static String configPath = "config/WTF-Expedition/";
	
	
	public static BiomeDictionary.Type getBiomeTypeFromString(String biomestring) throws Exception{
		try {
			return BiomeDictionary.Type.valueOf(biomestring.toUpperCase());
		} catch (IllegalArgumentException e){
			Type[] types = BiomeDictionary.Type.values();
			String string = "";
			for (Type type : types){
				string += type.toString() + ", " ;
			}
			throw new Exception("Ore Config Parsing Exception while trying to parse Biome Percent modifier : " + biomestring + ":  "  + "Unrecognised Forge BiomeDictionary BiomeType, the available biome types are : " + string);
		}
	}
	
	public static Block getBlockFromString(String string){
		
			Block block = Block.getBlockFromName(string);
			if (block == null){
				try {
					throw new NoBlockRegistryFoundForStringException ("Unable to find block for " + string);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return block;
	}
	
	public static IBlockState getBlockState(String string){
		if (string == null || !string.contains("@")){
			return null;
		}
		String[] stringArray = string.split("@");
		Block block = Block.getBlockFromName(stringArray[0]);

		if (block == null){
			try {
				throw new NoBlockRegistryFoundForStringException("Unable to find block for " + stringArray[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (stringArray.length < 1){
			try {
				throw new Exception("Unable to find metadata argument for :" + string +".");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		IBlockState state = block.getStateFromMeta(Integer.parseInt(stringArray[1]));
		if (state == null){
			try {
				throw new Exception("Invalid blockstate for block and meta " + string);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return state; 
	}

	public static ArrayList<IBlockState> getBlockStateArray(String string){
		ArrayList<IBlockState> list = new ArrayList<IBlockState>();
		String[] stringArray = string.split(",");
		for (String substring :stringArray){

			if (substring.equals("igneous")){
				list.addAll(Arrays.asList(UBCCompat.IgneousStone));
			}
			else if (substring.equals("metamorphic")){
				list.addAll(Arrays.asList(UBCCompat.MetamorphicStone));
			}
			else if (substring.equals("sedimentary")){
				list.addAll(Arrays.asList(UBCCompat.SedimentaryStone));
			}
			else {
				list.add(getBlockState(substring));
			}
		}
		return list;
	}

	protected static String[] appendArray(String[] arrayString, String stringToAdd){
		String[] newStringArray = new String[arrayString.length+1];
		for (int loop = 0; loop < arrayString.length; loop++){
			newStringArray[loop] = arrayString[loop];
		}
		newStringArray[newStringArray.length-1] = stringToAdd;
		return newStringArray;
		
		
	}
	public static class NoBlockRegistryFoundForStringException extends Exception{
		
		public NoBlockRegistryFoundForStringException(String string){
			super(string);
		}

		private static final long serialVersionUID = 1L;
		
		
	}
}
