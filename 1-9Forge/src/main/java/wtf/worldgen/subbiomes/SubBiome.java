package wtf.worldgen.subbiomes;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import wtf.utilities.wrappers.ChunkScan;

public interface SubBiome {

	public void resetTopBlock(World world, BlockPos pos);
	
	public double scale();
	public double freq();
	public Biome getBiome();
	public byte getID();
	public Biome getParentBiome();
	

	public WorldGenerator getTree(ChunkScan chunkscan, Random random);

	
}
