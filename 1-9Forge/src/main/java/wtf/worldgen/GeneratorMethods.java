package wtf.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import wtf.Core;
import wtf.blocks.BlockDenseOre;
import wtf.blocks.BlockDenseOreFalling;
import wtf.blocks.BlockRoots;
import wtf.blocks.BlockSpeleothem;
import wtf.blocks.BlockIcicle.IcicleType;
import wtf.blocks.BlockRoots.RootType;
import wtf.blocks.BlockSpeleothem.SpType;
import wtf.config.CaveBiomesConfig;
import wtf.init.BlockSets;
import wtf.init.WTFBlocks;
import wtf.utilities.wrappers.BlockMap;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.StateAndModifier;
import wtf.utilities.wrappers.StoneAndOre;
import wtf.worldgen.generators.queuedgen.QMobSpawner;
import wtf.worldgen.generators.queuedgen.QModify;
import wtf.worldgen.generators.queuedgen.QOreGen;
import wtf.worldgen.generators.queuedgen.QReplace;
import wtf.worldgen.generators.queuedgen.QReplaceNoCheck;
import wtf.worldgen.generators.queuedgen.QTreeReplace;


public class GeneratorMethods{

	public final Chunk chunk;
	public final BlockMap blockmap;
	public final Random random;

	public GeneratorMethods(World world, ChunkCoords coords, Random random) {
		chunk = coords.getChunk(world);
		blockmap = new BlockMap(world, coords);
		this.random = random;
	}

	public World getWorld(){
		return chunk.getWorld();
	}

	/**
	 **Used to set a block, based on a modifier and the block at the given location
	 **/
	public boolean transformBlock(BlockPos pos, BlockSets.Modifier modifier){
		return blockmap.add(pos, new QModify(modifier));
	}
	/**
	 **Used to replace a block.  Checks that the block is replaceable first
	 **/
	public boolean replaceBlock(BlockPos pos, IBlockState state){
		return blockmap.add(pos, new QReplace(state));
	}
	/**
	 **Used to replace a block without checking that the block is replaceable first
	 **/
	
	public boolean overrideBlock(BlockPos pos, IBlockState state){
		return blockmap.add(pos, new QReplaceNoCheck(state));
	}
	

	public boolean setOreBlock(BlockPos pos, IBlockState oreState, int density){
		return blockmap.add(pos, new QOreGen(oreState, density));
	}

	/**
	 **Used to set a block floor y+1, based on a modifier and the block at the given location.  e.g. cobblestone boulders on top of stone
	 **/
	public boolean setFloorAddon(BlockPos pos, BlockSets.Modifier modifier){
		IBlockState oldState = getWorld().getBlockState(pos.down());
		IBlockState newState = BlockSets.blockTransformer.get(new StateAndModifier(oldState, modifier));

		if (newState != null && !blockmap.posQueued(pos.down())){
			return replaceBlock(pos, newState);

		}
		return false;

	}
	public boolean setCeilingAddon(BlockPos pos, BlockSets.Modifier modifier){
		IBlockState oldState = getWorld().getBlockState(pos.up());
		IBlockState newState = BlockSets.blockTransformer.get(new StateAndModifier(oldState, modifier));
		if (newState != null && !blockmap.posQueued(pos.up())){
			return replaceBlock(pos, newState);
		}
		return false;

	}


	/**
	 **Used to set an ice patch above the given block, checks that it's a normal block first
	 **/
	public void setIcePatch(BlockPos pos){

		int hash = getWorld().getBlockState(pos).getMaterial().hashCode();
		
		if (isAir(pos.up())  && hash != Material.SNOW.hashCode() && hash != Material.ICE.hashCode() && hash != Material.PACKED_ICE.hashCode() && hash != Material.LAVA.hashCode()){
			replaceBlock(pos.up(), WTFBlocks.icePatch.getDefaultState());
		}
	}
	public void setSnowPatch(BlockPos pos){

		int hash = getWorld().getBlockState(pos).getMaterial().hashCode();
		
		if (isAir(pos.up())  && hash != Material.SNOW.hashCode() && hash != Material.ICE.hashCode() && hash != Material.PACKED_ICE.hashCode() && hash != Material.LAVA.hashCode()){
			replaceBlock(pos.up(), Blocks.SNOW_LAYER.getDefaultState());
		}
	}


	public void setWaterPatch(BlockPos pos){
		if (CaveBiomesConfig.enablePuddles && getWorld().getBlockState(pos.up()).getMaterial().isSolid()){
			replaceBlock(pos.up(), WTFBlocks.puddle.getDefaultState());
		}
	}

	public void genFloatingStone(BlockPos pos){
		//When implementing non-vanilla stone, this method needs to call the UBifier
		replaceBlock(pos, Blocks.STONE.getDefaultState());
	}


	public boolean genSpeleothem(BlockPos pos, int size, float depth, boolean frozen){

		if (blockmap.posQueued(pos.up()) || blockmap.posQueued(pos) || blockmap.posQueued(pos.down())){ 
			return false;
		}
		if (frozen && depth > 0.9){
			genIcicle(pos);
			return true;
		}

		IBlockState above = getWorld().getBlockState(pos.up());
		IBlockState below = getWorld().getBlockState(pos.down());
		int remaining = size;
		int direction;
		BlockSpeleothem speleothem;// = WTFBlocks.speleothemMap.get(oldState);
		
		if (isAir(pos.up()) && !isAir(pos.down())){
			direction = 1;
			speleothem = WTFBlocks.speleothemMap.get(below);
		}
		else if (!isAir(pos.up()) && isAir(pos.down())){
			direction = -1;
			speleothem = WTFBlocks.speleothemMap.get(above);
		}
		else {
			return false;
		}

		if (speleothem == null){
			if (direction == -1){
				if (above.getBlock().hashCode() == Blocks.DIRT.hashCode() && depth > 0.7){
					genRoot(pos);
					return true;
				}
				else if (frozen || above.getMaterial() == Material.ICE || above.getMaterial() == Material.PACKED_ICE){
					genIcicle(pos);
					return true;
				}
				//hanging glow shrooms
			}

			return false;	
		}

		if (frozen){
			speleothem = speleothem.frozen;
		}

		while (remaining > 0){
			IBlockState next = getWorld().getBlockState(pos.up(direction));
			boolean nextQueued = blockmap.posQueued(pos.up(direction));
			IBlockState set = null;

			if (!nextQueued && remaining == size){//first block
				if (isAir(pos.up(direction))){
					if (size > 1){
						set = direction == 1 ? speleothem.getBlockState(SpType.stalagmite_base) : speleothem.getBlockState(BlockSpeleothem.SpType.stalactite_base);
					}
					else {
						set = direction == 1 ? speleothem.getBlockState(SpType.stalagmite_small) : speleothem.getBlockState(BlockSpeleothem.SpType.stalactite_small);
					}
				}
				else {
					return false;//cave size = 1, generate nothing 
				}
			}
			else if (!nextQueued && remaining > 1){ //middle block
				if (isAir(pos.up(direction))){
					set = speleothem.getBlockState(SpType.column); 
				}
				else if (next.hashCode() == speleothem.parentBackground.hashCode()){
					set = direction == 1 ? speleothem.getBlockState(SpType.stalactite_base) : speleothem.getBlockState(BlockSpeleothem.SpType.stalagmite_base);
					remaining = 0;
				}
				else {
					set = direction == 1 ? speleothem.getBlockState(SpType.stalagmite_tip) : speleothem.getBlockState(BlockSpeleothem.SpType.stalactite_tip);
				}
			}
			else { //last block
				set = direction == 1 ? speleothem.getBlockState(SpType.stalagmite_tip) : speleothem.getBlockState(BlockSpeleothem.SpType.stalactite_tip);
			}


			replaceBlock(pos, set);
			pos = pos.up(direction);
			remaining --;
		}
		return true;
	}


	/**
	 **Generates an icicle hanging from the ceiling
	 **/
	public  void genIcicle (BlockPos pos){
			if (random.nextBoolean() && isAir(pos.down())){
				replaceBlock(pos, WTFBlocks.icicle.getBlockState(IcicleType.icicle_base));
				replaceBlock(pos.down(),  WTFBlocks.icicle.getBlockState(IcicleType.icicle_tip));
			}
			else {
				replaceBlock(pos, WTFBlocks.icicle.getBlockState(IcicleType.icicle_small));
			}
		
	}


	/**
	 **Generates vines hanging from the ceiling
	 * @return 
	 **/
	public void GenVines(BlockPos pos, EnumFacing facing)
	{
		if (!isAir(pos)){
			return;
		}
		IBlockState block = null;
		switch(facing){
		case EAST:
			block = Blocks.VINE.getDefaultState().withProperty(BlockVine.EAST,  true);
			break;
		case NORTH:
			block = Blocks.VINE.getDefaultState().withProperty(BlockVine.NORTH,  true);
			break;
		case SOUTH:
			block = Blocks.VINE.getDefaultState().withProperty(BlockVine.SOUTH,  true);
			break;
		case WEST:
			block = Blocks.VINE.getDefaultState().withProperty(BlockVine.WEST,  true);
			break;
		}
		replaceBlock(pos, block);

	}

	public void genRoot(BlockPos pos){
		Biome biome = chunk.getBiome(pos, chunk.getWorld().getBiomeProvider());
		if (BiomeDictionary.isBiomeOfType(biome, Type.CONIFEROUS)){
			replaceBlock(pos, WTFBlocks.roots.getDefaultState().withProperty(BlockRoots.TYPE, RootType.spruce));
		}
		else if (BiomeDictionary.isBiomeOfType(biome, Type.SAVANNA)){
			replaceBlock(pos, WTFBlocks.roots.getDefaultState().withProperty(BlockRoots.TYPE, RootType.acacia));
		}
		else if (BiomeDictionary.isBiomeOfType(biome, Type.JUNGLE)){
			replaceBlock(pos, WTFBlocks.roots.getDefaultState().withProperty(BlockRoots.TYPE, RootType.jungle));
		}
		else if (biome.getBiomeName().contains("ark")){
			replaceBlock(pos, WTFBlocks.roots.getDefaultState().withProperty(BlockRoots.TYPE, RootType.big_oak));
		}
		else if (biome.getBiomeName().contains("irch")){
			replaceBlock(pos, WTFBlocks.roots.getDefaultState().withProperty(BlockRoots.TYPE, RootType.birch));
		}
		else {
			replaceBlock(pos, WTFBlocks.roots.getDefaultState());
		}
	}


	/**
	 **Checks if spawners are enabled, and then generates a mob spawner
	 **/
	public void spawnVanillaSpawner(BlockPos pos, String entityName, int count){
		blockmap.add(pos, new QMobSpawner(this.getWorld(), pos, entityName, count));
	}

	private boolean isAir(BlockPos pos){
		return getWorld().isAirBlock(pos) && !blockmap.posQueued(pos);
	}

	public boolean setTreeBlock(BlockPos pos, IBlockState state) {
		return blockmap.add(pos, new QTreeReplace(pos, state));
	}
	
}
