package wtf.proxy;


import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import wtf.Core;
import wtf.crafting.render.WCICTESR;
import wtf.crafting.render.WCICTileEntity;
import wtf.init.WTFBlocks;


public class ClientProxy extends CommonProxy {

		
	@Override
	public void registerItemSubblocksRenderer(Block block, int meta){
		for (int loop = 0; loop < meta+1; loop++){
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), loop, new ModelResourceLocation(block.getRegistryName().toString(), "inventory"+loop));	
		}	
	}
	@Override
	public void registerItemRenderer(Block block){
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName().toString(), "inventory"));	
	}
	
	@Override
	public void registerItemRenderer(Item item){
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));
	}

	@Override
	public void initWCICRender(){
		 ClientRegistry.bindTileEntitySpecialRenderer(WCICTileEntity.class, new WCICTESR());
		 
	}




}
