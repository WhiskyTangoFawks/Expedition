package wtf.worldgen.subbiomes;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeSwamp;
import net.minecraft.world.gen.feature.WorldGenerator;
import wtf.config.OverworldGenConfig;
import wtf.utilities.wrappers.ChunkScan;
import wtf.worldgen.trees.WorldGenCustomTree;
import wtf.worldgen.trees.types.Mangrove;

public class SubBiomeMangroveSwamp extends BiomeSwamp implements SubBiome{

	private final Biome parentBiome;
	
	
	
	public SubBiomeMangroveSwamp(BiomeProperties properties, Biome parentBiome) {
		super(properties);
		this.parentBiome = parentBiome;
		this.theBiomeDecorator.treesPerChunk = 10;
	}
	


	@Override
	public void resetTopBlock(World world, BlockPos pos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double scale() {
		// TODO Auto-generated method stub
		return OverworldGenConfig.mangroveSize;
	}



	@Override
	public double freq() {
		// TODO Auto-generated method stub
		return (double)OverworldGenConfig.mangrovePercent/100;
	}



	@Override
	public Biome getBiome() {
		// TODO Auto-generated method stub
		return this;
	}
	
	private byte ID = -1;
	@Override
	public byte getID() {
		if (ID == -1){
			ID = (byte) Biome.getIdForBiome(this); 
		}
		return ID; 
	}

	@Override
	public Biome getParentBiome() {
		return this.parentBiome;
	}

	WorldGenCustomTree mangrove = null;
	@Override
	public WorldGenerator getTree(ChunkScan chunkscan, boolean doReplace, Random random) {
		
		if (chunkscan.getWaterList().size() > 64){
			if (mangrove == null){
				mangrove = new WorldGenCustomTree(chunkscan, new Mangrove(chunkscan.world));
			}
			return mangrove;
		}
		else {
			return this.genBigTreeChance(random);
		}
	}


}
