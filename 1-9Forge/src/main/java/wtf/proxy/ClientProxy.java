package wtf.proxy;


import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import wtf.Core;
import wtf.crafting.render.WCICTESR;
import wtf.crafting.render.WCICTileEntity;
import wtf.entities.customentities.EntityBlockHead;
import wtf.entities.customentities.EntityDerangedGolem;
import wtf.entities.customentities.EntityFireElemental;
import wtf.entities.customentities.EntityFlyingFlame;
import wtf.entities.customentities.EntityZombieGhost;
import wtf.entities.customentities.renderers.RenderBlockHead;
import wtf.entities.customentities.renderers.RenderDerangedGolem;
import wtf.entities.customentities.renderers.RenderFlyingFlame;
import wtf.entities.customentities.renderers.RenderZombieGhost;
import wtf.utilities.blockstatewriters.BlockstateWriter;
import wtf.utilities.blockstatewriters.LangFileWriter;


public class ClientProxy extends CommonProxy {

		
	@Override
	public void registerEntityRenderers()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityZombieGhost.class, RenderZombieGhost::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityFlyingFlame.class, RenderFlyingFlame::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDerangedGolem.class, RenderDerangedGolem::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBlockHead.class, RenderBlockHead::new);
		
		RenderingRegistry.registerEntityRenderingHandler(EntityFireElemental.class, RenderZombieGhost::new);
		
		
	}
	
	
	@Override
	public void enableBlockstateTexturePack(){
		
		Core.coreLog.info("Attempting to parse resource packs");
		
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
			Core.coreLog.info("WARNING: Resource pack not found- something is wrong with the blockstate writer- likely it is unable to find the default files due to an advanced setup");
		}
		else{
			Core.coreLog.info("Resource pack found- attempting to enable "+ wtfResourcePack.getResourcePackName());
		
		}
		// Choose which one you want and remove the other lines.
		
		// 2 Lines below enable resource pack, but it stays visible in RPs GUI and can be disabled by user.
		
		List<ResourcePackRepository.Entry> newlist = new ArrayList<ResourcePackRepository.Entry>();
		
		newlist.addAll(rpEnabled);
		
		if (!newlist.contains(wtfResourcePack)){
			Core.coreLog.info("Crash Warning: WTF Expedition has enabled the blockstate resource pack.  If this is the first time the game has been run to generate the options.txt file, it will crash.  This crash can be safely ignored afterwards, just restart the game and it should be fine.  To stop seeing this warning, manually enable the WTFExpedition resource pack");
			newlist.add(wtfResourcePack);
			repo.setRepositories(newlist);
			
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


	@Override
	public void writeResourcePack(){
		BlockstateWriter.writeResourcePack();
	}

	@Override
	public void writeSpeleothemBlockstate(IBlockState backState, String regName){
		BlockstateWriter.writeSpeleothemBlockstate(backState, regName);
	}
	
	@Override
	public void writeDenseOreBlockstate(IBlockState backState, String regName, String orestring, String stoneString){
		BlockstateWriter.writeDenseOreBlockstate(backState, regName, orestring, stoneString);
	}
	
	@Override
	public void writeDecoAnimBlockstate(IBlockState backState, String regName){
		BlockstateWriter.writeDecoAnimBlockstate(backState, regName);
	}
	
	@Override
	public void writeDecoStaticBlockstate(IBlockState backState, String regName){
		BlockstateWriter.writeDecoStaticBlockstate(backState, regName);
	}
	
	@Override
	public void loadLangFile(){
		LangFileWriter.loadLangFile();
	}
	
	@Override
	public void addName(String regName, String localName){
		LangFileWriter.addName(regName, localName);
	}
	
	@Override
	public void finishLangFile(){
		LangFileWriter.finishLangFile();
	}

}
