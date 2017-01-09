package wtf.proxy;


import java.util.ArrayList;
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
import wtf.crafting.render.WCICTESR;
import wtf.crafting.render.WCICTileEntity;


public class ClientProxy extends CommonProxy {

		
	@Override
	public void enableBlockstateTexturePack(){
		
		System.out.println("Attempting to parse resource packs");
		
		ResourcePackRepository repo = Minecraft.getMinecraft().getResourcePackRepository();
		List<ResourcePackRepository.Entry> rpEnabled = repo.getRepositoryEntries();
		ResourcePackRepository.Entry wtfResourcePack = null;
		
		for(ResourcePackRepository.Entry rp : repo.getRepositoryEntriesAll()){
			if(rp.getResourcePackName().equals("WTFExpedition")){
				wtfResourcePack = rp;
				break;
			}
		}
		
		if (wtfResourcePack==null){
			System.out.println("Resource pack not found- please enable manually");
		}
		else{
			System.out.println("Resource pack found- attempting to enable "+ wtfResourcePack.getResourcePackName());
		
		}
		// Choose which one you want and remove the other lines.
		
		// 2 Lines below enable resource pack, but it stays visible in RPs GUI and can be disabled by user.
		
		List<ResourcePackRepository.Entry> newlist = new ArrayList<ResourcePackRepository.Entry>();
		
		newlist.addAll(rpEnabled);
		
		if (!newlist.contains(wtfResourcePack)){
			newlist.add(wtfResourcePack);
			repo.setRepositories(newlist);
			System.out.println("Crash Warning: WTF Expedition has enabled the blockstate resource pack.  If this is the first time the game has been run to generate the options.txt file, it will crash.  This crash can be safely ignored afterwards, just restart the game and it should be fine.  To stop seeing this warning, manually enable the WTFExpedition resource pack");
		}

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
