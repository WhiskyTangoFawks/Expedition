package wtf.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import wtf.Core;
import wtf.init.BlockSets;


public class ConfigUtils {
	

	public static String getStringFromArrayList(ArrayList<String> defaultFallingBlocks){
		String fullString = "";
		for (String string : defaultFallingBlocks){
			fullString+= string+",";
		}
		return fullString;
	}

	public static void parseFallingBlocks(String fallingBlockString){
		
		String[] fallingBlockStringArray = fallingBlockString.split(",");
		for (String blockinfo : fallingBlockStringArray){

			String[] blockAndStability = blockinfo.split("@");		
			
			Block blockToFall = Block.getBlockFromName(blockAndStability[0]);
			if (blockToFall != Blocks.AIR || blockToFall != null){
				BlockSets.fallingBlocks.put(blockToFall, Integer.parseInt(blockAndStability[1])/100F);
				//System.out.println("Parsing Falling Blocks : Block added to falling block list: "+blockToFall.getRegistryName());
			}
			else {
				Core.coreLog.info("WTFTweaksConfig.Add falling block: Unable to find block for : " + blockAndStability[0]);
			}
		}
	}
	
	public static void parseOreFrac(String oreStringSet){
		
		String[] oreStringArray = oreStringSet.split(",");
		//for(int loop = 0; loop < oreStringArray.length; loop++)	{
		for (String oreString : oreStringArray){	
			Core.coreLog.info("WTFTweaksConfig.AddOre: block for : "+ oreString);
			Block oreBlock = Block.getBlockFromName(oreString);
			if (oreBlock != Blocks.AIR){
				BlockSets.oreAndFractures.add(oreBlock);
				Core.coreLog.info("Parsing Ore Fracturing List : Block added to ore list: "+oreBlock.getRegistryName());
			}
			else {
				Core.coreLog.info(" Parsing Ore Fracturing List : Unable to find block for : "+"oreStringArray[loop]");
			}
		}
	}

	public static void parseMiningSpeeds(String readMiningSpeed) {
		String[] blockStringArray = readMiningSpeed.split(",");
		for (String blockinfo : blockStringArray){
			String[] blockAndSpeed = blockinfo.split("@");
			
			Block block = Block.getBlockFromName(blockAndSpeed[0]);
	
			if (block == null){
				Core.coreLog.info("Could not find block for " + blockAndSpeed[0]);
			}
			
			float speed = Float.parseFloat(blockAndSpeed[1]);
			
			if (block != Blocks.AIR && block != null){
				BlockSets.blockMiningSpeed.put(block, speed);
				Core.coreLog.info("Parsing Block Mining Speed : Block added to mining speed list: " + block.getRegistryName() + "@"+speed);
			}
			else {
				Core.coreLog.info("Parsing Block Mining Speed: Unable to find block for : "+"blockStringArray[loop]");
			}
			if (speed == 0){
				Core.coreLog.info("Parsing Block Mining Speed : Speed float not parsed correctly for " + blockinfo);
			}
		}	}
	
	
}
