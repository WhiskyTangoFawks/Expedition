package wtf.worldgen.subbiomes;

import java.util.Random;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.gen.feature.WorldGenerator;
import wtf.config.OverworldGenConfig;
import wtf.utilities.wrappers.ChunkScan;
import wtf.worldgen.trees.WorldGenCustomTree;
import wtf.worldgen.trees.types.PoplarTree;

public class SubBiomeMeadow extends BiomePlains implements SubBiome{

	Random random = new Random();
	
	public SubBiomeMeadow(BiomeProperties properties) {
		super(false, properties);
		this.theBiomeDecorator.treesPerChunk = 6;
	}

	@Override
	public void resetTopBlock(World world, BlockPos pos) {
		if (random.nextFloat() < 0.05){
			 world.playEvent(2005, pos, 0);
		}
		
	}

	@Override
	public double scale() {
		// TODO Auto-generated method stub
		return OverworldGenConfig.meadowSize/100;
	}

	@Override
	public double freq() {
		// TODO Auto-generated method stub
		return OverworldGenConfig.meadowPercent/100;
	}

	@Override
	public Biome getBiome() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public byte getID() {
		// TODO Auto-generated method stub
		return (byte) OverworldGenConfig.meadowID;
	}

	@Override
	public Biome getParentBiome() {
		// TODO Auto-generated method stub
		return Biomes.PLAINS;
	}

	WorldGenerator poplar = null;
	@Override
	public WorldGenerator getTree(ChunkScan chunkscan, boolean doReplace, Random random) {
		
		if (poplar == null){
			poplar = new WorldGenCustomTree(chunkscan, new PoplarTree(chunkscan.world));
		}
		return poplar;
	}
	



}
