package wtf.ores.models;

import java.util.Collection;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IRetexturableModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import wtf.Core;
/*
public class OreModel implements IModel{

	public static final String MODEL_NAME = "wtf_ore";
	public static final String MODEL_PATH = Core.coreID+":block/"+MODEL_NAME;
	
	private final ResourceLocation stoneTexture;
	private final ResourceLocation oreTexture;
	private final IModel baseModel;
	
	public OreModel(WTFModelResourceLocation location){
		IModel baseModel = null;
		try {
			baseModel = ModelLoaderRegistry.getModel(new ResourceLocation(MODEL_PATH));
		}
		catch (Exception e){
			e.printStackTrace();
		}
		this.baseModel = baseModel;
		stoneTexture = new ResourceLocation(Core.coreID+":blocks/"+location.getVariant());
		oreTexture = //need a centralised registry for ore textures
	}
	
	//How this works- looks like the magic happens in IBakedModel
	//just makes a new version of the base model (which is hard coded)
	//Now, the question is: where do blockstates come into this?
	//There aren't any hard coded block states for ores
	//So- maybe it happens in the statemapper?
	
	@Override
	public Collection<ResourceLocation> getDependencies() {
		return ImmutableSet.of();
	}

	@Override
	public Collection<ResourceLocation> getTextures() {
		return ImmutableSet.of();
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format,
			Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		IRetexturableModel model = (IRetexturableModel) baseModel;
		IModel finalModel = model.retexture(ImmutableMap.of("stone", stoneTexture.toString(), "ore", oreTexture.toString()));
		return finalModel.bake(state, format, bakedTextureGetter);
	}

	@Override
	public IModelState getDefaultState() {
		return ModelRotation.X0_Y0;
	}

}
*/
