package wtf.utilities.blockstatewriters;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import net.minecraft.block.state.IBlockState;
import wtf.config.CoreConfig;
import wtf.config.StoneRegEntry;
import wtf.config.WTFStoneRegistry;
import wtf.config.ore.WTFOresNewConfig;

public class BlockstateWriter {

	static String dir = "resourcepacks"+File.separatorChar+"WTFExpedition"+File.separatorChar+"assets"+File.separatorChar+"wtfcore"+File.separatorChar+"blockstates";

	public static void writeResourcePack(){

		new File("resourcepacks"+File.separatorChar+"WTFExpedition").mkdir(); // creates a directory
		new File("resourcepacks"+File.separatorChar+"WTFExpedition"+File.separatorChar+"assets").mkdir();
		new File("resourcepacks"+File.separatorChar+"WTFExpedition"+File.separatorChar+"assets"+File.separatorChar+"wtfcore").mkdir();
		new File("resourcepacks"+File.separatorChar+"WTFExpedition"+File.separatorChar+"assets"+File.separatorChar+"wtfcore"+File.separatorChar+"blockstates").mkdir();

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter("resourcepacks"+File.separatorChar+"WTFExpedition"+File.separatorChar+"pack.mcmeta"));

			writer.write("{\"pack\": {\"pack_format\": 2,\"description\": \"Do Not Disable:WTF Expedition Blockstates\"}}\n");

			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeSpeleothemBlockstate(IBlockState backState, String regName){


		StoneRegEntry stone = WTFStoneRegistry.stoneReg.get(backState);

		try {
			String path = dir +File.separatorChar+ regName + ".json";
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));

			//some vanilla blocks don't follow the convention- so we implement an override here

			writer.write("{\"forge_marker\": 1,\n");
			writer.write("\"defaults\": {\"textures\": {\"texture\": \""+stone.textureLocation+"\"}},\n");
			writer.write("\"variants\": {\n");
			writer.write("\"normal\": [{}],\n");
			writer.write("\"inventory0\": [{\"model\": \"wtfcore:stalactite_small\" }],\n");
			writer.write("\"inventory1\": [{\"model\": \"wtfcore:stalactite_base\"}],\n");
			writer.write("\"inventory2\": [{\"model\": \"wtfcore:stalactite_tip\"}],\n");
			writer.write("\"inventory3\": [{\"model\": \"wtfcore:speleothem_column\"}],\n");
			writer.write("\"inventory4\": [{\"model\": \"wtfcore:stalagmite_small\"}],\n");
			writer.write("\"inventory5\": [{\"model\": \"wtfcore:stalagmite_base\"}],\n");
			writer.write("\"inventory6\": [{\"model\": \"wtfcore:stalagmite_tip\"}],\n");
			writer.write("\"type=stalactite_small\" : [{\"model\": \"wtfcore:stalactite_small\"},{\"model\": \"wtfcore:stalactite_small\", \"y\":90},{\"model\": \"wtfcore:stalactite_small\", \"y\":180},{\"model\": \"wtfcore:stalactite_small\", \"y\":270}],\n");
			writer.write("\"type=stalactite_base\" 	:[{\"model\": \"wtfcore:stalactite_base\"},{\"model\": \"wtfcore:stalactite_base\", \"y\":90},{\"model\": \"wtfcore:stalactite_base\", \"y\":180},{\"model\": \"wtfcore:stalactite_base\", \"y\":270}],\n");
			writer.write("\"type=stalactite_tip\" 	: [{\"model\": \"wtfcore:stalactite_tip\"},{\"model\": \"wtfcore:stalactite_tip\", \"y\":90},{\"model\": \"wtfcore:stalactite_tip\", \"y\":180},{\"model\": \"wtfcore:stalactite_tip\", \"y\":270}],\n");
			writer.write("\"type=column\" : [{\"model\": \"wtfcore:speleothem_column\"},{\"model\": \"wtfcore:speleothem_column\", \"y\":90},{\"model\": \"wtfcore:speleothem_column\", \"y\":180},{\"model\": \"wtfcore:speleothem_column\", \"y\":270}],\n");
			writer.write("\"type=stalagmite_small\" :[{\"model\": \"wtfcore:stalagmite_small\"},{\"model\": \"wtfcore:stalagmite_small\", \"y\":90},{\"model\": \"wtfcore:stalagmite_small\", \"y\":180},{\"model\": \"wtfcore:stalagmite_small\", \"y\":270}],\n");
			writer.write("\"type=stalagmite_base\"	: [{\"model\": \"wtfcore:stalagmite_base\"},{\"model\": \"wtfcore:stalagmite_base\", \"y\":90},{\"model\": \"wtfcore:stalagmite_base\", \"y\":180},{\"model\": \"wtfcore:stalagmite_base\", \"y\":270}],\n");
			writer.write("\"type=stalagmite_tip\"	: [{\"model\": \"wtfcore:stalagmite_tip\"},{\"model\": \"wtfcore:stalagmite_tip\", \"y\":90},{\"model\": \"wtfcore:stalagmite_tip\", \"y\":180},{\"model\": \"wtfcore:stalagmite_tip\", \"y\":270}]\n");
			writer.write("}}\n");
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		try {

			String path = dir + File.separatorChar + regName + "Frozen.json";
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));

			String background = backState.toString(); 
			background = background.replace("[", "#\n");
			background = background.replace("]", "\n");
			if (!background.contains("#")){
				background+="#normal";
			}

			writer.write("{\"forge_marker\": 1,\n");
			writer.write("\"defaults\": {\n");
			writer.write("\"model\": \"forge:multi-layer\",\n");
			writer.write("\"transform\": \"forge:default-block\",\n");
			writer.write("\"textures\": {\"texture\": \"blocks/stone\"}\n");
			writer.write("},\n");
			writer.write("\"variants\": {\n");
			writer.write("\"normal\": [{}],\n");
			writer.write("\"inventory0\": [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalactite_small\", \"Translucent\": \"ice#normal\" }}],\n");
			writer.write("\"inventory1\": [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalactite_base\", \"Translucent\": \"ice#normal\" }}],\n");
			writer.write(" \"inventory2\": [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalactite_tip\", \"Translucent\": \"ice#normal\" }}],\n");
			writer.write("\"inventory3\": [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=column\", \"Translucent\": \"ice#normal\" }}],\n");
			writer.write("\"inventory4\": [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalagmite_small\", \"Translucent\": \"ice#normal\" }}],\n");
			writer.write("\"inventory5\": [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalagmite_base\", \"Translucent\": \"ice#normal\" }}],\n");
			writer.write("\"inventory6\": [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalagmite_tip\", \"Translucent\": \"ice#normal\" }}],\n");
			writer.write("\"type=stalactite_small\" : [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalactite_small\", \"Translucent\": \"ice#normal\" }}],\n");
			writer.write("\"type=stalactite_base\" 	:[{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalactite_base\", \"Translucent\": \"ice#normal\" }}],\n");
			writer.write("\"type=stalactite_tip\" 	: [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalactite_tip\", \"Translucent\": \"ice#normal\" }}],\n");
			writer.write("\"type=column\" : [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=column\", \"Translucent\": \"ice#normal\" }}],\n");
			writer.write("\"type=stalagmite_small\" :[{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalagmite_small\", \"Translucent\": \"ice#normal\" }}],\n");
			writer.write("\"type=stalagmite_base\"	: [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalagmite_base\", \"Translucent\": \"ice#normal\" }}],\n");
			writer.write("\"type=stalagmite_tip\"	: [{\"custom\": { \"base\": \"ice#normal\", \"Solid\": \"wtfcore:"+regName+"#type=stalagmite_tip\", \"Translucent\": \"ice#normal\" }}]\n");
			writer.write("}}\n");

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
				throw new Exception("No stone registry entry found for "+ backState.getBlock().getRegistryName()+"@"+backState.getBlock().getMetaFromState(backState) + " please add it to the WTFStoneRegistry.cfg\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String path = dir + File.separatorChar + regName + ".json";

		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(path));

			if (CoreConfig.fancyBlockStates){
				try {
					FancyBlockstateWriter.writeDenseOreBlockstate(writer, backState, regName, orestring, entry);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				SimpleBlockstateWriter.writeDenseOreBlockstate(writer, backState, regName, orestring, entry);
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void writeDecoAnimBlockstate(IBlockState backState, String regName){

		String path = dir + File.separatorChar + regName + ".json";
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(path));

			StoneRegEntry entry =WTFStoneRegistry.stoneReg.get(backState); 

			if (CoreConfig.fancyBlockStates){
				try {
					FancyBlockstateWriter.writeDecoAnimBlockstate(writer, backState, regName, entry);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				SimpleBlockstateWriter.writeDecoAnimBlockstate(writer, backState, regName, entry);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void writeDecoStaticBlockstate(IBlockState backState, String regName){

		String path = dir + File.separatorChar + regName + ".json";
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(path));

			StoneRegEntry entry =WTFStoneRegistry.stoneReg.get(backState); 

			if (CoreConfig.fancyBlockStates){
				try {
					FancyBlockstateWriter.writeDecoStaticBlockstate(writer, backState, regName, entry);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				SimpleBlockstateWriter.writeDecoStaticBlockstate(writer, backState, regName, entry);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	
}
