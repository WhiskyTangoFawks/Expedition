package wtf.worldgen.generators;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import wtf.config.OverworldGenConfig;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.simplex.SimplexHelper;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;
import wtf.utilities.wrappers.SurfacePos;
import wtf.worldgen.GeneratorMethods;

public class SurfaceGenerator {

	private static SimplexHelper simplex = new SimplexHelper("SurfaceGenerator");
	
	public void generate(World world, ChunkCoords chunkcoords, Random random, ChunkScan chunkscan, GeneratorMethods gen){

		for (SurfacePos[] posArray : chunkscan.surface){
			for (SurfacePos pos : posArray){
				Biome biome = world.getBiome(pos);
				if (BiomeDictionary.isBiomeOfType(biome, Type.RIVER)){
					if (simplex.get2DNoise(world, pos.getX()*16, pos.getZ()*16) > OverworldGenConfig.riverFracChunkPercent &&
							simplex.get3DNoise(world, pos) > OverworldGenConfig.riverFracFreq){
						gen.transformBlock(pos, Modifier.COBBLE);
					}
				}
				else if (BiomeDictionary.isBiomeOfType(biome, Type.MOUNTAIN)){
					if (simplex.get2DNoise(world, pos.getX()*16, pos.getZ()*16) > OverworldGenConfig.mountainFracChunkPercent &&
							simplex.get3DNoise(world, pos) > OverworldGenConfig.mountainFracFreq){
						gen.transformBlock(pos, Modifier.COBBLE);
					}
				}
				else if (BiomeDictionary.isBiomeOfType(biome, Type.FOREST )){
					if (simplex.get2DNoise(world, pos.getX()*16, pos.getZ()*16) > OverworldGenConfig.forestMossChunkPercent &&
							simplex.get3DNoise(world, pos) > OverworldGenConfig.ForestMossFreq){
						gen.transformBlock(pos, Modifier.MOSSY);
					}
				}
				
				
			}
		}
	}
}
