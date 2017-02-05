package wtf.utilities.blockstatewriters;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import net.minecraft.block.state.IBlockState;
import wtf.config.MasterConfig;
import wtf.config.StoneRegEntry;
import wtf.config.WTFStoneRegistry;
import wtf.config.ore.WTFOresNewConfig;

public class FancyBlockstateWriter extends BlockstateWriter{

	public static void writeDenseOreBlockstate(BufferedWriter writer, IBlockState backState, String regName, String orestring, StoneRegEntry entry) throws IOException{

		String blockstateLocation = entry.blockstateLocation;

		writer.write("{\"forge_marker\": 1,\n");
		writer.write("\"defaults\": {\"model\": \"forge:multi-layer\",\"transform\": \"forge:default-block\"},\n");
		writer.write("\"variants\": {\n");
		writer.write("\"normal\": [{}],\n");
		writer.write(" \"inventory\": [{\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:"+regName+"#dense0\"}}],\n");
		writer.write("\"density\" : {\n");
		writer.write("\"0\" : {\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:"+regName+"#dense0\"} },\n");
		writer.write("\"1\" : {\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:"+regName+"#dense1\"} },\n");
		writer.write("\"2\" : {\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:"+regName+"#dense2\"} }\n");
		writer.write("},\n");
		for (int loop = 0; loop < 3; loop++){  
			writer.write("\"dense"+loop+"\": [\n");
			writer.write("{\"model\": \"minecraft:cube_all\", \"textures\": { \"all\": \"wtfcore:overlays/"+orestring+loop+"\" }},\n");
			writer.write("{\"model\": \"minecraft:cube_all\", \"y\":180, \"x\":180, \"textures\": { \"all\": \"wtfcore:overlays/"+orestring+loop+"\" }}\n");				
			if (!MasterConfig.rotate180only){
				writer.write(",\n");
				writer.write("{\"model\": \"minecraft:cube_all\", \"y\":90, \"x\":90, \"textures\": { \"all\": \"wtfcore:overlays/"+orestring+loop+"\" }},\n");
				writer.write("{\"model\": \"minecraft:cube_all\", \"y\":270, \"x\":270, \"textures\": { \"all\": \"wtfcore:overlays/"+orestring+loop+"\" }}\n");
			}
			writer.write("]\n");
			if (loop < 2){writer.write(",\n");}
		}
		writer.write("}}\n");
		writer.close();


	}

	public static void writeDecoAnimBlockstate(BufferedWriter writer, IBlockState backState, String regName, StoneRegEntry entry) throws IOException{

		String blockstateLocation = entry.blockstateLocation;

		writer.write("{\"forge_marker\": 1,\n");
		writer.write("\"defaults\": {\n");
		writer.write("\"model\": \"forge:multi-layer\",\n");
		writer.write("\"transform\": \"forge:default-block\"},\n");
		writer.write("\"variants\": {\n");
		writer.write("\"inventory0\": [{\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:base#lava\" }}],\n");
		writer.write("\"inventory1\": [{\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\" }}],\n");
		writer.write("\"inventory2\": [{\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\" }}],\n");
		writer.write("\"type\" : {\n");
		writer.write("\"lava_crust\": {\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:base#lava\" }},\n");
		writer.write("\"drip_water\": {\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\"}},\n");
		writer.write("\"drip_lava\": {\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\"}}\n");
		writer.write("},\n");
		writer.write("\"fast\" : {\"true\" : {},\"false\" : {}\n");
		writer.write("}}}\n");

		writer.close();

	}

	public static void writeDecoStaticBlockstate(BufferedWriter writer, IBlockState backState, String regName, StoneRegEntry entry) throws IOException{

		String blockstateLocation = entry.blockstateLocation;

		writer.write("{\"forge_marker\": 1,\n\n");
		writer.write("\"defaults\": {\n\n");
		writer.write("\"model\": \"forge:multi-layer\",\n\n");
		writer.write("\"transform\": \"forge:default-block\"},\n");
		writer.write("\"variants\": {\n");
		writer.write("\"inventory0\": [{\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:base#moss\" }}],\n");
		writer.write("\"inventory1\": [{\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:base#soul\" }}],\n");
		writer.write("\"inventory2\": [{\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:base#cracked\" }}],\n");
		writer.write("\"type\" : {\n");
		writer.write("\"moss\": {\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:base#moss\" }},\n");
		writer.write("\"soul\": {\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:base#soul\" }},\n");
		writer.write("\"cracked\": {\"custom\": { \"base\": \""+blockstateLocation+"\", \"Solid\": \""+blockstateLocation+"\", \"Translucent\": \"wtfcore:base#cracked\" }}\n");
		writer.write("}\n");
		writer.write("}\n");
		writer.write("}\n");
		writer.close();
	}


}
