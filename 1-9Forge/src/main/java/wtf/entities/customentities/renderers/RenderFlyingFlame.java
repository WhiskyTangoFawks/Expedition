package wtf.entities.customentities.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBat;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import wtf.entities.customentities.EntityFlyingFlame;

public class RenderFlyingFlame extends RenderLiving<EntityFlyingFlame>
{
    private static final ResourceLocation BAT_TEXTURES = new ResourceLocation("wtfcore:textures/entity/transparent.png");

    
    
    public RenderFlyingFlame(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelBlaze(), 0.25F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
	protected ResourceLocation getEntityTexture(EntityFlyingFlame entity)
    {
        return BAT_TEXTURES;
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    @Override
	protected void preRenderCallback(EntityFlyingFlame entitylivingbaseIn, float partialTickTime)
    {
        GlStateManager.scale(0.35F, 0.35F, 0.35F);
    }

    @Override
	protected void rotateCorpse(EntityFlyingFlame entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks)
    {
      
            GlStateManager.translate(0.0F, MathHelper.cos(p_77043_2_ * 0.3F) * 0.1F, 0.0F);
      

        super.rotateCorpse(entityLiving, p_77043_2_, p_77043_3_, partialTicks);
    }
    
    @Override
	public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks)
    {
        if (this.renderManager.options != null)
        {
            
            if (entityIn.canRenderOnFire() && (!(entityIn instanceof EntityPlayer) || !((EntityPlayer)entityIn).isSpectator()))
            {
                this.renderEntityOnFire((EntityFlyingFlame)entityIn, x, y, z, partialTicks);
            }
        }
    }

    
    private void renderEntityOnFire(EntityFlyingFlame entity, double x, double y, double z, float partialTicks)
    {
        GlStateManager.disableLighting();
        TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
        TextureAtlasSprite textureatlassprite = texturemap.getAtlasSprite("minecraft:blocks/fire_layer_0");
        TextureAtlasSprite textureatlassprite1 = texturemap.getAtlasSprite("minecraft:blocks/fire_layer_1");
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        float f = entity.width * entity.getFlameSize();
        GlStateManager.scale(f, f, f);
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        float f1 = 0.5F;
        float f2 = 0.0F;
        float f3 = entity.height / f;
        float f4 = (float)(entity.posY - entity.getEntityBoundingBox().minY);
        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.0F, -0.3F + ((int)f3) * 0.02F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        float f5 = 0.0F;
        int i = 0;
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);

       // while (f3 > 0.0F)
        //{
            TextureAtlasSprite textureatlassprite2 = i % 2 == 0 ? textureatlassprite : textureatlassprite1;
            this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            float f6 = textureatlassprite2.getMinU();
            float f7 = textureatlassprite2.getMinV();
            float f8 = textureatlassprite2.getMaxU();
            float f9 = textureatlassprite2.getMaxV();

            if (i / 2 % 2 == 0)
            {
                float f10 = f8;
                f8 = f6;
                f6 = f10;
            }

            vertexbuffer.pos(f1 - 0.0F, 0.0F - f4, f5).tex(f8, f9).endVertex();
            vertexbuffer.pos(-f1 - 0.0F, 0.0F - f4, f5).tex(f6, f9).endVertex();
            vertexbuffer.pos(-f1 - 0.0F, 1.4F - f4, f5).tex(f6, f7).endVertex();
            vertexbuffer.pos(f1 - 0.0F, 1.4F - f4, f5).tex(f8, f7).endVertex();
            f3 -= 0.45F;
            f4 -= 0.45F;
            f1 *= 0.9F;
            f5 += 0.03F;
            ++i;
        //}

        tessellator.draw();
        GlStateManager.popMatrix();
        GlStateManager.enableLighting();
    }


}