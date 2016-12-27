package wtf.proxy;


import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import wtf.Core;
import wtf.crafting.render.WCICTESR;
import wtf.crafting.render.WCICTileEntity;
import wtf.init.WTFBlocks;


public class ClientProxy extends CommonProxy {

		
	@Override
	public void enableBlockstateTexturePack(){
		ResourcePackRepository repo = Minecraft.getMinecraft().getResourcePackRepository();
		List<ResourcePackRepository.Entry> rpEnabled = repo.getRepositoryEntries();
		ResourcePackRepository.Entry wtfResourcePack = null;
		for(ResourcePackRepository.Entry rp : repo.getRepositoryEntriesAll()){
			if(rp.getResourcePackName().equals("WTFOres")){
				wtfResourcePack = rp;
				break;
			}
		}
		// Choose which one you want and remove the other lines.
		
		// 2 Lines below enable resource pack, but it stays visible in RPs GUI and can be disabled by user.
		//rpEnabled.add(wtfResourcePack);
		//repo.setRepositories(rpEnabled);

		// A line below enables resource packs and hides it from RPs GUI making user unable to disable it.
		//ReflectionHelper.<List<IResourcePack>, Minecraft>getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "defaultResourcePacks", "field_110449_ao").add(wtfResourcePack.getResourcePack());
	}
	
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
