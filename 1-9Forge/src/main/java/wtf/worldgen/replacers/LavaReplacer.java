package wtf.worldgen.replacers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.BiomeDictionary;
import wtf.api.Replacer;
import wtf.init.BlockSets;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.simplex.SimplexHelper;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class LavaReplacer extends Replacer{

	public LavaReplacer(Block block) {
		super(block);
		// TODO Auto-generated constructor stub
	}

	private static SimplexHelper simplex = new SimplexHelper("LavaReplacer");
	
	@Override
	public  boolean isNonSolidAndReplacement(Chunk chunk, BlockPos pos, CaveBiomeGenMethods gen, IBlockState oldState) {
		if (pos.getY() < 11){
			
			double x = pos.getX();
			double y = pos.getY();
			double z = pos.getZ();
			//A noise generated from 0 to 10
			double noise = simplex.get3DNoise(chunk.getWorld(), x/25, y, z/25)*10;
	
			
			World world = chunk.getWorld();
			Biome biome = world.getBiomeForCoordsBody(pos);
			
			if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.OCEAN)){
				gen.replaceBlock(pos, Blocks.WATER.getDefaultState());
			}
			else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.SNOWY)){
		
				if (noise <4){
					gen.replaceBlock(pos, Blocks.OBSIDIAN.getDefaultState());
				}
				else {
					gen.replaceBlock(pos, Blocks.COBBLESTONE.getDefaultState());
				}
				
			}
			else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.SANDY)){
				
				if (noise < 4){
					gen.replaceBlock(pos, Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND));
				}
				else if (noise < 7){
					gen.replaceBlock(pos, BlockSets.getTransformedState(Blocks.STONE.getDefaultState(), Modifier.LAVA_CRUST));
				}
			}
	
			
			
			return false;
			
		}
		return true;
	}

}
