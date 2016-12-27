package wtf.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import net.minecraft.block.state.IBlockState;
import wtf.config.StoneRegEntry;
import wtf.config.WTFStoneRegistry;
import wtf.config.ore.WTFOresNewConfig;

public class BlockstateWriter {

	static String dir = "resourcepacks\\WTFOres\\assets\\wtfcore\\blockstates";

	public static void writeResourcePack(){
		


		new File("resourcepacks\\WTFOres").mkdir(); // creates a directory
		new File("resourcepacks\\WTFOres\\assets").mkdir();
		new File("resourcepacks\\WTFOres\\assets\\wtfcore").mkdir();
		new File("resourcepacks\\WTFOres\\assets\\wtfcore\\blockstates").mkdir();

		try {
			
			BufferedWriter writer = new BufferedWriter(new FileWriter("resourcepacks\\WTFOres\\pack.mcmeta"));


			writer.write("{\"pack\": {\"pack_format\": 2,\"description\": \"WTF-Ores: Blockstates for Custom Added Ores\"}}");
			
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void writeDecoStaticBlockstate(IBlockState backState, String regName){

		try {

			//the blockname being created- which I have because it's the registry name, is what the filename needs to be
			String path = dir + "\\" + regName + ".json";
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));

			StoneRegEntry entry =WTFStoneRegistry.stoneReg.get(backState); 
			String blockstateLocation = entry.blockstateLocation;

			writer.write("{\"forge_marker\": 1,");
			writer.newLine();
			writer.write("\"defaults\": {");
			writer.newLine();
			writer.write("\"model\": \"forge:multi-layer\",");
			writer.newLine();
			writer.write("\"transform\": \"forge:default-block\"},");
			writer.newLine();
			writer.write("\"variants\": {");
			writer.newLine();
			writer.write("\"inventory0\": [{\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:base#moss\" }}],");
			writer.newLine();
			writer.write("\"inventory1\": [{\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:base#soul\" }}],");
			writer.newLine();
			writer.write("\"inventory2\": [{\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:base#cracked\" }}],");
			writer.newLine();
			writer.write("\"type\" : {");
			writer.newLine();
			writer.write("\"moss\": {\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:base#moss\" }},");
			writer.newLine();
			writer.write("\"soul\": {\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:base#soul\" }},");
			writer.newLine();
			writer.write("\"cracked\": {\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:base#cracked\" }}");
			writer.newLine();
			writer.write("}");
			writer.newLine();
			writer.write("}");
			writer.newLine();
			writer.write("}");

			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeDecoAnimBlockstate(IBlockState backState, String regName){

		try {

			//the blockname being created- which I have because it's the registry name, is what the filename needs to be
			String path = dir + "\\" + regName + ".json";
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));

			StoneRegEntry entry =WTFStoneRegistry.stoneReg.get(backState); 

			String blockstateLocation = entry.blockstateLocation;

			//some vanilla blocks don't follow the convention- so we implement an override here


			writer.write("{\"forge_marker\": 1,");
			writer.newLine();
			writer.write("\"defaults\": {");
			writer.newLine();
			writer.write("\"model\": \"forge:multi-layer\",");
			writer.newLine();
			writer.write("\"transform\": \"forge:default-block\"},");
			writer.newLine();
			writer.write("\"variants\": {");
			writer.newLine();
			writer.write("\"inventory0\": [{\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:base#lava\" }}],");
			writer.newLine();
			writer.write("\"inventory1\": [{\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\" }}],");
			writer.newLine();
			writer.write("\"inventory2\": [{\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\" }}],");
			writer.newLine();
			writer.write("\"type\" : {");
			writer.newLine();
			writer.write("\"lava_crust\": {\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:base#lava\" }},");
			writer.newLine();
			writer.write("\"drip_water\": {\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\"}},");
			writer.newLine();
			writer.write("\"drip_lava\": {\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\"}}");
			writer.newLine();
			writer.write("},");
			writer.newLine();
			writer.write("\"fast\" : {\"true\" : {},\"false\" : {}");
			writer.newLine();
			writer.write("}");
			writer.newLine();
			writer.write("}");
			writer.newLine();
			writer.write("}");

			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeSpeleothemBlockstate(IBlockState backState, String regName){


		StoneRegEntry stone = WTFStoneRegistry.stoneReg.get(backState);

		try {

			//the blockname being created- which I have because it's the registry name, is what the filename needs to be
			String path = dir + "\\" + regName + ".json";
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));

			

			//some vanilla blocks don't follow the convention- so we implement an override here

			//For these- I don't need the blockstate location- I need the block's texture location
			//for vanilla, it's going to be same as the override
			//for UBC and other mods- it's going to need to be coded in as an override
			
			writer.write("{\"forge_marker\": 1,");
			writer.newLine();
			writer.write("\"defaults\": {\"textures\": {\"texture\": \""+stone.textureLocation+"\"}},");
			writer.newLine();
			writer.write("\"variants\": {");
			writer.newLine();
			writer.write("\"normal\": [{}],");
			writer.newLine();
			writer.write("\"inventory0\": [{\"model\": \"wtfcore:stalactite_small\" }],");
			writer.newLine();
			writer.write("\"inventory1\": [{\"model\": \"wtfcore:stalactite_base\"}],");
			writer.newLine();
			writer.write("\"inventory2\": [{\"model\": \"wtfcore:stalactite_tip\"}],");
			writer.newLine();
			writer.write("\"inventory3\": [{\"model\": \"wtfcore:speleothem_column\"}],");
			writer.newLine();
			writer.write("\"inventory4\": [{\"model\": \"wtfcore:stalagmite_small\"}],");
			writer.newLine();
			writer.write("\"inventory5\": [{\"model\": \"wtfcore:stalagmite_base\"}],");
			writer.newLine();
			writer.write("\"inventory6\": [{\"model\": \"wtfcore:stalagmite_tip\"}],");
			writer.newLine();
			writer.write("\"type=stalactite_small\" : [{\"model\": \"wtfcore:stalactite_small\"},{\"model\": \"wtfcore:stalactite_small\", \"y\":90},{\"model\": \"wtfcore:stalactite_small\", \"y\":180},{\"model\": \"wtfcore:stalactite_small\", \"y\":270}],");
			writer.newLine();
			writer.write("\"type=stalactite_base\" 	:[{\"model\": \"wtfcore:stalactite_base\"},{\"model\": \"wtfcore:stalactite_base\", \"y\":90},{\"model\": \"wtfcore:stalactite_base\", \"y\":180},{\"model\": \"wtfcore:stalactite_base\", \"y\":270}],");
			writer.newLine();
			writer.write("\"type=stalactite_tip\" 	: [{\"model\": \"wtfcore:stalactite_tip\"},{\"model\": \"wtfcore:stalactite_tip\", \"y\":90},{\"model\": \"wtfcore:stalactite_tip\", \"y\":180},{\"model\": \"wtfcore:stalactite_tip\", \"y\":270}],");
			writer.newLine();
			writer.write("\"type=column\" : [{\"model\": \"wtfcore:speleothem_column\"},{\"model\": \"wtfcore:speleothem_column\", \"y\":90},{\"model\": \"wtfcore:speleothem_column\", \"y\":180},{\"model\": \"wtfcore:speleothem_column\", \"y\":270}],");
			writer.newLine();
			writer.write("\"type=stalagmite_small\" :[{\"model\": \"wtfcore:stalagmite_small\"},{\"model\": \"wtfcore:stalagmite_small\", \"y\":90},{\"model\": \"wtfcore:stalagmite_small\", \"y\":180},{\"model\": \"wtfcore:stalagmite_small\", \"y\":270}],");
			writer.newLine();
			writer.write("\"type=stalagmite_base\"	: [{\"model\": \"wtfcore:stalagmite_base\"},{\"model\": \"wtfcore:stalagmite_base\", \"y\":90},{\"model\": \"wtfcore:stalagmite_base\", \"y\":180},{\"model\": \"wtfcore:stalagmite_base\", \"y\":270}],");
			writer.newLine();
			writer.write("\"type=stalagmite_tip\"	: [{\"model\": \"wtfcore:stalagmite_tip\"},{\"model\": \"wtfcore:stalagmite_tip\", \"y\":90},{\"model\": \"wtfcore:stalagmite_tip\", \"y\":180},{\"model\": \"wtfcore:stalagmite_tip\", \"y\":270}]");
			writer.newLine();
			writer.write("}");
			writer.newLine();
			writer.write("}");

			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		try {

			String path = dir + "\\" + regName + "Frozen.json";
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));

			String background = backState.toString(); 
			background = background.replace("[", "#");
			background = background.replace("]", "");
			if (!background.contains("#")){
				background+="#normal";
			}

			//some vanilla blocks don't follow the convention- so we implement an override here

			//For these- I don't need the blockstate location- I need the block's texture location
			//for vanilla, it's going to be same as the override
			//for UBC and other mods- it's going to need to be coded in as an override

			writer.write("{\"forge_marker\": 1,");
			writer.newLine();
			writer.write("\"defaults\": {");
			writer.newLine();
			writer.write("\"model\": \"forge:multi-layer\",");
			writer.newLine();
			writer.write("\"transform\": \"forge:default-block\",");
			writer.newLine();
			writer.write("\"textures\": {\"texture\": \"blocks/stone\"}");
			writer.newLine();
			writer.write("},");
			writer.newLine();
			writer.write("\"variants\": {");
			writer.newLine();
			writer.write("\"normal\": [{}],");
			writer.newLine();
			writer.write("\"inventory0\": [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalactite_small\", \"Translucent\": \"ice#normal\" }}],");
			writer.newLine();
			writer.write("\"inventory1\": [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalactite_base\", \"Translucent\": \"ice#normal\" }}],");
			writer.newLine();
			writer.write(" \"inventory2\": [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalactite_tip\", \"Translucent\": \"ice#normal\" }}],");
			writer.newLine();
			writer.write("\"inventory3\": [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=column\", \"Translucent\": \"ice#normal\" }}],");
			writer.newLine();
			writer.write("\"inventory4\": [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalagmite_small\", \"Translucent\": \"ice#normal\" }}],");
			writer.newLine();			
			writer.write("\"inventory5\": [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalagmite_base\", \"Translucent\": \"ice#normal\" }}],");
			writer.newLine();
			writer.write("\"inventory6\": [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalagmite_tip\", \"Translucent\": \"ice#normal\" }}],");
			writer.newLine();
			writer.write("\"type=stalactite_small\" : [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalactite_small\", \"Translucent\": \"ice#normal\" }}],");
			writer.newLine();
			writer.write("\"type=stalactite_base\" 	:[{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalactite_base\", \"Translucent\": \"ice#normal\" }}],");
			writer.newLine();
			writer.write("\"type=stalactite_tip\" 	: [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalactite_tip\", \"Translucent\": \"ice#normal\" }}],");
			writer.newLine();
			writer.write("\"type=column\" : [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=column\", \"Translucent\": \"ice#normal\" }}],");
			writer.newLine();
			writer.write("\"type=stalagmite_small\" :[{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalagmite_small\", \"Translucent\": \"ice#normal\" }}],");
			writer.newLine();
			writer.write("\"type=stalagmite_base\"	: [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalagmite_base\", \"Translucent\": \"ice#normal\" }}],");
			writer.newLine();
			writer.write("\"type=stalagmite_tip\"	: [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalagmite_tip\", \"Translucent\": \"ice#normal\" }}]");
			writer.newLine();
			writer.write("}");
			writer.newLine();
			writer.write("}");

			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeDenseOreBlockstate(IBlockState backState, String regName, String orestring, String stoneString){

		StoneRegEntry entry = WTFStoneRegistry.stoneReg.get(backState); 
		if (entry == null){
			try {
				throw new Exception("No stone registry entry found for "+ backState.getBlock().getRegistryName()+"@"+backState.getBlock().getMetaFromState(backState) + " please add it to the WTFStoneRegistry.cfg");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String blockstateLocation = entry.blockstateLocation;

		try {

			//the blockname being created- which I have because it's the registry name, is what the filename needs to be
			String path = dir + "\\" + regName + ".json";
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			
			//Need to replace "dense_"+stoneString with the name of the actual thing
			//Alternatively- I could just move all the shit into base- but then it's no longer auto-generating, so maybe not...
			
			writer.write("{\"forge_marker\": 1,");
			writer.newLine();
			writer.write("\"defaults\": {\"model\": \"forge:multi-layer\",\"transform\": \"forge:default-block\"},");
			writer.newLine();
			writer.write("\"variants\": {");
			writer.newLine();
			writer.write("\"normal\": [{}],");
			writer.newLine();
			writer.write(" \"inventory\": [{\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:"+regName+"#dense0\"}}],");
			writer.newLine();
			writer.write("\"density\" : {");
			writer.newLine();
			writer.write("\"0\" : {\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:"+regName+"#dense0\"} },");
			writer.newLine();
			writer.write("\"1\" : {\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:"+regName+"#dense1\"} },");
			writer.newLine();
			writer.write("\"2\" : {\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:"+regName+"#dense2\"} }");
			writer.newLine();
			writer.write("},");
			writer.newLine();
			for (int loop = 0; loop < 3; loop++){  
				writer.write("\"dense"+loop+"\": [");
				writer.newLine();
				writer.write("{\"model\": \"minecraft:cube_all\", \"textures\": { \"all\": \"wtfcore:overlays/"+orestring+loop+"\" }},");
				writer.newLine();
				writer.write("{\"model\": \"minecraft:cube_all\", \"y\":180, \"x\":180, \"textures\": { \"all\": \"wtfcore:overlays/"+orestring+loop+"\" }}");				
				if (!WTFOresNewConfig.rotate180only){
					writer.write(",");
					writer.newLine();
					writer.write("{\"model\": \"minecraft:cube_all\", \"y\":90, \"x\":90, \"textures\": { \"all\": \"wtfcore:overlays/"+orestring+loop+"\" }},");
					writer.newLine();
					writer.write("{\"model\": \"minecraft:cube_all\", \"y\":270, \"x\":270, \"textures\": { \"all\": \"wtfcore:overlays/"+orestring+loop+"\" }}");
					
				}
				writer.newLine();
				writer.write("]");
				if (loop < 2){writer.write(",");}
				writer.newLine();
			}
			writer.write("}");
			writer.newLine();
			writer.write("}");

			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
