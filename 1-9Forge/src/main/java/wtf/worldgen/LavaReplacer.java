package wtf.worldgen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.BiomeDictionary;
import wtf.api.Replacer;
import wtf.blocks.AnimatedBlock;
import wtf.core.utilities.GenMethods;
import wtf.core.utilities.Simplex;
import wtf.init.WTFBlocks;

public class LavaReplacer extends Replacer{

	public LavaReplacer(Block block) {
		super(block);
		// TODO Auto-generated constructor stub
	}

	private static Simplex simplex = new Simplex(5000);
	
	@Override
	public  boolean isNonSolidAndReplacement(Chunk chunk, BlockPos pos, Block oldBlock) {
		if (pos.getY() < 11){
			
			double x = pos.getX();
			double y = pos.getY();
			double z = pos.getZ();
			//A noise generated from 0 to 10
			double noise = simplex.noise(x/25, y, z/25)*5 +5;
	
			
			World world = chunk.getWorld();
			Biome biome = world.getBiomeForCoordsBody(pos);
			
			if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.OCEAN)){
				GenMethods.setBlockState(world, pos, Blocks.WATER.getDefaultState());
			}
			else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.SNOWY)){
		
				if (noise <4){
					GenMethods.setBlockState(world, pos, Blocks.OBSIDIAN.getDefaultState());
				}
				else {
					GenMethods.setBlockState(world, pos, Blocks.COBBLESTONE.getDefaultState());
				}
				
			}
			else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.SANDY)){
				
				if (noise < 4){
					GenMethods.setBlockState(world, pos, Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND));
				}
				else if (noise < 7){
					GenMethods.setBlockState(world, pos, WTFBlocks.decoStone.getDefaultState().withProperty(AnimatedBlock.TYPE, AnimatedBlock.ANIMTYPE.LAVA_CRUST));
				}
			}
	
			
			
			return false;
			
		}
		return true;
	}

}
