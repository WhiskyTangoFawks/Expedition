package wtf.worldgen.caves;

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
import wtf.blocks.BlockRoots;
import wtf.blocks.BlockSpeleothem;
import wtf.blocks.BlockIcicle.IcicleType;
import wtf.blocks.BlockRoots.RootType;
import wtf.blocks.BlockSpeleothem.SpType;
import wtf.init.BlockSets;
import wtf.init.WTFBlocks;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkDividedHashMap;
import wtf.utilities.wrappers.StateAndModifier;


public class CaveBiomeGenMethods{

	public final Chunk chunk;
	public final ChunkDividedHashMap blocksToSet;
	public final Random random;

	public CaveBiomeGenMethods(World world, ChunkCoords coords, Random random) {
		chunk = coords.getChunk(world);
		blocksToSet = new ChunkDividedHashMap(world, coords);
		this.random = random;
	}

	/**
	 * Used to get a block in the world.  Checks the hashmap of blocks to be set
	 */
	public IBlockState getBlockState(BlockPos pos){
		//first it checks if the hashmap already has a block to be set in it
		IBlockState returnable = blocksToSet.get(pos); //so if there's nothing in the hashmap for the pos, =null
		if (returnable != null){
			return returnable;
		}
		
		//if it doesn't, then it goes to get the block in the world
		returnable = chunk.getBlockState(pos);
		int y = pos.getY() >> 4;
		if (y < chunk.getBlockStorageArray().length){
			ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[y];

			if (extendedblockstorage != Chunk.NULL_BLOCK_STORAGE)
			{
				returnable = extendedblockstorage.get(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15);
				if (returnable == null){
					returnable = Blocks.AIR.getDefaultState();
				}
			}
		}
		//System.out.println("Returnable = " + returnable.getBlock().getLocalizedName());

		return returnable;
	}

	/**
	 **Used to set a block, based on a modifier and the block at the given location
	 **/
	public boolean transformBlock(BlockPos pos, BlockSets.Modifier modifier){
		IBlockState oldBlock = getBlockState(pos);
		IBlockState blockToSet = BlockSets.blockTransformer.get(new StateAndModifier(oldBlock, modifier));
		if (blockToSet != null){
			blocksToSet.put(pos, blockToSet);
			return true;
		}
		else {
			//WTFCore.log.info("No block found for " + modifier);
			return false;
		}
	}
	/**
	 **Used to replace a block.  Checks that the block is replaceable first
	 **/
	public boolean replaceBlock(BlockPos pos, IBlockState state){
		
		if (BlockSets.ReplaceHashset.contains(getBlockState(pos).getBlock())){
			blocksToSet.put(pos, state);
			return true;
		}
		return false;
	}
	/**
	 **Used to replace a block without checking that the block is replaceable first
	 **/
	public boolean overrideBlock(BlockPos pos, IBlockState state){
		
		if (BlockSets.ReplaceHashset.contains(getBlockState(pos).getBlock())){
			blocksToSet.put(pos, state);
			return true;
		}
		return false;
	}
	/**
	 **Used to set a block floor y+1, based on a modifier and the block at the given location.  e.g. cobblestone boulders on top of stone
	 **/
	public boolean setFloorAddon(BlockPos pos, BlockSets.Modifier modifier){
		IBlockState oldState = getBlockState(pos.down());
		IBlockState newState = BlockSets.blockTransformer.get(new StateAndModifier(oldState, modifier));
		if (newState != null){
			blocksToSet.put(pos, newState);
			return true;
		}
			return false;
		
	}
	public boolean setCeilingAddon(BlockPos pos, BlockSets.Modifier modifier){
		IBlockState oldState = getBlockState(pos.up());
		IBlockState newState = BlockSets.blockTransformer.get(new StateAndModifier(oldState, modifier));
		if (newState != null){
			blocksToSet.put(pos, newState);
			return true;
		}
		return false;
	}


	/**
	 **Used to set an ice patch above the given block, checks that it's a normal block first
	 **/
	public void setIcePatch(BlockPos pos){
		
		int hash = getBlockState(pos).getMaterial().hashCode();
		if (getBlockState(pos.up()).getBlock().hashCode() == airHash  && hash != Material.SNOW.hashCode() && hash != Material.ICE.hashCode() && hash != Material.PACKED_ICE.hashCode()){
			blocksToSet.put(pos.up(), WTFBlocks.icePatch.getDefaultState());
		}
	}
	
	public void setWaterPatch(BlockPos pos){
		
		if (getBlockState(pos.up()).getBlock().hashCode() == airHash){
			blocksToSet.put(pos.up(), WTFBlocks.waterPatch.getDefaultState());
		}
	}
	public void setLavaPatch(BlockPos pos){
		
		if (BlockSets.ReplaceHashset.contains(getBlockState(pos).getBlock()) && getBlockState(pos.up()).getBlock().hashCode() == airHash){
			//System.out.println("Setting lava patch");
			blocksToSet.put(pos.up(), WTFBlocks.lavaPatch.getDefaultState());
		}
	}

	public void genFloatingStone(World world, BlockPos pos, Block block){
		//When implementing non-vanilla stone, this method needs to call the UBifier
		blocksToSet.put(pos, block.getDefaultState());
	}

	/**
	 * Generates a Stalagmite, starting at the given positoion
	 * Should be called at floor +1 (so that the block pos sent to it is air)
	 * @param world
	 * @param pos
	 * @param size
	 * @return
	 */
	public boolean genStalagmite(BlockPos pos, float depth, boolean frozen){
		
		if (chunk.getBlockState(pos).getBlock().hashCode() != airHash){ return false;}
		IBlockState oldState= getBlockState(pos.down());
		BlockSpeleothem block = WTFBlocks.speleothemMap.get(oldState);
		
		if (block == null){
			//no speleothem found for block
			return false;
		}
		if (frozen){
			block = block.frozen;
		}
		int size = MathHelper.ceiling_float_int(random.nextFloat() + 1-depth);
		//block.genStalagmite(blocksToSet, pos, genSize, frozen);

		int up1Hash;

		for (int i = 0; i < size; i++){
			up1Hash = getBlockState(pos.up(i)).getBlock().hashCode();
			
			if (i==0){ 
				if (size>1 && up1Hash == airHash) {

					blocksToSet.put(pos.up(i), block.getBlockState(SpType.stalagmite_base));
				}
				else {

					blocksToSet.put(pos.up(i), block.getBlockState(SpType.stalagmite_small));
					break;
				}
			}
			else if (i<size-1 && up1Hash == Blocks.AIR.hashCode()){
				blocksToSet.put(pos.up(i), block.getBlockState(SpType.column));
			}
			else{
				if (up1Hash == block.parentBackground.getBlock().hashCode()){
					blocksToSet.put(pos.up(i), block.getBlockState(SpType.stalactite_base));
					break;
				}

				else{
					blocksToSet.put(pos.up(i), block.getBlockState(SpType.stalagmite_tip));
					break;
				}
			}
		}
		return true;
	}

	int darkStateHash = Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.DARK_OAK).hashCode();
	
	public boolean genStalactite(BlockPos pos, float depth, boolean frozen){
		if (chunk.getBlockState(pos).getBlock().hashCode() != airHash){ return false;}
		IBlockState oldState= getBlockState(pos.up());
		BlockSpeleothem block = WTFBlocks.speleothemMap.get(oldState);
		if (block == null){
			
			if (oldState.getBlock().hashCode() == Blocks.DIRT.hashCode() && depth > 0.8){
				genRoot(pos);
			}
			//no speleothem found for block
			return false;
		}

		if (frozen){
			block = block.frozen;
		}
		
		int size = MathHelper.ceiling_float_int(random.nextFloat() + 1-depth);

		int down1Hash;

		for (int i = 0; i < size; i++){
			down1Hash = getBlockState(pos.down(i)).getBlock().hashCode();

			if (i==0){ 
				if (size>1 && down1Hash == airHash) {
					blocksToSet.put(pos.down(i), block.getBlockState(SpType.stalactite_base));
				}
				else {
					blocksToSet.put( pos.down(i), block.getBlockState(SpType.stalactite_small));
					break;
				}
			}
			else if (i<size-1 && down1Hash == airHash){
				blocksToSet.put(pos.down(i), block.getBlockState(SpType.column));
			}
			else{
				if (down1Hash == block.parentBackground.hashCode()){
					blocksToSet.put(pos.down(i), block.getBlockState(SpType.stalagmite_base));
					break;
				}

				else{
					blocksToSet.put(pos.down(i), block.getBlockState(SpType.stalactite_tip));
					break;
				}
			}
		}
		return true;
	}


	/**
	 **Generates an icicle hanging from the ceiling
	 **/
	public  void genIcicle (BlockPos pos){
		if (getBlockState(pos.up()).isNormalCube()){
			if (random.nextBoolean() && getBlockState(pos.down()).hashCode() == airHash){
				blocksToSet.put(pos, WTFBlocks.icicle.getBlockState(IcicleType.icicle_base));
				blocksToSet.put(pos.down(),  WTFBlocks.icicle.getBlockState(IcicleType.icicle_tip));
			}
			else {
				blocksToSet.put(pos, WTFBlocks.icicle.getBlockState(IcicleType.icicle_small));
			}
		}
	}

	/**
	 * Used to determine if a block is surrounded- any placement of fluids should call this method to prevent flooding
	 */
	public static int airHash = Blocks.AIR.hashCode(); 

	/**
	 **Generates vines hanging from the ceiling
	 * @return 
	 **/

	@SuppressWarnings("incomplete-switch")
	public void GenVines(BlockPos pos, EnumFacing facing)
	{
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
		blocksToSet.put(pos, block);

	}

	public void genRoot(BlockPos pos){
		Biome biome = chunk.getBiome(pos, chunk.getWorld().getBiomeProvider());
		if (BiomeDictionary.isBiomeOfType(biome, Type.CONIFEROUS)){
			blocksToSet.put(pos, WTFBlocks.roots.getDefaultState().withProperty(BlockRoots.TYPE, RootType.spruce));
		}
		else if (BiomeDictionary.isBiomeOfType(biome, Type.SAVANNA)){
			blocksToSet.put(pos, WTFBlocks.roots.getDefaultState().withProperty(BlockRoots.TYPE, RootType.acacia));
		}
		else if (BiomeDictionary.isBiomeOfType(biome, Type.JUNGLE)){
			blocksToSet.put(pos, WTFBlocks.roots.getDefaultState().withProperty(BlockRoots.TYPE, RootType.jungle));
		}
		else if (BiomeDictionary.isBiomeOfType(biome, Type.SWAMP)){
			blocksToSet.put(pos, WTFBlocks.roots.getDefaultState().withProperty(BlockRoots.TYPE, RootType.big_oak));
		}
		else if (biome.getBiomeName().contains("irch")){
			blocksToSet.put(pos, WTFBlocks.roots.getDefaultState().withProperty(BlockRoots.TYPE, RootType.birch));
		}
		else {
			blocksToSet.put(pos, WTFBlocks.roots.getDefaultState());
		}
	}


	/**
	 **Checks if spawners are enabled, and then generates a mob spawner
	 **/
	public void spawnVanillaSpawner(BlockPos pos, String entityName, int count){

		chunk.getWorld().setBlockState(pos, Blocks.MOB_SPAWNER.getDefaultState());
		TileEntityMobSpawner spawner = (TileEntityMobSpawner)chunk.getWorld().getTileEntity(pos);
		if (spawner != null){
			spawner.getSpawnerBaseLogic().setEntityName(entityName);
			NBTTagCompound nbt = new NBTTagCompound();
			spawner.writeToNBT(nbt);
			nbt.setShort("spawnCount",(short)count);
			nbt.setShort("MinSpawnDelay",(short)(1000/count));
			spawner.readFromNBT(nbt);
		}
		else{
			System.out.println("VanillaGen: SpawnVanillaSpawner- failed to set spawner entity");
		}

	}

	public boolean isChunkEdge(BlockPos pos){
		int x = pos.getX() &15;
		int z = pos.getZ() & 15;
		return x==0 || x==15 || z == 0 || z == 15;
	}

}
