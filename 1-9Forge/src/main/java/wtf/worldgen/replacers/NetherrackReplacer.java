package wtf.worldgen.replacers;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import wtf.api.Replacer;
import wtf.init.BlockSets.Modifier;
import wtf.utilities.simplex.SimplexHelper;
import wtf.worldgen.caves.CaveBiomeGenMethods;
import wtf.worldgen.caves.CaveTypeRegister;
import wtf.worldgen.caves.types.CaveTypeHell.hellBiome;

public class NetherrackReplacer extends Replacer{

	public NetherrackReplacer() {
		super(Blocks.NETHERRACK);
		
	}

	private static SimplexHelper simplex = new SimplexHelper("NetherrackReplacer");
	Random random = new Random();
	
	@Override
	public  boolean isNonSolidAndReplacement(Chunk chunk, BlockPos pos, CaveBiomeGenMethods gen,IBlockState oldState) {
		

		
		hellBiome biome = CaveTypeRegister.nether.getSubType(random, pos);

		switch (biome){
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
			double noise = desertNoise(chunk.getWorld(), pos); 
			if (noise < 0.3){
				gen.replaceBlock(pos, Blocks.SANDSTONE.getDefaultState());
				if (random.nextBoolean()){
					gen.transformBlock(pos, Modifier.SOUL);
				}
			}
			else if (noise < 0.6){	
				gen.replaceBlock(pos, Blocks.RED_SANDSTONE.getDefaultState());
				if (random.nextBoolean()){
					gen.transformBlock(pos, Modifier.SOUL);
				}
			}
			
		default:
			break;
	
		}

		return false;
	}

	private double desertNoise(World world, BlockPos pos){
		double x = ((double)pos.getX())/15;
		double y = ((double)pos.getY())/3;
		double z = ((double)pos.getZ())/15;
		//System.out.println(" simplex " + x + " " + y + " " +z);
		return simplex.get3DNoise(world, x, y, z); 
	}
	
	
}
