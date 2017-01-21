package wtf.ores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import wtf.config.ore.WTFOresNewConfig;
import wtf.utilities.simplex.SimplexHelper;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;

public abstract class OreGenAbstract{
	
	public final IBlockState oreBlock;
	
	//public String textureName;
	public HashMap<BiomeDictionary.Type, Float> biomeModifier = new HashMap<BiomeDictionary.Type, Float>(); 
	public HashSet<Integer> dimension = new HashSet<Integer>();
	
	public float maxGenRangeHeight;
	public float minGenRangeHeight;
	public int maxPerChunk;
	public int minPerChunk;
	public Float veinDensity = 1F;
	private final SimplexHelper simplex;
	private int seed = 0;
	public boolean genDenseOres;
	public final ArrayList<BiomeDictionary.Type> reqBiomeTypes = new ArrayList<BiomeDictionary.Type>();
		

	
	public OreGenAbstract(IBlockState blockstate, int[]genRange, int[] minmaxPerChunk, boolean denseGen){
		this.oreBlock = blockstate;
		this.maxGenRangeHeight = genRange[1]/100F;
		this.minGenRangeHeight = genRange[0]/100F;
		this.maxPerChunk = minmaxPerChunk[1];
		this.minPerChunk = minmaxPerChunk[0];
		genDenseOres = denseGen;
		simplex = new SimplexHelper(blockstate.toString());
	}
	

	public final void generate(World world, ChunkDividedOreMap map, Random random, ChunkCoords coords, ChunkScan chunkscan) throws Exception{
		if (this.dimension.contains(world.provider.getDimension())){
			Biome biome = world.getBiomeForCoordsBody(new BlockPos(coords.getWorldX(), 100, coords.getWorldZ()));
			if (reqBiomeTypes.size() > 0){
				for (BiomeDictionary.Type type : reqBiomeTypes){
					if (!BiomeDictionary.isBiomeOfType(biome, type)){
						//System.out.println("biome regected for ore spawn" + this.oreBlock.getBlock().getLocalizedName());
						return;
					}
				}
			}
			
			
			doOreGen(world, map, random, coords, chunkscan);
		}
	}
	
	public abstract void doOreGen(World world, ChunkDividedOreMap map,Random random, ChunkCoords coords, ChunkScan chunkscan) throws Exception;
	
	public abstract int genVein(World world, ChunkDividedOreMap map, Random random,	ChunkScan scan, BlockPos pos) throws Exception;
	
	public abstract int blocksReq();
	
	protected int getBlocksPerChunk(World world, ChunkCoords coords, Random random, double surfaceAvg){
				
		int genNum = WTFOresNewConfig.simplexGen ? (int) getSimplexOres(world, coords.getWorldX(), coords.getWorldZ()) : (int)(random.nextFloat()*(maxPerChunk-minPerChunk)+minPerChunk);
		
		
		Type[] biomeTypes = BiomeDictionary.getTypesForBiome(world.getBiome(new BlockPos(coords.getWorldX()+8, surfaceAvg, coords.getWorldZ()+8)));
		for (Type biome : biomeTypes){
			if (biomeModifier.containsKey(biome)){
				genNum+= (minPerChunk+(maxPerChunk-minPerChunk)/2) * biomeModifier.get(biome);
			}
		}

		return (int) (genNum*(float)surfaceAvg/world.getSeaLevel());
		
	}

	public int getGenStartHeight(double surfaceAvg, Random random) {
		
		int maxHeight = MathHelper.floor_float((float) (maxGenRangeHeight*surfaceAvg));
		int minHeight = MathHelper.floor_float((float) (minGenRangeHeight*surfaceAvg));
		
		int range = maxHeight-minHeight;
		if (range < 1){
			range = 1;
		}
		
		return random.nextInt(range)+minHeight;
	}

	public int getDensityToSet(Random random, double height, double surfaceAvg){
		
		//0 is a full ore, and 2 is a light ore
		double depth = height/(surfaceAvg*maxGenRangeHeight);
		double rand = random.nextFloat()+random.nextFloat()-1;
		
		double density = depth*3 + rand;
		
		if (density < 1){ return 0;}
		else if (density > 2){ return 2;}
		return 1;
	}

	/**
	 * This sets the density for the vein- it does not affect individual block density when using WTFOres dense ores function
	 * @param density
	 * @return 
	 */
	public OreGenAbstract setVeinDensity(float density){
		this.veinDensity = density;
		return this;
	}
	
	
	public double getSimplexOres(World world, double x, double z){
		double range = (this.maxPerChunk-this.minPerChunk);
		
		return simplex.get2DNoise(world, x/8, z/8)*range+ this.minPerChunk;
	}
}

