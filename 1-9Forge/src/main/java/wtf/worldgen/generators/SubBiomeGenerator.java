package wtf.worldgen.generators;

import java.util.HashMap;
import java.util.Random;

import net.minecraft.world.World;
import wtf.utilities.simplex.SimplexHelper;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;
import wtf.worldgen.GeneratorMethods;
import wtf.worldgen.subbiomes.SubBiome;

public class SubBiomeGenerator {

	public static HashMap <Byte, SubBiome> subBiomeRegistry = new HashMap<Byte, SubBiome>();
	private static SimplexHelper simplex = new SimplexHelper("SubBiome");
	
	public void generate(World world, ChunkCoords chunkcoords, Random random, ChunkScan chunkscan, GeneratorMethods gen){

	
		byte[] newBiomes = chunkcoords.getChunk(world).getBiomeArray();

		for (int xloop = 0; xloop < 16 ; xloop++){
			for (int zloop = 0; zloop < 16; zloop++){
				int loop = xloop + zloop*16;
				SubBiome sub = subBiomeRegistry.get(newBiomes[loop]);
				//Biome biome = world.getBiome(chunkscan.surface[xloop][zloop]);

				//CaveProfile profile = CaveTypeRegister.getCaveProfile(biome);
				//profile.caveShallow.setTopBlock(gen, random, chunkscan.surface[xloop][zloop]);

				if (sub != null){
					double x = (xloop+chunkcoords.getWorldX())/sub.scale();
					double z = (zloop+chunkcoords.getWorldZ())/sub.scale();
					if (simplex.get2DNoise(world, x, z) < sub.freq()){
						newBiomes[loop] = sub.getID();
					}
				}
			}
		}
	}
}
