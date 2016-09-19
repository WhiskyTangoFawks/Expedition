package wtf.worldgen.subbiomes;

import java.util.Random;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.gen.feature.WorldGenerator;
import wtf.config.OverworldGenConfig;
import wtf.utilities.wrappers.ChunkScan;
import wtf.worldgen.trees.TreeVars;
import wtf.worldgen.trees.WorldGenCustomTree;
import wtf.worldgen.trees.types.JungleGiant;
import wtf.worldgen.trees.types.JungleTree;

public class SubBiomeDenseJungle extends BiomeJungle implements SubBiome{

	final Biome parentbiome;
	
	public SubBiomeDenseJungle(BiomeProperties properties, Biome parentbiome) {
		super(false, properties);
		this.parentbiome = parentbiome;
		this.theBiomeDecorator.treesPerChunk = 16;
	}

	@Override
	public void resetTopBlock(World world, BlockPos pos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double scale() {
		return OverworldGenConfig.denseJungleSize;
	}

	@Override
	public double freq() {
		return (double)OverworldGenConfig.denseJunglePercent/100;
	}

	@Override
	public Biome getBiome() {
		return this;
	}

	@Override
	public byte getID() {
		return (byte)OverworldGenConfig.denseJungleID;
	}

	@Override
	public Biome getParentBiome() {
		return Biomes.JUNGLE;
	}

	@Override
	public WorldGenerator getTree(ChunkScan chunkscan, boolean doReplace, Random random) {
		// TODO Auto-generated method stub
		return this.parentbiome.genBigTreeChance(random);
	}

	




}
