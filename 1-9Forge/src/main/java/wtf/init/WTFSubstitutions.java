package wtf.init;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraftforge.fml.common.registry.ExistingSubstitutionException;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wtf.blocks.substitution.BlockWTFTorch;
import wtf.blocks.substitution.CustomNewLeaves;
import wtf.blocks.substitution.CustomOldLeaves;
import wtf.config.CoreConfig;
import wtf.config.GameplayConfig;
import wtf.config.OverworldGenConfig;

public class WTFSubstitutions {
	
	public static void init(){
		
		BlockWTFTorch.torch_on = WTFBlocks.registerBlock(new BlockWTFTorch(true), "torch_on");
		
		if (CoreConfig.gameplaytweaks && GameplayConfig.replaceTorch){
			
			

			System.out.println("Attempting torch replacement");
			try {
				GameRegistry.addSubstitutionAlias("minecraft:torch", GameRegistry.Type.BLOCK, BlockWTFTorch.torch_off);

			} catch (ExistingSubstitutionException e) {
				e.printStackTrace();
			}

			BlockWTFTorch.torch_off.setRegistryName("torch");
			BlockWTFTorch.torch_off.setUnlocalizedName("torch");
		}
		
		/*
		if (OverworldGenConfig.subLeaves){
			
			Block oldLeaves = new CustomOldLeaves().setUnlocalizedName("leaves");		
			try {
				GameRegistry.addSubstitutionAlias("minecraft:leaves", GameRegistry.Type.BLOCK, oldLeaves);

			} catch (ExistingSubstitutionException e) {
				e.printStackTrace();
			}
			oldLeaves.setRegistryName("minecraft:leaves");


			Block newLeaves = new CustomNewLeaves().setUnlocalizedName("leaves2");		
			try {
				GameRegistry.addSubstitutionAlias("minecraft:leaves2", GameRegistry.Type.BLOCK, newLeaves);

			} catch (ExistingSubstitutionException e) {
				e.printStackTrace();
			}
			newLeaves.setRegistryName("minecraft:leaves2");

			
		}
		*/
	}
	
}
