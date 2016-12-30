package wtf.utilities.blockstatewriters;

import java.io.BufferedWriter;
import java.io.IOException;

import net.minecraft.block.state.IBlockState;
import wtf.config.StoneRegEntry;
import wtf.config.WTFStoneRegistry;

public class SimpleBlockstateWriter extends BlockstateWriter{

	public static void writeDenseOreBlockstate(BufferedWriter writer, IBlockState backState, String regName, String orestring, StoneRegEntry entry) throws IOException{

		writer.write("{\"forge_marker\": 1,\n");
		writer.write("\"defaults\": {\n");
		writer.write("\"model\": \"wtfcore:cube_overlay\",\n");
		writer.write("\"textures\": {\"all\": \""+entry.textureLocation+"\"}\n");
		writer.write("},\n");
		writer.write("\"variants\": {\n");
		writer.write("\"normal\": [{}],\n");
		writer.write("\"inventory\": [{\"textures\" : { \"mask\" : \"wtfcore:overlays/"+orestring+"0\"}}],\n");
		writer.write("\"density\" : {\n");
		writer.write("\"0\" : { \"textures\" : { \"mask\" : \"wtfcore:overlays/"+orestring+"0\"}},\n");
		writer.write("\"1\" : { \"textures\" : { \"mask\" : \"wtfcore:overlays/"+orestring+"1\"}},\n");
		writer.write("\"2\" : { \"textures\" : { \"mask\" : \"wtfcore:overlays/"+orestring+"2\"}}\n");
		writer.write("}}}\n");

		writer.close();

	}

	public static void writeDecoAnimBlockstate(BufferedWriter writer, IBlockState backState, String regName, StoneRegEntry entry) throws IOException{

		writer.write("{\"forge_marker\": 1,\n");
		writer.write("\"defaults\": {\n");
		writer.write("\"model\": \"wtfcore:cube_overlay\",\n");
		writer.write("\"textures\": {\"all\": \""+entry.textureLocation+"\"}\n");
		writer.write("},\n");
		writer.write("\"variants\": {\n");
		writer.write("\"normal\": [{}],\n");
		writer.write("\"inventory0\": [{\"textures\" : { \"mask\" : \"wtfcore:blocks/lava_overlay\"}}],\n");
		writer.write("\"inventory1\": [{\"model\": \"cube_all\"}],\n");
		writer.write("\"inventory2\": [{\"model\": \"cube_all\"}],\n");
		writer.write("\"type\" : {\n");
		writer.write("\"lava_crust\" : { \"textures\" : { \"mask\" : \"wtfcore:blocks/lava_overlay\"}},\n");
		writer.write("\"drip_water\" : {\"model\": \"cube_all\"},\n");
		writer.write("\"drip_lava\" : {\"model\": \"cube_all\"}},\n");
		writer.write("\"fast\" : {\"true\" : {},\"false\" : {}}\n");
		writer.write("}}\n");

		writer.close();

	}
	public static void writeDecoStaticBlockstate(BufferedWriter writer, IBlockState backState, String regName, StoneRegEntry entry) throws IOException{


		writer.write("{\"forge_marker\": 1,\n");
		writer.write("\"defaults\": {\n");
		writer.write("\"model\": \"wtfcore:cube_overlay\",\n");    
		writer.write("\"textures\": {\"all\": \""+entry.textureLocation+"\"}\n");
		writer.write("},\n");
		writer.write("\"variants\": {\n");
		writer.write("\"normal\": [{}],\n");
		writer.write("\"inventory0\": [{\"textures\" : { \"mask\" : \"wtfcore:blocks/moss_overlay\"}}],\n");
		writer.write("\"inventory1\": [{\"textures\" : { \"mask\" : \"wtfcore:blocks/soul_overlay\"}}],\n");
		writer.write("\"inventory2\": [{\"textures\" : { \"mask\" : \"wtfcore:overlays/destroy_stage_2\"}}],\n");
		writer.write("\"type\" : {\n");
		writer.write("\"moss\" : { \"textures\" : { \"mask\" : \"wtfcore:blocks/moss_overlay\"}},\n");
		writer.write("\"soul\" : { \"textures\" : { \"mask\" : \"wtfcore:blocks/soul_overlay\"}},\n");
		writer.write("\"cracked\" : { \"textures\" : { \"mask\" : \"wtfcore:overlays/destroy_stage_2\"}}\n");
		writer.write("}}}\n");
		writer.close();

	}

}
