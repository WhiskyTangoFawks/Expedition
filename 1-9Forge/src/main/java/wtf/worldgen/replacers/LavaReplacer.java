package wtf.worldgen.replacers;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import wtf.api.Replacer;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.simplex.SimplexHelper;
import wtf.worldgen.caves.CaveBiomeGenMethods;
import wtf.worldgen.caves.CaveTypeRegister;
import wtf.worldgen.caves.types.CaveTypeHell.hellBiome;

public class LavaReplacer extends Replacer{

	public LavaReplacer(Block block) {
		super(block);
		// TODO Auto-generated constructor stub
	}

	private static SimplexHelper simplex = new SimplexHelper("LavaReplacer");
	
	@Override
	public  boolean isNonSolidAndReplacement(Chunk chunk, BlockPos pos, CaveBiomeGenMethods gen, IBlockState oldState) {
		//if (pos.getY() < 11){
			
			double noise = simplex.get3DNoise(chunk.getWorld(), pos);
	
			
			World world = chunk.getWorld();
			Biome biome = world.getBiomeForCoordsBody(pos);
			
			if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.OCEAN)){
				gen.replaceBlock(pos, Blocks.WATER.getDefaultState());
				return true;
			}
			else if (BiomeDictionary.isBiomeOfType(biome,BiomeDictionary.Type.SNOWY)){
		
				if (noise < 0.3){
					gen.replaceBlock(pos, Blocks.OBSIDIAN.getDefaultState());
				}
				else if (noise < 0.6) {
					gen.genFloatingStone(pos);
				}
				else if (noise < 0.9){
					gen.genFloatingStone(pos);
					gen.transformBlock(pos, Modifier.COBBLE);
				}
				if (simplex.get3DNoise(world, pos.getX()*3, pos.getY(), pos.getZ()*3) < 0.5){
					gen.transformBlock(pos, Modifier.LAVA_CRUST);
				}
				
			}
			else if (BiomeDictionary.isBiomeOfType(biome, Type.NETHER)){
				hellBiome netherbiome = CaveTypeRegister.nether.getSubType(pos);

				switch (netherbiome){
				case DEADFOREST:
					break;
				case FIREFOREST:
					break;
				case FROZEN:
					break;
				case MUSHROOM:
					break;
				case NORMAL:
					break;
				case SOULDESERT:
					
				default:
					break;
			
				}

			}
			return false;
			
		//}
		//return true;
	}

}
